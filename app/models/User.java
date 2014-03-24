package models;

import java.net.UnknownHostException;

import play.data.validation.Constraints.Required;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;

/**
 * @author rfanego
 */

public class User {
	@Required
	private String name;
	@Required
	private String password;
	private String email;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	private String getEmail() {
		return email;
	}
	
	public static boolean create(User user) {
		DBCollection userCollection = getUserCollection();
    	
    	BasicDBObject userDB = new BasicDBObject();
    	userDB.append("_id", user.getName());
    	userDB.append("password", user.getPassword());
    	
    	String email = user.getEmail();
    	if (email != null && !email.equals("")) {
            userDB.append("email", email);
        }
    	
    	try {
    		userCollection.insert(userDB);
            return true;
        } catch (MongoException.DuplicateKey e) {
            System.out.println("Username already in use: " + user.getName());
            return false;
        }
	}
	
	
	public static boolean validate(User user){
		DBCollection userCollection = getUserCollection();
		
		BasicDBObject userDB = new BasicDBObject("_id",user.getName()).append("password", user.getPassword());
		
		return userCollection.findOne(userDB) != null;	
	}
	
	private static DBCollection getUserCollection() {
		MongoClient mongoClient = null;
		try {
			mongoClient = new MongoClient( "localhost" , 27017 );
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		DB db = mongoClient.getDB( "blog" );
		
		DBCollection userCollection = db.getCollection("User");
		return userCollection;
	}
}
