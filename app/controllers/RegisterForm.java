package controllers;

import play.Logger;
import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Required;

public class RegisterForm {
	
//	@Required
	@MinLength(3)
	@MaxLength(15)
	String login;
//	
//	@Required
	@MinLength(3)
	@MaxLength(15)
	String password;
	
	String matchingPassword;



	public String getLogin() {
		return login;
	}


	public void setLogin(String login) {
		this.login = login;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getMatchingPassword() {
		return matchingPassword;
	}


	public void setMatchingPassword(String matchingPassword) {
		this.matchingPassword = matchingPassword;
	}
	
	public String validate(){
		Logger.debug("FIRST PASS " + password + " SECOND " + matchingPassword);
		if(!password.equals(matchingPassword)){

			return "Passwords do not match";
		};
		
		if(!UserManager.addUser(login, password)){
			return "User already exists";
		};

		return null;
	}
}
