package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import models.course.Course;

import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.ebean.Model;

@Entity 
public class Professor extends Model {
	
	@Id
	@GeneratedValue
    @Formats.NonEmpty
	public Long id;

	@Constraints.Required
	private String firstname;

	@Constraints.Required
	private String lastname;
	
	@Constraints.Required
	private String degree;
	
	
	@Constraints.Required
	private String imageURL;
	

	@ManyToMany(cascade = {CascadeType.ALL})
	public List<Course> courses;
	
	  public static Model.Finder<Long,Professor> find = new Model.Finder(Long.class, Professor.class);
	   
	  public String toString() {
	        return degree+" "+firstname +" "+lastname;
	    }
}
