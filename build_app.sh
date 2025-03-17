#!/usr/bin/env bash

cd backend-app || exit
mvn clean package -DskipTests