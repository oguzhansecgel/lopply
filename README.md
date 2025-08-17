# lopply

#### Grpc service
```bash
grpcurl -plaintext -d '{
"uid": "500b3640-9786-4d93-9f1d-e10e1e9463e3"
}' localhost:1231 com.lopply.subscriber.SubscriberService/GetSubscriberIdByUId
```

```bash
grpcurl -plaintext -d '{
"uid": "7d3035c4-9e51-4a86-9f3d-4c08e267febc"
}' localhost:1232 com.lopply.music.MusicService/GetMusicByUId
```

## Endpoint 
