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

import models.User;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import com.avaje.ebean.Ebean;
@Entity
public class Reply extends Model {
	
	@Id
	public Long id;

	@Constraints.Required
	@Constraints.MaxLength(value = 255)
	@Column(columnDefinition="TEXT")
	public String text;

	@ManyToOne
	public Topic topic;
	
	@ManyToOne
	public User user;

	
	@Constraints.Required
	public Date date;
	
	//public Date created;

	public static Model.Finder<Long, Reply> find = new Model.Finder<Long, Reply>(
			Long.class, Reply.class);

	public static List<Reply> getAllReplies() {
		List<Reply> replies = new ArrayList<Reply>();
		replies = Ebean.find(Reply.class).findList(); 
		return replies; 
	}
	
	public String dateToString(){
		Long yourmilliseconds = date.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm EE MMM yyyy",Locale.UK);
		GregorianCalendar calendar = new GregorianCalendar(TimeZone.getTimeZone("UK"));
		calendar.setTimeInMillis(yourmilliseconds);
		return sdf.format(calendar.getTime());
	}

}
