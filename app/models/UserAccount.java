package models;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import play.Logger;

@Entity
public class UserAccount{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String role = "user";
	private String login;
	private String password;
	private Date registration = new Date();
	private byte[] picture;
	@OneToMany(mappedBy="userAccount")
	private List<Contact> contacts = new ArrayList<Contact>();
	
	public UserAccount() {
		
	}
	
	public UserAccount(String login, String password) {
		super();
		this.login = login;
		this.password = password;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
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
	public byte[] getPicture() {
		return picture;
	}

	public void setPicture(byte[] picture) {
		this.picture = picture;
	}
	public List<Contact> getContacts() {
		return contacts;
	}
	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}

	public Date getRegistration() {
		return registration;
	}
	
	public String getSimpleRegistration() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/M/yyyy");
		String registrationDate = dateFormat.format(registration);
		return registrationDate;
	}

	public void setRegistration(Date registration) {
		this.registration = registration;
	}

	
}
