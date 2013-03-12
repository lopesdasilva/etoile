package controllers;



import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;


import flexjson.JSONSerializer;


import models.Student;
import models.User;
import models.module.Content;
import models.User;
import models.manytomany.Usertest;
import models.module.Module;

import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import sun.misc.BASE64Decoder;


public class ApiController extends Controller{

	
	
	public static Result getModules() {

	List<Module> obj = Module.find.all();
	JSONSerializer postDetailsSerializer = new JSONSerializer().include("contents").exclude("*.class");
	
	return ok(postDetailsSerializer.serialize(obj));

	}
	
	public static Result getMyProfile(String acronym){
		Student obj = Student.findByAcronym(acronym);
		JSONSerializer postDetailsSerializer = new JSONSerializer();
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
		
			return ok(postDetailsSerializer.serialize(user));
			
		} else{
		ObjectNode result = Json.newObject();
		result.put("status", "failure");
		
		return ok(result);
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
				return ok(result);
				} else{
					ObjectNode result = Json.newObject();
					result.put("status", "failure");
					result.put("error", "failed authentication");
					return ok(result);
				}
			
		}
		

		
	}
	

}
