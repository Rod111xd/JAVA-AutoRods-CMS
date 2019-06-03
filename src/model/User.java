package model;

import java.util.List;

public class User {
	private int id;
	private String name;
	private String password;
	private String email;
	private List<String> preferences;
	private String avatar;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public List<String> getPreferences() {
		return preferences;
	}
	public void setPreferences(List<String> preferences) {
		this.preferences = preferences;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public User(int id, String name, String password, String email, List<String> preferences,String avatar ) {
		this.setId(id);
		this.setName(name);
		this.setPassword(password);
		this.setEmail(email);
		this.setPreferences(preferences);
		this.setAvatar(avatar);
	}
	
	
	
}
