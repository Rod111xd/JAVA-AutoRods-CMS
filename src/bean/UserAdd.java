package bean;

import javax.faces.bean.ManagedBean;
import javax.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.util.List;

import model.User;
import control.UserControl;

@ManagedBean(name="UserAdd")
public class UserAdd {
	private int id;
	private String name;
	private String password;
	private String email;
	private List<String> preferences;
	private Part arquivo;
	
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
	public Part getArquivo() {
		return arquivo;
	}
	public void setArquivo(Part arquivo) {
		this.arquivo = arquivo;
	}
	
	public String addUser() throws IOException {
		if(!new UserControl().checkEmail(this.getEmail())) {
			
			User user = new User(this.getId(),this.getName(), this.getPassword(),this.getEmail(),this.getPreferences(),null);
			
			if(new UserControl().insertUser(user,arquivo,getFilename(arquivo))) {
				return "index";
			}else {
				return "login";
			}
			
		}else {
			return "login";
		}

	}
	
	public String getFilename(Part part) {
		for(String cd : part.getHeader("content-disposition").split(";")) {
			if(cd.trim().startsWith("filename")) {
				String filename = cd.substring(cd.indexOf("=") + 1).trim().replace("\"", "");
				return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1);
			}
		}
		return null;
	}
}
