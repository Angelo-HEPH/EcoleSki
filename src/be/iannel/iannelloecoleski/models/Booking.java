package be.iannel.iannelloecoleski.models;

import java.util.List;

import be.iannel.iannelloecoleski.DAO.BookingDAO;
import be.iannel.iannelloecoleski.DAO.InstructorDAO;
import be.iannel.iannelloecoleski.DAO.LessonDAO;
import be.iannel.iannelloecoleski.DAO.PeriodDAO;
import be.iannel.iannelloecoleski.DAO.SkierDAO;

public class Booking {
    private int id;
    private boolean hasInsurance;
    private boolean hasDiscount;
    private Period period;
    private Lesson lesson;
    private Skier skier;
    private Instructor instructor;

    
    public Booking() {
    	
    }
    
    public Booking(int id, boolean hasInsurance, boolean hasDiscount, Period period, Lesson lesson, Skier skier, Instructor instructor) {
    	
    	this.id = id;
        this.hasInsurance = hasInsurance;
        this.hasDiscount = hasDiscount;
        this.period = period;
        this.lesson = lesson;
        this.skier = skier;
        this.instructor = instructor;

    }

    // Get/Set
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public boolean getHasInsurance() {
        return hasInsurance;
    }
    public void setHasInsurance(boolean hasInsurance) {
        this.hasInsurance = hasInsurance;
    }

    public boolean getHasDiscount() {
        return hasDiscount;
    }
    public void setDiscount(boolean hasDiscount) {
        this.hasDiscount = hasDiscount;
    }

    public Period getPeriod() {
        return period;
    }
    public void setPeriod(Period period) {
        if (this.period != null && this.period != period) {
            this.period.removeBooking(this);
        }
        this.period = period;
        if (period != null) {
            period.addBooking(this);
        }
    }

    public Lesson getLesson() {
        return lesson;
    }
    public void setLesson(Lesson lesson) {
        if (this.lesson != null && this.lesson != lesson) {
            this.lesson.removeBooking(this);
        }
        this.lesson = lesson;
        if (lesson != null) {
            lesson.addBooking(this);
        }
    }

    public Skier getSkier() {
        return skier;
    }
    public void setSkier(Skier skier) {
        if (this.skier != null && this.skier != skier) {
            this.skier.removeBooking(this);
        }
        this.skier = skier;
        if (skier != null) {
            skier.addBooking(this);
        }
    }
    public Instructor getInstructor() {
        return instructor;
    }
    public void setInstructor(Instructor instructor) {
        if (this.instructor != null && this.instructor != instructor) {
            this.instructor.removeBooking(this);
        }
        this.instructor = instructor;
        if (instructor != null) {
            instructor.addBooking(this);
        }
    }
    
    //Méthodes
    public void loadRelations(PeriodDAO periodDAO, LessonDAO lessonDAO, SkierDAO skierDAO, InstructorDAO instructorDAO) {
        if (this.id <= 0) {
            throw new IllegalArgumentException("Id non valide, impossible de charger les relations");
        }

        if (this.period != null) {
            Period loadedPeriod = periodDAO.read(this.period.getId());
            setPeriod(loadedPeriod);
        }
        
        if (this.lesson != null) {
            Lesson loadedLesson = lessonDAO.read(this.lesson.getId());
            setLesson(loadedLesson);
        }

        if (this.skier != null) {
            Skier loadedSkier = skierDAO.read(this.skier.getId());
            setSkier(loadedSkier);
        }

        if (this.instructor != null) {
            Instructor loadedInstructor = instructorDAO.read(this.instructor.getId());
            setInstructor(loadedInstructor);
        }
    }
    
    public boolean create(BookingDAO bookingDAO) {
        return bookingDAO.create(this);
    }
    
    public static Booking getBookingById(int id, BookingDAO bookingDAO,
            PeriodDAO periodDAO, LessonDAO lessonDAO,
            SkierDAO skierDAO, InstructorDAO instructorDAO) {
    	
		Booking booking = bookingDAO.read(id);
		if (booking != null) {
			booking.loadRelations(periodDAO, lessonDAO, skierDAO, instructorDAO);
		}
		return booking;
	}

    public static List<Booking> getAllBooking(BookingDAO bookingDAO,
                 PeriodDAO periodDAO, LessonDAO lessonDAO,
                 SkierDAO skierDAO, InstructorDAO instructorDAO) {
    	
		List<Booking> bookings = bookingDAO.readAll();
		for (Booking booking : bookings) {
			booking.loadRelations(periodDAO, lessonDAO, skierDAO, instructorDAO);
		}
		return bookings;
	}


    public boolean deleteBooking(int id, BookingDAO bookingDAO) {
        Booking booking = bookingDAO.read(id);
        if (booking != null) {
        	bookingDAO.delete(id);
            return true;
        }
        return false;
    }
    
    public static List<Booking> getBookingsByLessonId(int lessonId, BookingDAO bookingDAO,
            PeriodDAO periodDAO, LessonDAO lessonDAO, SkierDAO skierDAO, InstructorDAO instructorDAO) {
        
        List<Booking> bookings = bookingDAO.getBookingsByLessonId(lessonId);

        for (Booking booking : bookings) {
            booking.loadRelations(periodDAO, lessonDAO, skierDAO, instructorDAO);
        }

        return bookings;
    }

    public static List<Booking> getBookingsByInstructorId(int instructorId, BookingDAO bookingDAO,
            PeriodDAO periodDAO, LessonDAO lessonDAO, SkierDAO skierDAO, InstructorDAO instructorDAO) {

        List<Booking> bookings = bookingDAO.getBookingsByInstructorId(instructorId);

        for (Booking booking : bookings) {
            booking.loadRelations(periodDAO, lessonDAO, skierDAO, instructorDAO);
        }

        return bookings;
    }

    public static List<Booking> getBookingsBySkierId(int skierId, BookingDAO bookingDAO,
            PeriodDAO periodDAO, LessonDAO lessonDAO, SkierDAO skierDAO, InstructorDAO instructorDAO) {

        List<Booking> bookings = bookingDAO.getBookingsBySkierId(skierId);

        for (Booking booking : bookings) {
            booking.loadRelations(periodDAO, lessonDAO, skierDAO, instructorDAO);
        }

        return bookings;
    }

}

