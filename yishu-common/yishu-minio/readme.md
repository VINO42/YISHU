# minio服务端docker启动方式
```aspectj
docker run  -p 9002:9000  -p 9003:9001  -e "MINIO_ACCESS_KEY=admin"  -e "MINIO_SECRET_KEY=admin123456"  -v /home/data:/data  minio/minio server /data --console-address ":9001" &
```