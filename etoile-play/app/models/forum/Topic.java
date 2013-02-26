package models.forum;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import models.User;
import models.manytomany.Usertopic;


import play.data.validation.Constraints;
import play.db.ebean.Model;

import com.avaje.ebean.Ebean;
@Entity
public class Topic extends Model {
	
	@Id
	public Long id;
	
	@Constraints.Required
	public String title;
	@ManyToOne
	public Forum forum;

	@ManyToOne
	public User starter;

	@Constraints.Required
	public Date date;

	@OneToMany
	public List<Reply> replies;
	
	@OneToMany
	public List<Usertopic> topicsubscriptions;

	//public Date created;

	public static Model.Finder<Long, Topic> find = new Model.Finder<Long, Topic>(
			Long.class, Topic.class);

	public static List<Topic> getAllTopics() {
		List<Topic> topics = new ArrayList<Topic>();
		topics = Ebean.find(Topic.class).findList(); 
		return topics; 
	}

	public static List<Topic> findByForum(Long forum_id){
		return find.where().eq("forum_id", forum_id).findList();
	}

	public String dateToString(){
		Long yourmilliseconds = date.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("EE MMM yyyy",Locale.UK);
		GregorianCalendar calendar = new GregorianCalendar(TimeZone.getTimeZone("UK"));
		calendar.setTimeInMillis(yourmilliseconds);
		return sdf.format(calendar.getTime());
	}

}
