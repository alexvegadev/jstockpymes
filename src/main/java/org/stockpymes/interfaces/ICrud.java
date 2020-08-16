package org.stockpymes.interfaces;

import java.util.List;

import org.stockpymes.StockPymes;
import org.stockpymes.models.OrderFactory;

/**
 * @author Alex P. Vega
 */
public interface ICrud<T> {
	public boolean create(T val);
	public List<T> getAll();
	public T findById(long id);
	public boolean delete(long id);
	public boolean update(T val);
	public OrderFactory<T> order();
	public StockPymes getAttachedAPI();
}
