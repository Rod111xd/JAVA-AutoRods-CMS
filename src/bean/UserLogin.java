package bean;

import javax.faces.bean.ManagedBean;
import model.User;
import control.UserControl;
import javax.servlet.http.HttpSession;
import javax.faces.context.FacesContext;

@ManagedBean(name="UserLogin")
public class UserLogin {
	private int id;
	private String name;
	private String password;
	private String email;
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
	
	public String login() {
		User user = new User(this.getId(),this.getName(), this.getPassword(),this.getEmail());
		User newUser = new UserControl().login(user);
		if(newUser!=null) {
			HttpSession session = ( HttpSession ) FacesContext.getCurrentInstance().getExternalContext().getSession( true );
			session.setAttribute( "user", newUser );
			System.out.println("deu certo");
			return "index";
		}else {
			System.out.println("deu errado");
			return "login";
		}
			
	}
}
