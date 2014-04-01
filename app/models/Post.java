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
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

/**
 * @author rfanego
 */
public class Post {
	private static final String USER_COLLECTION_NAME = "Post";
	@Required
	private String body;
	@Required
	private String title;
	@Required
	private String tag;
	private String author;
	private String permalink;
	private List<String> tags;
	private List<Comment> comments;
	private Date creationDate;
	private List<String> likes;
	
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getPermalink() {
		return permalink;
	}
	public List<String> getTags() {
		return tags;
	}
	public List<Comment> getComments() {
		return comments == null ? new ArrayList<Comment>() : comments;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public List<String> getLikes() {
		return likes == null ? new ArrayList<String>() : likes;
	}
	
	private void setTags(List<String> tags) {
		this.tags = tags;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public void setPermalink(String permalink) {
		this.permalink = permalink;
	}
	
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	public void setLikes(List<String> likes) {
		this.likes = likes;
	}
	
	public static Post create(Post post) {
		DBCollection postCollection = MongoUtil.getCollection(USER_COLLECTION_NAME);
		
		String permalink = post.getTitle().replaceAll("\\s", "_"); // whitespace becomes _
        permalink = permalink.replaceAll("\\W", ""); // get rid of non alphanumeric
        permalink = permalink.toLowerCase();
		
		post.setTags(fromTagToTagsList(post.getTag()));
		
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
	
	public static Post findByPermalink(String permalink){
		DBCollection postCollection = MongoUtil.getCollection(USER_COLLECTION_NAME);
		
		BasicDBObject postDB = new BasicDBObject("permalink",permalink);
		
		return setPostObject(postCollection.findOne(postDB));
	}
	
	private static Post setPostObject(DBObject postObject) {
		Post post = new Post();
		post.setAuthor(postObject.get("author").toString());
		post.setBody(postObject.get("body").toString());
		post.setTags(fromTagToTagsList(postObject.get("tags").toString()));
		post.setTitle(postObject.get("title").toString());
		post.setPermalink(postObject.get("permalink").toString());
		//TODO terminar de popular el post con el dbobject
		System.out.println(post.toString());
		return post;
	}
	
	private static ArrayList<String> fromTagToTagsList(String tag) {
		return new ArrayList<String>(Arrays.asList(tag.split(",")));
	}
	public static List<Post> findAll() {
		List<Post> listPost = new ArrayList<Post>();
		DBCursor cursor =  MongoUtil.getCollection(USER_COLLECTION_NAME).find();
		try {
		   while(cursor.hasNext())
			   listPost.add(setPostObject(cursor.next()));
		} finally {
		   cursor.close();
		}
		return listPost;
	}
}
