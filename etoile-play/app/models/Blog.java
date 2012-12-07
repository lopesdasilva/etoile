package models;

import java.util.*;

import javax.persistence.*;

import com.avaje.ebean.Ebean;

import play.db.ebean.*;
import play.data.validation.*;

@Entity
public class Blog extends Model {
	
	@Id
	@GeneratedValue
	public Long id;

	@Constraints.Required
	public String header;
	
	@Constraints.Required
	@Constraints.MaxLength(value = 45)
	public String alternateHeader;
	
	@Constraints.Required
	@Column(columnDefinition="TEXT")
	public String text;
	
	@Constraints.Required
	@Constraints.MaxLength(value = 255)
	@Column(columnDefinition="TEXT")
	public String alternateText;
	
	@Constraints.Required
	public String articleImageURL;
	
	//public Date created;

	@OneToMany(mappedBy="blog", cascade=CascadeType.ALL)
	public List<Comment> comments;

	public static Model.Finder<Long, Blog> find = new Model.Finder<Long, Blog>(
			Long.class, Blog.class);

	public static List<Blog> getAllBlogs() {
		List<Blog> blogs = new ArrayList<Blog>();
		blogs = Ebean.find(Blog.class)
				.findList(); 
		return blogs; 
	}
}