package com.lopply.music_service.service;

import com.lopply.music.MusicServiceGrpc;
import com.lopply.music.Musicservice;
import com.lopply.music_service.dto.request.music.CreateMusicRequest;
import com.lopply.music_service.dto.response.music.GetAllArtistMusic;
import com.lopply.music_service.dto.response.music.GetByUIdMusic;
import com.lopply.music_service.dto.response.music.GetMusicWithArtist;
import com.lopply.music_service.entity.Album;
import com.lopply.music_service.entity.Artist;
import com.lopply.music_service.entity.Genre;
import com.lopply.music_service.entity.Music;
import com.lopply.music_service.repository.AlbumRepository;
import com.lopply.music_service.repository.ArtistRepository;
import com.lopply.music_service.repository.GenreRepository;
import com.lopply.music_service.repository.MusicRepository;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class MusicServiceImpl {

    private final MusicRepository musicRepository;
    private final AlbumRepository albumRepository;
    private final GenreRepository genreRepository;
    private final ArtistRepository artistRepository;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public MusicServiceImpl(ArtistRepository artistRepository, GenreRepository genreRepository, AlbumRepository albumRepository, MusicRepository musicRepository) {
        this.artistRepository = artistRepository;
        this.genreRepository = genreRepository;
        this.albumRepository = albumRepository;
        this.musicRepository = musicRepository;
    }

    // asenkron sorgu örneği
    public Mono<Void> createMusic(CreateMusicRequest request) {
        logger.info("Incoming create music request {}", request);

        return albumRepository.findByUuid(request.getAlbumUId())
                .switchIfEmpty(Mono.error(new StatusRuntimeException(Status.NOT_FOUND.withDescription("Album Not Found"))))
                .flatMap(album -> {
                        return genreRepository.findByUuid(request.getGenreUId())
                        .switchIfEmpty(Mono.error(new StatusRuntimeException(Status.NOT_FOUND.withDescription("Genre Not Found"))))
                        .flatMap(genre -> {
                            return artistRepository.findByUuid(request.getArtistUId())
                                    .switchIfEmpty(Mono.error(new StatusRuntimeException(Status.NOT_FOUND.withDescription("Artist Not Found"))))
                                    .flatMap(artist -> {
                                        Music music = new Music();
                                        music.setAlbumId(album.getId());
                                        music.setGenreId(genre.getId());
                                        music.setArtistId(artist.getId());
                                        music.setTitle(request.getTitle());
                                        music.setDuration(request.getDuration());
                                        music.setLanguage(request.getLanguage());
                                        return musicRepository.save(music).then();
                                    });
                        });

                });
    }

    // paralel sorgu örneği
    public Mono<Void> createMusicWithZip(CreateMusicRequest request) {
        logger.info("Incoming create music request {}", request);

        Mono<Album> albumId = albumRepository.findByUuid(request.getAlbumUId())
                .switchIfEmpty(Mono.error(new StatusRuntimeException(Status.NOT_FOUND.withDescription("Album Not Found"))));

        Mono<Genre> genreId = genreRepository.findByUuid(request.getGenreUId())
                .switchIfEmpty(Mono.error(new StatusRuntimeException(Status.NOT_FOUND.withDescription("Genre Not Found"))));

        Mono<Artist> artistId = artistRepository.findByUuid(request.getArtistUId())
                .switchIfEmpty(Mono.error(new StatusRuntimeException(Status.NOT_FOUND.withDescription("Artist Not Found"))));

        return Mono.zip(albumId, genreId, artistId)
                .map(t -> {
                    Album album = t.getT1();
                    Genre genre = t.getT2();
                    Artist artist = t.getT3();

                    Music music = new Music();
                    music.setUuid(UUID.randomUUID());
                    music.setAlbumId(album.getId());
                    music.setGenreId(genre.getId());
                    music.setArtistId(artist.getId());
                    music.setTitle(request.getTitle());
                    music.setDuration(request.getDuration());
                    return music;
                })
                .flatMap(musicRepository::save)
                .then();
    }

    public Mono<GetByUIdMusic> getByUIdMusic(String uuid) {
        return musicRepository.findByUuid(UUID.fromString(uuid))
                .switchIfEmpty(Mono.error(new StatusRuntimeException(Status.NOT_FOUND.withDescription("Music Not Found"))))
                .map(music ->
                        new GetByUIdMusic(
                                music.getId(),
                                music.getUuid(),
                                music.getTitle(),
                                music.getAlbumId(),
                                music.getArtistId(),
                                music.getGenreId(),
                                music.getDuration(),
                                music.getLanguage()
                        ));
    }

    public Mono<GetMusicWithArtist> getMusicWithArtist(String musicUId){
        return musicRepository.findByUuid(UUID.fromString(musicUId))
                .flatMap(music ->
                        Mono.zip(
                                artistRepository.findById(music.getArtistId()),
                                musicRepository.findByMusicWithArtist(music.getId())
                        ).map(tuple -> {
                            var artist = tuple.getT1();
                            var music1 = tuple.getT2();

                            return new GetMusicWithArtist(
                                    artist.getUuid().toString(),
                                    artist.getName(),
                                    artist.getBio(),
                                    artist.getCountry(),
                                    music1.getId(),
                                    music1.getUuid().toString(),
                                    music1.getTitle(),
                                    music1.getGenreId(),
                                    music1.getDuration(),
                                    music1.getLanguage()
                            );
                        })
                );
    }

    public Flux<GetAllArtistMusic> getAllArtistMusic(String artistUId) {
        return artistRepository.findByUuid(artistUId)
                .flatMapMany(artist ->
                        musicRepository.findAllArtistMusic(artist.getId())
                                .map(music ->
                                        new GetAllArtistMusic(
                                                artist.getUuid().toString(),
                                                artist.getName(),
                                                music.getId(),
                                                music.getUuid().toString(),
                                                music.getTitle(),
                                                music.getGenreId(),
                                                music.getLanguage()
                                        )));
    }
}
