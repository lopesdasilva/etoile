package controllers.secured;

import controllers.routes;
import play.*;
import play.mvc.*;
import play.mvc.Http.*;

import models.*;
import models.module.Module;

public class SecuredProfessor extends Security.Authenticator {
    
    @Override
    public String getUsername(Context ctx) {
        return ctx.session().get("email");
    }
    
    @Override
    public Result onUnauthorized(Context ctx) {
        return redirect(routes.Application.index());
    }
    
    // Access rights
    
    public static boolean isProfessor(String user_email) {
        return (User.find.byId(user_email).account_type==1) ;
    }

	public static boolean isOwner(User user, Module module) {
		if (user.professorProfile.modules.contains(module))
			return true;
		return false;
	}    
}