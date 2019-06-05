package control;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.Part;

import com.mysql.jdbc.Statement;

import model.Media;
import model.Post;

public class PostControl {
	public boolean addPost(Post post,Part arquivo,String arquivoNome) {
		boolean result = false;
		try {
			Connection connect= new Conexao().abrirConexao();
			String sql="INSERT INTO Post(title,subtitle,text,type,category,date,views,id_admin) VALUES(?,?,?,?,?,?,?,?)";
			PreparedStatement ps= connect.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, post.getTitle());
			ps.setString(2, post.getSubtitle());
			ps.setString(3, post.getText());
			ps.setString(4, post.getType());
			ps.setString(5, post.getCategory());
			ps.setString(6, post.getDate());
			ps.setInt(7, 0);
			ps.setInt(8, post.getIdAdmin());
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if(rs.next()) {
				String path = "C:\\Users\\Ronald\\eclipse-workspace\\AutoRods\\WebContent\\resources\\imgs\\posts\\";
				String userFolder = path+rs.getInt(1);
				File directory = new File(userFolder);
				if(!directory.exists()) {
					directory.mkdir();
				}
				String finalDirectory = userFolder+"\\"+arquivoNome; 
				arquivo.write(finalDirectory);
				String reducedFinalDirectory = "imgs/posts/"+rs.getInt(1)+"/"+arquivoNome;
				Media media = new Media(0,reducedFinalDirectory,rs.getInt(1),0);
				if(new MediaControl().addMedia(media)) {
					result=true;
				}
			}
			new Conexao().fecharConexao(connect);
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
		return result;
	}
	
	public ArrayList<Post> listPosts(){
		ArrayList<Post> list=null;
		try {
			Connection connect= new Conexao().abrirConexao();
			String sql="SELECT * FROM Post;";
			PreparedStatement ps= connect.prepareStatement(sql);
			ResultSet rs= ps.executeQuery();
			if(rs!=null) {
				list = new ArrayList<Post>();
				while(rs.next()) {
					Media md = new MediaControl().selectMediaByPost(rs.getInt("id"));
					Post post= new Post(rs.getInt("id"),rs.getString("title"),rs.getString("subtitle"),rs.getString("text"),rs.getString("type"),rs.getString("category"),rs.getString("date"),rs.getInt("views"),rs.getInt("id_admin"),md.getUrlMedia());
					list.add(post);
				}
				new Conexao().fecharConexao(connect);
			}
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return list;
	}
	
	public Post selectPost(int id) {
		Post result= null;
		try {
			Connection connect = new Conexao().abrirConexao();
			String sql="SELECT * FROM Post WHERE id=?";
			PreparedStatement ps= connect.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs= ps.executeQuery();
			if(rs.next()){
				Media md = new MediaControl().selectMediaByPost(rs.getInt("id"));
				result = new Post(rs.getInt("id"),rs.getString("title"),rs.getString("subtitle"),rs.getString("text"),rs.getString("type"),rs.getString("category"),rs.getString("date"),rs.getInt("views"),rs.getInt("id_admin"),md.getUrlMedia());
			}
			new Conexao().fecharConexao(connect);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return result;
	}
	
	public boolean editPost(Post post) {
		boolean result = false;
		try {
			Connection connect= new Conexao().abrirConexao();
			String sql="UPDATE Post SET title=?,subtitle=?,text=?,type=?,category=?,date=?,views=? WHERE id=?";
			PreparedStatement ps= connect.prepareStatement(sql);
			ps.setString(1, post.getTitle());
			ps.setString(2, post.getSubtitle());
			ps.setString(3, post.getText());
			ps.setString(4, post.getType());
			ps.setString(5, post.getCategory());
			ps.setString(6, post.getDate());
			ps.setInt(7, post.getViews());
			ps.setInt(8, post.getId());
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
	
	public boolean deletePost(int id) {
		boolean result = false;
		try {
			Connection connect= new Conexao().abrirConexao();
			String sql="DELETE FROM Post WHERE id=?";
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
