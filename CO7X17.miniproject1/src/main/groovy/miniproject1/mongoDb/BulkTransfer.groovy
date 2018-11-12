package miniproject1.mongoDb

import com.mongodb.BasicDBObject
import com.mongodb.DBCollection
import com.mongodb.DBObject
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import miniproject1.reports.FriendDto

class BulkTransfer {
	static void main(String... args) {

		MongoDB mongo = new MongoDB()
		JsonSlurper slurper = new JsonSlurper()
		//using test data.
		def friends = slurper.parseText(new File('./src/main/resources/twitter/friends.json').text)
		def followers = slurper.parseText(new File('./src/main/resources/twitter/followers.json').text)
		def tweets = slurper.parseText(new File('./src/main/resources/twitter/tweets.json').text)
		
		mongo.initDb()
		
		// TODO Exercise 1
		
		//used to store all the tweets
		def tweetsList = []
		def temp_tweetList = []
		
		def tweetsSlurpper //object for parsing the json list of tweets.
		
		def db = mongo.db //mongodb instance 
		
		
		DBCollection coll = db.getCollection("friends")
		DBCollection coll1 = db.getCollection("followers")
		
		
		for(fr in friends.users) {
			//check the database connection to see if it works.
			if(mongo.connectionOk()) {
				//DBObject obj = new BasicDBObject();
				//insert the firends data into the database 
				coll.insert(["id_str": fr.id_str, "name": fr.name, 
					"description": fr.description, favorites_count: fr.favorite_count, followers_count: fr.followers_count,
					"friends_count": fr.friends_count, "location": fr.location, "screen_name": fr.screen_name, "url": fr.url, "created_at": fr.created_at
					])
				//iterate through the tweets and store the tweets that match those of the friends
				for(tweet in tweets) {
					if(!tweet.user.equals(null)) {
						//check if the twitter string id matches that of the friends  
						if(tweet.user.id_str == fr.id_str) {
							 temp_tweetList = JsonOutput.toJson([
								 id_str: tweet.id_str, created_at: tweet.created_at, entities: tweet.entities, favorite_count: tweet.favorite_count, 
								 favorited: tweet.favorited, user_id_str: tweet.user.id_str, in_reply_to_screen_name: tweet.in_reply_to_screen_name,
								 in_reply_to_status_id: tweet.in_reply_to_status_id, language_code: tweet.language_code, retweet_count: tweet.retweet_count,
								 retweeted: tweet.retweeted, source: tweet.source, text: tweet.text, lang: tweet.lang
								 ])
							  
							 tweetsList.add(temp_tweetList)
							 //parsing the tweetsList
							 tweetsSlurpper = slurper.parseText(tweetsList.toString())
							 coll.update([id_str: fr.id_str], [$set :[tweet: tweetsSlurpper]], true, true)
						}
					}
				} 
			}
			else 
				{
					println "Connection error, could not insert the data"
				}
			
		}
		
		//clear contents of the tweets list before adding tweets 
		tweetsList.clear()
		
		//check if the tweets List has been successfully cleared.
		println "Look at this tweetList $tweetsList"
		
		for(fl in followers.users) {
			//check the database connection to see if it is connected.
			if(mongo.connectionOk()) {
				coll1.insert(["id_str": fl.id_str, "name": fl.name,
					"description": fl.description, "favorites_count": fl.favorite_count, "followers_count": fl.followers_count,
					"friends_count": fl.friends_count, "location": fl.location, "screen_name": fl.screen_name, "url": fl.url, "created_at": fl.created_at
					])
				for(tw in tweets) {
					if(!tw.user.equals(null)) {
						if(tw.user.id_str == fl.id_str) {
							temp_tweetList = JsonOutput.toJson([
								id_str: tw.id_str, created_at: tw.created_at, entities: tw.entities, favorite_count: tw.favorite_count,
								favorited: tw.favorited, user_id_str: tw.user.id_str, in_reply_to_screen_name: tw.in_reply_to_screen_name,
								in_reply_to_status_id: tw.in_reply_to_status_id, language_code: tw.language_code, retweet_count: tw.retweet_count,
								retweeted: tw.retweeted, source: tw.source, text: tw.text, lang: tw.lang
								])
							 
							tweetsList.add(temp_tweetList)
							//parsing the tweetsList
							tweetsSlurpper = slurper.parseText(tweetsList.toString())
							coll1.update([id_str: fl.id_str], [$set :[tweet: tweetsSlurpper]], true, true)

						}
					}
				}
			}
			else
				{
					println "Connection error, could not insert the data"
				}
			
		}
		
				
		
		
		
		
		//end::exercise[]
	}
}

