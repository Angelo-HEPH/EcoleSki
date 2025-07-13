package be.iannel.iannelloecoleski.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import be.iannel.iannelloecoleski.DAO.interfaceDAO.AccreditationDAOInterface;
import be.iannel.iannelloecoleski.models.Accreditation;

public class AccreditationDAO implements AccreditationDAOInterface {

    private Connection connection;

    public AccreditationDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean create(Accreditation accreditation) {
        String sql = "INSERT INTO Accreditations (NAME) VALUES (?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, accreditation.getName());
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Accreditation read(int id) {
        String sql = "SELECT * FROM Accreditations WHERE ID = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Accreditation(
                    rs.getInt("ID"),
                    rs.getString("NAME")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Accreditation> getAccreditationsByInstructorId(int instructorId) {
        List<Accreditation> accreditations = new ArrayList<>();
        String sql = "SELECT a.ID, a.NAME FROM accreditations a " +
                     "JOIN instructor_accreditation ia ON a.ID = ia.accreditationId " +
                     "WHERE ia.instructorId = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, instructorId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Accreditation accreditation = new Accreditation(
                    rs.getInt("ID"),
                    rs.getString("NAME")
                );
                accreditations.add(accreditation);
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accreditations;
    }
    
    public Accreditation getAccreditationByLessonTypeId(int lessonTypeId) {
        String sql = "SELECT a.ID, a.NAME " +
                     "FROM Accreditations a " +
                     "JOIN LessonType lt ON a.ID = lt.ACCREDITATION_ID " +
                     "WHERE lt.ID = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, lessonTypeId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Accreditation(
                    rs.getInt("ID"),
                    rs.getString("NAME")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
