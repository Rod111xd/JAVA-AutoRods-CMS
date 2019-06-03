package bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import model.User;
import control.UserControl;
import java.util.List;
@ManagedBean(name="UserLogin")
@SessionScoped
public class UserLogin{
	private int id;
	private String name;
	private String password;
	private String email;
	private List<String> preferences;
	private String avatar;
	public boolean logged = false;
	
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
	public boolean getLogged() {
		return logged;
	}
	public void setLogged(boolean isLogged) {
		this.logged = isLogged;
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
	
	public String login() {
		User user = new User(this.getId(),this.getName(), this.getPassword(),this.getEmail(),this.getPreferences(),this.getAvatar());
		User newUser = new UserControl().login(user);
		if(newUser!=null) {
			System.out.println("deu certo");
			this.id = newUser.getId();
			this.name = newUser.getName();
			this.preferences = newUser.getPreferences();
			this.avatar = newUser.getAvatar();
			this.logged = true;
			return "index";
		}else {
			System.out.println("deu errado");
			return "login";
		}
			
	}
	
}
