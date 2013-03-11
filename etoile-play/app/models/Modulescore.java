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
	
	public static Model.Finder<Long,Modulescore> find = new Model.Finder(Long.class, Modulescore.class);
	
	public static Modulescore findByUserAndModule(String email, String module_acronym) {
		  User user =  User.findByEmail(email);
		  Module module = Module.findByAcronym(module_acronym);
		return find.where().eq("module", module).eq("user",user).findUnique();
	}
	
	
}
