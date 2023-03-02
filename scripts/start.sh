#!/usr/bin/env bash
docker-compose -p qwerty up -d --build
docker ps |grep qwerty
