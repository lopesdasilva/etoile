package controllers;



import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;


import flexjson.JSONSerializer;


import models.Student;
import models.User;
import models.module.Content;
import models.module.Lesson;
import models.User;
import models.manytomany.Usertest;
import models.module.Module;
import models.test.Test;

import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import sun.misc.BASE64Decoder;


public class ApiController extends Controller{



	public static Result getModules() {


	List<Module> obj = Module.find.all();
	JSONSerializer postDetailsSerializer = new JSONSerializer().include("contents").exclude("*.class");
	
	return ok(postDetailsSerializer.serialize(obj)).as("application/json");


	}

	public static Result getMyProfile(String acronym){
		Student obj = Student.findByAcronym(acronym);
		JSONSerializer postDetailsSerializer = new JSONSerializer().exclude("password","user","*.class");
		return ok(postDetailsSerializer.serialize(obj));
	}

	public static Result getDashboard() throws IOException{

		String auth=request().getHeader(AUTHORIZATION);
		if(auth!=null){

		auth=auth.substring(6);
		byte[] decodeAuth= new BASE64Decoder().decodeBuffer(auth);
		String[] credString= new String(decodeAuth,"UTF-8").split(":");
		
		String username=credString[0];
		String password=credString[1];
		
		if (User.authenticateSHA1(username, password)!=null){
			
			User user=User.findByEmail(username);
			JSONSerializer postDetailsSerializer = new JSONSerializer().include("modules","onGoingTests").exclude("password","*.class");
//			getOngoingTests
		
			return ok(postDetailsSerializer.serialize(user)).as("application/json");
			
		} else{
		ObjectNode result = Json.newObject();
		result.put("status", "failure");
		
		return ok(result).as("application/json");
		}

		}

		return badRequest("Expecting Json data.");
	}

	public static Result authenticate(){
		JsonNode json =request().body().asJson();
		if(json==null){
			return badRequest("Expecting Json data.");
		}
		else{
			String username= json.findPath("username").getTextValue();
			String password= json.findPath("password").getTextValue();
			if (username==null || password==null){
				return badRequest("Missing parameteres");
			}else if (User.authenticateSHA1(username, password)!=null){
				ObjectNode result = Json.newObject();
				result.put("status", "success");
				result.put("message", "all good!");
				return ok(result).as("application/json");
			} else{
				ObjectNode result = Json.newObject();
				result.put("status", "failure");
				result.put("error", "failed authentication");
				return ok(result).as("application/json");
			}

		}



	}

	public static Result getModule(String module_acronym) throws IOException{

		String auth=request().getHeader(AUTHORIZATION);
		if(auth!=null){
			auth=auth.substring(6);
			byte[] decodeAuth= new BASE64Decoder().decodeBuffer(auth);
			String[] credString= new String(decodeAuth,"UTF-8").split(":");

			String username=credString[0];
			String password=credString[1];
			Module module=Module.findByAcronym(module_acronym);
			if (User.authenticateSHA1(username, password)!=null && module!=null){

				
				JSONSerializer postDetailsSerializer = new JSONSerializer().include(
						"professors",
						"lessons",
						"contents",
						"bibliography",
						"categories",
						"language")
						.exclude("professors.user","*.class");

				return ok(postDetailsSerializer.serialize(module));

			} else{
				ObjectNode result = Json.newObject();
				result.put("status", "failure");

				return ok(result);
			}
		}
		return badRequest("Expecting Json data.");
	}
	
	public static Result getLesson(String module_acronym, String lesson_acronym) throws IOException{
		String auth=request().getHeader(AUTHORIZATION);
		if(auth!=null){
			auth=auth.substring(6);
			byte[] decodeAuth= new BASE64Decoder().decodeBuffer(auth);
			String[] credString= new String(decodeAuth,"UTF-8").split(":");

			String username=credString[0];
			String password=credString[1];
			Module module=Module.findByAcronym(module_acronym);
			if (User.authenticateSHA1(username, password)!=null && module!=null){
			Lesson lesson=Lesson.findByAcronym(lesson_acronym);
			if (lesson!=null && module.lessons.contains(lesson)){
			
				JSONSerializer postDetailsSerializer = new JSONSerializer().include(
						"tests",
						"lessoncontents",
						"lessonalerts")
						.exclude(
								"module",
								"*.class");
			
				return ok(postDetailsSerializer.serialize(lesson));
				}
			
			} else{
				ObjectNode result = Json.newObject();
				result.put("status", "failure");

				return ok(result);
			}
		}
		return badRequest("Expecting Json data.");
	}
	
	public static Result getTest(String module_acronym, String lesson_acronym,Long test_id) throws IOException{

		String auth=request().getHeader(AUTHORIZATION);
		if(auth!=null){
			auth=auth.substring(6);
			byte[] decodeAuth= new BASE64Decoder().decodeBuffer(auth);
			String[] credString= new String(decodeAuth,"UTF-8").split(":");

			String username=credString[0];
			String password=credString[1];
			Module module=Module.findByAcronym(module_acronym);
			if (User.authenticateSHA1(username, password)!=null && module!=null){
			Lesson lesson=Lesson.findByAcronym(lesson_acronym);
			if (lesson!=null && module.lessons.contains(lesson)){
			Test test=Test.find.byId(test_id);
				if(test!=null && lesson.tests.contains(test)){
				JSONSerializer postDetailsSerializer = new JSONSerializer().include(
						"groups")
						.exclude(
								"lesson",
								"*.class");
			
				return ok(postDetailsSerializer.serialize(test));
				}
			}
			} else{
				ObjectNode result = Json.newObject();
				result.put("status", "failure");

				return ok(result);
			}
		}
		return badRequest("Expecting Json data.");
	}
}
