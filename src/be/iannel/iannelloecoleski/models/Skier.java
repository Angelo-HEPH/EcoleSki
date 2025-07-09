package be.iannel.iannelloecoleski.models;

import java.sql.Connection;

import be.iannel.iannelloecoleski.DAO.SkierDAO;

public class Skier extends Person{
	
	private SkierDAO skierDAO = initDAO();
	
	private static SkierDAO  initDAO() {
		Connection connection = ConnectionBdd.getInstance();
		return new SkierDAO(connection);
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
	
	public void addSkier() {
		skierDAO.create(this);
	}
}