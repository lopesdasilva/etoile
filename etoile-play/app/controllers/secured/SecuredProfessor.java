package controllers.secured;

import controllers.routes;
import play.*;
import play.mvc.*;
import play.mvc.Http.*;

import models.*;

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
    	System.out.println(user_email);
        return (User.find.byId(user_email).account_type==1) ;
    }    
}