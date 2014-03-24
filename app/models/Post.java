/**
 * 
 */
package models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import play.data.validation.Constraints.Required;
import utils.MongoUtil;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

/**
 * @author rfanego
 */
public class Post {
	private static final String USER_COLLECTION_NAME = "Post";
	private String author;
	@Required
	private String body;
	@Required
	private String title;
	@Required
	private String tag;
	private String permalink;
	private List<String> tags;
	private List<String> comments;
	private Date creationDate;
	private List<String> likes;
	
	public String getTag() {
		return tag;
	}
	public String getAuthor() {
		return author;
	}
	public String getBody() {
		return body;
	}
	public String getTitle() {
		return title;
	}
	public String getPermalink() {
		return permalink;
	}
	public List<String> getTags() {
		return tags;
	}
	public List<String> getComments() {
		return comments;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public List<String> getLikes() {
		return likes;
	}
	
	private void setTags(List<String> tags) {
		this.tags = tags;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public static Post create(Post post) {
		DBCollection postCollection = MongoUtil.getCollection(USER_COLLECTION_NAME);
		
		String permalink = post.getTitle().replaceAll("\\s", "_"); // whitespace becomes _
        permalink = permalink.replaceAll("\\W", ""); // get rid of non alphanumeric
        permalink = permalink.toLowerCase();
		
		post.setTags(new ArrayList<String>(Arrays.asList(post.getTag().split(","))));
		
		BasicDBObject postDB = new BasicDBObject("title", post.getTitle());
        postDB.append("author", post.getAuthor());
        postDB.append("body", post.getBody());
        postDB.append("permalink", permalink);
        postDB.append("tags", post.getTags());
        postDB.append("comments", new ArrayList<String>());
        postDB.append("date", new java.util.Date());
    	
        try {
        	postCollection.insert(postDB);
            return post;
        } catch (Exception e) {
            throw new RuntimeException("Ha ocurrido un error al generar el post, intentelo nuevamente");
        }
	}
}
