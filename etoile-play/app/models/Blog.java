package models;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.persistence.*;

import com.avaje.ebean.Ebean;

import play.db.ebean.*;
import play.data.validation.*;

@Entity
public class Blog extends Model {
	
	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
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
	@Column(columnDefinition="TEXT")
	public String articleImageURL;
	

	@Constraints.Required
	public Date date;
	
	//public Date created;

	@OneToMany(mappedBy="blog", cascade=CascadeType.ALL)
    @OrderBy("date")
	public List<Comment> comments;

	public String dateToString(){
		
		Long yourmilliseconds = date.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("EE MMM yyyy",Locale.UK);
        GregorianCalendar calendar = new GregorianCalendar(TimeZone.getTimeZone("UK"));
        calendar.setTimeInMillis(yourmilliseconds);
		return sdf.format(calendar.getTime());
	}
	public static Model.Finder<Long, Blog> find = new Model.Finder<Long, Blog>(
			Long.class, Blog.class);

//	public static List<Blog> getAllBlogs() {
//		List<Blog> blogs = new ArrayList<Blog>();
//		Ebean.
//		blogs = Ebean.find(Blog.class)
//				.findList(); 
//		return blogs; 
//	}
	
}