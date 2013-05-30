package models.test.question;

import java.util.List;

import javax.persistence.*;

import org.joda.time.DateTime;

import models.User;


import play.data.validation.Constraints;
import play.db.ebean.Model;


@Entity 
public class URL extends Model implements Comparable<URL> {
	
	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
	public Long id;
	
	@Constraints.Required
	@Column(columnDefinition="TEXT")
	public String adress;
	
	@Constraints.Required
	public int likes=0;
	
	//TODO: AUTO GET THIS PARAMETERS
		
	@Constraints.Required
	public String name;
	
	@Constraints.Required
	public String description;
	
	@Constraints.Required
	@Column(columnDefinition="TEXT")
	public String imageURL;
	
	@Constraints.Required
	public DateTime added;
	
	//Associations
	
	@ManyToOne
	public Question question;
	
	@ManyToOne
	public User user;
	
	@ManyToMany
	@JoinTable(name="voters_url")
    @OrderBy("id")
	public List<User> voters;
	
	
	public static Model.Finder<Long, URL> find = new Model.Finder<Long, URL>(
			Long.class, URL.class);
	
	
    /**
     * Retrieve a URLS from question.
     */
    @OrderBy("id")
    public static List<URL> findByQuestion(long question_id) {
        return find.where().eq("question_id", question_id).findList();
    }
	
    public boolean isFeatured(long urlID){
    	DateTime dt= new DateTime();
    	URL url= URL.find.byId(urlID);
    	
    	if (dt.isAfter(url.added.plusDays(15))){
    		return false;
    	}
    	
		return true;
    	
    }


	@Override
	public int compareTo(URL o) {
		return (o.likes<likes ? -1 : (o.likes==likes ? 0 : 1));
	}

}
