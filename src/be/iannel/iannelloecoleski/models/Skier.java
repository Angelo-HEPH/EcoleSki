package be.iannel.iannelloecoleski.models;

import java.util.ArrayList;
import java.util.List;

import be.iannel.iannelloecoleski.DAO.BookingDAO;
import be.iannel.iannelloecoleski.DAO.InstructorDAO;
import be.iannel.iannelloecoleski.DAO.LessonDAO;
import be.iannel.iannelloecoleski.DAO.PeriodDAO;
import be.iannel.iannelloecoleski.DAO.SkierDAO;

public class Skier extends Person{
	
    private List<Booking> bookings;

	public Skier() {
		super();
        bookings = new ArrayList<>();
	}
	
	// Constructeur sans id
	public Skier(String firstName, String lastName, String email,
			String street, int streetNumber, String city, String phoneNumber, int age) {
		super(0,firstName,lastName,email,street,streetNumber,city,phoneNumber,age);
        bookings = new ArrayList<>();
	}
	
	// Constructeur avec id
	public Skier(int id, String firstName, String lastName, String email,
			String street, int streetNumber, String city, String phoneNumber, int age) {
		super(id,firstName,lastName,email,street,streetNumber,city,phoneNumber,age);
        bookings = new ArrayList<>();
	}
    
	//Get/Set
	public List<Booking> getBookings() {
		return bookings;
	}

	public void setBookings(List<Booking> bookings) {
		this.bookings = bookings;
	}

	// Méthodes
	public boolean addBooking(Booking booking) {
        if (booking != null && !bookings.contains(booking)) {
            bookings.add(booking);
            if (booking.getSkier() != this) {
                booking.setSkier(this);
            }
            return true;
        }
        return false;
    }

    public boolean removeBooking(Booking booking) {
        if (booking != null && bookings.contains(booking)) {
            bookings.remove(booking);
            if (booking.getSkier() == this) {
                booking.setSkier(null);
            }
            return true;
        }
        return false;
    }

    public void loadBookings(BookingDAO bookingDAO, PeriodDAO periodDAO, LessonDAO lessonDAO, SkierDAO skierDAO, InstructorDAO instructorDAO) {
        if (this.id <= 0) {
            throw new IllegalArgumentException("Id invalide, impossible de charger les bookings");
        }
        List<Booking> loadedBookings = Booking.getBookingsBySkierId(this.id, bookingDAO, periodDAO, lessonDAO, skierDAO, instructorDAO);
        for (Booking booking : loadedBookings) {
            booking.setSkier(this);
            this.bookings.add(booking);
        }
    }
    
	// Méthodes DAO
	public boolean addSkier(SkierDAO skierDAO) {
		return skierDAO.create(this);
	}
	
	public static Skier getSkierById(int id, SkierDAO skierDAO) {
		if(id <= 0) {
			throw new IllegalArgumentException("Id plus petit ou égal à 0");
		}
		return skierDAO.read(id);
	}
	
	public static List<Skier> getAllSkiers(SkierDAO skierDAO){
		return skierDAO.readAll();
	}
	
	public boolean deleteSkierById(int id, SkierDAO skierDAO) {
		if(id <= 0) {
			throw new IllegalArgumentException("Id plus petit ou égal à 0");
		}
		return skierDAO.delete(id);
	}
	
	
}