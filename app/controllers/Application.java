package controllers;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;

import akka.event.Logging.Debug;
import play.*;
import play.data.Form;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import Models.Contact;
import Models.UserAccount;
import play.mvc.*;
import views.html.*;


public class Application extends Controller {
	
    @Transactional
    @Security.Authenticated(Secured.class)
    public static Result index() {
		String currentUser = session().get("login");
		List<Contact> contactList = UserManager.getContactList(currentUser);
		
    	return ok(index.render(currentUser, contactList));
    }
	
	public static Result register(){
		return ok(register.render(Form.form(RegisterForm.class)));
	}
	
	@Transactional
	public static Result registerAuth() {
		Form<RegisterForm> form = Form.form(RegisterForm.class).bindFromRequest();
		Logger.debug("Registering ");
		if(form.hasErrors()){
			Logger.debug("Exists");
			return badRequest(register.render(form));
		}else{
			return redirect(routes.Application.index());
		}
	}
    
    
    public static Result login() {
    	if(session().get("login") == null)
    		return ok(login.render(Form.form(LoginForm.class)));
    	else{
            return redirect(routes.Application.index());
    	}
    }

    public static Result logout() {
		Logger.debug(request().username());
        session().clear();

        return redirect(routes.Application.login());
    }

    @Transactional
    public static Result authenticate() {
    	Form<LoginForm> form = Form.form(LoginForm.class).bindFromRequest();
    	Logger.debug("Authenticating");
        if (form.hasErrors()) {
        	Logger.debug("Not authenticated");
            return badRequest(login.render(form));
        } 
        else {
        	Logger.debug("Authenticated");
        	session().clear();
        	session("login", form.get().login);
        	return redirect(routes.Application.index());
        }

    }
    
    public static Result userList() {
    	
    	return ok();
    }

    
}
