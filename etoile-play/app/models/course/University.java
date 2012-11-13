package models.course;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import models.continent.Continent;


import com.avaje.ebean.Ebean;


import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.ebean.Model;

@Entity
public class University extends Model {

	
	@Id
	@GeneratedValue
    @Formats.NonEmpty
	public Long id;
	
	@Constraints.Required
	public String name;
	
	@Constraints.Required
	public String acronym;
	
	@Constraints.Required
	public String imageURL;

	
	@OneToMany
	public List<Course> courses;
	
	@ManyToOne
	public Continent continent;
	
	public static Model.Finder<Long, University> find = new Model.Finder<Long, University>(
			Long.class, University.class);

	public static List<University> getAllUniversities() {
		List<University> universities = new ArrayList<University>();
		universities = Ebean.find(University.class)
				.findList(); 
		return universities; 
	}
}
