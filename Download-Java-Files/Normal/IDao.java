package edu.mum.library.dataaccess.base;

import java.util.List;

public interface IDao<T, ID> {
	List<T> getAll();

	T readById(ID id);

	void save(T t);

	boolean delete(ID id);

	boolean deleteEntity(T t);

	void insert(T t);
}
