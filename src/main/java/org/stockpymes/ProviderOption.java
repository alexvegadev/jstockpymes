package org.stockpymes;

import java.util.ArrayList;
import java.util.List;

import org.stockpymes.interfaces.ICrud;
import org.stockpymes.models.OrderFactory;
import org.stockpymes.models.Provider;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * @author Alex P. Vega
 */
public class ProviderOption implements ICrud<Provider> {
	
	private final StockPymes _stockAPI;
	private final OrderFactory<Provider> _orderFactory;
	
	protected ProviderOption(StockPymes stockAPI) {
		this._stockAPI = stockAPI;
		this._orderFactory = OrderFactory.newOrder(null);
	}
	
	@Override
	public boolean create(Provider val) {
		if(val != null) {
			String response = Utility.requestPost(_stockAPI, "providers", val);
			return response != null;
		}
		return false;
	}

	@Override
	public List<Provider> getAll() {
		final String result = Utility.requestGet(_stockAPI, "providers");
		if(result != null) {
			List<Provider> prods = new ArrayList<>();
			JsonElement json = JsonParser.parseString(result);
	        for(JsonElement prod : json.getAsJsonArray()) {
	        	JsonObject jsobj = prod.getAsJsonObject();
	        	Long id = Long.valueOf(Utility.getValJson(jsobj, "id"));
	        	String name = Utility.getValJson(jsobj, "providerName");
	        	prods.add( new Provider(id, name));
	        }
	        if(order().getHandler() != null) {
	        	prods.sort(order().getHandler());
	        }
	        
	        return prods;
		}
		return null;
	}

	@Override
	public Provider findById(long id) {
		final String result = Utility.requestGet(_stockAPI, "providers/"+id);
		Provider provider = null;
		if(result != null) {
	        JsonObject jsobj = JsonParser.parseString(result).getAsJsonObject();
	        String name = Utility.getValJson(jsobj, "providerName");
	        provider = new Provider(id, name);
		}
		return provider;
	}

	@Override
	public boolean delete(long id) {
		String response = Utility.requestDelete(_stockAPI, "providers/"+id);
		return response != null;
	}

	@Override
	public boolean update(Provider val) {
		String response = Utility.requestPut(_stockAPI, "providers", val);
		return response != null;
	}

	@Override
	public OrderFactory<Provider> order() {
		return _orderFactory;
	}

	@Override
	public StockPymes getAttachedAPI() {
		return _stockAPI;
	}

}
