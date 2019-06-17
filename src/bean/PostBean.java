package bean;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;

import control.AdminControl;
import control.CommentControl;
import control.MediaControl;
import control.PostControl;
import model.Admin;
import model.Post;

/**
* Bean para questões relacionadas à publicações.
* @author Rodrigo da Silva Freitas <rodrigojato@hotmail.com>
* @package bean
*/

@ManagedBean(name="PostBean")
@ViewScoped
public class PostBean {
	private ArrayList<ArrayList<Post>> list = this.sliceArray(new PostControl().listPosts());
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
	private boolean recommendation = false;
	
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
	
	public ArrayList<ArrayList<Post>> getList() {
		return list;
	}
	public void setLista(ArrayList<ArrayList<Post>> list) {
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
	public boolean isRecommendation() {
		return recommendation;
	}
	public void setRecommendation(boolean recommendation) {
		this.recommendation = recommendation;
	}
	
	
	/**
	* Método para adição de publicações.
	* @param int idAdm Vai indicar qual admin criou a publicação
	* @return String
	*/
	public String addPost(int idAdm) throws IOException {
		if(arquivo!=null && (arquivo.getContentType().endsWith("png") || arquivo.getContentType().endsWith("jpg") || arquivo.getContentType().endsWith("jpeg") || arquivo.getContentType().endsWith("mp4") || arquivo.getContentType().endsWith("wmv") || arquivo.getContentType().endsWith("mpg") || arquivo.getContentType().endsWith("avi"))) {
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
	
	/**
	* Método para resgatar o nome do arquivo .
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
	
	/**
	* Método para carregamento de uma determinada publicação.
	* @param int id Vai indicar qual publicação deve ser resgatada
	*/
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
	
	/**
	* Método para edição de publicações.
	* @param int id Vai indicar qual post será editado
	* @param int idAdm Vai indicar qual admin editará
	* @return String
	*/
	public String editPost(int id,int idAdm) {
		if(arquivo!=null && (arquivo.getContentType().endsWith("png") || arquivo.getContentType().endsWith("jpg") || arquivo.getContentType().endsWith("jpeg") || arquivo.getContentType().endsWith("mp4") || arquivo.getContentType().endsWith("wmv") || arquivo.getContentType().endsWith("mpg") || arquivo.getContentType().endsWith("avi"))) {
			System.out.println(this.getIdAdmin());
			Date dAtual = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dataAtual = sdf.format(dAtual);
			this.setDate(dataAtual);
			Post post = new Post(this.getId(),this.getTitle(), this.getSubtitle(),this.getText(),this.getType(),this.getCategory(),this.getDate(),0,idAdm,null);
			
			if(new PostControl().editPost(post,arquivo,getFilename(arquivo),this.urlMedia)) {
				return "index";
			}else {
				return null;
			}
			
		}else {
			return null;
		}
	}
	
	
	/**
	* Método para remoção de publicações.
	* @param int idr Vai indicar qual publicação será removida
	*/
	public void delete(int id) throws IOException {
		new MediaControl().deleteMedia(id);
		new CommentControl().deleteCommentsByPost(id);
		new PostControl().deletePost(id);
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(ec.getRequestContextPath() + "/index.xhtml");
	}
	
	/**
	* Método para remoção de comentários sem parâmetros.
	*/
	public void deletePost() throws IOException {
		new MediaControl().deleteMedia(this.id);
		new CommentControl().deleteCommentsByPost(this.id);
		new PostControl().deletePost(this.id);
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(ec.getRequestContextPath() + "/index.xhtml");
	}
	
	/**
	* Método para seleção de publicações a partir da pesquisa.
	*/
	public void searchTitle() {
		this.setCategory("");
		this.setRecommendation(false);
		try {
			Thread.sleep(200);
		}catch(InterruptedException e) {
			e.getMessage();
		}
		ArrayList<Post> postList = null;
		if(pesquisa==null) {
			postList = new PostControl().listPosts();
			System.out.println("pesquisa nulo");
		}else {
			postList = new PostControl().listPostsSearchTitle(pesquisa);
			System.out.println("pesquisa preenchida");
		}
		this.setLista(sliceArray(postList));
	}
	
	
	/**
	* Método para seleção de publicações a partir da categoria.
	* @param String cat Vai indicar qual categoria será criterizada
	*/
	public void searchCategory(String cat) {
		this.setPesquisa("");
		this.setRecommendation(false);
		try {
			Thread.sleep(200);
		}catch(InterruptedException e) {
			e.getMessage();
		}
		this.setCategory(new PostControl().translateCategory(cat));
		ArrayList<Post> postList = null;
		postList = new PostControl().listPostsSearchCategory(cat);
		this.setLista(sliceArray(postList));
	}
	
	/**
	* Método para seleção de publicações a partir da recomendação do usuário.
	* @param List<String> pref Vai indicar as preferências do usuário
	*/
	public void searchRecommendation(List<String> pref) {
		this.setPesquisa("");
		this.setCategory("");
		this.setRecommendation(true);
		try {
			Thread.sleep(200);
		}catch(InterruptedException e) {
			e.getMessage();
		}
		ArrayList<Post> postList = null;
		postList = new PostControl().listPostsByRecommendation(pref);
		System.out.println("ó o tamanho: "+postList.size());
		this.setLista(sliceArray(postList));
	}
	
	/**
	* Método para dividir uma lista em várias.
	* @param ArrayList<Post> Lista que vai ser cortada
	* @return ArrayList<ArrayList<Post>>
	*/
	public ArrayList<ArrayList<Post>> sliceArray(ArrayList<Post> lst) {
		System.out.println(lst.size());
		ArrayList<ArrayList<Post>> arraySliced = new ArrayList<ArrayList<Post>>();
		ArrayList<Post> arPst = new ArrayList<Post>();
		int i=1;
		for(Post item :lst) {
			arPst.add(item);
			if(i==lst.size()) {
				System.out.println("doinho1");
				arraySliced.add(arPst);
				break;
			}else if(i%10==0) {
				System.out.println("doinho2");
				arraySliced.add(arPst);
				arPst = new ArrayList<Post>();
			}
			i=i+1;
		}
		
		this.setLista(arraySliced);
		return arraySliced;
	}
	
	/**
	* Método para limpar a pesquisa.
	*/
	public void cleanSearch() {
		this.setPesquisa("");
		this.setCategory("");
		this.setRecommendation(false);
		searchTitle();
	}
	
	/**
	* Método para incrementar o índice.
	* @param ind Índice a ser incrementado
	* @return int
	*/
	public int sumIndex(int ind) {
		return ind+1;
	}
	
	/**
	* Método para saber se é video ou imagem.
	* @param String urlMd Vai indicar a url da mídia
	* @return String
	*/
	public String mediaType(String urlMd) {
		if(urlMd.endsWith("png") || urlMd.endsWith("jpg") || urlMd.endsWith("jpeg")) {
			return "image";
		}else if(urlMd.endsWith("mp4") || urlMd.endsWith("wmv") || urlMd.endsWith("mpg") || urlMd.endsWith("avi")) {
			return "video";
		}
		return "image";
	}

	/**
	* Método para resgatar o tipo MYME do vídeo.
	* @param String urlMd Vai indicar a url da mídia
	* @return String
	*/
	public String getVideoType(String urlMd) {
		if(urlMd.endsWith("mp4")) {
			return "video/mp4";
		}else if(urlMd.endsWith("wmv")) {
			return "video/wmv";
		}else if(urlMd.endsWith("mpg")) {
			return "video/mpg";
		}else if(urlMd.endsWith("avi")) {
			return "video/avi";
		}
		return "video/mp4";
		
	}
}
