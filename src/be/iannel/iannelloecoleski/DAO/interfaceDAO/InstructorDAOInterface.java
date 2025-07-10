package be.iannel.iannelloecoleski.DAO.interfaceDAO;

import be.iannel.iannelloecoleski.models.Instructor;

public interface InstructorDAOInterface extends InterfaceDAO<Instructor>{

	boolean existsByEmail(String email);
}
