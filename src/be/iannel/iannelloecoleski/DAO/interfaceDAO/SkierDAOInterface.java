package be.iannel.iannelloecoleski.DAO.interfaceDAO;

import be.iannel.iannelloecoleski.models.Skier;

public interface SkierDAOInterface extends InterfaceDAO<Skier> {

	boolean existsByEmail(String email);
}