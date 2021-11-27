#!/usr/bin/env bash
MYSQL_ROOT_PASS=password

docker run \
    --name mysql \
     -e MYSQL_ROOT_PASSWORD=$MYSQL_ROOT_PASS \
     --rm -d \
     -p 3306:3306 \
     -v $(pwd)/sql/keretrendszer_beadando.sql:/docker-entrypoint-initdb.d/keretrendszer_beadando.sql \
     mysql:8
