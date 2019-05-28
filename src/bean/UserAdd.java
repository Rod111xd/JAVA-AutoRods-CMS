package bean;

import javax.faces.bean.ManagedBean;
import model.User;
import control.UserControl;

@ManagedBean(name="UserAdd")
public class UserAdd {
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
	
	public String addUser() {
		if(!new UserControl().checkEmail(this.getEmail())) {
			User user = new User(this.getId(),this.getName(), this.getPassword(),this.getEmail());
			if(new UserControl().insertUser(user)) {
				return "index";
			}else {
				return "login";
			}
		}else {
			return "login";
		}
			

	}
}
