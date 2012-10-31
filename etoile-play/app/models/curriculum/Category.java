package models.curriculum;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.UniqueConstraint;

import models.course.Course;

import com.avaje.ebean.Ebean;

import play.db.ebean.*;
import play.data.validation.Constraints;
import play.db.ebean.Model;

@Entity
public class Category extends Model {

	@Id
	public Long id;
	
	
	@Constraints.Required
	@Column(unique = true)
	public String keyword;
	
	@Constraints.Required
	public String name;
	
	@Constraints.Required
	public String description;
	
	@ManyToMany(mappedBy="categories")
	public List<Course> courses;
	
	@ManyToMany(cascade = {CascadeType.ALL})
	public List<Curriculumcourse> curriculumcourses;
	
	public static Model.Finder<Long, Category> find = new Model.Finder<Long, Category>(
			Long.class, Category.class);

	public static List<Category> getAllCategories() {
		List<Category> categories = new ArrayList<Category>();
		categories = Ebean.find(Category.class).findList(); 
		return categories; 
	}
	
}
