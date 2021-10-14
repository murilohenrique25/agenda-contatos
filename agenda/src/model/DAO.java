package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DAO {
//*Modulo de conexão*//
	//Parametros de conexão
	private String driver = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3308/dbagenda?useTimezone=true&serverTimezone=UTC";
	private String user = "root";
	private String password = "root";
	//Metodo conexao
	private Connection conectar() {
		Connection con = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			return con;
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
			return null;
		}
		
	}
	
	//CRUD CREATE //
	
	public void inserirContato(JavaBeans contato) {
		String sql = "insert into contatos(nome, telefone, email) values(?,?,?)";
		try {
			//abrir a conexao
			Connection con = conectar();
			//Preparar aquery para execução
			PreparedStatement pst = con.prepareStatement(sql);
			
			pst.setString(1, contato.getNome());
			pst.setString(2, contato.getFone());
			pst.setString(3, contato.getEmail());
			
			pst.executeUpdate();
			con.close();
		}catch (Exception e) {
			System.out.println(e);
		}
	}
	
	//CRUD READ
	
	public ArrayList<JavaBeans> listarContatos(){
		ArrayList<JavaBeans> listar = new ArrayList<>();
		String sql = "select * from contatos order by nome";
		try {
			Connection con = conectar();
			//Preparar aquery para execução
			PreparedStatement pst = con.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				String idcon = rs.getString(1);
				String nome = rs.getString(2);
				String fone = rs.getString(3);
				String email = rs.getString(4);
				listar.add(new JavaBeans(idcon, nome, fone, email));
				
			}
			con.close();
			return listar;
		}catch (Exception e) {
			 System.out.println(e);
			 return null;
		}
	}
	
	
}
