#!/usr/bin/env bash

twurl "/1.1/statuses/user_timeline.json?user_id=$1&count=$2" | jq . 