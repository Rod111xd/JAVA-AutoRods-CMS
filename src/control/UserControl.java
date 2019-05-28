package control;

import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import control.Conexao;

public class UserControl {

	public boolean insertUser(User user) {
		boolean result = false;
		try {
			Connection connect= new Conexao().abrirConexao();
			String sql="INSERT INTO User(name,password, email) VALUES(?,?,?)";
			PreparedStatement ps= connect.prepareStatement(sql);
			ps.setString(1, user.getName());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getEmail());
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
	
	public ArrayList<User> listUsers(){
		ArrayList<User> list=null;
		try {
			Connection connect= new Conexao().abrirConexao();
			String sql="SELECT * FROM User;";
			PreparedStatement ps= connect.prepareStatement(sql);
			ResultSet rs= ps.executeQuery();
			if(rs!=null) {
				list = new ArrayList<User>();
				while(rs.next()) {
					User user= new User(rs.getInt("id"),rs.getString("name"),rs.getString("password"),null);
					list.add(user);
				}
				new Conexao().fecharConexao(connect);
			}
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return list;
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
			String sql="SELECT * FROM User WHERE email=? and password=?;";
			PreparedStatement ps= connect.prepareStatement(sql);
			ps.setString(1, user.getEmail());
			ps.setString(2, user.getPassword());
			ResultSet rs= ps.executeQuery();
			if(rs.next()){
				result = new User(rs.getInt("id"),rs.getString("name"),rs.getString("password"),rs.getString("email"));
			}
			new Conexao().fecharConexao(connect);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return result;
	}
}
