package br.com.pjr.exemplojsf.util;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Conexao {

	public static Connection obterConexao() throws ClassNotFoundException, SQLException {

		Class.forName("org.postgresql.Driver");

		final String url = "jdbc:postgresql://localhost:5432/Exemplo";

		final String usuario = "postgres";
		final String senha = ""; // inserir a senha

		return DriverManager.getConnection(url, usuario, senha);

	}

	public static void fecharConexao(PreparedStatement ps, Connection conn, ResultSet rs) throws SQLException {
		if (ps != null) {
			ps.close();
		}
		if (rs != null) {
			rs.close();
		}
		if (conn != null) {
			conn.close();
		}
	}
	
	public static int executarSQL(PreparedStatement preparedStatement, Object... params) throws SQLException {
		
		for (int i = 0; i < params.length; i++) {
			
			Object objeto = params[i];
			
			if (objeto instanceof Long) {
				preparedStatement.setLong(i + 1 , (Long) objeto);
			} else if (objeto instanceof String) {
				preparedStatement.setString(i + 1 , (String) objeto);
			} else if (objeto instanceof BigDecimal) {
				preparedStatement.setDouble(i + 1 , ((BigDecimal) objeto).doubleValue());
			} 
		}
		
		return preparedStatement.executeUpdate();
	}
	
	public static ResultSet consultarSQL(PreparedStatement preparedStatement, Object... params) throws SQLException {
		
		for (int i = 0; i < params.length; i++) {
			
			Object objeto = params[i];
			
			if (objeto instanceof Long) {
				preparedStatement.setLong(i + 1 , (Long) objeto);
			} else if (objeto instanceof String) {
				preparedStatement.setString(i + 1 , (String) objeto);
			} else if (objeto instanceof BigDecimal) {
				preparedStatement.setBigDecimal(i + 1, (BigDecimal) objeto);
			} 
		}
		
		return preparedStatement.executeQuery();
	}

	public static void main(String[] args) {
		try {
			obterConexao();
			System.out.println("Conectou no banco de dados!");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
}