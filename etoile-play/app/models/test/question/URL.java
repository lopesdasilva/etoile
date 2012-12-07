package models.test.question;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import models.User;


import play.data.validation.Constraints;
import play.db.ebean.Model;


@Entity 
public class URL extends Model implements Comparable<URL> {
	
	@Id
	@GeneratedValue
	public Long id;
	
	@Constraints.Required
	public String adress;
	
	@Constraints.Required
	public int likes=0;
	
	//TODO: AUTO GET THIS PARAMETERS
		
	@Constraints.Required
	public String name;
	
	@Constraints.Required
	public String description;
	
	@Constraints.Required
	public String imageURL;
	
	//Associations
	
	@ManyToOne
	public Question question;
	
	@ManyToOne
	public User user;
	
	
	
	public static Model.Finder<Long, URL> find = new Model.Finder<Long, URL>(
			Long.class, URL.class);
	
	
    /**
     * Retrieve a URLS from question.
     */
    public static List<URL> findByQuestion(long question_id) {
        return find.where().eq("question_id", question_id).findList();
    }
	


	@Override
	public int compareTo(URL o) {
		return (o.likes<likes ? -1 : (o.likes==likes ? 0 : 1));
	}

}