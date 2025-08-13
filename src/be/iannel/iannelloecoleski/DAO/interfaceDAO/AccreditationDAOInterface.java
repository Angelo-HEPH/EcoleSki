package be.iannel.iannelloecoleski.DAO.interfaceDAO;

import java.util.List;

import be.iannel.iannelloecoleski.models.Accreditation;

public interface AccreditationDAOInterface extends InterfaceDAO<Accreditation>{
	List<Accreditation> getAccreditationsByInstructorId(int instructorId);
	Accreditation getAccreditationByLessonTypeId(int lessonTypeId);
}
