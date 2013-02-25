package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import models.User;
import models.curriculum.Curriculummodule;
import models.module.Module;

import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.ebean.Model;

@Entity
public class Modulescore extends Model{

	@Id
	@GeneratedValue
    @Formats.NonEmpty
	public Long id;

	
	@Constraints.Required
	public int score;
	
	@ManyToOne
	public Module module;
	
	@ManyToOne
	public User user;
	
	
}
