FROM nginx:stable

MAINTAINER lijiajia

RUN sed -i 's/deb.debian.org/mirrors.aliyun.com/g' /etc/apt/sources.list; \
    sed -i 's/security.debian.org/mirrors.aliyun.com/g' /etc/apt/sources.list; \
    apt clean -y; \
    apt update -y; \
    apt upgrade -y; \
    apt install curl wget jq iputils-ping inetutils-ping -y

ENV TZ Asia/Shanghai
ENV LANG C.UTF-8
ENV LANGUAGE C.UTF-8
ENV LC_ALL C.UTF-8
