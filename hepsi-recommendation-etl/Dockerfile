FROM alpine:3.12.0
RUN ["apk", "add", "--no-cache", "openjdk11"]

# Add dockerize tool -------------------
RUN apk add --no-cache openssl
ENV DOCKERIZE_VERSION v0.6.1
RUN wget https://github.com/jwilder/dockerize/releases/download/$DOCKERIZE_VERSION/dockerize-alpine-linux-amd64-$DOCKERIZE_VERSION.tar.gz \
    && tar -C /usr/local/bin -xzvf dockerize-alpine-linux-amd64-$DOCKERIZE_VERSION.tar.gz \
    && rm dockerize-alpine-linux-amd64-$DOCKERIZE_VERSION.tar.gz

ARG SOURCEDIR=./target
ARG JARFILENAME=hepsi-recommendation-etl-0.0.1-SNAPSHOT.jar
ARG TARGETDIR=/usr/app/
COPY $SOURCEDIR/$JARFILENAME $TARGETDIR
WORKDIR /usr/app
ENV JAR=$JARFILENAME
ENTRYPOINT ["sh", "-c", "java -Djava.security.egd=file:/dev/./urandom -jar $JAR"]
