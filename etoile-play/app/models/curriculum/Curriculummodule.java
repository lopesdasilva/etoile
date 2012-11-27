package models.curriculum;

import java.util.*;

import javax.persistence.*;

import models.curriculum.Category;
import models.module.Content;
import models.User;

import com.avaje.ebean.Ebean;

import play.db.ebean.*;
import play.data.format.Formats;
import play.data.validation.*;

@Entity
public class Curriculummodule extends Model {
	@Id
	@GeneratedValue
    @Formats.NonEmpty
	public Long id;
	
	
	@Constraints.Required
	@Column(unique = true)
	public String keyword;

	@Constraints.Required
	public String name;
	
	@Constraints.Required
	@Column(columnDefinition="TEXT")
	public String text;

	@ManyToMany(cascade = {CascadeType.ALL})
	public List<Curriculumlesson> curriculumlessons;

	@ManyToMany (mappedBy="curriculummodules")
	public List<Category> curriculumcategories;



	public static Model.Finder<Long, Curriculummodule> find = new Model.Finder<Long, Curriculummodule>(
			Long.class, Curriculummodule.class);

	public static List<Curriculummodule> getAllModules() {
		List<Curriculummodule> modules = new ArrayList<Curriculummodule>();
		modules = Ebean.find(Curriculummodule.class).findList(); 
		return modules; 
	}
}