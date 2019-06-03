package bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import model.Admin;
import control.AdminControl;

@ManagedBean(name="AdminLogin")
@SessionScoped
public class AdminLogin {
	private int id;
	private String name;
	private String password;
	private boolean logged = false;
	
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
	public boolean getLogged() {
		return logged;
	}
	public void setLogged(boolean isLogged) {
		this.logged = isLogged;
	}
	
	public String login() {
		Admin admin = new Admin(this.getId(),this.getName(), this.getPassword());
		Admin newAdmin = new AdminControl().login(admin);
		if(newAdmin!=null) {
			System.out.println("deu certo");
			this.logged = true;
			return "index";
		}else {
			System.out.println("deu errado");
			return "login";
		}
			
	}
	
}
