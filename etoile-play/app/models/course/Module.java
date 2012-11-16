package models.course;

import java.util.*;

import javax.persistence.*;

import models.test.Answer;
import models.test.OpenQuestion;
import models.test.Test;

import com.avaje.ebean.Ebean;

import play.db.ebean.*;
import play.data.format.Formats;
import play.data.validation.*;

@Entity
public class Module extends Model{
	@Id
	@GeneratedValue
	public Long id;

	@Constraints.Required
	public String name;
	
	@Constraints.Required
	public String acronym;
	
	@Constraints.Required
	@Column(columnDefinition="TEXT")
	public String description;
	
	@Constraints.Required
	@Constraints.MaxLength(value = 255)
	@Column(columnDefinition="TEXT")
	public String shortDescription;
	
	@Constraints.Required
	public String imageURL;
	
	@Constraints.Required
	public String videoURL;

	@ManyToMany(mappedBy="modules")
	public Course course;
	
	@ManyToMany(cascade = {CascadeType.ALL})
	public List<Test> tests;
	
	@ManyToMany(cascade = {CascadeType.ALL})
	public List<Modulecontent> modulecontents;
	
	@OneToMany
	public List<OpenQuestion> questions;
	
	public static Model.Finder<Long, Module> find = new Model.Finder<Long, Module>(
			Long.class, Module.class);
	
	public static Module findByAcronym(String acronym) {
        return find.where().eq("acronym", acronym).findUnique();
    }

	public static List<Module> getAllModules() {
		List<Module> modules = new ArrayList<Module>();
		modules = Ebean.find(Module.class).findList(); 
		return modules; 
	}
	
}
