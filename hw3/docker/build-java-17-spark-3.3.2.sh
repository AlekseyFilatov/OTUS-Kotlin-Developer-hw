#!/bin/bash
echo "=========== 1st stage ==========="
echo "Docker repo: $DOCKER_REPO"
echo "### build base cuda12 java17 image ###"
#docker build -f Dockerfile-cuda-java17 -t cuda-jdk17:v1 .

echo "=========== 2nd stage ==========="
echo "### build Spark image ###"
cd spark-3.3.2

./bin/docker-image-tool.sh -t jdk17-3.3.2 -b java_image_tag=cuda-jdk17:v1 -p ./kubernetes/dockerfiles/spark/Dockerfile -n build

cd ..

docker tag spark:jdk17-3.3.2 $DOCKER_REPO/cuda-jdk17-spark-3.3.2:v1

echo "=========== 3rt stage ==========="
echo "### push Spark image ###"
docker push $DOCKER_REPO/cuda-jdk17-spark-3.3.2:v1

