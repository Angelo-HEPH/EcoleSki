package be.iannel.iannelloecoleski.models;

import java.util.List;

import be.iannel.iannelloecoleski.DAO.AccreditationDAO;
import be.iannel.iannelloecoleski.DAO.LessonTypeDAO;

public class LessonType {
    private int id;
    private String name;
    private double pricePerWeek;
    private int lessonLevel;
    private Accreditation accreditation;

    public LessonType() {}
    
    public LessonType(int id, String name, double pricePerWeek, int lessonLevel, Accreditation accreditation) {
        this.id = id;
        this.name = name;
        this.pricePerWeek = pricePerWeek;
        this.lessonLevel = lessonLevel;
        setAccreditation(accreditation);
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
    
    //Méthodes
    
    public void loadRelations(AccreditationDAO accreditationDAO) {
		if(id <= 0) {
			throw new IllegalArgumentException("Id plus petit ou égal à 0, impossible de charger les relations");
		}

        Accreditation acc = accreditationDAO.getAccreditationByLessonTypeId(this.id);
        if (acc != null) {
            this.setAccreditation(acc);
        }
    }

    public boolean addLessonType(LessonTypeDAO lessonTypeDAO) {
        if(lessonTypeDAO == null) {
            throw new IllegalArgumentException("LessonTypeDAO est null");
        }
        return lessonTypeDAO.create(this);
    }

    public static LessonType getLessonTypeById(int id, LessonTypeDAO lessonTypeDAO, AccreditationDAO accreditationDAO) {
        LessonType lessonType = lessonTypeDAO.read(id);
        if (lessonType != null) {
            lessonType.loadRelations(accreditationDAO);
        }
        return lessonType;
    }

    public static List<LessonType> getAllLessonTypes(LessonTypeDAO lessonTypeDAO, AccreditationDAO accreditationDAO) {
        List<LessonType> lessonTypes = lessonTypeDAO.readAll();
        for (LessonType lessonType : lessonTypes) {
        	lessonType.loadRelations(accreditationDAO);
        }
        return lessonTypes;
    }

    public boolean deleteLessonTypeById(int id, LessonTypeDAO lessonTypeDAO) {
        if (lessonTypeDAO == null) {
            System.out.println("Erreur : LessonTypeDAO non initialisé !");
            return false;
        }
		if(id <= 0) {
			throw new IllegalArgumentException("Id plus petit ou égal à 0");
		}
        return lessonTypeDAO.delete(id);
    }
}
