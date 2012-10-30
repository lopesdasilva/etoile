package models.curriculum;

import java.util.*;

import javax.persistence.*;

import models.test.Test;

import com.avaje.ebean.Ebean;

import play.db.ebean.*;
import play.data.format.Formats;
import play.data.validation.*;

@Entity
public class Curriculummodule extends Model{
	@Id
	@GeneratedValue
	public Long id;

	@Constraints.Required
	@Column(columnDefinition="TEXT")
	public String text;
	


	@ManyToMany(mappedBy="curriculummodules")
	public Curriculumcourse curriculumcourse;
	
	@ManyToMany(cascade = {CascadeType.ALL})
	public List<Curriculumtopic> curriculumtopics;
	
	public static Model.Finder<Long, Curriculummodule> find = new Model.Finder<Long,Curriculummodule>(
			Long.class, Curriculummodule.class);

	public static List<Curriculummodule> getAllModules() {
		List<Curriculummodule> modules = new ArrayList<Curriculummodule>();
		modules = Ebean.find(Curriculummodule.class).findList(); 
		return modules; 
	}
	
}
