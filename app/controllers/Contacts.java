package controllers;

import javax.persistence.Query;

import ch.qos.logback.core.Context;
import Models.Contact;
import Models.UserAccount;
import play.libs.F;
import play.mvc.Controller;
import play.mvc.Security;
import play.*;
import play.data.Form;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.*;
import views.html.*;


public class Contacts extends Controller{

	public static Query userIdQuery = JPA.em().createQuery("select id FROM UserAccount where login=:login");

	
    @Transactional
    @Security.Authenticated(Secured.class)
	public static Result add(){
		
		return ok(addContact.render(Form.form(Contact.class)));
	}

    @Transactional
    @Security.Authenticated(Secured.class)
	public static Result addContact() {
    	Form<Contact> form = Form.form(Contact.class).bindFromRequest();
    	
        if (form.hasErrors()) {
            return badRequest(addContact.render(form));
        }else{
        	
        	UserManager.addContact(form.get().getName(), form.get().getSurname(), form.get().getCompanyName(), form.get().getEmail(), form.get().getPhoneNumber(), session().get("login"));

        	return redirect(routes.Application.index());
        }
	}
	
    @Transactional
    public static Result delete(Long id){
    	Logger.debug("DELETING " +  id);
    	UserManager.removeContact(id);
    	return redirect(routes.Application.index());
    }
    
}
