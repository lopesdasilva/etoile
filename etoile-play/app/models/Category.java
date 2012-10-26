package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.avaje.ebean.Ebean;

import play.db.ebean.*;
import play.data.validation.Constraints;
import play.db.ebean.Model;

@Entity
public class Category extends Model {

	@Id
	public Long id;
	
	@Constraints.Required
	public String name;
	
	@Constraints.Required
	public String description;
	
	@ManyToMany
	public List<Course> courses;
	
	public static Model.Finder<Long, Category> find = new Model.Finder<Long, Category>(
			Long.class, Category.class);

	public static List<Category> getAllCategories() {
		List<Category> categories = new ArrayList<Category>();
		categories = Ebean.find(Category.class).findList(); 
		return categories; 
	}
	
}
