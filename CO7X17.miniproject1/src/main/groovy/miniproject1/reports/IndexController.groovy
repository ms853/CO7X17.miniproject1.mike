package miniproject1.reports;

import com.mongodb.DBCollection
import java.util.List
import miniproject1.mongoDb.MongoDB
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class IndexController {

	def mongo = new MongoDB()
	
	@RequestMapping
	public String index(Model model) {
		def connectionStatus = 'PENDING'
		if (mongo.connectionOk()) {
			connectionStatus = 'CONNECTED'
		}
		
		model.addAttribute('connectionStatus', connectionStatus)
		return "main"
		
	}
	

	@RequestMapping(value="followers", method=RequestMethod.GET)
    public String followers(Model model) {
		def followedFollowers = []
		
		// tag::exercise[]
		db.followers.find()
		// Exercise 2
		
		//end::exercise[]
		
		model.addAttribute("followers", followedFollowers);
	    	return "followers";
    }

	
	@RequestMapping(value="friends",method=RequestMethod.GET)
	public String friends(Model model) {
		List<FriendDto> friends = new ArrayList<FriendDto>()
		
		// tag::exercise[]
		
		// Exercise 3
		
		//end::exercise[]
		
		model.addAttribute("friends", friends);
		return "friends";
	}
	
}
