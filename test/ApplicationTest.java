import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import Models.UserAccount;

import com.fasterxml.jackson.databind.JsonNode;

import controllers.UserManager;

import org.fest.assertions.AssertExtension;

import javax.persistence.Query; 

import org.junit.*;
import org.mockito.InjectMocks;

import play.Logger;
import play.mvc.*;
import play.test.*;
import play.data.DynamicForm;
import play.data.validation.ValidationError;
import play.data.validation.Constraints.RequiredValidator;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.i18n.Lang;
import play.libs.F;
import play.libs.F.*;
import play.twirl.api.Content;
import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;


/**
*
* Simple (JUnit) tests that can call all parts of a play app.
* If you are interested in mocking a whole application, see the wiki for more details.
*
*/
public class ApplicationTest {
	
	
    private static FakeApplication fakeApplication;
	private final UserManager um = new UserManager();
    private final String login = "Jareczek";
	private final String password = "SuperTajneHaslo";
	private final String contactName = "TajnyKontakt";
	private final String companyName = "Tajna Firma";
    
	@Before
	public void settingUp(){
		fakeApplication = fakeApplication();
		start(fakeApplication);
	}
	
	@After
	public void stopping(){
		stop(fakeApplication);
	}
	
    @Test
    public void simpleCheck() {
        int a = 1 + 1;
        assertThat(a).isEqualTo(2);
    }

    @Test
    public void renderTemplate() {
//        Content html = views.html.index.render("Your new application is ready.");
//        assertThat(contentType(html)).isEqualTo("text/html");
//        assertThat(contentAsString(html)).contains("Your new application is ready.");
    }
    
    @Test
    public void createUser(){
		JPA.withTransaction(new F.Callback0() {	
			@Override
			public void invoke() throws Throwable {	
		    	Query query = JPA.em().createQuery("select u from UserAccount u");
		    	int currentSize = query.getResultList().size();
		    	
		    	//UserAccount u = new UserAccount(login, password);
		    	boolean added = UserManager.addUser(login, password);
		    	
		    	Logger.debug("User was added: " + added);
		    			
				assertThat(currentSize + 1).isEqualTo(query.getResultList().size());
				
				//Delete after user was added or test will fail on second run
			}
		});
    }


}
