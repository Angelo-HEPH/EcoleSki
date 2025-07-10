package be.iannel.iannelloecoleski.models;

import java.sql.Connection;
import java.util.List;

import be.iannel.iannelloecoleski.DAO.InstructorDAO;

public class Instructor extends Person {

	private InstructorDAO instructorDAO;
	
	private InstructorDAO initDAO() {
		Connection connection = ConnectionBdd.getInstance();
		return new InstructorDAO(connection);
	}
	
	public Instructor() {
		super();
		this.instructorDAO = initDAO();
	}
	
	//Constructeur sans id
	public Instructor(String firstName, String lastName, String email,
			String street, int streetNumber, String city, String phoneNumber, int age) {
		super(0,firstName,lastName,email,street,streetNumber,city,phoneNumber,age);
		
		this.instructorDAO = initDAO();
	}
	
	//Constructeur avec id
	public Instructor(int id, String firstName, String lastName, String email,
			String street, int streetNumber, String city, String phoneNumber, int age) {
		super(id, firstName, lastName, email, street, streetNumber, city, phoneNumber, age);
		
		this.instructorDAO = initDAO();
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
}
