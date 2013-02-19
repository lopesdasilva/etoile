package models.test;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import models.User;
import models.continent.Continent;


import com.avaje.ebean.Ebean;


import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.ebean.Model;

@Entity
public class Evaluation extends Model {

	
	@Id
	@GeneratedValue
    @Formats.NonEmpty
	public Long id;
	
	@Constraints.Required
	public Long evaluation;
	
	@ManyToOne
	public Answer answer;

	@ManyToOne
	public User user;
	
	public static Model.Finder<Long, Evaluation> find = new Model.Finder<Long, Evaluation>(
			Long.class, Evaluation.class);

	public static List<Evaluation> getAllEvaluations() {
		List<Evaluation> evaluations = new ArrayList<Evaluation>();
		evaluations = Ebean.find(Evaluation.class)
				.findList(); 
		return evaluations; 
	}
}
