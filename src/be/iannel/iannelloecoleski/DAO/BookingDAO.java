package be.iannel.iannelloecoleski.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import be.iannel.iannelloecoleski.models.Booking;
import be.iannel.iannelloecoleski.models.ConnectionBdd;
import be.iannel.iannelloecoleski.models.Period;
import be.iannel.iannelloecoleski.models.Lesson;
import be.iannel.iannelloecoleski.models.Skier;
import be.iannel.iannelloecoleski.models.Instructor;

public class BookingDAO {

    private Connection connection;

    public BookingDAO() {
        this.connection = ConnectionBdd.getInstance();
    }

    public boolean create(Booking booking) {
        String sql = "INSERT INTO booking (ID, HASINSURANCE, HASDISCOUNT, PERIODID, LESSONID, SKIERID, INSTRUCTORID) " +
                     "VALUES (booking_seq.NEXTVAL, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, booking.getHasInsurance() ? 1 : 0);
            stmt.setInt(2, booking.getHasDiscount() ? 1 : 0);
            stmt.setInt(3, booking.getPeriod().getId());
            stmt.setInt(4, booking.getLesson().getId());
            stmt.setInt(5, booking.getSkier().getId());
            stmt.setInt(6, booking.getInstructor().getId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public Booking read(int id) {
        Booking booking = null;
        String sql = "SELECT * FROM booking WHERE ID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Period period = new Period();
                period.setId(rs.getInt("PERIODID"));

                Lesson lesson = new Lesson();
                lesson.setId(rs.getInt("LESSONID"));

                Skier skier = new Skier();
                skier.setId(rs.getInt("SKIERID"));

                Instructor instructor = new Instructor();
                instructor.setId(rs.getInt("INSTRUCTORID"));

                booking = new Booking(
                    rs.getInt("ID"),
                    rs.getInt("HASINSURANCE") == 1,
                    rs.getInt("HASDISCOUNT") == 1,
                    period,
                    lesson,
                    skier,
                    instructor
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return booking;
    }

    public List<Booking> readAll() {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM booking";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Period period = new Period();
                period.setId(rs.getInt("PERIODID"));

                Lesson lesson = new Lesson();
                lesson.setId(rs.getInt("LESSONID"));

                Skier skier = new Skier();
                skier.setId(rs.getInt("SKIERID"));

                Instructor instructor = new Instructor();
                instructor.setId(rs.getInt("INSTRUCTORID"));

                Booking booking = new Booking(
                    rs.getInt("ID"),
                    rs.getInt("HASINSURANCE") == 1,
                    rs.getInt("HASDISCOUNT") == 1,
                    period,
                    lesson,
                    skier,
                    instructor
                );
                bookings.add(booking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM booking WHERE ID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public List<Booking> getBookingsByLessonId(int lessonId) {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM booking WHERE LESSONID = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, lessonId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Period period = new Period();
                period.setId(rs.getInt("PERIODID"));

                Lesson lesson = new Lesson();
                lesson.setId(rs.getInt("LESSONID"));

                Skier skier = new Skier();
                skier.setId(rs.getInt("SKIERID"));

                Instructor instructor = new Instructor();
                instructor.setId(rs.getInt("INSTRUCTORID"));

                Booking booking = new Booking(
                    rs.getInt("ID"),
                    rs.getInt("HASINSURANCE") == 1,
                    rs.getInt("HASDISCOUNT") == 1,
                    period,
                    lesson,
                    skier,
                    instructor
                );

                bookings.add(booking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bookings;
    }
    
    public List<Booking> getBookingsByInstructorId(int instructorId) {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM booking WHERE INSTRUCTORID = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, instructorId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Period period = new Period();
                period.setId(rs.getInt("PERIODID"));

                Lesson lesson = new Lesson();
                lesson.setId(rs.getInt("LESSONID"));

                Skier skier = new Skier();
                skier.setId(rs.getInt("SKIERID"));

                Instructor instructor = new Instructor();
                instructor.setId(rs.getInt("INSTRUCTORID"));

                Booking booking = new Booking(
                    rs.getInt("ID"),
                    rs.getInt("HASINSURANCE") == 1,
                    rs.getInt("HASDISCOUNT") == 1,
                    period,
                    lesson,
                    skier,
                    instructor
                );

                bookings.add(booking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bookings;
    }

    public List<Booking> getBookingsBySkierId(int skierId) {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM booking WHERE SKIERID = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, skierId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Period period = new Period();
                period.setId(rs.getInt("PERIODID"));

                Lesson lesson = new Lesson();
                lesson.setId(rs.getInt("LESSONID"));

                Skier skier = new Skier();
                skier.setId(rs.getInt("SKIERID"));

                Instructor instructor = new Instructor();
                instructor.setId(rs.getInt("INSTRUCTORID"));

                Booking booking = new Booking(
                    rs.getInt("ID"),
                    rs.getInt("HASINSURANCE") == 1,
                    rs.getInt("HASDISCOUNT") == 1,
                    period,
                    lesson,
                    skier,
                    instructor
                );

                bookings.add(booking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bookings;
    }

    public List<Booking> getBookingsByPeriodId(int periodId) {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM booking WHERE PERIODID = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, periodId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Period period = new Period();
                period.setId(rs.getInt("PERIODID"));

                Lesson lesson = new Lesson();
                lesson.setId(rs.getInt("LESSONID"));

                Skier skier = new Skier();
                skier.setId(rs.getInt("SKIERID"));

                Instructor instructor = new Instructor();
                instructor.setId(rs.getInt("INSTRUCTORID"));

                Booking booking = new Booking(
                    rs.getInt("ID"),
                    rs.getInt("HASINSURANCE") == 1,
                    rs.getInt("HASDISCOUNT") == 1,
                    period,
                    lesson,
                    skier,
                    instructor
                );

                bookings.add(booking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bookings;
    }

}
