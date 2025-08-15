package be.iannel.iannelloecoleski.models;

import java.util.ArrayList;
import java.util.List;

import be.iannel.iannelloecoleski.DAO.AccreditationDAO;
import be.iannel.iannelloecoleski.DAO.LessonDAO;
import be.iannel.iannelloecoleski.DAO.LessonTypeDAO;

public class LessonType {
    private int id;
    private String name;
    private double pricePerWeek;
    private int lessonLevel;
    private Accreditation accreditation;
    private List<Lesson> lessons;

    private static LessonTypeDAO lessonTypeDAO = new LessonTypeDAO();
    private static AccreditationDAO accreditationDAO = new AccreditationDAO();
    private static LessonDAO lessonDAO = new LessonDAO();

    
    public LessonType() {lessons = new ArrayList<>();}
    
    public LessonType(int id, String name, double pricePerWeek, int lessonLevel, Accreditation accreditation) {
        this.id = id;
        this.name = name;
        this.pricePerWeek = pricePerWeek;
        this.lessonLevel = lessonLevel;
        setAccreditation(accreditation);
        this.lessons = new ArrayList<>();
    }

    // Getters / Setters 
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	
	public void setPricePerWeek(double pricePerWeek) {
		if(pricePerWeek < 0) {
			throw new IllegalArgumentException("Le prix ne peut pas être négatif");
		}
		this.pricePerWeek = pricePerWeek;
	}
	public double getPricePerWeek() {
		return pricePerWeek;
	}
	
    public Accreditation getAccreditation() {
        return accreditation;
    }
    
    public void setAccreditation(Accreditation accreditation) {
        this.accreditation = accreditation;
        if (accreditation != null && !accreditation.getLessonTypes().contains(this)) {
            accreditation.addLessonType(this);
        }
    }
    
    public int getLessonLevel() {
    	return lessonLevel;
    }
    
    public void setLessonLevel(int lessonLevel) {
    	this.lessonLevel = lessonLevel;
    }
    
    public List<Lesson> getLessons() {
		return lessons;
	}

	public void setLessons(List<Lesson> lessons) {
		this.lessons = lessons;
	}
	

    public void addLesson(Lesson lesson) {
        if(lesson != null && !lessons.contains(lesson)) {
            lessons.add(lesson);
            lesson.setLessonType(this);
        }
    }

    public void removeLesson(Lesson lesson) {
        if(lesson != null && lessons.contains(lesson)) {
            lessons.remove(lesson);
            lesson.setLessonType(null);
        }
    }
    
    //Méthodes
	public void loadRelations() {
		if(id <= 0) {
			throw new IllegalArgumentException("Id plus petit ou égal à 0, impossible de charger les relations");
		}

        Accreditation acc = accreditationDAO.getAccreditationByLessonTypeId(this.id);
        if (acc != null) {
            this.setAccreditation(acc);
        }
        
        if (lessonDAO != null) {
            List<Lesson> lessonsFromDB = lessonDAO.getLessonsByLessonTypeId(this.id);
            if (lessonsFromDB != null) {
                this.lessons.clear();
                for (Lesson lesson : lessonsFromDB) {
                    this.addLesson(lesson);
                }
            }
        }
    }
	
	public boolean isForChildren() {
	    return accreditation != null && (accreditation.getId() == 1 || accreditation.getId() == 3);
	}

	public boolean isForAdults() {
	    return accreditation != null && (accreditation.getId() == 2 
	        || accreditation.getId() == 4
	        || accreditation.getId() == 5
	        || accreditation.getId() == 6);
	}

	public int getMinSkier() {
	    if (isForChildren() || isCompetitionOrOffPiste()) {
	        return 5;
	    }
	    if (isForAdults()) {
	        return 6;
	    }
	    return 1;
	}

	public int getMaxSkier() {
	    if (isForChildren() || isCompetitionOrOffPiste()) {
	        return 8;
	    }
	    if (isForAdults()) {
	        return 10;
	    }
	    return 4;
	}

	public boolean isCompetitionOrOffPiste() {
	    String n = name.toLowerCase();
	    return n.contains("compétition") || n.contains("hors-piste");
	}

	

    public boolean addLessonType() {
        if(lessonTypeDAO == null) {
            throw new IllegalArgumentException("LessonTypeDAO est null");
        }
        return lessonTypeDAO.create(this);
    }

    public static LessonType getLessonTypeById(int id) {
        LessonType lessonType = lessonTypeDAO.read(id);
        if (lessonType != null) {
            lessonType.loadRelations();
        }
        return lessonType;
    }

    public static List<LessonType> getAllLessonTypes() {
        List<LessonType> lessonTypes = lessonTypeDAO.readAll();
        for (LessonType lessonType : lessonTypes) {
        	lessonType.loadRelations();
        }
        return lessonTypes;
    }

    public boolean deleteLessonTypeById(int id) {
        if (lessonTypeDAO == null) {
            System.out.println("Erreur : LessonTypeDAO non initialisé !");
            return false;
        }
		if(id <= 0) {
			throw new IllegalArgumentException("Id plus petit ou égal à 0");
		}
        return lessonTypeDAO.delete(id);
    }
    
    @Override
    public String toString() {
        return "LessonType {" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pricePerWeek=" + pricePerWeek +
                ", lessonLevel=" + lessonLevel +
                ", accreditation=" + (accreditation != null ? accreditation.getName() : "null") +
                '}';
    }

}
