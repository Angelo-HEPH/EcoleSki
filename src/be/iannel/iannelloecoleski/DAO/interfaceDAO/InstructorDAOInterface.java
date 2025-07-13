package be.iannel.iannelloecoleski.DAO.interfaceDAO;

import java.util.List;

import be.iannel.iannelloecoleski.models.Instructor;

public interface InstructorDAOInterface extends InterfaceDAO<Instructor>{

	boolean existsByEmail(String email);
	List<Instructor> getInstructorsByAccreditationId(int accreditationId);
}
