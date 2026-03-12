package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoDB {
	
	private static final String URL = "jdbc:postgresql://ep-twilight-moon-acwrtu3o-pooler.sa-east-1.aws.neon.tech/aulas";
	private static final String USUARIO = "senaipato";
	private static final String SENHA = "SenaiPatoBranco";
	private static Connection conexao = null;
	
	public static Connection getConexao(){
		if (conexao == null) { // na primeira vez
			try {
				Class.forName("org.postgresql.Driver");
				conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
				System.out.println("Conexão deu boa");
			}
			catch(Exception ex) {
				System.out.println("Deu ruim");
				ex.printStackTrace();
			}
		}
		return conexao;
	}
	
}
