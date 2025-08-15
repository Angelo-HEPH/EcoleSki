package be.iannel.iannelloecoleski.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import be.iannel.iannelloecoleski.DAO.SkierDAO;

public class Skier extends Person{
	
    private List<Booking> bookings;

    private static SkierDAO skierDAO = new SkierDAO();
    
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

    public void loadBookings() {
        if (this.id <= 0) {
            throw new IllegalArgumentException("Id invalide, impossible de charger les bookings");
        }
        List<Booking> loadedBookings = Booking.getBookingsBySkierId(this.id);
        for (Booking booking : loadedBookings) {
            booking.setSkier(this);
            this.bookings.add(booking);
        }
    }
    
    public List<Booking> getBookingsForDate(LocalDate date) {
        List<Booking> bookingsForDate = new ArrayList<>();
        for (Booking booking : bookings) {
            if (booking.getLesson() != null && booking.getLesson().getLessonDate() != null) {
                if (booking.getLesson().getLessonDate().equals(date)) {
                    bookingsForDate.add(booking);
                }
            }
        }
        return bookingsForDate;
    }
    
	// Méthodes DAO
	public boolean addSkier() {
		return skierDAO.create(this);
	}
	
	public static Skier getSkierById(int id) {
		if(id <= 0) {
			throw new IllegalArgumentException("Id plus petit ou égal à 0");
		}
		return skierDAO.read(id);
	}
	
	public static List<Skier> getAllSkiers(){
		return skierDAO.readAll();
	}
	
	public boolean deleteSkierById(int id) {
		if(id <= 0) {
			throw new IllegalArgumentException("Id plus petit ou égal à 0");
		}
		return skierDAO.delete(id);
	}
	
	@Override
	public String toString() {
	    return "Skieur [ID=" + getId() + 
	           ", Prénom=" + getFirstName() + 
	           ", Nom=" + getLastName() + 
	           ", Email=" + getEmail() + 
	           ", Adresse=" + getStreet() + " " + getStreetNumber() + ", " + getCity() + 
	           ", Téléphone=" + getPhoneNumber() + 
	           ", Âge=" + getAge();
	}
}