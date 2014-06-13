package com.tokyoanimation.models;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterModel {
	private final String USERNAME         = "UserName";
	private final String EMAIL            = "Email";
	private final String PASSWORD         = "Password";
	private final String CONFIRM_PASSWORD = "ConfirmPassword";
	
	private String username;
	private String email;
	private String password;
	private String confirmPassword;
	
	public RegisterModel(){}
	public RegisterModel(String username, String email, String password, String confirmPassword){
		this.setUsername(username);
		this.setEmail(email);
		this.setPassword(password);
		this.setConfirmPassword(confirmPassword);
	}
	
	public void setUsername(String username){
		this.username = username;
	}
	public void setEmail(String email){
		this.email = email;
	}
	public void setPassword(String password){
		this.password = password;
	}
	public void setConfirmPassword(String password){
		this.confirmPassword = password;
	}
	
	public String getUsername(){
		return this.username;
	}
	public String getEmail(){
		return this.email;
	}
	public String getPassword(){
		return this.password;
	}
	public String getConfirmPassword(){
		return this.confirmPassword;
	}
	
	public JSONObject toJson() throws JSONException{
		JSONObject obj = new JSONObject();
		obj.put(USERNAME, getUsername());
		obj.put(EMAIL, getEmail());
		obj.put(PASSWORD, getPassword());
		obj.put(CONFIRM_PASSWORD, getConfirmPassword());
		return obj;
	}
}
