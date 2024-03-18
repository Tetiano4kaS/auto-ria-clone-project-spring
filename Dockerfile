FROM openjdk:17

MAINTAINER Tetiana Stetsko

RUN mkdir /app
WORKDIR /app

COPY wait-for-it.sh /

RUN chmod +x /wait-for-it.sh