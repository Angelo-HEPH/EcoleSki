package be.iannel.iannelloecoleski.DAO.interfaceDAO;

import java.util.List;

import be.iannel.iannelloecoleski.models.LessonType;

public interface LessonTypeDAOInterface extends InterfaceDAO<LessonType>{
	List<LessonType> getLessonTypesByAccreditationId(int accreditationId);
}
