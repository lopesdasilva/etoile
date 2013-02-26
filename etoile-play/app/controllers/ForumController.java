package controllers;

import controllers.secured.Secured;
import models.User;
import models.curriculum.Category;
import models.forum.Reply;
import models.forum.Topic;
import models.manytomany.Usertopic;
import models.module.Module;
import play.mvc.Controller;
import play.mvc.Result;

public class ForumController extends Controller {

	
	
	public static Result forum(String module_acronym){
		
		Module module = Module.findByAcronym(module_acronym);
		if (module==null){
			System.out.println("The module does not exist.");
			return redirect(routes.Application.modules());
		}
		
		
		User user = User.find.byId(session("email"));
		if(Secured.isStudent(user.email)) {
			
			module.forum.refresh();
			
			return ok(views.html.secured.forum.render(user,module));
		}
		
		
		return redirect(routes.Application.module(module_acronym));
	}
	
	public static Result topic(String module_acronym,Long topic_id){
	
		Module module = Module.findByAcronym(module_acronym);
		if (module==null){
			System.out.println("The module does not exist.");
			return redirect(routes.Application.modules());
		}
		Topic topic = Topic.find.byId(topic_id);
		if(topic==null || !module.forum.topics.contains(topic)){
			return redirect(routes.ForumController.forum(module_acronym));
		}
		
		User user = User.find.byId(session("email"));
		if(Secured.isStudent(user.email)) {
			Usertopic usertopic = Usertopic.findByUserAndTopic(user.email, topic_id);
			usertopic.seen=true;
			usertopic.save();
			topic.starter.refresh();
			for(Reply reply: topic.replies){
				reply.refresh();
				reply.user.refresh();
				reply.user.studentProfile.refresh();
			}
			return ok(views.html.secured.topic.render(user,module,topic));
		}
		
		return redirect(routes.Application.module(module_acronym));
	}
			
}
