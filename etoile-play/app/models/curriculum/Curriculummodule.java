package models.curriculum;

import java.util.*;

import javax.persistence.*;
import javax.validation.Constraint;

import models.curriculum.Category;
import models.module.Content;
import models.test.question.Question;
import models.SubtopicReputation;
import models.User;

import com.avaje.ebean.Ebean;

import play.db.ebean.*;
import play.data.format.Formats;
import play.data.validation.*;

@Entity
public class Curriculummodule extends Model {
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Formats.NonEmpty
	public Long id;
	
	
	@Constraints.Required
	@Column(unique = true)
	public String keyword;

	@Constraints.Required
	@Column(unique=true)
	public String name;
	
	@Constraints.Required
	@Column(columnDefinition="TEXT")
	public String text;

	@ManyToMany(cascade = {CascadeType.ALL})
	public List<Curriculumlesson> curriculumlessons;

	@ManyToMany (mappedBy="curriculummodules")
	public List<Category> curriculumcategories;

	@OneToMany
	public List<Question> questions;
	
	@OneToMany
	public List<SubtopicReputation> subtopicsreputation;

	public static Model.Finder<Long, Curriculummodule> find = new Model.Finder<Long, Curriculummodule>(
			Long.class, Curriculummodule.class);
	
	public static Curriculummodule findByName(String name) {
        return find.where().eq("name", name).findUnique();
    }

	public static List<Curriculummodule> getAllModules() {
		List<Curriculummodule> modules = new ArrayList<Curriculummodule>();
		modules = Ebean.find(Curriculummodule.class).findList(); 
		return modules; 
	}
}