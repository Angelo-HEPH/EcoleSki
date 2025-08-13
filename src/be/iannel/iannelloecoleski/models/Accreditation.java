package be.iannel.iannelloecoleski.models;

import java.util.ArrayList;
import java.util.List;

import be.iannel.iannelloecoleski.DAO.AccreditationDAO;
import be.iannel.iannelloecoleski.DAO.InstructorDAO;
import be.iannel.iannelloecoleski.DAO.LessonTypeDAO;

public class Accreditation {

    private int id;
    private String name;
    private List<Instructor> instructors;
    private List<LessonType> lessonTypes;

    public Accreditation(int id, String name) {
        this.id = id;
        this.name = name;
        this.instructors = new ArrayList<>();
        this.lessonTypes = new ArrayList<>();
    }
    
    public Accreditation() {
        this.instructors = new ArrayList<>();
        this.lessonTypes = new ArrayList<>();
    }
    
    // Getter / Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id > 0) this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name != null) this.name = name;
    }

    public List<Instructor> getInstructors() {
        return instructors;
    }

    public List<LessonType> getLessonTypes() {
        return lessonTypes;
    }
    
    //Méthodes
    public void loadRelations(InstructorDAO instructorDAO, LessonTypeDAO lessonTypeDAO) {
		if(id <= 0) {
			throw new IllegalArgumentException("Id plus petit ou égal à 0, impossible de charger les relations");
		}

        this.instructors = instructorDAO.getInstructorsByAccreditationId(this.id);
        for (Instructor instructor : instructors) {
            instructor.addAccreditation(this);
        }

        this.lessonTypes = lessonTypeDAO.getLessonTypesByAccreditationId(this.id);
        for (LessonType lt : lessonTypes) {
            lt.setAccreditation(this);
        }
    }
    
    public boolean addAccreditation(AccreditationDAO accreditationDAO) {
        return accreditationDAO.create(this);
    }
    
    public static Accreditation getAccreditationById(int id, AccreditationDAO accreditationDAO, InstructorDAO instructorDAO, LessonTypeDAO lessonTypeDAO) {
		if(id <= 0) {
			throw new IllegalArgumentException("Id plus petit ou égal à 0");
		}
        Accreditation accreditation = accreditationDAO.read(id);
        if (accreditation != null) {
            accreditation.loadRelations(instructorDAO, lessonTypeDAO);
        }
        return accreditation;
    }
    
    public static List<Accreditation> getAllAccreditations(AccreditationDAO accreditationDAO, InstructorDAO instructorDAO, LessonTypeDAO lessonTypeDAO) {
        List<Accreditation> accs = accreditationDAO.readAll();
        for (Accreditation acc : accs) {
            acc.loadRelations(instructorDAO, lessonTypeDAO);
        }
        return accs;
    }
    
    public boolean deleteAccreditationById(int id, AccreditationDAO accreditationDAO) {
		if(id <= 0) {
			throw new IllegalArgumentException("Id plus petit ou égal à 0");
		}
        return accreditationDAO.delete(id);
    }
    
    public void addInstructor(Instructor instructor) {
        if (instructor != null && !instructors.contains(instructor)) {
            instructors.add(instructor);
            instructor.addAccreditation(this);
        }
    }

    public void addLessonType(LessonType lessonType) {
        if (lessonType != null && !lessonTypes.contains(lessonType)) {
            lessonTypes.add(lessonType);
            lessonType.setAccreditation(this);
        }
    }

	@Override
	public int hashCode() {
		return (name != null ? name.hashCode() : 0) + id;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		if(obj == null || obj.getClass() != this.getClass()) {
			return false;
		}
		
		Accreditation other = (Accreditation) obj;
		
		return this.id == other.getId() && (this.name != null && this.name.equals(other.getName()));
	}
}
