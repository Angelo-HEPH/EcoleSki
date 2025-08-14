package be.iannel.iannelloecoleski.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import be.iannel.iannelloecoleski.DAO.BookingDAO;
import be.iannel.iannelloecoleski.DAO.InstructorDAO;
import be.iannel.iannelloecoleski.DAO.LessonDAO;
import be.iannel.iannelloecoleski.DAO.LessonTypeDAO;
import be.iannel.iannelloecoleski.DAO.PeriodDAO;
import be.iannel.iannelloecoleski.DAO.SkierDAO;

public class Lesson {

    private int id;
    private int minBookings;
    private int maxBookings;

    private LocalDate lessonDate;
    private LocalDateTime startTime;
    private int durationMinutes;
    private boolean isPrivate;

	private LessonType lessonType;
    private Instructor instructor;
    private List<Booking> bookings;

    public Lesson() {
        bookings = new ArrayList<>();
    }
    
    // Constructeur sans ID
    public Lesson(int minBookings, int maxBookings, LocalDate lessonDate, LocalDateTime startTime,
                  int durationMinutes, boolean isPrivate, LessonType lessonType, Instructor instructor) {

        this.minBookings = minBookings;
        this.maxBookings = maxBookings;
        this.lessonDate = lessonDate;
        this.startTime = startTime;
        this.durationMinutes = durationMinutes;
        this.isPrivate = isPrivate;
        this.lessonType = lessonType;
        
        bookings = new ArrayList<>();
        setInstructor(instructor);
    }

    // Constructeur avec ID
    public Lesson(int id, int minBookings, int maxBookings, LocalDate lessonDate, LocalDateTime startTime,
                  int durationMinutes, boolean isPrivate, LessonType lessonType, Instructor instructor) {

        this(minBookings, maxBookings, lessonDate, startTime, durationMinutes, isPrivate, lessonType, instructor);
        this.id = id;
        bookings = new ArrayList<>();

    }

    public void loadRelations(InstructorDAO instructorDAO, LessonTypeDAO lessonTypeDAO,
            BookingDAO bookingDAO, PeriodDAO periodDAO, SkierDAO skierDAO,
            InstructorDAO bookingInstructorDAO, LessonDAO lessonDAO) {
		if (this.id <= 0) {
		throw new IllegalArgumentException("Impossible de charger les relations : id invalide");
		}
		
		Instructor loadedInstructor = instructorDAO.read(getInstructor().getId());
		if (loadedInstructor != null) {
		setInstructor(loadedInstructor);
		}
		
		LessonType loadedLessonType = lessonTypeDAO.read(getLessonType().getId());
		if (loadedLessonType != null) {
		setLessonType(loadedLessonType);
		}
		
		List<Booking> loadedBookings = Booking.getBookingsByLessonId(this.id, bookingDAO,
		                                                   periodDAO, lessonDAO,
		                                                   skierDAO, bookingInstructorDAO);
		for (Booking booking : loadedBookings) {
		booking.setLesson(this);
		this.bookings.add(booking);
		}
    }

    public void setInstructor(Instructor instructor) {
        if (this.instructor != instructor) {
            Instructor oldInstructor = this.instructor;
            this.instructor = instructor;

            if (oldInstructor != null) {
                oldInstructor.getLessons().remove(this);
            }

            if (instructor != null && !instructor.getLessons().contains(this)) {
                instructor.getLessons().add(this);
            }
        }
    }
	
	public Instructor getInstructor() {
		return instructor;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getMinBookings() {
		return minBookings;
	}
	
	public void setMinBookings(int nb) {
	    if (nb > 0 && (maxBookings == 0 || nb <= maxBookings)) {
	        this.minBookings = nb;
	    } else {
	        throw new IllegalArgumentException("minBookings doit être plus petit que 0 et plus grand ou égal à maxBookings.");
	    }
	}

	public int getMaxBookings() {
		return maxBookings;
	}
	
	public void setMaxBookings(int nb) {
	    if (nb > 0 && nb >= minBookings) {
	        this.maxBookings = nb;
	    } else {
	    	throw new IllegalArgumentException("maxBookings doit être plus grand que 0 et plus grand ou égal à minBookings.");
	    }
	}
	
    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public LocalDate getLessonDate() {
        return lessonDate;
    }

    public void setLessonDate(LocalDate lessonDate) {
        this.lessonDate = lessonDate;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }
    
    public LessonType getLessonType() {
    	return lessonType;
    }
    
    public void setLessonType(LessonType lessonType) {
    	this.lessonType = lessonType;
    }
    
    public List<Booking> getBookings() {
		return bookings;
	}

	public void setBookings(List<Booking> bookings) {
		this.bookings = bookings;
	}
	
    //Méthodes
    public boolean canAddBooking() {
        return bookings == null || bookings.size() < maxBookings;
    }

    
    public boolean addBooking(Booking booking) {
        if (canAddBooking()) {

            bookings.add(booking);

            if (booking.getLesson() != this) {
                booking.setLesson(this);
            }
            return true;
        } else {
            throw new IllegalArgumentException("Impossible d'ajouter la réservation : nombre maximum atteint.");
        }
    }

    public boolean removeBooking(Booking booking) {
        if (bookings != null && bookings.contains(booking)) {
            bookings.remove(booking);

            if (booking.getLesson() == this) {
                booking.setLesson(null);
            }
            return true;
        }
        return false;
    }

    public boolean addLesson(LessonDAO lessonDAO) {
        return lessonDAO.create(this);
    }
    
    public static Lesson getLessonById(int id, LessonDAO lessonDAO, InstructorDAO instructorDAO) {
		if(id <= 0) {
			throw new IllegalArgumentException("Id plus petit ou égal à 0");
		}
        Lesson lesson = lessonDAO.read(id);
        if (lesson != null && lesson.getInstructor() != null) {
            Instructor instructor = instructorDAO.read(lesson.getInstructor().getId());
            lesson.setInstructor(instructor);
        }
        return lesson;
    }
    
    public static List<Lesson> getAllLessons(LessonDAO lessonDAO, InstructorDAO instructorDAO) {
        List<Lesson> lessons = lessonDAO.readAll();
        for (Lesson lesson : lessons) {
            if (lesson.getInstructor() != null) {
                Instructor instructor = instructorDAO.read(lesson.getInstructor().getId());
                lesson.setInstructor(instructor);
            }
        }
        return lessons;
    }
    
    public static boolean deleteLessonById(int id, LessonDAO lessonDAO) {
		if(id <= 0) {
			throw new IllegalArgumentException("Id plus petit ou égal à 0");
		}
        return lessonDAO.delete(id);
    }
    
    public static List<Lesson> getLessonsByInstructorId(int id, LessonDAO lessonDAO){
		if(id <= 0) {
			throw new IllegalArgumentException("Id plus petit ou égal à 0");
		}
    	return lessonDAO.getLessonsByInstructorId(id);
    }
}
