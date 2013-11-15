package models.curriculum;

import java.util.*;

import javax.persistence.*;

import models.test.Test;

import com.avaje.ebean.Ebean;

import play.db.ebean.*;
import play.data.format.Formats;
import play.data.validation.*;

@Entity
public class Curriculumlesson extends Model{
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long id;
	
	
	@Constraints.Required
	@Column(unique = true)
	public String keyword;

	@Constraints.Required
	public String name;
	
	@Constraints.Required
	@Column(columnDefinition="TEXT")
	public String text;

	@ManyToMany(mappedBy="curriculumlessons")
	public Curriculummodule curriculummodule;
	
	@OneToMany(cascade = {CascadeType.ALL})
    @OrderBy("id")
	public List<Curriculumtopic> curriculumtopics;
	
	public static Model.Finder<Long, Curriculumlesson> find = new Model.Finder<Long,Curriculumlesson>(
			Long.class, Curriculumlesson.class);

	public static List<Curriculumlesson> getAllLessons() {
		List<Curriculumlesson> lessons = new ArrayList<Curriculumlesson>();
		lessons = Ebean.find(Curriculumlesson.class).findList(); 
		return lessons; 
	}
	
}
