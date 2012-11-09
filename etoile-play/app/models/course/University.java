package models.course;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.ebean.Model;

@Entity
public class University extends Model {

	
	@Id
	@GeneratedValue
    @Formats.NonEmpty
	public Long id;
	
	@Constraints.Required
	public String name;
	
	
}
