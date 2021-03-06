package models.module;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import models.Student;
import models.continent.Continent;
import models.curriculum.Curriculummodule;


import com.avaje.ebean.Ebean;


import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.ebean.Model;

@Entity
public class University extends Model {

	
	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Formats.NonEmpty
	public Long id;
	
	@Constraints.Required
	@Column(unique=true)
	public String name;
	
	@Constraints.Required
	public String acronym;
	
	@Constraints.Required
	@Column(columnDefinition="TEXT")
	public String imageURL;
	
	@Constraints.Required
	@Column(columnDefinition="TEXT")
	public String adressURL;
	
	@Constraints.Required
	@Column(columnDefinition="TEXT")
	public String modulebannerURL;

	@OneToMany
    @OrderBy("id")
	public List<Student> students;
	
	@OneToMany
    @OrderBy("id")
	public List<Module> modules;
	
	@ManyToOne
	public Continent continent;
	
	public static Model.Finder<Long, University> find = new Model.Finder<Long, University>(
			Long.class, University.class);

    @OrderBy("id")
	public static List<University> getAllUniversities() {
		List<University> universities = new ArrayList<University>();
		universities = Ebean.find(University.class)
				.findList(); 
		return universities; 
	}
	
	public static University findByName(String name) {
        return find.where().eq("name", name).findUnique();
    }
}
