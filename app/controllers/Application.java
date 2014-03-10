package controllers;

import java.net.UnknownHostException;
import java.util.Set;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render("Aplicacion con mongo (y redis tal vez)"));
    }
    
    
    public static Result mongoTest(){
    	MongoClient mongoClient = null;
		try {
			mongoClient = new MongoClient( "localhost" , 27017 );
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

    	DB db = mongoClient.getDB( "book" );
    	
    	Set<String> colls = db.getCollectionNames();
    	StringBuilder collections = new StringBuilder();
    	
    	for (String s : colls) {
    		collections.append(s);
    	}
    	
    	DBCollection coll = db.getCollection("towns");
    	
    	return ok(coll.findOne().toString());
    }
}
