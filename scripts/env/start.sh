#!/usr/bin/env bash
WORKDIR=$PWD
cd $WORKDIR/project-adminer/ && sh start.sh
cd $WORKDIR/project-mysql/ && sh start.sh
cd $WORKDIR/project-minio/ && sh start.sh
cd $WORKDIR/project-redis/ && sh start.sh
docker ps |grep project
