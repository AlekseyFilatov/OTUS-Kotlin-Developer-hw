#!/bin/bash

export DOCKER_REPO="$1"

# build cuda and spark images
./build-java-17-spark-3.3.2.sh
#./build-java-17-spark-3.3.2-local-docker.sh

# build driver-image
./build-driver-cuda18-jdk17.sh