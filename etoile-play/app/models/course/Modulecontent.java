package models.course;

import java.util.*;

import javax.persistence.*;

import models.course.Module;

import com.avaje.ebean.Ebean;

import play.db.ebean.*;
import play.data.format.Formats;
import play.data.validation.*;

@Entity
public class Modulecontent extends Model{
	@Id
	@GeneratedValue
	public Long id;

	@Constraints.Required
    @Formats.NonEmpty
	public String name;
	
	@Constraints.Required
    @Formats.NonEmpty
	public String text;

	@ManyToMany(mappedBy="modulescontents")
	public Module module;
	
	@Constraints.Required
    @Formats.NonEmpty
	public String url;
	
	@Constraints.Required
    @Formats.NonEmpty
	public String moduleContentImageURL;
	
	

	
	public static Model.Finder<Long, Modulecontent> find = new Model.Finder<Long, Modulecontent>(
			Long.class, Modulecontent.class);

	public static List<Modulecontent> getAllTests() {
		List<Modulecontent> tests = new ArrayList<Modulecontent>();
		tests = Ebean.find(Modulecontent.class).findList(); 
		return tests; 
	}
	
}
