package models;

import java.util.*;

import javax.persistence.*;
import javax.validation.Constraint;

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

/**
 * User entity managed by Ebean
 */
@Entity 
@Table(name="account")
public class User extends Model {
	
	
    @Constraints.Required
    @GeneratedValue
    public Long id;
	
    @Id
    @Constraints.Required
    @Formats.NonEmpty
    public String email;
    
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
	public List<Usertopic> topicssubscriptions;
    
    
	@ManyToMany(cascade = {CascadeType.ALL})
//	@JoinTable(name="account_module", joinColumns={@JoinColumn(name="account_email")}, inverseJoinColumns={@JoinColumn(name="module_id")})
	public List<Module> modules;
	
	@OneToMany(cascade = {CascadeType.ALL})
	public List<Comment> comments;
	
	@OneToMany(cascade = {CascadeType.ALL})
	public List<Question> openquestions;
	
	@OneToMany(cascade = {CascadeType.ALL})
	public List<AnswerMarker> answerMarker;
	
    // -- Queries
	
	
	
	@OneToMany(cascade = {CascadeType.ALL})
	List<Hypothesis> hypothesis;
	
	@OneToMany(cascade = {CascadeType.ALL})
	public List<Usertest> tests;
	
	@OneToMany(cascade = {CascadeType.ALL})
	public List<URL> urls;
	
	@OneToMany(cascade = {CascadeType.ALL})
	public List<SubtopicReputation> subtopicreputation;
	
	@OneToMany(cascade = {CascadeType.ALL})
	public List<Modulescore> modulesscores;
	
	@OneToMany(cascade= {CascadeType.ALL})
	public List<Topic> topics;
	
	@OneToMany(cascade= {CascadeType.ALL})
	public List<Reply> replies;
	
    // -- Queries
	
	@Constraints.Required
	public Long globalReputation;
    
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
    	System.out.println("User loggedin: "+email);
        return find.where()
            .eq("email", email)
            .eq("password",sha1.parseSHA1Password(  password))
            .findUnique();
    }
    
    // --
    
    public String toString() {
        return username;
    }
    
    public boolean isUserSignupTest(Test t){

    	boolean signup=false;
    	for(Usertest usertest: tests){
    		if(usertest.test.id==t.id)
    			signup=true;
    	}
    	
    	return signup;
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

}

