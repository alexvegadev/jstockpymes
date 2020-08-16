package org.stockpymes;

import java.util.ArrayList;
import java.util.List;

import org.stockpymes.interfaces.ICrud;
import org.stockpymes.models.Client;
import org.stockpymes.models.OrderFactory;

import com.google.gson.JsonParser;

/**
 * @author Alex P. Vega
 */
public class ClientOption implements ICrud<Client> {
	private final StockPymes _stockAPI;
	private final OrderFactory<Client> _orderFactory;
	
	protected ClientOption(StockPymes stockAPI) {
		this._stockAPI = stockAPI;
		this._orderFactory = OrderFactory.newOrder((Client a, Client b) -> (int)(a.getId() - b.getId()));//ordered by default per id
	}
	
	@Override
	public boolean create(Client val) {
		if(val != null) {
			String response = Utility.requestPost(_stockAPI, "clients", val);
			return response != null;
		}
		return false;
	}

	@Override
	public List<Client> getAll() {
		final String result = Utility.requestGet(_stockAPI, "products");
		if(result != null) {
			var clients = new ArrayList<Client>();
			var json = JsonParser.parseString(result);
	        for(var prod : json.getAsJsonArray()) {
	        	var jsobj = prod.getAsJsonObject();
	        	var id = Long.valueOf(Utility.getValJson(jsobj, "id"));
	        	var firstName = Utility.getValJson(jsobj, "firstName");
	        	var lastName = Utility.getValJson(jsobj, "lastName");
	        	clients.add( new Client(id, firstName, lastName));
	        }
	        clients.sort(order().getHandler());
	        return clients;
		}
        
		return null;
	}

	@Override
	public Client findById(long id) {
		final String result = Utility.requestGet(_stockAPI, "products/"+id);
		if(result != null) {
			Client client = null;
			var json = JsonParser.parseString(result);
	        for(var prod : json.getAsJsonArray()) {
	        	var jsobj = prod.getAsJsonObject();
	        	var firstName = Utility.getValJson(jsobj, "firstName");
	        	var lastName = Utility.getValJson(jsobj, "lastName");
	        	client = new Client(id, firstName, lastName);
	        }
	        return client;
		}
		return null;
	}

	@Override
	public boolean delete(long id) {
		var response = Utility.requestDelete(_stockAPI, "clients/"+id);
		return response != null;
	}

	@Override
	public boolean update(Client val) {
		var response = Utility.requestPut(_stockAPI, "clients", val);
		return response != null;
	}
	
	@Override
	public OrderFactory<Client> order() {
		return _orderFactory;
	}
	
	public StockPymes getAttachedAPI() {
		return _stockAPI;
	}
}
