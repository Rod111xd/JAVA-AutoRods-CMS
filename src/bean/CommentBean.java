package bean;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;

import control.CommentControl;
import control.PostControl;
import model.Comment;
import model.Post;

@ManagedBean(name="CommentBean")
@ViewScoped
public class CommentBean {
	private ArrayList<Comment> list;
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
	public ArrayList<Comment> getList() {
		return list;
	}
	public void setList(ArrayList<Comment> list) {
		this.list = list;
	}
	
	public void loadList(int idPost) {
		this.setList(new CommentControl().listCommentsByPost(idPost));
	}
	
	public String addComment(int idUser,int idPost) throws IOException {
		if(true) {
			Comment comment = new Comment(0,this.getContent(),idPost,0,idUser,null,null,null);
			
			if(new CommentControl().addComment(comment)) {
				return "index";
			}else {
				return "addComment";
			}
			
		}else {
			return "addComment";
		}
	}
	
	public String addReply(int idUser,int idPost) {
		System.out.println(this.id);
		if(true) {
			Comment comment = new Comment(0,this.getContent(),idPost,this.id,idUser,null,null,null);
			if(new CommentControl().addComment(comment)) {
				return "index";
			}else {
				return "addComment";
			}
			
		}else {
			return "addComment";
		}
	}
	
	public void deleteComment() {
		new CommentControl().deleteComment(this.id);
	}
	
}
