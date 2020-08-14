package org.stockpymes.models;

import java.util.Comparator;

/**
 * @author Alex P. Vega
 */
public class OrderFactory<T> {
	private String _param;
	private Comparator<T> _handler;
	
	public OrderFactory(Comparator<T> _handler) {
		this._handler = _handler;
	}
	
	public static <P> OrderFactory<P> newOrder(Comparator<P> comp) {
		return new OrderFactory<P>(comp);
	}
	
	public static <P> OrderFactory<P> newOrder(String param, OrderType order){
		
		return null;
	}
	
	public void changeOrder(Comparator<T> comp) {
		this._handler = comp;
	}
	
	public Comparator<?> getHandler() {
		return _handler;
	}
	
	public String getParam() {
		return _param;
	}
}
