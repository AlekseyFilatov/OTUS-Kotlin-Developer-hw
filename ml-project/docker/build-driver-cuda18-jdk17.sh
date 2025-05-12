#!/bin/bash
export DOCKER_REPO="$1"
echo "Docker repo: $DOCKER_REPO"

mvn clean install -DskipTests=true

docker stop ML
docker rm ML
docker build -f Dockerfile-driver-jdk17 --build-arg JAR_FILE=target/ml-service.jar -t ml:jdk17 .

docker run --memory="4g" --cpus="4" --gpus all \
        -p 9090:9090 \
        -p 1098:1098 \
        -p 5005:5005 \
        -p 4040:4040 \
        -p 33139-33155:33139-33155 \
        -p 45029-45045:45029-45045 \
        -e SPARK_EXECUTORS=1 -e SPARK_MASTER=spark://192.168.145.128:7077 \
        -e CASSANDRA_HOST=cassandra \
        -e POSTGRES_HOST=postgres \
        -e POSTGRES_USER=postgres \
        -e POSTGRES_PASS=postgres \
        --name=ML \
        -it -d ml:jdk17

