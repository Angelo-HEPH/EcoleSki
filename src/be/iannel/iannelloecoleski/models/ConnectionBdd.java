package be.iannel.iannelloecoleski.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class ConnectionBdd {

	private static Connection instance = null;
	
	private ConnectionBdd() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			String url = "jdbc:oracle:thin:@//193.190.64.10:1522/xepdb1"; 
			String username = "student03_23";
			String password = "ProjetJava23";
			
			instance = DriverManager.getConnection(url, username, password);
			
			System.out.println("Connexion réussie à la base de données oracle !");
		}
		catch(ClassNotFoundException ex) {
			JOptionPane.showMessageDialog(null, "Classe de driver introuvable : "+ ex.getMessage());
		}
		catch(SQLException ex) {
			JOptionPane.showMessageDialog(null, "Erreur JDBC : " + ex.getMessage());
		}
		
		if(instance == null) {
			JOptionPane.showMessageDialog(null, "La base de données est inaccessible, fermeture du programme.");
			System.exit(0);
		}
	}
	
	public static Connection getInstance() {
		if(instance == null) {
			new ConnectionBdd();
		}
		
		return instance;
	}
	
	
	public static void main(String[] args) {
		getInstance(); //Test de connexion à la bdd
	}

}
