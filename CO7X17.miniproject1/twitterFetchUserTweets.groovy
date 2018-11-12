#!/usr/bin/env groovy

def TWEET_COUNT
if (args.size() == 0)
	TWEET_COUNT = 10
else 
	TWEET_COUNT = args[0]


def idSet = [] as Set<String>

def friends = new groovy.json.JsonSlurper().parseText(new File("./friends.json").text)
friends.users.each { friend ->
    idSet << friend.id 
}

def followers = new groovy.json.JsonSlurper().parseText(new File("./followers.json").text)
followers.users.each { friend ->
	idSet << friend.id 
}


def result = []
def slurper = new groovy.json.JsonSlurper()

idSet.each{
	def tweets = "./twitterFetchTweets.sh $it $TWEET_COUNT".execute().text

	result += slurper.parseText(tweets)
}
println groovy.json.JsonOutput.toJson(result)
//output = 'twurl -H "https://api.twitter.com" "/1.1/followers/list.json" > followers.json'.execute().text
//friends.each { friend->
//	println "${friend.name}: ${tweet.description}"
//}


