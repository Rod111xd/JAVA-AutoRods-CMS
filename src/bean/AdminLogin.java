package bean;

import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import model.Admin;
import control.AdminControl;

/**
* Bean para questões relacionadas à Login do Admin e sua sessão.
* @author Rodrigo da Silva Freitas <rodrigojato@hotmail.com>
* @package bean
*/

@ManagedBean(name="AdminLogin")
@SessionScoped
public class AdminLogin {
	private int id;
	private String name;
	private String password;
	private boolean logged = false;
	private String toForm = "";
	
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
	public String getToForm() {
		return toForm;
	}
	public void setToForm(String toForm) {
		this.toForm = toForm;
	}
	/**
	* Método para login do Admin.
	* @return String
	*/
	public String login() throws IOException {
		Admin admin = new Admin(this.getId(),this.getName(), this.getPassword());
		Admin newAdmin = new AdminControl().login(admin);
		if(newAdmin!=null) {
			System.out.println("deu certo");
			this.setId(newAdmin.getId());
			this.logged = true;
			this.setToForm("");
			return "index";
		}else {
			System.out.println("deu errado");
			FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage("loginAdminError", new FacesMessage("Nome ou senha errados!"));
            this.setToForm("<script>mudarFormulario('adminLogin')</script>");
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("UserLogin");
			return "login";
		}
			
	}
	
}
