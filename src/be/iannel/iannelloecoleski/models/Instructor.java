package be.iannel.iannelloecoleski.models;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import be.iannel.iannelloecoleski.DAO.InstructorDAO;

public class Instructor extends Person {

	private List<Accreditation> accreditations;
	private InstructorDAO instructorDAO;
	
	private InstructorDAO initDAO() {
		Connection connection = ConnectionBdd.getInstance();
		return new InstructorDAO(connection);
	}
	
	public Instructor() {
		super();
		this.instructorDAO = initDAO();
		accreditations = new ArrayList<Accreditation>();
	}
	
	//Constructeur sans id
	public Instructor(String firstName, String lastName, String email,
			String street, int streetNumber, String city, String phoneNumber, int age, Accreditation accreditation) {
		super(0,firstName,lastName,email,street,streetNumber,city,phoneNumber,age);
		
		this.instructorDAO = initDAO();
		accreditations = new ArrayList<Accreditation>();
		
		if(accreditation == null) {
			throw new IllegalArgumentException("Un instructeur doit avoir au moins une accréditation.");
		}
		
		accreditations.add(accreditation);
	}
	
	//Constructeur avec id
	public Instructor(int id, String firstName, String lastName, String email,
			String street, int streetNumber, String city, String phoneNumber, int age, Accreditation accreditation) {
		super(id, firstName, lastName, email, street, streetNumber, city, phoneNumber, age);
		
		this.instructorDAO = initDAO();
		accreditations = new ArrayList<Accreditation>();
		
		if(accreditation == null) {
			throw new IllegalArgumentException("Un instructeur doit avoir au moins une accréditation.");
		}
		
		accreditations.add(accreditation);
	}
	
	//Méthodes
	public boolean addInstructor() {
		return instructorDAO.create(this);
	}
	
	public Instructor getInstructorById(int id) {
		if(id <= 0) {
			System.out.println("Id plus petit ou égal à 0.");
			return null;
		}
		return instructorDAO.read(id);
	}
	
	public List<Instructor> getAllInstructors(){
		return instructorDAO.readAll();
	}
	
	public boolean deleteInstructorById(int id) {
		if(id <= 0) {
			System.out.println("Id plus petit ou égal à 0.");
			return false;
		}
		return instructorDAO.delete(id);
	}
	
	public void addAccreditation(Accreditation accreditation) {
		if(!accreditations.contains(accreditation)) {
		accreditations.add(accreditation);
		}
	}
	
	public List<Accreditation> getAccreditations(){
		return accreditations;
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
