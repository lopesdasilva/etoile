package models;

import java.util.*;

import javax.persistence.*;
import javax.validation.Constraint;

import models.curriculum.Curriculumtopic;
import models.test.Answer;
import models.test.AnswerMarker;
import models.test.Hypothesis;
import models.test.Test;
import models.test.question.Question;
import models.test.question.URL;
import models.forum.Reply;
import models.forum.Topic;
import models.manytomany.Usertest;
import models.manytomany.Usertopic;
import models.module.Module;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;


import controllers.extra.sha1;
import flexjson.JSON;

/**
 * User entity managed by Ebean
 */
@Entity 
@Table(name="account")
public class User extends Model {
	
	
    @Constraints.Required
    public Long id;
	
    @Id
    @Constraints.Required
    @Formats.NonEmpty
    public String email;
    
    @Constraints.Required
    public boolean olduser;
    
    @Constraints.Required
    public String name;
    
    @Constraints.Required
    public String password;

    @Constraints.Required
    public String username;

    @Constraints.Required
    public String country;

    @Constraints.Required
    public int account_type;
    
    //Only if account_type=1
    //Professor profile
    @OneToOne
    public Professor professorProfile;
    
    @OneToOne
    public Student studentProfile;
    
    @OneToMany(cascade = {CascadeType.ALL})
    @OrderBy("id")
	public List<Usertopic> topicssubscriptions;
    
    
	@ManyToMany(cascade = {CascadeType.ALL})
//	@JoinTable(name="account_module", joinColumns={@JoinColumn(name="account_email")}, inverseJoinColumns={@JoinColumn(name="module_id")})
	@OrderBy("id")
	public List<Module> modules;
	
	@OneToMany(cascade = {CascadeType.ALL})
	@OrderBy("id")
	public List<Comment> comments;
	
	@OneToMany(cascade = {CascadeType.ALL})
	@OrderBy("id")
	public List<Question> openquestions;
	
	@OneToMany(cascade = {CascadeType.ALL})
	@OrderBy("id")
	public List<AnswerMarker> answerMarker;
	
    // -- Queries
	
	
	
	@OneToMany(cascade = {CascadeType.ALL})
	@OrderBy("id")
	List<Hypothesis> hypothesis;
	
	@OneToMany(cascade = {CascadeType.ALL})
	@OrderBy("id")
	public List<Usertest> tests;
	
	@OneToMany(cascade = {CascadeType.ALL})
	@OrderBy("id")
	public List<URL> urls;
	
	@ManyToMany
	@JoinTable(name="voters_url")
	@OrderBy("id")
	public List<URL> urls_voted;

    @ManyToMany (cascade = {CascadeType.ALL})
 //   @JoinTable(name="voters_resources")
  //  @OrderBy("id")
    public List<Curriculumtopic> resources_voted;

    @OneToMany (cascade = {CascadeType.ALL})
    public List<Curriculumtopic> resources;
	
	@OneToMany(cascade = {CascadeType.ALL})
	@OrderBy("id")
	public List<SubtopicReputation> subtopicreputation;
	
	@OneToMany(cascade = {CascadeType.ALL})
	@OrderBy("id")
	public List<Modulescore> modulesscores;
	
	@OneToMany(cascade= {CascadeType.ALL})
	@OrderBy("id")
	public List<Topic> topics;
	
	@OneToMany(cascade= {CascadeType.ALL})
	@OrderBy("id")
	public List<Reply> replies;
	
    // -- Queries
	
	@Constraints.Required
	public Long globalReputation;

    @Constraints.Required
    public int commitmentReputation;

    @Constraints.Required
    public boolean tester;
    
    public static Model.Finder<String,User> find = new Model.Finder(String.class, User.class);
    
    /**
     * Retrieve all users.
     */
    public static List<User> findAll() {
        return find.all();
    }

    /**
     * Retrieve a User from email.
     */
    public static User findByEmail(String email) {
        return find.where().eq("email", email).findUnique();
    }
    
    /**
     * Authenticate a User.
     */
    public static User authenticate(String email, String password) {
        return find.where()
            .eq("email", email)
            .eq("password",sha1.parseSHA1Password(password))
            .findUnique();
    }
    
    /**
     * Authenticate a User.
     */
    public static User authenticateSHA1(String email, String password) {
        return find.where()
            .eq("email", email)
            .eq("password",password)
            .findUnique();
    }
    
    // --
    
    public String toString() {
        return username;
    }
    
    public boolean isUserSignupTest(Test t){

    	return Usertest.findByUserAndTest(this.email, t.id)!=null;
    	
//    	boolean signup=false;
//    	for(Usertest usertest: tests){
//    		if(usertest.test.id==t.id)
//    			signup=true;
//    	}
//    	
//    	return signup;
    }
    
    public boolean userSuggestedQuestion(Test t){
    	
    	boolean suggested=false;
    	for(Usertest usertest: tests){
    		if(usertest.test.id==t.id)
    			return usertest.inmodule;
    	}
    	
    	return suggested;
    }
    
    public boolean haveAnswersToMark(){
    	for(AnswerMarker answer_markers: answerMarker){
    		if(!answer_markers.isMarked){
    			return true;
    		}
    	}
    	return false;
    	
    	
    }
    
    @JSON(include=true)
    public List<Usertest> getOngoingTests(){
    	List<Usertest> onGoing= new LinkedList<Usertest>();
    	for(Usertest usertest: tests){
    		if(usertest.inmodule){
    			onGoing.add(usertest);
    		}
    	}
    	return onGoing;
    }

}

