package bean;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

import control.CommentControl;
import control.PostControl;
import model.Comment;
import model.Post;

/**
* Bean para questões relacionadas à comentários.
* @author Rodrigo da Silva Freitas <rodrigojato@hotmail.com>
* @package bean
*/

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
	
	/**
	* Método para adição de comentários.
	* @param int idUser Vai indicar qual usuário realizou o comentário
	* @param int idPost Vai indicar qual post foi comentado
	* @return String
	*/
	public void addComment(int idUser,int idPost) throws IOException {
		FacesContext ctx = FacesContext.getCurrentInstance();
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		if(this.getContent()!=null && this.getContent()!="") {
			Comment comment = new Comment(0,this.getContent(),idPost,0,idUser,null,null,null);
			
			if(new CommentControl().addComment(comment)) {
				ec.redirect(ec.getRequestContextPath() + "/" + "content.xhtml?id="+idPost);
			}else {
				ctx.addMessage("addCommentError", new FacesMessage("Seu comentário não deu certo!"));
				ec.redirect(ec.getRequestContextPath() + "/" + "content.xhtml?id="+idPost);
			}
			
		}else {
			ctx.addMessage("addCommentError", new FacesMessage("Seu comentário não deu certo!"));
			ec.redirect(ec.getRequestContextPath() + "/" + "content.xhtml?id="+idPost);
		}
	}
	
	
	/**
	* Método para adição de respostas.
	* @param int idUser Vai indicar qual usuário realizou a resposta
	* @param int idPost Vai indicar qual post cujo comentário foi respondido
	* @return String
	*/
	public void addReply(int idUser,int idPost) throws IOException {
		FacesContext ctx = FacesContext.getCurrentInstance();
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		if(this.getContent()!=null && this.getContent()!="") {
			Comment comment = new Comment(0,this.getContent(),idPost,this.id,idUser,null,null,null);
			if(new CommentControl().addComment(comment)) {
				ec.redirect(ec.getRequestContextPath() + "/" + "content.xhtml?id="+idPost);
			}else {
				ctx.addMessage("addReplyError", new FacesMessage("Sua resposta não deu certo!"));
				ec.redirect(ec.getRequestContextPath() + "/" + "content.xhtml?id="+idPost);
			}
			
		}else {
			ctx.addMessage("addReplyError", new FacesMessage("Sua resposta não deu certo!"));
			ec.redirect(ec.getRequestContextPath() + "/" + "content.xhtml?id="+idPost);
		}
	}
	
	/**
	* Método para remoção de comentários.
	*/
	public void deleteComment(int idPost) throws IOException {
		new CommentControl().deleteComment(this.id);
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		ec.redirect(ec.getRequestContextPath() + "/" + "content.xhtml?id="+idPost);
	}
	
}
