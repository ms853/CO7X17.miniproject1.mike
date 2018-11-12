package miniproject1.mongoDb

import com.gmongo.GMongo
import com.mongodb.DB
import com.mongodb.MongoURI

class MongoDB {

	// EDIT THE FOLLOWING PARAMETERS
	def USERNAME = "abc"
	def PWD = "abc123456"
	def HOST = "ds145083.mlab.com"
	def PORT = "45083"
	def DATABASE = "twitterdb"
	
	public DB db
	
	// MAKING THE CONNECTION
	public MongoDB() {
		def connectionString = new MongoURI("mongodb://${USERNAME}:${PWD}@${HOST}:${PORT}/${DATABASE}")
		def mongo = new GMongo(connectionString)
		db = mongo.getDB(DATABASE)
	}
	
	def connectionOk() {
		def DESC = 'testing the connection'
		db.test.remove([:])
		db.test << [id: 0, task: DESC]
		def item = db.test.findOne(id: 0)
		db.test.remove([:])
		
		return (item.task == DESC)
	}
	
	def initDb() {
		db.friends.remove([:])
		db.followers.remove([:])
	}
	
	
}
