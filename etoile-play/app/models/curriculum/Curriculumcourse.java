package models.curriculum;

import java.util.*;

import javax.persistence.*;

import models.course.Content;
import models.curriculum.Category;
import models.User;

import com.avaje.ebean.Ebean;

import play.db.ebean.*;
import play.data.format.Formats;
import play.data.validation.*;

@Entity
public class Curriculumcourse extends Model {
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

	@ManyToMany (mappedBy="curriculumcourses")
	public List<Category> curriculumcategories;



	public static Model.Finder<Long, Curriculumcourse> find = new Model.Finder<Long, Curriculumcourse>(
			Long.class, Curriculumcourse.class);

	public static List<Curriculumcourse> getAllCourses() {
		List<Curriculumcourse> courses = new ArrayList<Curriculumcourse>();
		courses = Ebean.find(Curriculumcourse.class).findList(); 
		return courses; 
	}
}