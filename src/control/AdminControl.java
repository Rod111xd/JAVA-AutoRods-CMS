package control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.Admin;

/**
* Controle para o Admin.
* @author Rodrigo da Silva Freitas <rodrigojato@hotmail.com>
* @package control
*/

public class AdminControl {
	/**
	* Método para fazer o login do admin
	* @param Admin admin Objeto admin para fazer o login
	* @return Admin
	*/
	public Admin login(Admin admin) {
		Admin result= null;
		try {
			Connection connect = new Conexao().abrirConexao();
			String sql="SELECT * FROM Admin WHERE name=? and password=?";
			PreparedStatement ps= connect.prepareStatement(sql);
			ps.setString(1, admin.getName());
			ps.setString(2, admin.getPassword());
			ResultSet rs= ps.executeQuery();
			if(rs.next()){
				result = new Admin(rs.getInt("id"),rs.getString("name"),rs.getString("password"));
			}
			new Conexao().fecharConexao(connect);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return result;
	}
	
	/**
	* Método para fazer a seleção do Admin.
	* @param int id Id do admin a ser selecionado
	* @return Admin
	*/
	public Admin selectAdmin(int id) {
		Admin result= null;
		try {
			Connection connect = new Conexao().abrirConexao();
			String sql="SELECT * FROM Admin WHERE id=?";
			PreparedStatement ps= connect.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs= ps.executeQuery();
			if(rs.next()){
				result = new Admin(rs.getInt("id"),rs.getString("name"),rs.getString("password"));
			}
			new Conexao().fecharConexao(connect);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return result;
	}
}
