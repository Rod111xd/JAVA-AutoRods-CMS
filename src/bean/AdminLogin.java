package bean;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

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
	
	public void login() throws IOException {
		Admin admin = new Admin(this.getId(),this.getName(), this.getPassword());
		Admin newAdmin = new AdminControl().login(admin);
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		if(newAdmin!=null) {
			System.out.println("deu certo");
			this.setId(newAdmin.getId());
			this.logged = true;
			ec.redirect(ec.getRequestContextPath() + "/index.xhtml");
		}else {
			System.out.println("deu errado");
			ec.redirect(ec.getRequestContextPath() + "/login.xhtml");
		}
			
	}
	
}
