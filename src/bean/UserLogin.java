package bean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import model.User;
import control.UserControl;

import java.io.IOException;
import java.util.List;

/**
* Bean para questões relacionadas ao login do usuário e sua sessão.
* @author Rodrigo da Silva Freitas <rodrigojato@hotmail.com>
* @package bean
*/

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
	private String toForm = "<script>mudarFormulario('login')</script>";
	
	
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
	public String getToForm() {
		return toForm;
	}
	public void setToForm(String toForm) {
		this.toForm = toForm;
	}
	/**
	* Método para login do usuário.
	* @return String
	*/
	public String login() throws IOException {
		User user = new User(this.getId(),this.getName(), this.getPassword(),this.getEmail(),this.getPreferences(),this.getAvatar());
		User newUser = new UserControl().login(user);
        
		if(newUser!=null) {
			System.out.println("deu certo");
			this.id = newUser.getId();
			this.name = newUser.getName();
			this.preferences = newUser.getPreferences();
			this.avatar = newUser.getAvatar();
			this.logged = true;
			this.setToForm("");
			return "index";
		}else {
			System.out.println("deu errado");
			FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage("loginError", new FacesMessage("E-mail ou senha errados!"));
            this.setToForm("<script>mudarFormulario('login')</script>");
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("AdminLogin");
			return "login";
		}
			
	}
	
}
