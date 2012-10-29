package models.course;

import java.util.*;

import javax.persistence.*;

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
    @Formats.NonEmpty
	public String name;
	
	@Constraints.Required
	@Column(columnDefinition="TEXT")
	public String text;
	
	@Constraints.Required
	@Constraints.MaxLength(value = 255)
	@Column(columnDefinition="TEXT")
	public String alternateText;
	
	@Constraints.Required
	public String moduleImageURL;

	@ManyToMany(mappedBy="modules")
	public Course course;
	
	@ManyToMany(cascade = {CascadeType.ALL})
	public List<Test> tests;
	
	public static Model.Finder<Long, Module> find = new Model.Finder<Long, Module>(
			Long.class, Module.class);

	public static List<Module> getAllModules() {
		List<Module> modules = new ArrayList<Module>();
		modules = Ebean.find(Module.class).findList(); 
		return modules; 
	}
	
}
