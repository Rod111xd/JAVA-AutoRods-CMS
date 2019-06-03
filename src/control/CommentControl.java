package control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import model.Comment;
public class CommentControl {
	public boolean addComment(Comment comment) {
		boolean result = false;
		try {
			Connection connect= new Conexao().abrirConexao();
			String sql="INSERT INTO Comment(content,id_post,id_comment,id_user) VALUES(?,?,?,?)";
			PreparedStatement ps= connect.prepareStatement(sql);
			ps.setString(1, comment.getContent());
			ps.setInt(2, comment.getIdPost());
			if(comment.getIdComment()!=0) {
				ps.setInt(3, comment.getIdComment());
			}else {
				ps.setNull(3, Types.INTEGER);
			}
			ps.setInt(4, comment.getIdUser());
			
			if(!ps.execute()) {
				result=true;
			}
			new Conexao().fecharConexao(connect);
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return result;
	}
	
	public ArrayList<Comment> listCommentsByPost(int idPost){
		ArrayList<Comment> list=null;
		try {
			Connection connect= new Conexao().abrirConexao();
			String sql="SELECT * FROM Comment WHERE id_post=?";
			PreparedStatement ps= connect.prepareStatement(sql);
			ps.setInt(1, idPost);
			ResultSet rs= ps.executeQuery();
			if(rs!=null) {
				list = new ArrayList<Comment>();
				while(rs.next()) {
					Comment comment= new Comment(rs.getInt("id"),rs.getString("content"),rs.getInt("id_post"),rs.getInt("id_comment"),rs.getInt("id_user"));
					list.add(comment);
				}
				new Conexao().fecharConexao(connect);
			}
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return list;
	}
	
	public boolean editComment(Comment comment) {
		boolean result = false;
		try {
			Connection connect= new Conexao().abrirConexao();
			String sql="UPDATE Comment SET content=?,id_post=?,id_comment=?,id_user=? WHERE id=?";
			PreparedStatement ps= connect.prepareStatement(sql);
			ps.setString(1, comment.getContent());
			ps.setInt(2, comment.getIdPost());
			if(comment.getIdComment()!=0) {
				ps.setInt(3, comment.getIdComment());
			}else {
				ps.setNull(3, Types.INTEGER);
			}
			if(comment.getIdUser()!=0) {
				ps.setInt(4, comment.getIdUser());
			}else {
				ps.setNull(4, Types.INTEGER);
			}
			ps.setInt(5, comment.getId());
			if(!ps.execute()) {
				result=true;
			}
			new Conexao().fecharConexao(connect);
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return result;
	}
	
	public boolean deleteComment(int id) {
		boolean result = false;
		try {
			Connection connect= new Conexao().abrirConexao();
			String sql="DELETE FROM Comment WHERE id=?";
			PreparedStatement ps= connect.prepareStatement(sql);
			ps.setInt(1, id);
			if(!ps.execute()) {
				result=true;
			}
			new Conexao().fecharConexao(connect);
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return result;
	}
	
}
