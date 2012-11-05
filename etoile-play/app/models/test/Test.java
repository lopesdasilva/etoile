package models.test;

import java.util.*;

import javax.persistence.*;

import models.course.Module;

import com.avaje.ebean.Ebean;

import play.db.ebean.*;
import play.data.format.Formats;
import play.data.validation.*;

@Entity
public class Test extends Model{
	@Id
	@GeneratedValue
	public Long id;

	@Constraints.Required
    @Formats.NonEmpty
	public String name;
	
	@Constraints.Required
	@Constraints.MaxLength(value = 255)
	@Column(columnDefinition="TEXT")
	public String text;
	
	@Constraints.Required
	public String testImageURL;

	@ManyToMany(mappedBy="modules")
	public Module module;
	
	@ManyToMany(cascade = {CascadeType.ALL})
	public List<OpenQuestion> openquestions;
	
	@ManyToMany(cascade = {CascadeType.ALL})
	public List<OneChoiceQuestion> onechoicequestions;
	

	
	public static Model.Finder<Long, Test> find = new Model.Finder<Long, Test>(
			Long.class, Test.class);

	public static List<Test> getAllTests() {
		List<Test> tests = new ArrayList<Test>();
		tests = Ebean.find(Test.class).findList(); 
		return tests; 
	}
	
}
