package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import models.curriculum.Curriculummodule;

import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.ebean.Model;

@Entity
public class SubtopicReputation extends Model{

	@Id
	@GeneratedValue
    @Formats.NonEmpty
	public Long id;
	
	@Constraints.Required
	public Long reputation;
	
	@ManyToOne
	public Curriculummodule subtopic;
	
	@ManyToOne
	public User user;
	
	
}
