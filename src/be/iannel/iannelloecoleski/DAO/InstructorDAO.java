package be.iannel.iannelloecoleski.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import be.iannel.iannelloecoleski.DAO.interfaceDAO.InstructorDAOInterface;
import be.iannel.iannelloecoleski.models.Accreditation;
import be.iannel.iannelloecoleski.models.Instructor;

public class InstructorDAO implements InstructorDAOInterface {

	private Connection connection;
	
	public InstructorDAO(Connection connection) {
		this.connection = connection;
	}
	
	@Override
	public boolean create(Instructor instructor) {
	    if (existsByEmail(instructor.getEmail())) {
	        System.out.println("Email déjà utilisé.");
	        return false;
	    }

	    String sql = "INSERT INTO instructor (FIRSTNAME, LASTNAME, EMAIL, PHONENUMBER, AGE, STREET, STREETNUMBER, CITY) " +
	                 "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

	    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
	        stmt.setString(1, instructor.getFirstName());
	        stmt.setString(2, instructor.getLastName());
	        stmt.setString(3, instructor.getEmail());
	        stmt.setString(4, instructor.getPhoneNumber());
	        stmt.setInt(5, instructor.getAge());
	        stmt.setString(6, instructor.getStreet());
	        stmt.setInt(7, instructor.getStreetNumber());
	        stmt.setString(8, instructor.getCity());

	        int rowsAffected = stmt.executeUpdate();

	        if (rowsAffected > 0) {
	            String selectSql = "SELECT ID FROM instructor WHERE EMAIL = ?";

	            try (PreparedStatement selectStmt = connection.prepareStatement(selectSql)) {
	                selectStmt.setString(1, instructor.getEmail());

	                try (ResultSet rs = selectStmt.executeQuery()) {
	                    if (rs.next()) {
	                        int id = rs.getInt("ID");
	                        instructor.setId(id);

	                        for (Accreditation accreditation : instructor.getAccreditations()) {
	                            addAccreditationToInstructor(id, accreditation.getId());
	                        }

	                        System.out.println("Instructeur inséré avec ID: " + id);
	                        return true;
	                    } else {
	                        System.out.println("Échec récupération ID après insertion.");
	                    }
	                }
	            }
	        } else {
	            System.out.println("Aucune ligne insérée.");
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return false;
	}

	private void addAccreditationToInstructor(int instructorId, int accreditationId) {
		String sql = "INSERT INTO instructor_accreditation (instructorId, accreditationId) VALUES(?, ?)";
		
		try(PreparedStatement stmt = connection.prepareStatement(sql)){
			stmt.setInt(1, instructorId);
			stmt.setInt(2, accreditationId);
			stmt.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public Instructor read(int id) {
	    Instructor instructor = null;
	    String sql = "SELECT * FROM instructor WHERE id = ?";

	    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
	        stmt.setInt(1, id);
	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	        	Accreditation acc = new Accreditation(0, null);
	        	
	            instructor = new Instructor(
	                rs.getInt("ID"),
	                rs.getString("FIRSTNAME"),
	                rs.getString("LASTNAME"),
	                rs.getString("EMAIL"),
	                rs.getString("STREET"),
	                rs.getInt("STREETNUMBER"),
	                rs.getString("CITY"),
	                rs.getString("PHONENUMBER"),
	                rs.getInt("AGE"),
	                acc
	            );
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return instructor;
	}

	@Override
	public List<Instructor> readAll() {
	    List<Instructor> instructors = new ArrayList<>();
	    String sql = "SELECT * FROM instructor";

	    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
	        ResultSet rs = stmt.executeQuery();

	        while (rs.next()) {
	        	Accreditation acc = new Accreditation(0, null);
	        	
	            Instructor instructor = new Instructor(
	                rs.getInt("ID"),
	                rs.getString("FIRSTNAME"),
	                rs.getString("LASTNAME"),
	                rs.getString("EMAIL"),
	                rs.getString("STREET"),
	                rs.getInt("STREETNUMBER"),
	                rs.getString("CITY"),
	                rs.getString("PHONENUMBER"),
	                rs.getInt("AGE"),
	                acc
	            );

	            instructors.add(instructor);
	        }
	    } catch (SQLException e) {
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
	
	@Override
	public List<Instructor> getInstructorsByAccreditationId(int accreditationId) {
	    List<Instructor> instructors = new ArrayList<>();

	    String sql = "SELECT i.* FROM instructor i " +
	                 "JOIN instructor_accreditation ia ON i.id = ia.instructorId " +
	                 "WHERE ia.accreditationId = ?";

	    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
	        stmt.setInt(1, accreditationId);
	        ResultSet rs = stmt.executeQuery();

	        while (rs.next()) {
	            Accreditation acc = new Accreditation(accreditationId, null);

	            Instructor instructor = new Instructor(
	                rs.getInt("ID"),
	                rs.getString("FIRSTNAME"),
	                rs.getString("LASTNAME"),
	                rs.getString("EMAIL"),
	                rs.getString("STREET"),
	                rs.getInt("STREETNUMBER"),
	                rs.getString("CITY"),
	                rs.getString("PHONENUMBER"),
	                rs.getInt("AGE"),
	                acc
	            );

	            instructors.add(instructor);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return instructors;
	}

}
