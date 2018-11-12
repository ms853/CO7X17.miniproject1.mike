#!/usr/bin/env bash
#
# REQUIRES
#   Bash terminal for Linux/MacOS/Windows10
#   		on Windows 10: https://msdn.microsoft.com/en-gb/commandline/wsl/about
#	twurl: https://github.com/twitter/twurl
# 	jq: https://stedolan.github.io/jq/download/
#		* jq is optional: if you don't want to install it, remove '| jq .' from the commands below
#   Configuration of connection to mLab in miniproject1.mongoDb.MongoDB
#
# PARAMETERS
#  	fetch bound -  number of records to be retrieved in each call (max is 200)
#
# EFFECTS
#   The script will show a URL which should be pasted on to a browser 
#	and the PIN obtained should be pasted on the terminal
#
#	Once authorization is successful, friends and follower's tweets 
#	will be filtered and copied onto your MongoDB database on mLab 
# 	
#
[ -e "./friends.json" ] && rm "./friends.json"
[ -e "./followers.json" ] && rm "./followers.json"
[ -e "./tweets.json" ] && rm "./tweets.json"

twurl authorize --consumer-key w3J1fPGElVJBQ9bDiAlxYJKX1 --consumer-secret N6idqgZxFrdrIwyQwWau4hO8WINGEq4k98VlNwk1MFlurEmxk5
twurl -H "https://api.twitter.com" "/1.1/friends/list.json?count=$1" | jq . > friends.json
twurl -H "https://api.twitter.com" "/1.1/followers/list.json?count=$1" | jq . > followers.json
./twitterFetchUserTweets.groovy $1 | jq . > tweets.json
./gradlew migrateToMongoDb