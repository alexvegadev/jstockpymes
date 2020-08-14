package org.stockpymes;

import java.util.List;

/**
 * @author Alex P. Vega
 */
public interface ICrud<T> {
	public boolean create(T val);
	public List<T> getAll();
	public T findById(long id);
	public boolean delete(long id);
}
