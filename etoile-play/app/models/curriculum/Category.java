package models.curriculum;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import models.module.Module;

import com.avaje.ebean.Ebean;

import play.db.ebean.*;
import play.data.validation.Constraints;
import play.db.ebean.Model;

@Entity
public class Category extends Model {

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long id;


	@Constraints.Required
	@Column(unique = true)
	public String keyword;
	
	@Constraints.Required
	public String name;
	
	@Constraints.Required
	public String description;
	
	@ManyToMany(mappedBy="categories")
    @OrderBy("id")
	public List<Module> modules;
	
	@ManyToMany(cascade = {CascadeType.ALL})
    @OrderBy("id")
    public List<Curriculummodule> curriculummodules;
	
	public static Model.Finder<Long, Category> find = new Model.Finder<Long, Category>(
			Long.class, Category.class);

}
