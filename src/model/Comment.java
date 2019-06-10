package model;

import java.util.ArrayList;

public class Comment {
	private int id;
	private String content;
	private int idPost;
	private int idComment;
	private int idUser;
	private String userName;
	private String urlMedia;
	private ArrayList<Comment> listComments;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getIdPost() {
		return idPost;
	}
	public void setIdPost(int idPost) {
		this.idPost = idPost;
	}
	public int getIdComment() {
		return idComment;
	}
	public void setIdComment(int idComment) {
		this.idComment = idComment;
	}
	public int getIdUser() {
		return idUser;
	}
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	public ArrayList<Comment> getListComments() {
		return listComments;
	}
	public void setListComments(ArrayList<Comment> listComments) {
		this.listComments = listComments;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUrlMedia() {
		return urlMedia;
	}
	public void setUrlMedia(String urlMedia) {
		this.urlMedia = urlMedia;
	}
	public Comment(int id, String content, int idPost, int idComment, int idUser,String usNm,String urlMd,ArrayList<Comment> listC) {
		this.id = id;
		this.content = content;
		this.idPost = idPost;
		this.idComment = idComment;
		this.idUser = idUser;
		this.userName = usNm;
		this.urlMedia = urlMd;
		this.listComments = listC;
	}
	
	
	
	
}
