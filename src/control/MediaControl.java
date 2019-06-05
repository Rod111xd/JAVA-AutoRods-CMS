package control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import model.Media;

public class MediaControl {
	
	public boolean addMedia(Media media) {
		boolean result = false;
		try {
			Connection connect= new Conexao().abrirConexao();
			String sql="INSERT INTO Media(urlMedia,id_post,id_user) VALUES(?,?,?)";
			PreparedStatement ps= connect.prepareStatement(sql);
			ps.setString(1, media.getUrlMedia());
			if(media.getIdPost()!=0) {
				ps.setInt(2, media.getIdPost());
			}else {
				ps.setNull(2, Types.INTEGER);
			}
			if(media.getIdUser()!=0) {
				ps.setInt(3, media.getIdUser());
			}else {
				ps.setNull(3, Types.INTEGER);
			}
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
	
	public Media selectMediaByPost(int id_post) {
		Media result= null;
		try {
			Connection connect = new Conexao().abrirConexao();
			String sql="SELECT * FROM Media WHERE id_post=?";
			PreparedStatement ps= connect.prepareStatement(sql);
			ps.setInt(1, id_post);
			ResultSet rs= ps.executeQuery();
			if(rs.next()){
				result = new Media(rs.getInt("id"),rs.getString("urlMedia"),rs.getInt("id_post"),rs.getInt("id_user"));
			}
			new Conexao().fecharConexao(connect);
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return result;
	}
	
	public Media selectMediaByUser(int id_user) {
		Media result= null;
		try {
			Connection connect = new Conexao().abrirConexao();
			String sql="SELECT * FROM Media WHERE id_user=?";
			PreparedStatement ps= connect.prepareStatement(sql);
			ps.setInt(1, id_user);
			ResultSet rs= ps.executeQuery();
			if(rs.next()){
				result = new Media(rs.getInt("id"),rs.getString("urlMedia"),rs.getInt("id_post"),rs.getInt("id_user"));
			}
			new Conexao().fecharConexao(connect);
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return result;
	}
	
	public boolean editMedia(Media media) {
		boolean result = false;
		try {
			Connection connect= new Conexao().abrirConexao();
			String sql="UPDATE Media SET urlMedia=?,id_post=?,id_user=? WHERE id=?";
			PreparedStatement ps= connect.prepareStatement(sql);
			ps.setString(1, media.getUrlMedia());
			if(media.getIdPost()!=0) {
				ps.setInt(2, media.getIdPost());
			}else {
				ps.setNull(2, Types.INTEGER);
			}
			if(media.getIdUser()!=0) {
				ps.setInt(3, media.getIdUser());
			}else {
				ps.setNull(3, Types.INTEGER);
			}
			ps.setInt(4, media.getId());
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
	
	public boolean deleteMedia(int id) {
		boolean result = false;
		try {
			Connection connect= new Conexao().abrirConexao();
			String sql="DELETE FROM Media WHERE id=?";
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
