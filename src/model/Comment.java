package model;

public class Comment {
	private int id;
	private String content;
	private int idPost;
	private int idComment;
	private int idUser;
	
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
	
	public Comment(int id, String content, int idPost, int idComment, int idUser) {
		this.id = id;
		this.content = content;
		this.idPost = idPost;
		this.idComment = idComment;
		this.idUser = idUser;
	}
	
	
	
	
}
