package controllers;

import java.util.Date;


import controllers.secured.Secured;
import controllers.secured.SecuredProfessor;
import models.User;
import models.forum.Reply;
import models.forum.Topic;
import models.manytomany.Usertopic;
import models.module.Module;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

public class ForumController extends Controller {

	public static class Topic_form {

		public String title;

		public String reply;
	}

	public static class Reply_form {

		public String reply;
	}

	public static Result forum(String module_acronym){
		Module module = Module.findByAcronym(module_acronym);
		if (module==null){
			return redirect(routes.Application.modules());
		}

		if(session("email")!=null){
			User user = User.find.byId(session("email"));

			if(Secured.isStudent(user.email)) {

				if(!user.modules.contains(module)){
					return redirect(routes.Application.modules());
				}
				for(Topic topic: module.forum.topics){
					Usertopic usertopic= Usertopic.findByUserAndTopic(user.email, topic.id);
					if(usertopic==null){
						usertopic= new Usertopic();
						usertopic.topic=topic;
						usertopic.seen=false;
						usertopic.user=user;
						usertopic.save();
					}

				}
				module.forum.refresh();



				return ok(views.html.secured.forum.render(user,module));
			}
			if(SecuredProfessor.isProfessor(user.email) && SecuredProfessor.isOwner(user, module)){

				for(Topic topic: module.forum.topics){
					Usertopic usertopic= Usertopic.findByUserAndTopic(user.email, topic.id);

					if(usertopic==null){
						usertopic= new Usertopic();
						usertopic.topic=topic;
						usertopic.seen=false;
						usertopic.user=user;
						usertopic.save();
					}
					module.forum.refresh();
				}	
				return ok(views.html.professor.forum.render(user,module));


			}


		}
		module.forum.refresh();
		return ok(views.html.statics.forum.render(module));
	}


	public static Result addtopic(String module_acronym){

		Module module = Module.findByAcronym(module_acronym);
		User user = User.find.byId(session("email"));

		if (module==null ){
			System.out.println("The module does not exist.");
			return redirect(routes.Application.modules());
		}
		if((Secured.isStudent(user.email) && user.modules.contains(module))|| SecuredProfessor.isProfessor(user.email)) {
			Form<Topic_form> form = Form.form(Topic_form.class).bindFromRequest();


			Topic topic = new Topic();
			topic.date=new Date();
			topic.forum=module.forum;
			topic.starter=user;
			topic.title=form.get().title;
			topic.save();

			Usertopic usertopic= new Usertopic();
			usertopic.topic=topic;
			usertopic.user=user;
			usertopic.seen=false;
			usertopic.save();

			Reply reply= new Reply();
			reply.date=new Date();
			reply.topic=topic;
			reply.user=user;
			reply.text=form.get().reply;
			reply.save();

			topic.replies.add(reply);
			topic.topicsubscriptions.add(usertopic);
			topic.save();


			return redirect(routes.ForumController.topic(module.acronym, topic.id));
		}

		return redirect(routes.Application.module(module_acronym));
	}

	public static Result topic(String module_acronym,Long topic_id){

		Module module = Module.findByAcronym(module_acronym);
		if (module==null){
			return redirect(routes.Application.modules());
		}
		Topic topic = Topic.find.byId(topic_id);
		if(topic==null || !module.forum.topics.contains(topic)){
			return redirect(routes.ForumController.forum(module_acronym));
		}

		if(session("email")!=null){
			User user = User.find.byId(session("email"));



			if(Secured.isStudent(user.email)) {
				if(!user.modules.contains(module)){
					return redirect(routes.Application.modules());
				}

				Usertopic usertopic = Usertopic.findByUserAndTopic(user.email, topic_id);
				usertopic.seen=true;
				usertopic.save();
				topic.starter.refresh();
				for(Reply reply: topic.replies){
					reply.refresh();
					reply.user.refresh();
					reply.user.studentProfile.refresh();
				}
				user.studentProfile.refresh();
				return ok(views.html.secured.topic.render(user,module,topic));
			}
			if(SecuredProfessor.isProfessor(user.email) && SecuredProfessor.isOwner(user, module)){
				Usertopic usertopic = Usertopic.findByUserAndTopic(user.email, topic_id);
				usertopic.seen=true;
				usertopic.save();
				topic.starter.refresh();
				for(Reply reply: topic.replies){
					reply.refresh();
					reply.user.refresh();
					if(reply.user.account_type==0)
					reply.user.studentProfile.refresh();
					else
						reply.user.professorProfile.refresh();
				}
				user.professorProfile.refresh();
				return ok(views.html.professor.topic.render(user,module,topic));
			}
		}

		topic.starter.refresh();
		for(Reply reply: topic.replies){
			reply.refresh();
			reply.user.refresh();
			reply.user.studentProfile.refresh();
		}

		return ok(views.html.statics.topic.render(module,topic));
	}

	@Security.Authenticated(Secured.class)
	public static Result addReply(String module_acronym,Long topic_id){

		User user = User.find.byId(session("email"));
		Module module = Module.findByAcronym(module_acronym);
		if (module==null){
			System.out.println("The module does not exist.");
			return redirect(routes.Application.modules());
		}
		Topic topic = Topic.find.byId(topic_id);
		if(topic==null || !module.forum.topics.contains(topic)){
			return redirect(routes.ForumController.forum(module_acronym));
		}


		if((Secured.isStudent(user.email) && user.modules.contains(module))|| SecuredProfessor.isProfessor(user.email)) {
			Form<Reply_form> form = Form.form(Reply_form.class).bindFromRequest();
			Reply reply = new Reply();
			reply.text=form.get().reply;
			reply.user=user;
			reply.date=new Date();
			reply.topic=topic;
			reply.save();

			return redirect(routes.ForumController.topic(module.acronym, topic.id));
		}

		return redirect(routes.Application.module(module_acronym));
	}

}
