package be.iannel.iannelloecoleski.models;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import be.iannel.iannelloecoleski.DAO.AccreditationDAO;
import be.iannel.iannelloecoleski.DAO.InstructorDAO;

public class Accreditation {

	private int id;
	private String name;
	private List<Instructor>instructors;
	private AccreditationDAO accreditationDAO;
	
	private AccreditationDAO initDAO() {
		Connection connection = ConnectionBdd.getInstance();
		return new AccreditationDAO(connection);
	}
	
	public Accreditation(int id, String name) {
		this.id = id;
		this.name = name;
		
		this.accreditationDAO = initDAO();
		instructors = new ArrayList<Instructor>();
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		if(id <= 0) {
			System.out.println("Id plus petit ou égal à 0.");
		} else {
			this.id = id;
		}
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		if(name != null) {
			this.name = name;
		} else {
			System.out.println("Name est null");
		}
	}
	
	public List<Instructor> getInstructors(){
		if(instructors.isEmpty()) {
			instructors = accreditationDAO.getInstructorsByAccreditationId(this.id);
		}
		return instructors;
	}
	
	public void addInstructor(Instructor instructor) {
		if(!instructors.contains(instructor) && instructor != null) {
		instructor.addAccreditation(this);
		instructors.add(instructor);
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
