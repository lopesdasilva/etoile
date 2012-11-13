package models.curriculum;

import java.util.*;

import javax.persistence.*;

import models.test.Test;

import com.avaje.ebean.Ebean;

import play.db.ebean.*;
import play.data.format.Formats;
import play.data.validation.*;

@Entity
public class Curriculumtopic extends Model{
	@Id
	@GeneratedValue
	public Long id;
	
	
	@Constraints.Required
	@Column(unique = true)
	public String keyword;

	@Constraints.Required
	@Column(columnDefinition="TEXT")
	public String text;

	@ManyToMany(mappedBy="curriculumtopics")
	public Curriculummodule curriculummodule;
	
	
	public static Model.Finder<Long, Curriculumtopic> find = new Model.Finder<Long,Curriculumtopic>(
			Long.class, Curriculumtopic.class);

	public static List<Curriculumtopic> getAllModules() {
		List<Curriculumtopic> modules = new ArrayList<Curriculumtopic>();
		modules = Ebean.find(Curriculumtopic.class).findList(); 
		return modules; 
	}
	
}
