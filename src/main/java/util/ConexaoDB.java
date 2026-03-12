package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoDB {
	
	private static final String URL = "jdbc:postgresql://ep-twilight-moon-acwrtu3o-pooler.sa-east-1.aws.neon.tech/aulas";
	private static final String USUARIO = "senaipato";
	private static final String SENHA = "SenaiPatoBranco";
	private static int contador = 0;
	
	public static Connection getConexao() throws SQLException{
		
		if(contador++ == 0) {
			try{
				Class.forName("org.postgresql.Driver");
			}
			catch(ClassNotFoundException ex) {
				System.out.println("Não carregou o driver do PostgreSQL");
				return null;
			}
		}
		
		try {
			Connection conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
			System.out.println("Conexão deu boa ("+contador+")");
			return conexao;
		}
		catch(SQLException ex) {
			System.out.println("Deu ruim ("+contador+")");
			ex.printStackTrace();
			throw ex; // toca a exceção pra cima
		}
	}
	
}
