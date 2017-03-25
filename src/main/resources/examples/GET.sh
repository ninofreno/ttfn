#!/bin/sh

curl -s http://localhost:10080/statistics | jq -S .

