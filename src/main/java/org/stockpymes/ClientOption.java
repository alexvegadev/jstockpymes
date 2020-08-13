package org.stockpymes;

import java.util.ArrayList;
import java.util.List;

import org.stockpymes.models.Client;

import com.google.gson.JsonParser;

/**
 * @author Alex P. Vega
 */
public class ClientOption {
	private final StockPymes _stockAPI;
	
	protected ClientOption(StockPymes stockAPI) {
		this._stockAPI = stockAPI;
	}
	
	public List<Client> getClients(){
		var response = Utility.requestGet(_stockAPI, "clients");
		if(response != null) {
			var clients = new ArrayList<Client>();
			var json = JsonParser.parseString(response);
	        for(var prod : json.getAsJsonArray()) {
	        	var jsobj = prod.getAsJsonObject();
	        	var id = Long.valueOf(Utility.getValJson(jsobj, "id"));
	        	var firstName = Utility.getValJson(jsobj, "firstName");
	        	var lastName = Utility.getValJson(jsobj, "lastName");
	        	clients.add( new Client(id, firstName, lastName));
	        }
	        
	        return clients;
		}
		return null;
	}
	
	public StockPymes getAttachedAPI() {
		return _stockAPI;
	}
}
