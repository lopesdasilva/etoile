package models.continent;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.avaje.ebean.Ebean;

import models.module.Module;
import models.module.University;
import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.ebean.Model;


@Entity
public class Continent extends Model {

	@Id
	@GeneratedValue
    @Formats.NonEmpty
	public Long id;
	
	@Constraints.Required
	public String name;
	
	@Constraints.Required
	public String acronym;
	
	@Constraints.Required
	public String imageURL;
	
	
	@OneToMany
	public List<University> universities;
	
	public static Model.Finder<Long, Continent> find = new Model.Finder<Long, Continent>(
			Long.class, Continent.class);

	public static List<Continent> getAllContinents() {
		List<Continent> continents = new ArrayList<Continent>();
		continents = Ebean.find(Continent.class)
				.findList(); 
		return continents; 
	}
	
	public static Continent findByAcronym(String acronym) {
        return find.where().eq("acronym", acronym).findUnique();
    }
}
