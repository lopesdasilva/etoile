package controllers;



import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;

import flexjson.JSONSerializer;

import models.module.Content;
import models.module.Module;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;


public class ApiController extends Controller{

	
	
	public static Result getModules(){
	List<ObjectNode> results = new LinkedList<ObjectNode>();
	List<Module> modules = Module.find.all();
	 ObjectNode result;
	for (Module module: modules){
	   result = Json.newObject();
	  result.put("id", module.id);
	  result.put("name", module.name);
	  result.put("acronym", module.acronym);
	  result.put("duration", module.duration);
	  result.put("description", module.description);
	  result.put("short_description", module.short_description);
	  result.put("videoURL", module.videoURL);
	  result.put("imageURL", module.imageURL);
	  result.put("language",  module.language.name);
	  Map<String,JsonNode> contents = new HashMap<String,JsonNode>();
	  ObjectNode content;
	  for(Content modulecontent:module.contents){
	   content= Json.newObject();
	  content.put("id",modulecontent.id);
	  content.put("title",modulecontent.title);
	  content.put("text",modulecontent.text);
	  contents.put("contents", content);
	  }
	  result.putAll(contents);
	  results.add(result);
	}
//	return ok(Json.toJson(results));
	List<Module> obj = Module.find.all();
	JSONSerializer postDetailsSerializer = new JSONSerializer().include("contents");

	
	return ok(postDetailsSerializer.serialize(obj));
	
	
		
	}
}
