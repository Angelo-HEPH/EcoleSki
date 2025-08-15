package be.iannel.iannelloecoleski.DAO.interfaceDAO;

import java.util.List;

import be.iannel.iannelloecoleski.models.Booking;

public interface BookingDAOInterface extends InterfaceDAO<Booking>{
	List<Booking> getBookingsByLessonId(int lessonId);
	List<Booking> getBookingsByInstructorId(int instructorId);
	List<Booking> getBookingsBySkierId(int skierId);
	List<Booking> getBookingsByPeriodId(int periodId);
}
