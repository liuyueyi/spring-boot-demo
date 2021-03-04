#!/bin/bash

mvn clean package docker:build -DskipTests=true
