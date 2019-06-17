package control;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.apache.tomcat.util.http.fileupload.FileUtils;

import model.Media;

/**
* Controle para a M�dia.
* @author Rodrigo da Silva Freitas <rodrigojato@hotmail.com>
* @package control
*/
public class MediaControl {
	
	/**
	* M�todo para fazer a adi��o de M�dia
	* @param Media media M�dia a ser adicionada
	* @return boolean
	*/
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
	
	/**
	* M�todo para fazer sele��o de M�dia a partir da publica��o
	* @param int id_post Id da publica��o
	* @return Media
	*/
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
	
	/**
	* M�todo para fazer sele��o de M�dia a partir do usu�rio
	* @param int id_user Id do usu�rio
	* @return Media
	*/
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
	
	/**
	* M�todo para fazer a edi��o da M�dia
	* @param Media media M�dia em quest�o para ser editada
	* @return boolean
	*/
	public boolean editMedia(Media media) {
		boolean result = false;
		try {
			Connection connect= new Conexao().abrirConexao();
			String sql="UPDATE Media SET urlMedia=?,id_user=? WHERE id_post=?";
			PreparedStatement ps= connect.prepareStatement(sql);
			ps.setString(1, media.getUrlMedia());
			if(media.getIdUser()!=0) {
				ps.setInt(2, media.getIdUser());
			}else {
				ps.setNull(2, Types.INTEGER);
			}
			ps.setInt(3, media.getIdPost());
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
	
	/**
	* M�todo para fazer a remo��o de M�dia
	* @param int id Id da m�dia
	* @return boolean
	*/
	public boolean deleteMedia(int id) {
		boolean result = false;
		try {
			Connection connect= new Conexao().abrirConexao();
			String sql="DELETE FROM Media WHERE id_post=?";
			PreparedStatement ps= connect.prepareStatement(sql);
			ps.setInt(1, id);
			if(!ps.execute()) {
				String path = "C:\\Users\\Ronald\\eclipse-workspace\\AutoRods\\WebContent\\resources\\imgs\\posts\\"+id;
				deleteFolder(path);
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
	
	/**
	* M�todo para apagar pasta
	* @param String path Endere�o da pasta
	*/
	public void deleteFolder(String path) throws IOException {
		FileUtils.deleteDirectory(new File(path));
	}
}
