package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import models.module.Module;

import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.ebean.Model;

@Entity 
public class Professor extends Model {

	@Id
	@GeneratedValue
	@Formats.NonEmpty
	public Long id;
	
	@Constraints.Required
	public String email;
	
	@Constraints.Required
	public String acronym;

	@Constraints.Required
	public String firstname;

	@Constraints.Required
	public String lastname;

	@Constraints.Required
	public String degree;

	@Constraints.Required
	public String imageURL;

	@Constraints.Required
	@Column(columnDefinition="TEXT")
	public String contact;
	
	@Constraints.Required
	@Column(columnDefinition="TEXT")
	public String shortdescription;
	
	
	@OneToMany
	public List<ProfessorContent> contents;

	@ManyToMany(cascade = {CascadeType.ALL})
	public List<Module> modules;

	public static Model.Finder<Long,Professor> find = new Model.Finder(Long.class, Professor.class);

	public static Professor findByAcronym(String acronym) {
		return find.where().eq("acronym", acronym).findUnique();
	}

	public String toString() {
		return degree+" "+firstname +" "+lastname;
	}
}
