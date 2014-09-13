package controllers;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;

import models.Contact;
import models.UserAccount;
import akka.event.Logging.Debug;
import play.*;
import play.data.Form;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.*;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import views.html.*;


public class Application extends Controller {
	
    @Transactional
    @Security.Authenticated(Secured.class)
    public static Result index() {
		String currentUser = session().get("login");
		List<Contact> contactList = UserManager.getContactList(currentUser);
		
    	return ok(index.render(currentUser, contactList));
    }
    
    @Transactional
	public static Result profile() {
		UserAccount u = UserManager.getUser(session().get("login"));
		return ok(profile.render(u));
	}
    
    @Transactional
    public static Result getImage() {
    	UserAccount u = UserManager.getUser(session().get("login"));
    	ByteArrayInputStream img = null;
    	
    	if(u.getPicture() != null){
    		img = new ByteArrayInputStream(u.getPicture());
    	}else{
    		//no photo get from assets
    	}
    	
    	
    	return ok(img).as("image/jpeg");
    }
    
    @Transactional
    public static Result uploadPicture() {
		  MultipartFormData body = request().body().asMultipartFormData();
		  FilePart picture = body.getFile("picture");
		  if (picture != null) {
		    String fileName = picture.getFilename();
		    String contentType = picture.getContentType(); 
		    File file = picture.getFile();
		    Logger.debug(contentType);
		    if(contentType.contains("jpeg") || contentType.contains("png")){
		    	Logger.debug("GUT");
		    	UserManager.uploadPicture(file, session().get("login"));
		    }
		    return ok("File uploaded");
		  } else {
		    flash("error", "Missing file");
		    return redirect(routes.Application.index());    
		  }
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
    
    @Transactional
    @Security.Authenticated(Secured.class)
    public static Result userList() {
    	List<UserAccount> listOfUsers = UserManager.getListOfUsers();
    	
    	return ok(users.render(listOfUsers));
    }

    
}
