package model;

public class Media {
	private int id;
	private String urlMedia;
	private int idPost;
	private int idUser;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUrlMedia() {
		return urlMedia;
	}
	public void setUrlMedia(String urlMedia) {
		this.urlMedia = urlMedia;
	}
	public int getIdPost() {
		return idPost;
	}
	public void setIdPost(int idPost) {
		this.idPost = idPost;
	}
	public int getIdUser() {
		return idUser;
	}
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	
	public Media(int id, String urlMedia, int idPost, int idUser) {
		this.id = id;
		this.urlMedia = urlMedia;
		this.idPost = idPost;
		this.idUser = idUser;
	}
	
	
	
	
}
