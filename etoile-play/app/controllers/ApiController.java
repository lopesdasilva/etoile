package controllers;



import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;


import flexjson.JSONSerializer;


import models.Blog;
import models.Student;
import models.User;
import models.manytomany.Usertest;
import models.module.Lesson;
import models.module.Module;
import models.test.Answer;
import models.test.Hypothesis;
import models.test.Test;
import models.test.question.Question;
import models.test.question.QuestionGroup;


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
	
	public static Result getNews(){
		List<Blog> obj = Blog.find.all();
		JSONSerializer postDetailsSerializer = new JSONSerializer().exclude("*.class");
		
		return ok(postDetailsSerializer.serialize(obj)).as("application/json");
	}

	public static Result getMyProfile(String acronym){
		Student obj = Student.findByAcronym(acronym);
		JSONSerializer postDetailsSerializer = new JSONSerializer().exclude("password","user","*.class");
		return ok(postDetailsSerializer.serialize(obj)).as("application/json");
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

				return ok(postDetailsSerializer.serialize(module)).as("application/json");

			} else{
				ObjectNode result = Json.newObject();
				result.put("status", "failure");

				return ok(result).as("application/json");
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
			
                System.out.println("tests size: "+lesson.tests.size());
                Usertest aux=null;
			for(Test test: lesson.tests){
                                System.out.println("usertest size: "+test.users.size());
				for( Usertest utest: test.users){
                    System.out.println(username);
					if(utest.user!=null){
                         System.out.println(utest.user.email);
                        if(utest.user.email==username){
                            aux=utest;
                        }
                    }
					test.users.remove(utest);
				}
                if(aux!=null){
                    test.users.add(aux);
                }
			}
                
			
			if (lesson!=null && module.lessons.contains(lesson)){
			
				JSONSerializer postDetailsSerializer = new JSONSerializer().include(
						"tests",
						"lessoncontents",
						"lessonalerts",
						"tests.users")
						.exclude(
								"module",
								"tests.users.user",
								"*.class");
			
				return ok(postDetailsSerializer.serialize(lesson)).as("application/json");
				}
			
			} else{
				ObjectNode result = Json.newObject();
				result.put("status", "failure");

				return ok(result).as("application/json");
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
						"test.groups",
						"test.groups.questions",
						"test.groups.questions.hypothesislist")
						.exclude(
								"lesson",
								"user",
								"test.lesson",
								"test.groups.questions.lesson",
								"test.groups.questions.user",
								"test.groups.questions.answerSuggestedByStudent",
								"test.groups.questions.isCopy",
								"test.groups.questions.hypothesislist.user",
								"test.groups.questions.hypothesislist.isCorrect",
								"test.groups.questions.hypothesislist.isSaved",
								"test.groups.questions.hypothesislist.questionImageURL",
								"*.class");
				Usertest userTest= Usertest.findByUserAndTest(username, test_id);

				for(QuestionGroup group: userTest.test.groups){
					for(Question question : group.questions){
						switch(question.typeOfQuestion){
						case 0:
							question.openanswer=Answer.findByUserTestAndQuestion(userTest.id, question.id);
							break;
						case 1:
							question.hypothesislist=Hypothesis.findByUserEmailAndQuestion(username, question.id);
							break;
						case 2:
							question.hypothesislist=Hypothesis.findByUserEmailAndQuestion(username, question.id);
							break;
						}
						
					}
				}
				
				if(userTest==null){
					//TODO create new userTest
				}
				return ok(postDetailsSerializer.serialize(userTest)).as("application/json");
				}
			}
			} else{
				ObjectNode result = Json.newObject();
				result.put("status", "failure");

				return ok(result).as("application/json");
			}
		}
		return badRequest("Expecting Json data.");
	}
}
