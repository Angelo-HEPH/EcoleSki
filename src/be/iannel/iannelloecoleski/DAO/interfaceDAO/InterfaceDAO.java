package be.iannel.iannelloecoleski.DAO.interfaceDAO;

import java.util.List;

public interface InterfaceDAO<T> {

	boolean create(T entity);
	T read(int id);
	List<T> readAll();
	boolean delete(int id);
}
