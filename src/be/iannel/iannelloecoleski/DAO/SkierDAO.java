package be.iannel.iannelloecoleski.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import be.iannel.iannelloecoleski.DAO.interfaceDAO.SkierDAOInterface;
import be.iannel.iannelloecoleski.models.Skier;

public class SkierDAO implements SkierDAOInterface{

	private Connection connection;
	
	public SkierDAO(Connection connection) {
		this.connection = connection;
	}
	
	@Override
	public boolean create(Skier skier) {
		if(existsByEmail(skier.getEmail())) {
			return false;
		}
		
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
				return true;
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public Skier read(int id) {
		Skier skier = null;
		String sql = "SELECT * FROM skier WHERE id = ?";
		
		try(PreparedStatement stmt = connection.prepareStatement(sql)){
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				skier = new Skier(
						rs.getInt("ID"),
						rs.getString("FIRSTNAME"),
						rs.getString("LASTNAME"),
						rs.getString("EMAIL"),
						rs.getString("STREET"),
						rs.getInt("STREETNUMBER"),
						rs.getString("CITY"),
						rs.getString("PHONENUMBER"),
						rs.getInt("AGE")
						);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return skier;
	}
	
	@Override
	public List<Skier> readAll(){
		List<Skier> skiers = new ArrayList<>();
		String sql = "SELECT * FROM skier";
		
		try(PreparedStatement stmt = connection.prepareStatement(sql)){
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				Skier skier = new Skier(
						rs.getInt("ID"),
						rs.getString("FIRSTNAME"),
						rs.getString("LASTNAME"),
						rs.getString("EMAIL"),
						rs.getString("STREET"),
						rs.getInt("STREETNUMBER"),
						rs.getString("CITY"),
						rs.getString("PHONENUMBER"),
						rs.getInt("AGE")
						);
				skiers.add(skier);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return skiers;
	}
	
	@Override
	public boolean delete(int id) {
		String sql = "DELETE FROM skier WHERE id = ?";
		
		try(PreparedStatement stmt = connection.prepareStatement(sql)){
			stmt.setInt(1, id);
			int rowsAffected = stmt.executeUpdate();
			
			return rowsAffected > 0;
	
		} catch(SQLException e) {
				e.printStackTrace();
				return false;
		}	
	}
	
	@Override
	public boolean existsByEmail(String email) {
		String sql = "SELECT COUNT(*) FROM skier WHERE email = ?";
		
		try(PreparedStatement stmt = connection.prepareStatement(sql)){
			stmt.setString(1, email);
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				return rs.getInt(1) > 0;
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
