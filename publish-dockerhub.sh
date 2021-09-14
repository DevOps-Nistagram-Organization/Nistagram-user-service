#!/bin/bash

VERSION=${1}
DOCKERHUB_USERNAME=${2}
DOCKERHUB_PASSWORD=${3}


USER=${DOCKERHUB_USERNAME}/nistagram-user-service:${VERSION}

DOCKER_BUILDKIT=1 docker build -t ${USER} --no-cache .

docker login --username ${DOCKERHUB_USERNAME} --password ${DOCKERHUB_PASSWORD}

docker push ${USER}