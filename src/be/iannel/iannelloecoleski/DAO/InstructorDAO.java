package be.iannel.iannelloecoleski.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import be.iannel.iannelloecoleski.DAO.interfaceDAO.InstructorDAOInterface;
import be.iannel.iannelloecoleski.models.Instructor;

public class InstructorDAO implements InstructorDAOInterface {

	private Connection connection;
	
	public InstructorDAO(Connection connection) {
		this.connection = connection;
	}
	
	@Override
	public boolean create(Instructor instructor) {
		if(existsByEmail(instructor.getEmail())) {
			return false;
		}
		
		String sql = "INSERT INTO instructor (LASTNAME, FIRSTNAME, EMAIL, PHONENUMBER, AGE, STREET, STREETNUMBER, CITY) " +
					"VALUES(?,?,?,?,?,?,?,?)";
		
		try(PreparedStatement stmt = connection.prepareStatement(sql)){
			stmt.setString(1, instructor.getLastName());
			stmt.setString(2, instructor.getFirstName());
			stmt.setString(3, instructor.getEmail());
			stmt.setString(4, instructor.getPhoneNumber());
			stmt.setInt(5, instructor.getAge());
			stmt.setString(6, instructor.getStreet());
			stmt.setInt(7, instructor.getStreetNumber());
			stmt.setString(8, instructor.getCity());
			
			int rowsAffected = stmt.executeUpdate();
			
			if(rowsAffected > 0) {
				System.out.println("Instructeur créée avec succès.");
				return true;
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public Instructor read(int id) {
		Instructor instructor = null;
		String sql = "SELECT * FROM instructor WHERE id = ?";
		
		try(PreparedStatement stmt = connection.prepareStatement(sql)){
			stmt.setInt(1,id);
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				instructor = new Instructor(
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
		return instructor;
	}
	
	@Override
	public List<Instructor> readAll(){
		List<Instructor> instructors = new ArrayList<>();
		String sql = "SELECT * FROM instructor";
		
		try(PreparedStatement stmt = connection.prepareStatement(sql)){
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				Instructor instructor = new Instructor(
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
				instructors.add(instructor);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return instructors;
	}
	
	@Override
	public boolean delete(int id) {
		String sql = "DELETE FROM instructor WHERE id = ?";
				
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
		String sql = "SELECT COUNT(*) FROM instructor WHERE email = ?";
		
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
