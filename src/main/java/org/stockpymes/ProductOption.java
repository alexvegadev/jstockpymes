package org.stockpymes;

import java.util.ArrayList;
import java.util.List;

import org.stockpymes.interfaces.ICrud;
import org.stockpymes.models.OrderFactory;
import org.stockpymes.models.Product;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * @author Alex P. Vega
 */
public class ProductOption implements ICrud<Product> {
	
	private final StockPymes _stockAPI;
	private OrderFactory<Product> _orderFactory;
	
	protected ProductOption(StockPymes stockAPI) {
		this._stockAPI = stockAPI;
		this._orderFactory = OrderFactory.newOrder(null);
	}
	
	@Override
	public OrderFactory<Product> order(){
		return _orderFactory;
	}
	
	public StockPymes getAttachedAPI() {
		return _stockAPI;
	}

	@Override
	public boolean create(Product val) {
		if(val != null) {
			String response = Utility.requestPost(_stockAPI, "products", val);
			return response != null;
		}
		return false;
	}

	@Override
	public List<Product> getAll() {
		final String result = Utility.requestGet(_stockAPI, "products");
		if(result != null) {
			List<Product> prods = new ArrayList<>();
			JsonElement json = JsonParser.parseString(result);
	        for(JsonElement prod : json.getAsJsonArray()) {
	        	JsonObject jsobj = prod.getAsJsonObject();
	        	Long id = Long.valueOf(Utility.getValJson(jsobj, "id"));
	        	String name = Utility.getValJson(jsobj, "name");
	        	String category = Utility.getValJson(jsobj, "category");
	        	String image = Utility.getValJson(jsobj, "image");
	        	Double price = Double.valueOf(Utility.getValJson(jsobj, "price"));
	        	Double priceToSell = Double.valueOf(Utility.getValJson(jsobj, "priceToSell"));
	        	Double pricePerUnit = Double.valueOf(Utility.getValJson(jsobj, "pricePerUnit"));
	        	Integer quantity = Integer.valueOf(Utility.getValJson(jsobj, "quantity"));
	        	prods.add( new Product(id, name, category, image, price, priceToSell, pricePerUnit, quantity));
	        }
	        if(order().getHandler() != null) {
	        	prods.sort(order().getHandler());
	        }
	        return prods;
		}
        
		return null;
	}

	@Override
	public Product findById(long id) {
		final String result = Utility.requestGet(_stockAPI, "products/"+id);
		Product product = null;
		if(result != null) {
	        JsonObject jsobj = JsonParser.parseString(result).getAsJsonObject();
	        String name = Utility.getValJson(jsobj, "name");
	        String category = Utility.getValJson(jsobj, "category");
	        String image = Utility.getValJson(jsobj, "image");
	        Double price = Double.valueOf(Utility.getValJson(jsobj, "price"));
	        Double priceToSell = Double.valueOf(Utility.getValJson(jsobj, "priceToSell"));
	        Double pricePerUnit = Double.valueOf(Utility.getValJson(jsobj, "pricePerUnit"));
	        Integer quantity = Integer.valueOf(Utility.getValJson(jsobj, "quantity"));
	        product = new Product(id, name, category, image, price, priceToSell, pricePerUnit, quantity);
		}
		return product;
	}

	@Override
	public boolean delete(long id) {
		String response = Utility.requestDelete(_stockAPI, "products/"+id);
		return response != null;
	}

	@Override
	public boolean update(Product val) {
		String response = Utility.requestPut(_stockAPI, "products", val);
		return response != null;
	}
}
