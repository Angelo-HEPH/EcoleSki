package be.iannel.iannelloecoleski.models;

import java.util.ArrayList;
import java.util.List;

import be.iannel.iannelloecoleski.DAO.AccreditationDAO;
import be.iannel.iannelloecoleski.DAO.BookingDAO;
import be.iannel.iannelloecoleski.DAO.InstructorDAO;
import be.iannel.iannelloecoleski.DAO.LessonDAO;
import be.iannel.iannelloecoleski.DAO.PeriodDAO;
import be.iannel.iannelloecoleski.DAO.SkierDAO;

public class Instructor extends Person {

    private List<Accreditation> accreditations;
    private List<Lesson> lessons;
    private List<Booking> bookings;

    public Instructor() {
        super();
        accreditations = new ArrayList<>();
        lessons = new ArrayList<>();
        bookings = new ArrayList<>();
    }

    // Constructeur sans ID
    public Instructor(String firstName, String lastName, String email,
                      String street, int streetNumber, String city,
                      String phoneNumber, int age, Accreditation accreditation) {

        super(0, firstName, lastName, email, street, streetNumber, city, phoneNumber, age);

        accreditations = new ArrayList<>();
        lessons = new ArrayList<>();
        bookings = new ArrayList<>();
        
        if (accreditation == null) {
            throw new IllegalArgumentException("Un instructeur doit avoir au moins une accréditation.");
        }

        addAccreditation(accreditation);
    }

    // Constructeur avec ID
    public Instructor(int id, String firstName, String lastName, String email,
                      String street, int streetNumber, String city,
                      String phoneNumber, int age, Accreditation accreditation) {

        super(id, firstName, lastName, email, street, streetNumber, city, phoneNumber, age);

        accreditations = new ArrayList<>();
        lessons = new ArrayList<>();
        bookings = new ArrayList<>();

        if (accreditation == null) {
            throw new IllegalArgumentException("Un instructeur doit avoir au moins une accréditation.");
        }

        addAccreditation(accreditation);
    }
	
    //Get/Set
    public List<Accreditation> getAccreditations() {
		return accreditations;
	}

	public void setAccreditations(List<Accreditation> accreditations) {
		this.accreditations = accreditations;
	}

	public List<Lesson> getLessons() {
		return lessons;
	}

	public void setLessons(List<Lesson> lessons) {
		this.lessons = lessons;
	}

	public List<Booking> getBookings() {
		return bookings;
	}

	public void setBookings(List<Booking> bookings) {
		this.bookings = bookings;
	}
    
	//Méthodes
    public void loadRelations(AccreditationDAO accreditationDAO, LessonDAO lessonDAO, BookingDAO bookingDAO,
            PeriodDAO periodDAO, SkierDAO skierDAO, InstructorDAO instructorDAO) {
    	
        if (this.id <= 0) {
            throw new IllegalArgumentException("Id plus petit ou égal à 0, impossible de charger les relations");
        }
        
        List<Accreditation> loadedAccreditations = accreditationDAO.getAccreditationsByInstructorId(this.id);
        this.accreditations.addAll(loadedAccreditations);

        List<Lesson> loadedLessons = lessonDAO.getLessonsByInstructorId(this.id);
        for (Lesson lesson : loadedLessons) {
            lesson.setInstructor(this);
            this.lessons.add(lesson);
        }
        
        List<Booking> loadedBookings = Booking.getBookingsByInstructorId(this.id, bookingDAO,
                periodDAO, lessonDAO,
                skierDAO, instructorDAO);
		for (Booking booking : loadedBookings) {
		booking.setInstructor(this);
		this.bookings.add(booking);
}
    }

    public boolean addBooking(Booking booking) {
        if (booking != null && !bookings.contains(booking)) {
            bookings.add(booking);
            if (booking.getInstructor() != this) {
                booking.setInstructor(this);
            }
            return true;
        }
        return false;
    }

    public boolean removeBooking(Booking booking) {
        if (booking != null && bookings.contains(booking)) {
            bookings.remove(booking);
            if (booking.getInstructor() == this) {
                booking.setInstructor(null);
            }
            return true;
        }
        return false;
    }
	
    //Méthodes DAO
	public boolean addInstructor(InstructorDAO instructorDAO) {
		return instructorDAO.create(this);
	}
    
	public static Instructor getInstructorById(int id, InstructorDAO instructorDAO, AccreditationDAO accreditationDAO, LessonDAO lessonDAO, BookingDAO bookingDAO,PeriodDAO periodDAO,
	        SkierDAO skierDAO) {
        if (id <= 0) {
            throw new IllegalArgumentException("Id plus petit ou égal à 0");
        }
	    Instructor instructor = instructorDAO.read(id);
	    if (instructor != null) {
	        instructor.loadRelations(accreditationDAO, lessonDAO, bookingDAO, periodDAO, skierDAO, instructorDAO);
	    }
	    return instructor;
	}

	public static List<Instructor> getAllInstructors(InstructorDAO instructorDAO, AccreditationDAO accreditationDAO, LessonDAO lessonDAO, BookingDAO bookingDAO,PeriodDAO periodDAO,
	        SkierDAO skierDAO) {
	    List<Instructor> instructors = instructorDAO.readAll();
	    for (Instructor instructor : instructors) {
	        instructor.loadRelations(accreditationDAO, lessonDAO, bookingDAO, periodDAO, skierDAO, instructorDAO);
	    }
	    return instructors;
	}

	
	public boolean deleteInstructorById(int id, InstructorDAO instructorDAO) {
        if (id <= 0) {
            throw new IllegalArgumentException("Id plus petit ou égal à 0");
        }
		return instructorDAO.delete(id);
	}
	
	public void addAccreditation(Accreditation accreditation) {
		if(!accreditations.contains(accreditation)) {
		accreditations.add(accreditation);
		}
	}
	

	
	public void addLesson(Lesson lesson) {
		if(!lessons.contains(lesson)) {
			lessons.add(lesson);
			lesson.setInstructor(this);
		}
	}
	
	
	@Override
	public int hashCode() {
	    int result = id;
	    result += (getFirstName() != null ? getFirstName().hashCode() : 0);
	    result += (getLastName() != null ? getLastName().hashCode() : 0);
	    result += (getEmail() != null ? getEmail().hashCode() : 0);
	    return result;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
	    if (obj == null || obj.getClass() != this.getClass()) {
	        return false;
	    }

	    Instructor other = (Instructor) obj;

	    return this.id == other.getId() &&
	           (this.getFirstName() != null && this.getFirstName().equals(other.getFirstName())) &&
	           (this.getLastName() != null && this.getLastName().equals(other.getLastName())) &&
	           (this.getEmail() != null && this.getEmail().equals(other.getEmail()));
	}

}
