package be.iannel.iannelloecoleski.DAO.interfaceDAO;

import java.util.List;

import be.iannel.iannelloecoleski.models.Skier;

public interface SkierDAOInterface {

	boolean create(Skier skier);
	Skier read(int id);
	List<Skier> readAll();
	boolean delete(int id);
	boolean existsByEmail(String email);
}