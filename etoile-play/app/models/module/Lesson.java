package models.module;

import java.util.*;

import javax.persistence.*;

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

	@ManyToMany(mappedBy="lessons")
	public Module module;
	
	@ManyToMany(cascade = {CascadeType.ALL})
	public List<Test> tests;
	
	@ManyToMany(cascade = {CascadeType.ALL})
	public List<Lessoncontent> lessoncontents;
	
	@OneToMany
	public List<Question> questions;
	
	public static Model.Finder<Long, Lesson> find = new Model.Finder<Long, Lesson>(
			Long.class, Lesson.class);
	
	public static Lesson findByAcronym(String acronym) {
        return find.where().eq("acronym", acronym).findUnique();
    }

	public static List<Lesson> getAllLessons() {
		List<Lesson> lessons = new ArrayList<Lesson>();
		lessons = Ebean.find(Lesson.class).findList(); 
		return lessons; 
	}
	
}
