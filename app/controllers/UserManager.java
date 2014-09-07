package controllers;

import java.util.ArrayList;
import java.util.List;

import javassist.bytecode.Descriptor.Iterator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.ejb.EntityManagerFactoryImpl;

import play.Logger;
import play.db.jpa.JPA;
import play.libs.F;
import play.db.jpa.Transactional;
import Models.Contact;
import Models.UserAccount;

public class UserManager {
	
	public static boolean authenticated;
	public static boolean exists;
	public static Query userIdQuery = JPA.em().createQuery("select id FROM UserAccount where login=:login");
	
	public static boolean addUser(String login, String password){
		final String currentLogin = login;
		final String currentPasssword = password;
		boolean added = false;
		 
		
//		try {
//			added =  JPA.withTransaction(new F.Function0<Boolean>() {
//
//			@Override
//			public Boolean apply() throws Throwable {
					
	  			Query q = JPA.em().createQuery("select u FROM UserAccount u where login=:login").setParameter("login", currentLogin);
	  			Logger.debug("Checking if user exists");
	  			if(q.getResultList().isEmpty()){
	  				exists = false;
	  			}else{
	  				exists = true;
	  			}

				if(!exists){
					UserAccount u = new UserAccount(currentLogin, currentPasssword);
					JPA.em().persist(u);
					
					return true;
				}
				else{
					return false;
				}
//			}
//			});
//		} catch (Throwable e) {
//			e.printStackTrace();
//		}
//	   
//	   return added;
	}
	
	public static boolean authenticate(String login, String password){
		final String currentLogin = login;
		final String currentPasssword = password;
		boolean authenticated = false;
		

//		try {
//			authenticated = JPA.withTransaction(new F.Function0<Boolean>() {
//
//				@Override
//				public Boolean apply() throws Throwable {
		  			String serverPass = null;
		  			Query q = JPA.em().createQuery("select password FROM UserAccount where login=:login").setParameter("login", currentLogin);
		  			Logger.debug("Checking password");
		  			
		  			if(!q.getResultList().isEmpty()){
		  				serverPass = (String) q.getResultList().get(0);
		  			}
		  			
		  			if(currentPasssword.equals(serverPass)){
		  				return true;
		  			}else {
		  				return false;
		  			}
		
//				}
//				
//			});
//		} catch (Throwable e) {
//			e.printStackTrace();
//		}
//		  			
//		  			
//		return authenticated;
	}
	

	public static List<Contact> getContactList(String login) {
		final List<Contact> contactList = new ArrayList<Contact>();
		final String currentLogin = login;
//		
//		JPA.withTransaction(new F.Callback0() {
//			
//			@Override
//			public void invoke() throws Throwable {
				Query userIdQuery = JPA.em().createQuery("select id FROM UserAccount where login=:login").setParameter("login", currentLogin);
				Long id = (Long) userIdQuery.getResultList().get(0);
				Query contactsQuery = JPA.em().createQuery(" FROM Contact where useraccount_id=:id").setParameter("id", id);
			
				for(Object c : contactsQuery.getResultList()){
					contactList.add((Contact) c);
				}
					
//			}
//		});
//		getListOfUsers();
		
		return contactList;
	}
	
	public static List<UserAccount> getListOfUsers() {
		final List<UserAccount> accountList = new ArrayList<UserAccount>();
		
//		JPA.withTransaction(new F.Callback0() {
//			
//			@Override
//			public void invoke() throws Throwable {
				Long count = (Long) JPA.em().createQuery("select COUNT(u) FROM UserAccount u").getSingleResult();
				Logger.debug("Number of users: " + count);
				
				Query usersQuery = JPA.em().createQuery(" FROM UserAccount u");
				
				for(Object u : usersQuery.getResultList()){
					accountList.add((UserAccount) u);
				}
//			
//					
//			}
//		});
		
		
		return accountList;
		
	}
	

	public static void addContact(final String name, final String surname, final String companyName, final String email, final String phoneNumber, final String login) {
		
//		JPA.withTransaction(new F.Callback0() {
//			@Override
//			public void invoke() throws Throwable {
//			
		Session session = (Session) JPA.em().getDelegate();
	    Session s = session.getSessionFactory().getCurrentSession();
	    s.beginTransaction();

				Logger.debug("Adding contact");
				//Long id = (long) userIdQuery.setParameter("login", login).getResultList().get(0);
				Long id = (long) JPA.em().createQuery("select id FROM UserAccount where login=:login").setParameter("login", login).getResultList().get(0);
				Logger.debug("Adding error?");
				Contact c = new Contact();
				c.setCompanyName(companyName);
				c.setEmail(email);
				c.setName(name);
				c.setPhoneNumber(phoneNumber);
				c.setSurname(surname);
				c.setUserAccount(JPA.em().find(UserAccount.class, id));
				JPA.em().persist(c);
//				Session session = (Session) JPA.em().getDelegate();
//				session.beginTransaction();
//				session.persist(c);
//				session.close();
//				
//				}
//			});
		
	}
}
