package be.iannel.iannelloecoleski.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import be.iannel.iannelloecoleski.DAO.interfaceDAO.LessonDAOInterface;
import be.iannel.iannelloecoleski.models.Instructor;
import be.iannel.iannelloecoleski.models.Lesson;
import be.iannel.iannelloecoleski.models.LessonType;

public class LessonDAO implements LessonDAOInterface{

    private Connection connection;

    public LessonDAO(Connection connection) {
        this.connection = connection;
    }

	public boolean create(Lesson lesson) {
		String sql = "INSERT INTO lesson (MINBOOKINGS, MAXBOOKINGS, LESSONTYPEID, INSTRUCTORID,"
				+ "LESSON_DATE, START_TIME, DURATION_MINUTES, IS_PRIVATE) " +
				"VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
		
		try(PreparedStatement stmt = connection.prepareStatement(sql)){
			stmt.setInt(1, lesson.getMinBookings());
			stmt.setInt(2, lesson.getMaxBookings());
			stmt.setInt(3, lesson.getLessonType().getId());
			stmt.setInt(4, lesson.getInstructor().getId());
			stmt.setDate(5, java.sql.Date.valueOf(lesson.getLessonDate()));
			stmt.setTimestamp(6, java.sql.Timestamp.valueOf(lesson.getStartTime()));
            stmt.setInt(7, lesson.getDurationMinutes());
            stmt.setInt(8, lesson.isPrivate() ? 1 : 0);
            
            int rowsAffected = stmt.executeUpdate();
            
            if(rowsAffected > 0) {
            	return true;
            }
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

    public Lesson read(int id) {
        String sql = "SELECT * FROM lesson WHERE ID = ?";
        Lesson lesson = null;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int lessonTypeId = rs.getInt("LESSONTYPEID");
                int instructorId = rs.getInt("INSTRUCTORID");

                LessonType lessonType = new LessonType();
                lessonType.setId(lessonTypeId);

                Instructor instructor = new Instructor();
                instructor.setId(instructorId);

                lesson = new Lesson(
                    rs.getInt("ID"),
                    rs.getInt("MINBOOKINGS"),
                    rs.getInt("MAXBOOKINGS"),
                    rs.getDate("LESSON_DATE").toLocalDate(),
                    rs.getTimestamp("START_TIME").toLocalDateTime(),
                    rs.getInt("DURATION_MINUTES"),
                    rs.getInt("IS_PRIVATE") == 1,
                    lessonType,
                    instructor
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lesson;
    }
	
    public List<Lesson> readAll() {
        List<Lesson> lessons = new ArrayList<>();
        String sql = "SELECT * FROM lesson";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int lessonTypeId = rs.getInt("LESSONTYPEID");
                int instructorId = rs.getInt("INSTRUCTORID");

                LessonType lessonType = new LessonType();
                lessonType.setId(lessonTypeId);

                Instructor instructor = new Instructor();
                instructor.setId(instructorId);

                Lesson lesson = new Lesson(
                    rs.getInt("ID"),
                    rs.getInt("MINBOOKINGS"),
                    rs.getInt("MAXBOOKINGS"),
                    rs.getDate("LESSON_DATE").toLocalDate(),
                    rs.getTimestamp("START_TIME").toLocalDateTime(),
                    rs.getInt("DURATION_MINUTES"),
                    rs.getInt("IS_PRIVATE") == 1,
                    lessonType,
                    instructor
                );

                lessons.add(lesson);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lessons;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM lesson WHERE ID = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Lesson> getLessonsByInstructorId(int instructorId) {
        List<Lesson> lessons = new ArrayList<>();
        String sql = "SELECT * FROM lesson WHERE INSTRUCTORID = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, instructorId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int lessonTypeId = rs.getInt("LESSONTYPEID");

                LessonType lessonType = new LessonType();
                lessonType.setId(lessonTypeId);

                Instructor instructor = new Instructor();
                instructor.setId(instructorId);

                Lesson lesson = new Lesson(
                    rs.getInt("ID"),
                    rs.getInt("MINBOOKINGS"),
                    rs.getInt("MAXBOOKINGS"),
                    rs.getDate("LESSON_DATE").toLocalDate(),
                    rs.getTimestamp("START_TIME").toLocalDateTime(),
                    rs.getInt("DURATION_MINUTES"),
                    rs.getInt("IS_PRIVATE") == 1,
                    lessonType,
                    instructor
                );

                lessons.add(lesson);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lessons;
    }
}
