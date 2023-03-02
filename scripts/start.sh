#!/usr/bin/env bash
docker-compose -p project up -d --build
docker ps |grep project
