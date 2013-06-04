package models;

import java.util.*;

import javax.persistence.*;

import com.avaje.ebean.Ebean;

import play.db.ebean.*;
import play.data.validation.*;

@Entity
public class Comment extends Model {


	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	public Long id;

	@Constraints.Required
	@Constraints.MaxLength(value = 255)
	@Column(columnDefinition="TEXT")
	public String text;

	@ManyToOne
	public Blog blog;
	
	@ManyToOne
	public User user;

	@Constraints.Required
	public Date date;
	//public Date created;

	public static Model.Finder<Long, Comment> find = new Model.Finder<Long, Comment>(
			Long.class, Comment.class);

    @OrderBy("id")
	public static List<Comment> getAllComments() {
		List<Comment> comments = new ArrayList<Comment>();
		comments = Ebean.find(Comment.class).findList(); 
		return comments; 
	}
}