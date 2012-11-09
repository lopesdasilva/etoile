package models.course;

import java.util.*;

import javax.persistence.*;

import models.curriculum.Category;
import models.User;

import com.avaje.ebean.Ebean;

import play.db.ebean.*;
import play.data.format.Formats;
import play.data.validation.*;

@Entity
public class Course extends Model {
	@Id
	@GeneratedValue
    @Formats.NonEmpty
	public Long id;

	@Constraints.Required
	public String name;
	
	@Constraints.Required
	public String acronym;
	
	@Constraints.Required
	public String duration;
	
	@Constraints.Required
	@Column(columnDefinition="TEXT")
	public String description;

	@Constraints.Required
	@Column(columnDefinition="TEXT")
	public String videoURL;
	
	@Constraints.Required
	public String imageURL;
	

	@ManyToMany(mappedBy="courses")
	public User user;

	@ManyToMany(cascade = {CascadeType.ALL})
	public List<Module> modules;

	@ManyToMany (cascade = {CascadeType.ALL})
	public List<Category> categories;
	
	@OneToMany
	public List<Content> contents;
	
	//public Date created;

	public static Model.Finder<Long, Course> find = new Model.Finder<Long, Course>(
			Long.class, Course.class);

	public static List<Course> getAllCourses() {
		List<Course> courses = new ArrayList<Course>();
		courses = Ebean.find(Course.class).findList(); 
		return courses; 
	}
}