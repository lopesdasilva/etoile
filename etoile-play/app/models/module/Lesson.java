package models.module;

import java.util.*;

import javax.persistence.*;
import javax.validation.Constraint;

import models.test.Answer;
import models.test.Test;
import models.test.question.Question;

import com.avaje.ebean.Ebean;

import play.db.ebean.*;
import play.data.format.Formats;
import play.data.validation.*;

@Entity
public class Lesson extends Model{
	
	@Id
	@GeneratedValue
	public Long id;

	@Constraints.Required
	public String name;
	
	@Constraints.Required
	public int number;
	
	@Column(unique=true)
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
	@Column(columnDefinition="TEXT")
	public String imageURL;
	
	@Constraints.Required
	@Column(columnDefinition="TEXT")
	public String videoURL;

	@ManyToOne
	public Module module;
	
	@OneToMany(cascade = {CascadeType.ALL})
	public List<Test> tests;
	
	@OneToMany(cascade = {CascadeType.ALL})
	public List<Lessoncontent> lessoncontents;
	
	@OneToMany(cascade = {CascadeType.ALL})
	public List<Lessonalert> lessonalerts;
	
	@OneToMany
	public List<Question> questions;
	
	public static Model.Finder<Long, Lesson> find = new Model.Finder<Long, Lesson>(
			Long.class, Lesson.class);
	
	public static Lesson findByAcronym(String acronym) {
        return find.where().eq("acronym", acronym).findUnique();
    }
	public static Lesson findByModuleAndAcronym(Long module_id,String acronym) {
        return find.where().eq("acronym", acronym).eq("module_id", module_id).findUnique();
    }

	public static List<Lesson> getAllLessons() {
		List<Lesson> lessons = new ArrayList<Lesson>();
		lessons = Ebean.find(Lesson.class).findList(); 
		return lessons; 
	}
	
	public int publishedTests(Lesson lesson){
		int number=0;
		for (Test test: lesson.tests){
			if(test.published){
				number++;
			}
		}
		
		return number;
	}
	
}
