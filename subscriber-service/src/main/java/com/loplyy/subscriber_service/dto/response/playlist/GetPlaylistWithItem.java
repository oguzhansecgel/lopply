package com.loplyy.subscriber_service.dto.response.playlist;

import com.loplyy.subscriber_service.dto.response.playlistItem.GetPlaylistItem;
import org.springframework.data.relational.core.mapping.Column;

import java.util.List;
import java.util.UUID;

public class GetPlaylistWithItem {
    private long id;
    private UUID uuid;
    private long subscriberId;
    private boolean isPublic;
    private List<GetPlaylistItem> getPlaylistItemList;

    public GetPlaylistWithItem() {
    }

    public GetPlaylistWithItem(long id, UUID uuid, long subscriberId, boolean isPublic, List<GetPlaylistItem> getPlaylistItemList) {
        this.id = id;
        this.uuid = uuid;
        this.subscriberId = subscriberId;
        this.isPublic = isPublic;
        this.getPlaylistItemList = getPlaylistItemList;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public long getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(long subscriberId) {
        this.subscriberId = subscriberId;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public List<GetPlaylistItem> getGetPlaylistItemList() {
        return getPlaylistItemList;
    }

    public void setGetPlaylistItemList(List<GetPlaylistItem> getPlaylistItemList) {
        this.getPlaylistItemList = getPlaylistItemList;
    }
}
