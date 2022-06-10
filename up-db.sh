#! /bin/bash

docker run -d --rm \
     -p 5432:5432 \
     -e POSTGRES_USER=dbuser \
     -e POSTGRES_PASSWORD=theSecretPassword \
     -e POSTGRES_DB=todo \
     postgres:12-alpine