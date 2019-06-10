package bean;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;

import control.AdminControl;
import control.MediaControl;
import control.PostControl;
import model.Admin;
import model.Post;

@ManagedBean(name="PostBean")
@ViewScoped
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
	private int views;
	private String urlMedia;
	private Part arquivo;
	private String adminName;
	private String pesquisa;
	
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
	public String getAdminName() {
		return adminName;
	}
	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}
	public int getViews() {
		return views;
	}
	public void setViews(int views) {
		this.views = views;
	}
	public String getPesquisa() {
		return pesquisa;
	}
	public void setPesquisa(String pesquisa) {
		this.pesquisa = pesquisa;
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
	
	public void loadId(int id) throws IOException, ParseException {
		Post post = new PostControl().selectPost(id);
		if(post!=null) {
			this.setId(post.getId());
			this.setTitle(post.getTitle());
			this.setSubtitle(post.getSubtitle());
			this.setText(post.getText());
			this.setType(post.getType());
			this.setCategory(post.getCategory());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			Date data = sdf.parse(post.getDate());
			this.setDate(sdf2.format(data));
			this.setIdAdmin(post.getIdAdmin());
			this.setViews(post.getViews());
			this.setUrlMedia(post.getUrlMedia());
			Admin adm = new AdminControl().selectAdmin(post.getIdAdmin());
			this.setAdminName(adm.getName());
			new PostControl().addView(post.getId());
		}else {
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
	        ec.redirect(ec.getRequestContextPath() + "/index.xhtml");
		}
	}
	
	public String editPost(int id,int idAdm) {
		if(true) {
			System.out.println(this.getIdAdmin());
			Date dAtual = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dataAtual = sdf.format(dAtual);
			this.setDate(dataAtual);
			Post post = new Post(this.getId(),this.getTitle(), this.getSubtitle(),this.getText(),this.getType(),this.getCategory(),this.getDate(),0,idAdm,null);
			
			if(new PostControl().editPost(post,arquivo,getFilename(arquivo))) {
				return "index";
			}else {
				return "addPost";
			}
			
		}else {
			return "addPost";
		}
	}
	
	public void delete(int id) throws IOException {
		new MediaControl().deleteMedia(id);
		new PostControl().deletePost(id);
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(ec.getRequestContextPath() + "/index.xhtml");
	}
	
	public void searchTitle() {
		ArrayList<Post> postList = null;
		if(pesquisa==null) {
			postList = new PostControl().listPosts();
			System.out.println("pesquisa nulo");
		}else {
			postList = new PostControl().listPostsSearchTitle(pesquisa);
			System.out.println("pesquisa preenchida");
		}
		this.setLista(postList);
	}
	
	public void cleanSearch() {
		this.setPesquisa("");
		searchTitle();
	}
	
}
