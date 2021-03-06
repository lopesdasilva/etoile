package models.module;

import java.util.*;

import javax.persistence.*;

import models.curriculum.Category;
import models.forum.Forum;
import models.manytomany.Usertest;
import models.test.Test;
import models.Language;
import models.Modulescore;
import models.Professor;
import models.User;

import com.avaje.ebean.Ebean;

import play.db.ebean.*;
import play.data.format.Formats;
import play.data.validation.*;

@Entity
public class Module extends Model {
	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Formats.NonEmpty
	public Long id;

	@Constraints.Required
	public String name;
	
	@Constraints.Required
	public String acronym;
	
	@Constraints.Required
	public String duration;
	
	@Constraints.Required
	@Column(columnDefinition="TEXT")
	public String description;
	
	@Constraints.Required
	@Column(columnDefinition="TEXT")
	public String short_description;

	@Constraints.Required
	@Column(columnDefinition="TEXT")
	public String videoURL;
	
	@Constraints.Required
	@Column(columnDefinition="TEXT")
	public String imageURL;

    @Constraints.Required
    public boolean published;
	
	@ManyToMany(mappedBy="modules")
    @OrderBy("id")
	public List<User> users;
	
	@ManyToMany(mappedBy="modules")
	public List<Professor> professors;

	@OneToMany(cascade = {CascadeType.ALL})
    @OrderBy("number")
	public List<Lesson> lessons;

	@ManyToMany (cascade = {CascadeType.ALL})
    @OrderBy("id")
	public List<Category> categories;


	@OneToMany
    @OrderBy("date")
	public List<Content> contents;
	
	@OneToMany
    @OrderBy("date")
	public List<Bibliography> bibliography;
	
	@ManyToOne
	public University university;
	
	@ManyToOne
	public Language language;
	
	@OneToMany(cascade = {CascadeType.ALL})
    @OrderBy("id")
	public List<Modulescore> modulescore;
	
	@OneToOne
	public Forum forum;
	
	//public Date created;

	public static Model.Finder<Long, Module> find = new Model.Finder<Long, Module>(
			Long.class, Module.class);
	
	public static Module findByAcronym(String acronym) {
        return find.where().eq("acronym", acronym).findUnique();
    }

//	public static List<Module> getAllModules() {
//		List<Module> modules = new ArrayList<Module>();
//		modules = Ebean.find(Module.class).findList(); 
//		return modules; 
//	}
	
	
	public int getNumberOfQuestions(){
		int n = 0;
		for(Lesson lesson: this.lessons){
			n = n + lesson.questions.size();
		}
		return n;
	}
	
	public int getNumberOfTestsToMark(){
		int n = 0;
		for(Lesson lesson: this.lessons){
			for(Test test : lesson.tests){
				for(Usertest ut: test.users){
					if(ut.submitted && !ut.reviewd){
						n = n + 1;
					}
				}
			}
		}
		return n;
		}

    public static int getNumberOfPublishedModules(){
        int n = 0;
        for(Module m: Module.find.all()){
            if(m.published){
             n++;
            }
        }
        return n;
    }
}