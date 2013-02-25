package models.manytomany;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import models.User;
import models.forum.Topic;
import play.db.ebean.Model;


@Entity
public class Usertopic extends Model {
	
	@Id
	@GeneratedValue
	public Long id;
	
	@ManyToOne
	public Topic topic;
	
	@ManyToOne
	public User user;


	public boolean seen=true;
	

	public static Model.Finder<Long, Usertopic> find = new Model.Finder<Long, Usertopic>(
			Long.class, Usertopic.class);

	public static Usertopic findByUserAndTopic(String user_email, Long topic_id){
		   return find.where().eq("user_email", user_email).eq("topic_id",topic_id).findUnique();
	}
	
}
