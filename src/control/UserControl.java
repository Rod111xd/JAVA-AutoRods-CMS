package control;

import model.Media;
import model.User;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.http.Part;

import com.mysql.jdbc.Statement;

import control.Conexao;
import control.Crypt;

/**
* Controle para o Usuário.
* @author Rodrigo da Silva Freitas <rodrigojato@hotmail.com>
* @package control
*/
public class UserControl {

	/**
	* Método para cadastrar um usuário
	* @param User user Usuário a ser cadastrado
	* @param Part arquivo Imagem do usuário a ser cadastrado
	* @param String arquivoNome Nome do arquivo de imagem do usuário
	* @return boolean
	*/
	public boolean insertUser(User user,Part arquivo,String arquivoNome) {
		boolean result = false;
		try {
			Connection connect= new Conexao().abrirConexao();
			String sql="INSERT INTO User(name,password, email) VALUES(?,?,?)";
			PreparedStatement ps= connect.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, user.getName());
			String key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDL3zlyBF/j6AfR\r\n" + 
					"9IwKAtZ6B5tq41b0L8dqm8FOycDNuHWGChg7CPNWrn44PVMsOQpy6SD532rqx/5A\r\n" + 
					"M36ieylS+jrADwk9PW258qTMZPMyx1DEJG2PiGfs+mpMD03lycRuGmLEKQeInIS9\r\n" + 
					"TClOmFFPeTnSkTeDkqX5y1A3TOH1FEXoGgKOpDWXZvGITlkl+kH4fwcoagLsIsek\r\n" + 
					"yK88byCw6r4NLH22MeQD9LxEXuihe0HqaMTu8CPSeBljVdEjkmYx6pp4UAfuqnn8\r\n" + 
					"zIuQFNUhuw1vJepj4pXIKsitoEXa918GuPetXjCtA1oAIzoUOF5tSpsPAoY44JJV\r\n" + 
					"es13rVhfAgMBAAECggEADAci8f2dFKqbT4FAg9SwB6oMOs2n0ydAeiMvT/EqPVjd\r\n" + 
					"IifVTyJTjPElhDbmAc1ptubXKbbLLYfYEbyYA4kFop4dujgI4QKPRzGwFFj+WigV\r\n" + 
					"NUU664VuMDaD7/HVNDHns2E+I3mSNraZRDvKkhb9cRVjWm9z2YDc5vReSqzwBc/t\r\n" + 
					"4SG7j2cBOicOd+dOZh7DfsMWORERpcdQjazQf1ArdXV2GwuBPRdz1BfgvrZmNj9o\r\n" + 
					"6/WjVQV+MtMnX7HAmsw2s8jUCYO3lfx7I0/m/EIOFLfbuAgP4y+bVhDyKlQnQLOg\r\n" + 
					"VrZmiYqB05JcqGsw51Df7sK8kKzBrTi+FOtANb7dpQKBgQDyPsQEBFe+tVKDUMGo\r\n" + 
					"+LytXbheENbcq4t1C7c+ZmK7wzDwTdLOhdw8QcDyif3bm2NZcBNqZSYegWoFLn+2\r\n" + 
					"H/SzntAIvAGzzE8Z86FeEKQu4PnfUuxpNa6oDaTdLgBBAJoRTD8wzsCMkwET8sGp\r\n" + 
					"6ge6Whzz4r83NO8mz+q/R94bYwKBgQDXcqwe13Y78i7fkhs3vv2UxLbnUbAJKcjK\r\n" + 
					"3l2JlxJmvamtZ1Ew0jRsXYzAYvCIS+/GFy4r2YzlBLEbd2bi7l/sgh50UNSpXaSx\r\n" + 
					"uCARYZOm5Z70LxvhkMGmLXHPV1kzxa6Sy72XmYXhaBc/Ns4orbJ+sMeJOAbm72Po\r\n" + 
					"4RNfuWvl1QKBgDsPRmbcUDA0sNtHExAJJKb31H1KibffMu7kXlaeS7APVJ0hvCWR\r\n" + 
					"yTH/rfTz46po5f3mLzWfV33Ue26r+YMDo3svWvTmMVwOkbJ4DX2LfRvYydLCutSj\r\n" + 
					"u+NJAErUbkdqyCUze6yAm70qEfc1FjZA0oWCdtCXFZt2EmBaDJd6BBKVAoGBAME0\r\n" + 
					"iZvi1pmtdlFxwcy9DsShn/BS9g1RlkovHSys+IiAHzBszYd9iht/zSAd2dwwVOaM\r\n" + 
					"lRAnuM0L5xNdgTuSTx1WFp9yeTMk0fO5zbAok/OASYpq0JL4cGBosn4gs9LUvNfR\r\n" + 
					"s8TGnSPlZ6t9p2UdV0t7loS8ZJwmI6+MYAZgzpy9AoGBAIsrLcu8B0O0SWbtPH/C\r\n" + 
					"jtq4YFt79/aSbCr6JtRQE6Yvs2RaqxcSe7coEugz9weMl3oOqVaYe5zIjUQBxdkh\r\n" + 
					"+xulA5lcLblZMyMCplo3debzEZ+tIN736r5bmQjEzq+PeIyQgtleoXwjfpEHyQMD\r\n" + 
					"eszFTW8/Jff2F4lVO/5J8xR6";
			ps.setBytes(2, Crypt.encrypt(user.getPassword(), key));
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
	
	/**
	* Método para inserir as preferências do usuário
	* @param int id_user Id do usuário
	* @param String pref Preferência do usuário
	* @return boolean
	*/
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
	
	/**
	* Método para fazer a checagem de existência de um email no banco
	* @param String email Email a ser checado
	* @return boolean
	*/
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
	
	/**
	* Método para fazer o login do usuário
	* @param User user Usuário a ser logado
	* @return User
	*/
	public User login(User user) {
		User result= null;
		try {
			Connection connect = new Conexao().abrirConexao();
			String sql="SELECT * FROM User WHERE email=?";
			PreparedStatement ps= connect.prepareStatement(sql);
			ps.setString(1, user.getEmail());
			ResultSet rs= ps.executeQuery();
			List<String> preferences = new ArrayList<String>();
			if(rs.next()){
				String key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDL3zlyBF/j6AfR\r\n" + 
						"9IwKAtZ6B5tq41b0L8dqm8FOycDNuHWGChg7CPNWrn44PVMsOQpy6SD532rqx/5A\r\n" + 
						"M36ieylS+jrADwk9PW258qTMZPMyx1DEJG2PiGfs+mpMD03lycRuGmLEKQeInIS9\r\n" + 
						"TClOmFFPeTnSkTeDkqX5y1A3TOH1FEXoGgKOpDWXZvGITlkl+kH4fwcoagLsIsek\r\n" + 
						"yK88byCw6r4NLH22MeQD9LxEXuihe0HqaMTu8CPSeBljVdEjkmYx6pp4UAfuqnn8\r\n" + 
						"zIuQFNUhuw1vJepj4pXIKsitoEXa918GuPetXjCtA1oAIzoUOF5tSpsPAoY44JJV\r\n" + 
						"es13rVhfAgMBAAECggEADAci8f2dFKqbT4FAg9SwB6oMOs2n0ydAeiMvT/EqPVjd\r\n" + 
						"IifVTyJTjPElhDbmAc1ptubXKbbLLYfYEbyYA4kFop4dujgI4QKPRzGwFFj+WigV\r\n" + 
						"NUU664VuMDaD7/HVNDHns2E+I3mSNraZRDvKkhb9cRVjWm9z2YDc5vReSqzwBc/t\r\n" + 
						"4SG7j2cBOicOd+dOZh7DfsMWORERpcdQjazQf1ArdXV2GwuBPRdz1BfgvrZmNj9o\r\n" + 
						"6/WjVQV+MtMnX7HAmsw2s8jUCYO3lfx7I0/m/EIOFLfbuAgP4y+bVhDyKlQnQLOg\r\n" + 
						"VrZmiYqB05JcqGsw51Df7sK8kKzBrTi+FOtANb7dpQKBgQDyPsQEBFe+tVKDUMGo\r\n" + 
						"+LytXbheENbcq4t1C7c+ZmK7wzDwTdLOhdw8QcDyif3bm2NZcBNqZSYegWoFLn+2\r\n" + 
						"H/SzntAIvAGzzE8Z86FeEKQu4PnfUuxpNa6oDaTdLgBBAJoRTD8wzsCMkwET8sGp\r\n" + 
						"6ge6Whzz4r83NO8mz+q/R94bYwKBgQDXcqwe13Y78i7fkhs3vv2UxLbnUbAJKcjK\r\n" + 
						"3l2JlxJmvamtZ1Ew0jRsXYzAYvCIS+/GFy4r2YzlBLEbd2bi7l/sgh50UNSpXaSx\r\n" + 
						"uCARYZOm5Z70LxvhkMGmLXHPV1kzxa6Sy72XmYXhaBc/Ns4orbJ+sMeJOAbm72Po\r\n" + 
						"4RNfuWvl1QKBgDsPRmbcUDA0sNtHExAJJKb31H1KibffMu7kXlaeS7APVJ0hvCWR\r\n" + 
						"yTH/rfTz46po5f3mLzWfV33Ue26r+YMDo3svWvTmMVwOkbJ4DX2LfRvYydLCutSj\r\n" + 
						"u+NJAErUbkdqyCUze6yAm70qEfc1FjZA0oWCdtCXFZt2EmBaDJd6BBKVAoGBAME0\r\n" + 
						"iZvi1pmtdlFxwcy9DsShn/BS9g1RlkovHSys+IiAHzBszYd9iht/zSAd2dwwVOaM\r\n" + 
						"lRAnuM0L5xNdgTuSTx1WFp9yeTMk0fO5zbAok/OASYpq0JL4cGBosn4gs9LUvNfR\r\n" + 
						"s8TGnSPlZ6t9p2UdV0t7loS8ZJwmI6+MYAZgzpy9AoGBAIsrLcu8B0O0SWbtPH/C\r\n" + 
						"jtq4YFt79/aSbCr6JtRQE6Yvs2RaqxcSe7coEugz9weMl3oOqVaYe5zIjUQBxdkh\r\n" + 
						"+xulA5lcLblZMyMCplo3debzEZ+tIN736r5bmQjEzq+PeIyQgtleoXwjfpEHyQMD\r\n" + 
						"eszFTW8/Jff2F4lVO/5J8xR6";
				String bdPwd = Crypt.decrypt(rs.getBytes("password"), key);
				System.out.println(bdPwd);
				if(bdPwd.equals(user.getPassword())) {
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
				
			}
			new Conexao().fecharConexao(connect);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return result;
	}
	
	/**
	* Método para retornar o nome do usuário
	* @param int id Id do usúário
	* @return String
	*/
	public String getUserName(int id) {
		String result= null;
		try {
			Connection connect = new Conexao().abrirConexao();
			String sql="SELECT * FROM User WHERE id=?";
			PreparedStatement ps= connect.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs= ps.executeQuery();
			if(rs.next()){
				result = rs.getString("name");
			}
			new Conexao().fecharConexao(connect);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return result;
	}
	
}
