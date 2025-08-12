package be.iannel.iannelloecoleski.models;

import java.util.List;

import be.iannel.iannelloecoleski.DAO.SkierDAO;

public class Skier extends Person{
	
	public Skier() {
		super();
	}
	
	// Constructeur sans id
	public Skier(String firstName, String lastName, String email,
			String street, int streetNumber, String city, String phoneNumber, int age) {
		super(0,firstName,lastName,email,street,streetNumber,city,phoneNumber,age);
	}
	
	// Constructeur avec id
	public Skier(int id, String firstName, String lastName, String email,
			String street, int streetNumber, String city, String phoneNumber, int age) {
		super(id,firstName,lastName,email,street,streetNumber,city,phoneNumber,age);
	}
    
	//Méthodes
	public boolean addSkier(SkierDAO skierDAO) {
		return skierDAO.create(this);
	}
	
	public static Skier getSkierById(int id, SkierDAO skierDAO) {
		if(id <= 0) {
			throw new IllegalArgumentException("Id plus petit ou égal à 0");
		}
		return skierDAO.read(id);
	}
	
	public static List<Skier> getAllSkiers(SkierDAO skierDAO){
		return skierDAO.readAll();
	}
	
	public boolean deleteSkierById(int id, SkierDAO skierDAO) {
		if(id <= 0) {
			throw new IllegalArgumentException("Id plus petit ou égal à 0");
		}
		return skierDAO.delete(id);
	}
}