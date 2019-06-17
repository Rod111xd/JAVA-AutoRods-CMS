package control;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Part;

import com.mysql.jdbc.Statement;

import model.Media;
import model.Post;

/**
* Controle para a Publicação.
* @author Rodrigo da Silva Freitas <rodrigojato@hotmail.com>
* @package control
*/
public class PostControl {
	
	/**
	* Método para fazer a adição de publicações
	* @param Post post Publicação a ser adicionada
	* @param Part arquivo Arquivo de mídia a ser adicionada
	* @param String arquivoNome Nome do arquivo de mídia a ser adicionado
	* @return boolean
	*/
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
	
	/**
	* Método para fazer a seleção de publicações
	* @return ArrayList<Post>
	*/
	public ArrayList<Post> listPosts(){
		System.out.println("gfdhdfhdh");
		ArrayList<Post> list=null;
		try {
			Connection connect= new Conexao().abrirConexao();
			String sql="SELECT * FROM Post ORDER BY date DESC";
			PreparedStatement ps= connect.prepareStatement(sql);
			ResultSet rs= ps.executeQuery();
			if(rs!=null) {
				list = new ArrayList<Post>();
				while(rs.next()) {
					Media md = new MediaControl().selectMediaByPost(rs.getInt("id"));
					Post post= new Post(rs.getInt("id"),rs.getString("title"),rs.getString("subtitle"),rs.getString("text"),translateType(rs.getString("type")),translateCategory(rs.getString("category")),rs.getString("date"),rs.getInt("views"),rs.getInt("id_admin"),md.getUrlMedia());
					list.add(post);
				}
				
			}
			new Conexao().fecharConexao(connect);
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return list;
	}
	
	/**
	* Método para fazer a seleção de publicações a partir de um título
	* @param String title Título a ser pesquisado
	* @return ArrayList<Post>
	*/
	public ArrayList<Post> listPostsSearchTitle(String title){
		ArrayList<Post> list=null;
		try {
			Connection connect= new Conexao().abrirConexao();
			String sql="SELECT * FROM Post WHERE title LIKE ? ORDER BY date DESC";
			PreparedStatement ps= connect.prepareStatement(sql);
			ps.setString(1, "%"+title+"%");
			ResultSet rs= ps.executeQuery();
			if(rs!=null) {
				list = new ArrayList<Post>();
				while(rs.next()) {
					Media md = new MediaControl().selectMediaByPost(rs.getInt("id"));
					Post post= new Post(rs.getInt("id"),rs.getString("title"),rs.getString("subtitle"),rs.getString("text"),translateType(rs.getString("type")),translateCategory(rs.getString("category")),rs.getString("date"),rs.getInt("views"),rs.getInt("id_admin"),md.getUrlMedia());
					list.add(post);
				}
				
			}
			new Conexao().fecharConexao(connect);
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return list;
	}
	
	/**
	* Método para fazer a seleção de publicações a partir de uma categoria
	* @param String cat Categoria a ser pesquisada
	* @return ArrayList<Post>
	*/
	public ArrayList<Post> listPostsSearchCategory(String cat){
		ArrayList<Post> list=null;
		try {
			Connection connect= new Conexao().abrirConexao();
			String sql="SELECT * FROM Post WHERE category=? ORDER BY date DESC";
			PreparedStatement ps= connect.prepareStatement(sql);
			ps.setString(1, cat);
			ResultSet rs= ps.executeQuery();
			if(rs!=null) {
				list = new ArrayList<Post>();
				while(rs.next()) {
					Media md = new MediaControl().selectMediaByPost(rs.getInt("id"));
					Post post= new Post(rs.getInt("id"),rs.getString("title"),rs.getString("subtitle"),rs.getString("text"),translateType(rs.getString("type")),translateCategory(rs.getString("category")),rs.getString("date"),rs.getInt("views"),rs.getInt("id_admin"),md.getUrlMedia());
					list.add(post);
				}
				
			}
			new Conexao().fecharConexao(connect);
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return list;
	}
	
	/**
	* Método para fazer a seleção de publicações a partir das recomendações de um usuário
	* @param List<String> pref Preferências do usuário
	* @return ArrayList<Post>
	*/
	public ArrayList<Post> listPostsByRecommendation(List<String> pref){
		ArrayList<Post> list=null;
		try {
			Connection connect= new Conexao().abrirConexao();
			String queryCat = "";
			boolean first = false;
			if(pref.contains("Carro")) {
				if(first) {
					queryCat = queryCat + " OR ";
				}
				queryCat = queryCat + " category='car' ";
				first = true;
			}
			if(pref.contains("Moto")) {
				if(first) {
					queryCat = queryCat + " OR ";
				}
				queryCat = queryCat + " category='motocicle' ";
				first = true;
			}
			if(pref.contains("Onibus")) {
				if(first) {
					queryCat = queryCat + " OR ";
				}
				queryCat = queryCat + " category='bus' ";
				first = true;
			}
			if(pref.contains("Caminhao")) {
				if(first) {
					queryCat = queryCat + " OR ";
				}
				queryCat = queryCat + " category='truck' ";
				first = true;
			}
			if(pref.contains("Aviao")) {
				if(first) {
					queryCat = queryCat + " OR ";
				}
				queryCat = queryCat + " category='airplane' ";
				first = true;
			}
			if(pref.contains("Helicoptero")) {
				if(first) {
					queryCat = queryCat + " OR ";
				}
				queryCat = queryCat + " category='helicopter' ";
				first = true;
			}
			if(pref.contains("Barco")) {
				if(first) {
					queryCat = queryCat + " OR ";
				}
				queryCat = queryCat + " category='boat' ";
				first = true;
			}
			if(pref.contains("Navio")) {
				if(first) {
					queryCat = queryCat + " OR ";
				}
				queryCat = queryCat + " category='ship' ";
				first = true;
			}
			String sql="SELECT * FROM Post WHERE "+queryCat+" ORDER BY date DESC LIMIT 10";
			PreparedStatement ps= connect.prepareStatement(sql);
			ResultSet rs= ps.executeQuery();
			if(rs!=null) {
				list = new ArrayList<Post>();
				while(rs.next()) {
					Media md = new MediaControl().selectMediaByPost(rs.getInt("id"));
					Post post= new Post(rs.getInt("id"),rs.getString("title"),rs.getString("subtitle"),rs.getString("text"),translateType(rs.getString("type")),translateCategory(rs.getString("category")),rs.getString("date"),rs.getInt("views"),rs.getInt("id_admin"),md.getUrlMedia());
					list.add(post);
				}
				
			}
			new Conexao().fecharConexao(connect);
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return list;
	}
	
	/**
	* Método para fazer a seleção de uma única publicação
	* @param int id Id da publicação a ser selecionada
	* @return Post
	*/
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
				result = new Post(rs.getInt("id"),rs.getString("title"),rs.getString("subtitle"),rs.getString("text"),translateType(rs.getString("type")),translateCategory(rs.getString("category")),rs.getString("date"),rs.getInt("views"),rs.getInt("id_admin"),md.getUrlMedia());
			}
			new Conexao().fecharConexao(connect);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return result;
	}
	
	/**
	* Método para fazer a edição de uma publicação
	* @param Post post Publicação a ser editada
	* @param Part arquivo Mídia a ser editada
	* @param String arquivoNome Nome do arquivo de mídia a ser editado
	* @param String urlMd Endereço do atual arquivo de mídia
	* @return boolean
	*/
	public boolean editPost(Post post,Part arquivo,String arquivoNome,String urlMd) {
		boolean result = false;
		try {
			Connection connect= new Conexao().abrirConexao();
			String sql="UPDATE Post SET title=?,subtitle=?,text=?,type=?,category=?,date=? WHERE id=?";
			PreparedStatement ps= connect.prepareStatement(sql);
			ps.setString(1, post.getTitle());
			ps.setString(2, post.getSubtitle());
			ps.setString(3, post.getText());
			ps.setString(4, post.getType());
			ps.setString(5, post.getCategory());
			ps.setString(6, post.getDate());
			ps.setInt(7, post.getId());
			if(!ps.execute()) {
				String reducedFinalDirectory = urlMd;
				if(arquivo!=null) {
					String path = "C:\\Users\\Ronald\\eclipse-workspace\\AutoRods\\WebContent\\resources\\imgs\\posts\\";
					String userFolder = path+post.getId();
					new MediaControl().deleteFolder(userFolder);
					File directory = new File(userFolder);
					if(!directory.exists()) {
						directory.mkdir();
					}
					String finalDirectory = userFolder+"\\"+arquivoNome; 
					arquivo.write(finalDirectory);
					reducedFinalDirectory = "imgs/posts/"+post.getId()+"/"+arquivoNome;
				}
				
				Media media = new Media(0,reducedFinalDirectory,post.getId(),0);
				if(new MediaControl().editMedia(media)) {
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
	
	/**
	* Método para fazer a remoção de publicações
	* @param int id Id da publicação a ser removida
	* @return boolean
	*/
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
	
	/**
	* Método para incrementar as visualizações de uma publicação
	* @param int id Id da publicação
	* @return boolean
	*/
	public boolean addView(int id) {
		boolean result = false;
		try {
			Connection connect= new Conexao().abrirConexao();
			String sql="UPDATE Post SET views=views+1 WHERE id=?";
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
	
	/**
	* Método para fazer a tradução dos tipos de publicação
	* @param String type Tipo a ser traduzido
	* @return String
	*/
	public String translateType(String type) {
		String typeVar = type;
		switch(type) {
		case "news":
			typeVar = "Notícia";
			break;
		case "curiosity":
			typeVar = "Curiosidade";
			break;
		case "tips":
			typeVar = "Dica";
			break;
			default:
		}
		return typeVar;
	}
	
	/**
	* Método para fazer a tradução das categorias de publicação
	* @param String category Categoria a ser traduzida
	* @return String
	*/
	public String translateCategory(String category) {
		String categoryVar = category;
		switch(categoryVar) {
		case "car":
			categoryVar = "Carro";
			break;
		case "motocicle":
			categoryVar = "Moto";
			break;
		case "bus":
			categoryVar = "Ônibus";
			break;
		case "truck":
			categoryVar = "Caminhão";
			break;
		case "airplane":
			categoryVar = "Avião";
			break;
		case "helicopter":
			categoryVar = "Helicóptero";
			break;
		case "boat":
			categoryVar = "Barco";
			break;
		case "ship":
			categoryVar = "Navio";
			break;
			default:
		}
		return categoryVar;
	}
	
}
