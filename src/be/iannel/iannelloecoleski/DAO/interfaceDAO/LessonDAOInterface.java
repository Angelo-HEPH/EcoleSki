package be.iannel.iannelloecoleski.DAO.interfaceDAO;

import java.util.List;

import be.iannel.iannelloecoleski.models.Lesson;

public interface LessonDAOInterface extends InterfaceDAO<Lesson> {
	List<Lesson> getLessonsByInstructorId(int instructorId);
}
