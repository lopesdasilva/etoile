package models;

import java.util.*;

import javax.persistence.*;

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

	@ManyToMany
	public Course course;
	
	public static Model.Finder<Long, Module> find = new Model.Finder<Long, Module>(
			Long.class, Module.class);

	public static List<Module> getAllModules() {
		List<Module> modules = new ArrayList<Module>();
		modules = Ebean.find(Module.class).findList(); 
		return modules; 
	}
	
}
