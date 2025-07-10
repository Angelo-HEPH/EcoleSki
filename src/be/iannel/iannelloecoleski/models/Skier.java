package be.iannel.iannelloecoleski.models;

import java.sql.Connection;
import java.util.List;

import be.iannel.iannelloecoleski.DAO.SkierDAO;

public class Skier extends Person{
	
	private SkierDAO skierDAO = initDAO();
	
	private SkierDAO  initDAO() {
		Connection connection = ConnectionBdd.getInstance();
		return new SkierDAO(connection);
	}
	
	public Skier() {
		super();
		
		this.skierDAO = initDAO();
	}
	
	// Constructeur sans id
	public Skier(String firstName, String lastName, String email,
			String street, int streetNumber, String city, String phoneNumber, int age) {
		super(0,firstName,lastName,email,street,streetNumber,city,phoneNumber,age);
		
		this.skierDAO = initDAO();
	}
	
	// Constructeur avec id
	public Skier(int id, String firstName, String lastName, String email,
			String street, int streetNumber, String city, String phoneNumber, int age) {
		super(id,firstName,lastName,email,street,streetNumber,city,phoneNumber,age);
		
		this.skierDAO = initDAO();
	}
	
	//Méthodes
	public boolean addSkier() {
		return skierDAO.create(this);
	}
	
	public Skier getSkierById(int id) {
		if(id <= 0) {
			System.out.println("Id plus petit ou égal à 0.");
			return null;
		}
		return skierDAO.read(id);
	}
	
	public List<Skier> getAllSkiers(){
		return skierDAO.readAll();
	}
	
	public boolean deleteSkierById(int id) {
		if(id <= 0) {
			System.out.println("Id plus petit ou égal à 0.");
			return false;
		}
		return skierDAO.delete(id);
	}
}