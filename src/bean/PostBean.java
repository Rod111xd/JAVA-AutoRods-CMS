package bean;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.servlet.http.Part;

import control.PostControl;
import model.Post;

@ManagedBean(name="PostBean")
public class PostBean {
	private ArrayList<Post> list = new PostControl().listPosts();
	private int id;
	private String title;
	private String subtitle;
	private String text;
	private String type;
	private String category;
	private String date;
	private int idAdmin;
	private String urlMedia;
	private Part arquivo;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSubtitle() {
		return subtitle;
	}
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getIdAdmin() {
		return idAdmin;
	}
	public void setIdAdmin(int idAdmin) {
		this.idAdmin = idAdmin;
	}
	public String getUrlMedia() {
		return urlMedia;
	}
	public void setUrlMedia(String urlMedia) {
		this.urlMedia = urlMedia;
	}
	public ArrayList<Post> getList() {
		return list;
	}
	public void setLista(ArrayList<Post> list) {
		this.list = list;
	}
	public Part getArquivo() {
		return arquivo;
	}
	public void setArquivo(Part arquivo) {
		this.arquivo = arquivo;
	}
	
	public String addPost(int idAdm) throws IOException {
		if(true) {
			System.out.println(this.getIdAdmin());
			Date dAtual = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dataAtual = sdf.format(dAtual);
			this.setDate(dataAtual);
			Post post = new Post(this.getId(),this.getTitle(), this.getSubtitle(),this.getText(),this.getType(),this.getCategory(),this.getDate(),0,idAdm,null);
			
			if(new PostControl().addPost(post,arquivo,getFilename(arquivo))) {
				return "index";
			}else {
				return "addPost";
			}
			
		}else {
			return "addPost";
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
