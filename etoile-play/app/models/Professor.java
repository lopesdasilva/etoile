package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.avaje.ebean.Ebean;

import models.module.Module;

import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.ebean.Model;

@Entity 
public class Professor extends Model {

	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
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
	@Column(columnDefinition="TEXT")
	public String imageURL;

	@Constraints.Required
	@Column(columnDefinition="TEXT")
	public String contact;
	
	@Constraints.Required
	@Column(columnDefinition="TEXT")
	public String shortdescription;

    @Constraints.Required
    public boolean master=false;

	@OneToOne
	public User user;
	
	@OneToMany
	@OrderBy("id")
	public List<ProfessorContent> contents;

	@ManyToMany(cascade = {CascadeType.ALL})
	@OrderBy("id")
	public List<Module> modules;

	public static Model.Finder<Long,Professor> find = new Model.Finder(Long.class, Professor.class);

	public static String[] getAllEmails(){
		
		
		List<Professor> professors = new ArrayList<Professor>();
		professors = Ebean.find(Professor.class).findList();
		String[] profs_emails = new String[professors.size()];
		int i=0;
		for(Professor p : professors){
			profs_emails[i]=p.email;
			i++;
		}
		return profs_emails;
	}
	
	public static Professor findByAcronym(String acronym) {
		return find.where().eq("acronym", acronym).findUnique();
	}

	public String toString() {
		return degree+" "+firstname +" "+lastname;
	}
}
