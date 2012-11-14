package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import models.course.Course;


import com.avaje.ebean.Ebean;

import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.ebean.Model;

@Entity
public class ProfessorContent extends Model{
	@Id
	@GeneratedValue
	public Long id;

	@Constraints.Required
    @Formats.NonEmpty
	public String title;
	
	@Constraints.Required
    @Formats.NonEmpty
	public String imageURL;
	
	@Constraints.Required
	@Column(columnDefinition="TEXT")
	public String description;
	
	@ManyToOne
	public Professor professor;
	
	public static Model.Finder<Long, ProfessorContent> find = new Model.Finder<Long, ProfessorContent>(
			Long.class, ProfessorContent.class);
	
	

	public static List<ProfessorContent> getAllContent() {
		List<ProfessorContent> contents = new ArrayList<ProfessorContent>();
		contents = Ebean.find(ProfessorContent.class).findList(); 
		return contents; 
	}
	
}
