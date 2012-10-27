package models;

import java.util.*;

import javax.persistence.*;

import com.avaje.ebean.Ebean;

import play.db.ebean.*;
import play.data.format.Formats;
import play.data.validation.*;

@Entity
public class Course extends Model {
	@Id
	@GeneratedValue
	@Column(name="COURSE_ID")
    @Formats.NonEmpty
	public Long id;

	@Constraints.Required
	public String name;
	
	@Constraints.Required
	public String description;
	
	@Constraints.Required
	public String courseImageURL;
	

	@ManyToMany(mappedBy="courses")
	public User user;

	@ManyToMany
	public List<Module> modules;

	@ManyToMany
	public Category category;
	
	//public Date created;

	public static Model.Finder<Long, Course> find = new Model.Finder<Long, Course>(
			Long.class, Course.class);

	public static List<Course> getAllCourses() {
		List<Course> courses = new ArrayList<Course>();
		courses = Ebean.find(Course.class).findList(); 
		return courses; 
	}
}