#!/usr/bin/env bash
WORKDIR=$PWD
cd $WORKDIR/project-mysql/ && sh start.sh
docker ps |grep project
