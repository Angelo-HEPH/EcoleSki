package be.iannel.iannelloecoleski.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import be.iannel.iannelloecoleski.DAO.interfaceDAO.SkierDAOInterface;
import be.iannel.iannelloecoleski.models.Skier;

public class SkierDAO implements SkierDAOInterface{

	private Connection connection;
	
	public SkierDAO(Connection connection) {
		this.connection = connection;
	}
	
	@Override
	public void create(Skier skier) {
		String sql = "INSERT INTO skier (LASTNAME, FIRSTNAME, EMAIL, PHONENUMBER, AGE, STREET, STREETNUMBER, CITY) " +
					"VALUES (?,?,?,?,?,?,?,?)";
		
		try(PreparedStatement stmt = connection.prepareStatement(sql)){
			stmt.setString(1, skier.getLastName());
			stmt.setString(2, skier.getFirstName());
			stmt.setString(3, skier.getEmail());
			stmt.setString(4, skier.getPhoneNumber());
			stmt.setInt(5, skier.getAge());
			stmt.setString(6, skier.getStreet());
			stmt.setInt(7, skier.getStreetNumber());
			stmt.setString(8, skier.getCity());
			
			int rowsAffected = stmt.executeUpdate();
			
			if(rowsAffected > 0) {
				System.out.println("Skier crée avec succès.");
			} else {
				System.out.println("Echec de la création du skier.");
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
