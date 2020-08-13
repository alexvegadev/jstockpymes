package org.stockpymes;

import java.util.ArrayList;
import java.util.List;

import org.stockpymes.models.Product;

import com.google.gson.JsonParser;

/**
 * @author Alex P. Vega
 */
public class ProductOption {
	
	private final StockPymes _stockAPI;
	
	protected ProductOption(StockPymes stockAPI) {
		this._stockAPI = stockAPI;
	}
	
	public List<Product> getProducts(){
		final String result = Utility.requestGet(_stockAPI, "products");
		if(result != null) {
			var prods = new ArrayList<Product>();
			var json = JsonParser.parseString(result);
	        for(var prod : json.getAsJsonArray()) {
	        	var jsobj = prod.getAsJsonObject();
	        	var id = Long.valueOf(Utility.getValJson(jsobj, "id"));
	        	var name = Utility.getValJson(jsobj, "name");
	        	var category = Utility.getValJson(jsobj, "category");
	        	var image = Utility.getValJson(jsobj, "image");
	        	var price = Double.valueOf(Utility.getValJson(jsobj, "price"));
	        	var priceToSell = Double.valueOf(Utility.getValJson(jsobj, "priceToSell"));
	        	var pricePerUnit = Double.valueOf(Utility.getValJson(jsobj, "pricePerUnit"));
	        	var quantity = Integer.valueOf(Utility.getValJson(jsobj, "quantity"));
	        	prods.add( new Product(id, name, category, image, price, priceToSell, pricePerUnit, quantity));
	        }
	        
	        return prods;
		}
        
		return null;
	}
	
	public StockPymes getAttachedAPI() {
		return _stockAPI;
	}
}
