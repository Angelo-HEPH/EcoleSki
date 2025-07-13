package be.iannel.iannelloecoleski.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import be.iannel.iannelloecoleski.DAO.interfaceDAO.LessonTypeDAOInterface;
import be.iannel.iannelloecoleski.models.Accreditation;
import be.iannel.iannelloecoleski.models.LessonType;

public class LessonTypeDAO implements LessonTypeDAOInterface {

    private Connection connection;


    public LessonTypeDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean create(LessonType lessonType) {
        String sql = "INSERT INTO LessonType (NAME, PRICE_PER_WEEK, ACCREDITATION_ID, LESSON_LEVEL) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, lessonType.getName());
            stmt.setDouble(2, lessonType.getPricePerWeek());
            stmt.setInt(3, lessonType.getAccreditation().getId());
            stmt.setInt(4, lessonType.getLessonLevel());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public LessonType read(int id) {
        String sql = "SELECT * FROM LessonType WHERE ID = ?";
        LessonType lessonType = null;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int accreditationId = rs.getInt("ACCREDITATION_ID");
                Accreditation accreditation = new Accreditation(accreditationId, null);

                lessonType = new LessonType(
                    rs.getInt("ID"),
                    rs.getString("NAME"),
                    rs.getDouble("PRICE_PER_WEEK"),
                    rs.getInt("LESSON_LEVEL"),
                    accreditation
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lessonType;
    }

    @Override
    public List<LessonType> readAll() {
        List<LessonType> lessonTypes = new ArrayList<>();
        String sql = "SELECT * FROM LessonType";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int accreditationId = rs.getInt("ACCREDITATION_ID");
                Accreditation accreditation = new Accreditation(accreditationId, null);

                LessonType lessonType = new LessonType(
                    rs.getInt("ID"),
                    rs.getString("NAME"),
                    rs.getDouble("PRICE_PER_WEEK"),
                    rs.getInt("LESSON_LEVEL"),
                    accreditation
                );

                lessonTypes.add(lessonType);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lessonTypes;
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM LessonType WHERE ID = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<LessonType> getLessonTypesByAccreditationId(int accreditationId) {
        List<LessonType> lessonTypes = new ArrayList<>();
        String sql = "SELECT * FROM LessonType WHERE ACCREDITATION_ID = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, accreditationId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Accreditation accreditation = new Accreditation(accreditationId, null);
                LessonType lessonType = new LessonType(
                    rs.getInt("ID"),
                    rs.getString("NAME"),
                    rs.getDouble("PRICE_PER_WEEK"),
                    rs.getInt("LESSON_LEVEL"),
                    accreditation
                );
                lessonTypes.add(lessonType);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lessonTypes;
    }
}
