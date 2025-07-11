package be.iannel.iannelloecoleski.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import be.iannel.iannelloecoleski.DAO.interfaceDAO.InterfaceDAO;
import be.iannel.iannelloecoleski.models.Accreditation;
import be.iannel.iannelloecoleski.models.Instructor;

public class AccreditationDAO implements InterfaceDAO<Accreditation>{

	private Connection connection;
	
	public AccreditationDAO(Connection connection) {
		this.connection = connection;
	}
	
    @Override
    public boolean create(Accreditation accreditation) {
        String sql = "INSERT INTO Accreditations (NAME) VALUES (?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, accreditation.getName());
            int rowsAffected = stmt.executeUpdate();
            
            if(rowsAffected > 0) {
            	System.out.println("Accréditations ajoutée avec succès.");
            	return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
	
    @Override
    public Accreditation read(int id) {
        String sql = "SELECT * FROM Accreditations WHERE ID = ?";
        Accreditation accreditation = null;
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                accreditation = new Accreditation(
                        rs.getInt("ID"),
                        rs.getString("NAME")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accreditation;
    }
	
    @Override
    public List<Accreditation> readAll() {
        String sql = "SELECT * FROM Accreditations";
        List<Accreditation> accreditations = new ArrayList<>();
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Accreditation accreditation = new Accreditation(
                        rs.getInt("ID"),
                        rs.getString("NAME")
                );
                
                List<Instructor> instructors = getInstructorsByAccreditationId(accreditation.getId());
                for(Instructor instructor : instructors) {
                	accreditation.addInstructor(instructor);
                }
                
                accreditations.add(accreditation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accreditations;
    }
	
    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM Accreditations WHERE ID = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            
            if(rowsAffected > 0) {
            	System.out.println("Accréditation supprimée avec succès.");
            	return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public List<Accreditation> getAccreditationsByInstructorId(int instructorId){
    	List<Accreditation> accreditations = new ArrayList<>();
    	
    	String sql = "SELECT a.ID, a.NAME " +
    				"FROM accreditation a " +
    				"JOIN instructor_accreditation ia ON a.ID = ia.accreditationId " +
    				"WHERE ia.instructorId = ?";
    	
    	try(PreparedStatement stmt = connection.prepareStatement(sql)){
    		stmt.setInt(1, instructorId);
    		ResultSet rs = stmt.executeQuery();
    		
    		while(rs.next()) {
    			Accreditation accreditation = new Accreditation(
    				rs.getInt("ID"),
    				rs.getString("NAME")
    			);
    			accreditations.add(accreditation);
    		}
    	} catch(SQLException e) {
    		e.printStackTrace();
    	}
    	return accreditations;
    }
    
    public List<Instructor> getInstructorsByAccreditationId(int accreditationId){
    	List<Instructor> instructors = new ArrayList<>();
    	String sql = "SELECT i.* FROM instructor i " +
                "JOIN instructor_accreditation ia ON i.id = ia.instructorId " +
                "WHERE ia.accreditationId = ?";
    	
    	try(PreparedStatement stmt = connection.prepareStatement(sql)){
    		stmt.setInt(1, accreditationId);
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
    	                rs.getInt("AGE"),
    	                null
    	                );
    			instructors.add(instructor);
    		}
    	} catch(SQLException e) {
    		e.printStackTrace();
    	}
    	return instructors;
    }
}
