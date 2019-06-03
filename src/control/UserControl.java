package control;

import model.Media;
import model.User;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Part;

import com.mysql.jdbc.Statement;

import control.Conexao;

public class UserControl {

	public boolean insertUser(User user,Part arquivo,String arquivoNome) {
		boolean result = false;
		try {
			Connection connect= new Conexao().abrirConexao();
			String sql="INSERT INTO User(name,password, email) VALUES(?,?,?)";
			PreparedStatement ps= connect.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, user.getName());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getEmail());
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if(rs.next()) {
				for (String pref : user.getPreferences()) {
					insertPreference(rs.getInt(1),pref);
				}
				String path = "C:\\Users\\Ronald\\eclipse-workspace\\AutoRods\\WebContent\\resources\\imgs\\users\\";
				String userFolder = path+rs.getInt(1);
				File directory = new File(userFolder);
				if(!directory.exists()) {
					directory.mkdir();
				}
				String finalDirectory = userFolder+"\\"+arquivoNome; 
				arquivo.write(finalDirectory);
				String reducedFinalDirectory = "imgs/users/"+rs.getInt(1)+"/"+arquivoNome;
				Media media = new Media(0,reducedFinalDirectory,0,rs.getInt(1));
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
	
	public boolean insertPreference(int id_user,String pref) {
		boolean result = false;
		try {
			Connection connect= new Conexao().abrirConexao();
			String sql="INSERT INTO Preferences(id_user,id_vehicleType) VALUES(?,?)";
			PreparedStatement ps= connect.prepareStatement(sql);
			ps.setInt(1, id_user);
			int newPref = 1;
			switch(pref) {
			case "car":
				newPref = 1;
				break;
			case "motocicle":
				newPref = 2;
				break;
			case "bus":
				newPref = 3;
				break;
			case "truck":
				newPref = 4;
				break;
				default:
					newPref = 1;
			}
			ps.setInt(2, newPref);
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
	
	
	public boolean checkEmail(String email) {
		boolean result = false;
		try {
			Connection connect= new Conexao().abrirConexao();
			String sql="SELECT email FROM User WHERE email = ?";
			PreparedStatement ps = connect.prepareStatement(sql);
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			if(rs != null && rs.next()) {
				result = true;
			}
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return result;
	}
	
	public User login(User user) {
		User result= null;
		try {
			Connection connect = new Conexao().abrirConexao();
			String sql="SELECT * FROM User WHERE email=? and password=?";
			PreparedStatement ps= connect.prepareStatement(sql);
			ps.setString(1, user.getEmail());
			ps.setString(2, user.getPassword());
			ResultSet rs= ps.executeQuery();
			List<String> preferences = new ArrayList<String>();
			if(rs.next()){
				String sql2="SELECT v.name from Vehicle_Type as v INNER JOIN Preferences as p ON v.id=p.id_vehicleType INNER JOIN User as u ON u.id=p.id_user WHERE u.id=?";
				PreparedStatement ps2= connect.prepareStatement(sql2);
				ps2.setInt(1, rs.getInt("id"));
				ResultSet rs2= ps2.executeQuery();
				while(rs2.next()) {
					preferences.add(rs2.getString("name"));
				}
				Media userMedia = new MediaControl().selectMediaByUser(rs.getInt("id"));
				result = new User(rs.getInt("id"),rs.getString("name"),rs.getString("password"),rs.getString("email"),preferences,userMedia.getUrlMedia());
			}
			new Conexao().fecharConexao(connect);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return result;
	}
}
