package com.lopply.music_service.grpc;

import com.lopply.music.MusicServiceGrpc;
import com.lopply.music.Musicservice;
import com.lopply.music_service.service.MusicServiceImpl;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
public class MusicGrpcService extends MusicServiceGrpc.MusicServiceImplBase {

    private final MusicServiceImpl musicService;
    private Logger logger = LoggerFactory.getLogger(MusicGrpcService.class);


    public MusicGrpcService(MusicServiceImpl musicService) {
        this.musicService = musicService;
    }

    @Override
    public void getMusicByUId(Musicservice.MusicUIdRequest request, StreamObserver<Musicservice.MusicById> responseObserver) {
        logger.info("Incoming get music request {}", request);

        musicService.getByUIdMusic(request.getUid())
                .map(music -> Musicservice.MusicById.newBuilder().setId(music.getId()).build())
                .subscribe(resp -> {
                            responseObserver.onNext(resp);
                            responseObserver.onCompleted();
                        }, e -> {
                            logger.warn(e.getMessage());
                            responseObserver.onError(e);
                        }
                );
    }
}
