package be.iannel.iannelloecoleski.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import be.iannel.iannelloecoleski.DAO.InstructorDAO;
import be.iannel.iannelloecoleski.DAO.LessonDAO;
import be.iannel.iannelloecoleski.DAO.LessonTypeDAO;

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

        setInstructor(instructor);
    }

    // Constructeur avec ID
    public Lesson(int id, int minBookings, int maxBookings, LocalDate lessonDate, LocalDateTime startTime,
                  int durationMinutes, boolean isPrivate, LessonType lessonType, Instructor instructor) {

        this(minBookings, maxBookings, lessonDate, startTime, durationMinutes, isPrivate, lessonType, instructor);
        this.id = id;
    }

    public void loadRelations(InstructorDAO instructorDAO, LessonTypeDAO lessonTypeDAO) {
        if (this.id <= 0) {
            System.out.println("Impossible de charger les relations : id invalide");
            return;
        }

        // Chargement instructor
        Instructor loadedInstructor = instructorDAO.read(getInstructor().id);
        if (loadedInstructor != null) {
            setInstructor(loadedInstructor);
        }

        // Chargement lessonType
        LessonType loadedLessonType = lessonTypeDAO.read(getLessonType().getId());
        if (loadedLessonType != null) {
            setLessonType(loadedLessonType);
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
    
    public boolean addLesson(LessonDAO lessonDAO) {
        return lessonDAO.create(this);
    }
    
    public static Lesson getLessonById(int id, LessonDAO lessonDAO, InstructorDAO instructorDAO) {
        if (id <= 0) {
            System.out.println("Id plus petit ou égal à 0.");
            return null;
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
        if (id <= 0) {
            System.out.println("Id plus petit ou égal à 0.");
            return false;
        }
        return lessonDAO.delete(id);
    }
    
    public static List<Lesson> getLessonsByInstructorId(int id, LessonDAO lessonDAO){
    	if(id <= 0) {
    		return null;
    	}
    	return lessonDAO.getLessonsByInstructorId(id);
    }
}
