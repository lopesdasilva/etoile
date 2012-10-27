package models;

import java.util.*;

import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

import com.avaje.ebean.*;

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
    @Column(name="ACCOUNT_EMAIL")
    @Formats.NonEmpty
    public String email;
    
    @Constraints.Required
    public String name;
    
    @Constraints.Required
    public String password;
    
	@ManyToMany(cascade = {CascadeType.ALL})
//	@JoinTable(name="account_course", joinColumns={@JoinColumn(name="account_email")}, inverseJoinColumns={@JoinColumn(name="course_id")})
	public List<Course> courses;
    
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
    	System.out.println("Email: "+email);
    	System.out.println("Password: "+password);
        return find.where()
            .eq("email", email)
            .eq("password", password)
            .findUnique();
    }
    
    // --
    
    public String toString() {
        return "User(" + email + ")";
    }

}

