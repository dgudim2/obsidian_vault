#!/bin/bash

export WEBOTS_HOME=/usr/local/webots
export WEBOTS_HOME_PATH=/usr/local/webots

make && rm bumper && mv ./build/release/bumper . && rm -r build
