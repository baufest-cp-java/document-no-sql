/**
 * 
 */
package models;

import java.util.Date;
import java.util.List;

/**
 * @author rfanego
 */
public class Post {
	private String author;
	private String body;
	private String title;
	private String permalink;
	private List<String> tags;
	private List<String> comments;
	private Date creationDate;
	private List<String> likes;
	
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
	
	
}
