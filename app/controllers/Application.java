package controllers;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Set;

import models.Post;
import models.User;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

public class Application extends Controller {

	static Form<User> userForm = Form.form(User.class);
	static Form<Post> postForm = Form.form(Post.class);
	
    public static Result index() {
    	return redirect(routes.Application.signup());
    }
    
    public static Result signup() {
    	return ok(views.html.index.render(userForm));
    }
    
    public static Result welcome(String user) {
    	return ok(views.html.welcome.render(user));
    }
    
    public static Result login() {
        return TODO;
    }
    
    public static Result logout() {
        return TODO;
    }
    
    public static Result blog(String user) {
    	//TODO pasar lista de los últimos posts
    	return ok(views.html.blog.render(user,new ArrayList<Post>())); 
    }
    
    public static Result postForm(String user) {
    	return ok(views.html.createPost.render(user,postForm));
    }
    
    public static Result newPost(String user) {
        return TODO;
    }
    
    public static Result newUser() {
    	Form<User> filledForm = userForm.bindFromRequest();
    	  if(filledForm.hasErrors()) {
    	    return badRequest(
    	      views.html.index.render(filledForm)
    	    );
    	  } else {
    	    if(User.create(filledForm.get())){    	    	
    	    	return redirect(routes.Application.welcome(filledForm.get().getName()));
    	    }else{
    	    	return badRequest(
    	      	      views.html.index.render(filledForm)
    	      	    );
    	    }
    	  }
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
