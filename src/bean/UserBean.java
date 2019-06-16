package bean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.servlet.http.Part;

import java.io.IOException;
import java.util.List;

import model.User;
import control.UserControl;

/**
* Bean para questões relacionadas ao usuário.
* @author Rodrigo da Silva Freitas <rodrigojato@hotmail.com>
* @package bean
*/

@ManagedBean(name="UserBean")
@ViewScoped
public class UserBean {
	private int id;
	private String name;
	private String password;
	private String passwordConfirm;
	private String email;
	private List<String> preferences;
	private Part arquivo;
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
	public String getPasswordConfirm() {
		return passwordConfirm;
	}
	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}
	public String getToForm() {
		return toForm;
	}
	public void setToForm(String toForm) {
		this.toForm = toForm;
	}
	/**
	* Método para adição de usuários.
	* @return String
	*/
	public String addUser() throws IOException {
		FacesContext ctx = FacesContext.getCurrentInstance();
        
		if(!new UserControl().checkEmail(this.getEmail())) {
			
			User user = new User(this.getId(),this.getName(), this.getPassword(),this.getEmail(),this.getPreferences(),null);
			if(this.getPassword().equals(this.getPasswordConfirm())) {
				if(new UserControl().insertUser(user,arquivo,getFilename(arquivo))) {
					this.setToForm("");
					return "index";
				}else {
					ctx.addMessage("registerError", new FacesMessage("Algo deu errado! Por favor, tente novamente."));
					this.setToForm("<script>mudarFormulario('registro')</script>");
					return "login";
				}
			}else {
				ctx.addMessage("registerError", new FacesMessage("Senha não bate com a sua confirmação!"));
				this.setToForm("<script>mudarFormulario('registro')</script>");
				return "login";
			}
		}else {
			ctx.addMessage("registerError", new FacesMessage("E-mail já cadastrado!"));
			this.setToForm("<script>mudarFormulario('registro')</script>");
			return "login";
		}

	}
	
	/**
	* Método para resgatar o nome do arquivo.
	* @param Part part Representa o arquivo em sí
	* @return String
	*/
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
