package controllers;

import play.Logger;
import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Required;

public class LoginForm {
//	@Required
//	@MinLength(3)
//	@MaxLength(15)
	public String login;
	
//	@Required
//	@MinLength(6)
//	@MaxLength(15)
	public String password;

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
	
	public String validate(){

		if(!UserManager.authenticate(login, password)){
			return "Wrong login and/or password";
		};
		return null;
	}
	
}
