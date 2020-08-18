package org.stockpymes;

import java.util.ArrayList;
import java.util.List;

import org.stockpymes.interfaces.ICrud;
import org.stockpymes.models.Client;
import org.stockpymes.models.OrderFactory;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * @author Alex P. Vega
 */
public class ClientOption implements ICrud<Client> {
	private final StockPymes _stockAPI;
	private final OrderFactory<Client> _orderFactory;
	
	protected ClientOption(StockPymes stockAPI) {
		this._stockAPI = stockAPI;
		this._orderFactory = OrderFactory.newOrder(null);//ordered by default per id
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
			List<Client> clients = new ArrayList<>();
			JsonElement json = JsonParser.parseString(result);
	        for(JsonElement prod : json.getAsJsonArray()) {
	        	JsonObject jsobj = prod.getAsJsonObject();
	        	Long id = Long.valueOf(Utility.getValJson(jsobj, "id"));
	        	String firstName = Utility.getValJson(jsobj, "firstName");
	        	String lastName = Utility.getValJson(jsobj, "lastName");
	        	clients.add( new Client(id, firstName, lastName));
	        }
	        if(order().getHandler() != null) {
	        	clients.sort(order().getHandler());
	        }
	        return clients;
		}
        
		return null;
	}

	@Override
	public Client findById(long id) {
		final String result = Utility.requestGet(_stockAPI, "products/"+id);
		if(result != null) {
			Client client = null;
			JsonElement json = JsonParser.parseString(result);
	        for(JsonElement prod : json.getAsJsonArray()) {
	        	JsonObject jsobj = prod.getAsJsonObject();
	        	String firstName = Utility.getValJson(jsobj, "firstName");
	        	String lastName = Utility.getValJson(jsobj, "lastName");
	        	client = new Client(id, firstName, lastName);
	        }
	        return client;
		}
		return null;
	}

	@Override
	public boolean delete(long id) {
		String response = Utility.requestDelete(_stockAPI, "clients/"+id);
		return response != null;
	}

	@Override
	public boolean update(Client val) {
		String response = Utility.requestPut(_stockAPI, "clients", val);
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
