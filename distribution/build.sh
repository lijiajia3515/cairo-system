docker build -t registry.cn-beijing.aliyuncs.com/hfhksoft/cairo-system-service:snapshot -f system-service/Dockerfile ./system-service

docker build -t registry.cn-beijing.aliyuncs.com/hfhksoft/cairo-system-service:snapshot .
docker push registry.cn-beijing.aliyuncs.com/hfhksoft/cairo-system-service:snapshot
