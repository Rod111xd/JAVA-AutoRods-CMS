package control;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

/**
* Controle para a Conexão.
* @author Rodrigo da Silva Freitas <rodrigojato@hotmail.com>
* @package control
*/
public final class Conexao {
	/**
	* Método para abrir a conexão
	* @return Connection
	*/
	public Connection abrirConexao() {
		Connection connect = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String banco = "AutoRods";
			String servidor = "jdbc:mysql://localhost/" + banco;
			String usr = "root";
			String pwd = "";
			connect = DriverManager.getConnection(servidor,usr,pwd) ;
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return connect;
	}
	
	/**
	* Método para fazer o login do admin
	* @param Connection con Conexão a ser fechada
	*/
	public void fecharConexao(Connection con) {
		try {
			con.close();
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}

