package models;

import java.util.*;

import javax.persistence.*;

import models.test.Answer;
import models.test.Evaluation;
import models.test.Hypothesis;
import models.test.Test;
import models.test.question.Question;
import models.test.question.URL;
import models.manytomany.Usertest;
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
    
    
    
	@ManyToMany(cascade = {CascadeType.ALL})
//	@JoinTable(name="account_module", joinColumns={@JoinColumn(name="account_email")}, inverseJoinColumns={@JoinColumn(name="module_id")})
	public List<Module> modules;
	
	@OneToMany(cascade = {CascadeType.ALL})
	public List<Comment> comments;
	
	@OneToMany(cascade = {CascadeType.ALL})
	public List<Question> openquestions;
	
	@OneToMany(cascade = {CascadeType.ALL})
	public List<Evaluation> evaluations;
	
    // -- Queries
	
	
	@ManyToMany(mappedBy="markers")
	public List<Answer> answersToMark;
	
	@OneToMany(cascade = {CascadeType.ALL})
	List<Hypothesis> hypothesis;
	
	@OneToMany(cascade = {CascadeType.ALL})
	public List<Usertest> tests;
	
	@OneToMany(cascade = {CascadeType.ALL})
	public List<URL> urls;
	
    // -- Queries
    
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
    	System.out.println("ifEstou no signuptest");
    	boolean signup=false;
    	for(Usertest usertest: tests){
    		if(usertest.test.id==t.id)
    			signup=true;
    	}
    	
    	return signup;
    }

}

