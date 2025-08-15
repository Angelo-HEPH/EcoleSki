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

    private static InstructorDAO instructorDAO = new InstructorDAO();
    private static LessonDAO lessonDAO = new LessonDAO();
    private static BookingDAO bookingDAO = new BookingDAO();
    private static PeriodDAO periodDAO = new PeriodDAO();
    private static SkierDAO skierDAO = new SkierDAO();
    
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
    public void loadRelations() {
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
    
    public int calculatePrice() {
        int basePrice = lesson.getLessonPrice();

        if (getHasInsurance()) {
            basePrice += Math.round(20.0f / 6);
        }

        if (isFullDayBooking()) {
            basePrice = Math.round(basePrice * 0.85f);
        }

        return basePrice;
    }

    private boolean isFullDayBooking() {
        List<Booking> allBookings = getSkier().getBookingsForDate(lesson.getLessonDate());
        boolean hasMorning = false;
        boolean hasAfternoon = false;

        for (Booking b : allBookings) {
            if (b.getLesson().isMorningLesson()) hasMorning = true;
            if (b.getLesson().isAfternoonLesson()) hasAfternoon = true;
        }

        return hasMorning && hasAfternoon;
    }


    
    public boolean create() {
        return bookingDAO.create(this);
    }
    
    public static Booking getBookingById(int id) {
    	
		Booking booking = bookingDAO.read(id);
		if (booking != null) {
			booking.loadRelations();
		}
		return booking;
	}

    public static List<Booking> getAllBooking() {
    	
		List<Booking> bookings = bookingDAO.readAll();
		for (Booking booking : bookings) {
			booking.loadRelations();
		}
		return bookings;
	}


    public boolean deleteBooking(int id) {
        Booking booking = bookingDAO.read(id);
        if (booking != null) {
        	bookingDAO.delete(id);
            return true;
        }
        return false;
    }
    
    public static List<Booking> getBookingsByLessonId(int lessonId) {
        
        List<Booking> bookings = bookingDAO.getBookingsByLessonId(lessonId);

        for (Booking booking : bookings) {
            booking.loadRelations();
        }

        return bookings;
    }

    public static List<Booking> getBookingsByInstructorId(int instructorId) {

        List<Booking> bookings = bookingDAO.getBookingsByInstructorId(instructorId);

        for (Booking booking : bookings) {
            booking.loadRelations();
        }

        return bookings;
    }

    public static List<Booking> getBookingsBySkierId(int skierId) {

        List<Booking> bookings = bookingDAO.getBookingsBySkierId(skierId);

        for (Booking booking : bookings) {
            booking.loadRelations();
        }

        return bookings;
    }

    @Override
    public String toString() {
        return "Booking {" +
                "hasInsurance=" + hasInsurance +
                ", hasDiscount=" + hasDiscount +
                ", period=" + (period != null ? period.getName() : "null") +
                ", lessonId=" + (lesson != null ? lesson.getId() : "null") +
                ", skier=" + (skier != null ? skier.getLastName() : "null") +
                ", instructor=" + (instructor != null ? instructor.getLastName() : "null") +
                "}";
    }

}

