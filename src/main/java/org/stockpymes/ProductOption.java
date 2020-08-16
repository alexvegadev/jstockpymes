package org.stockpymes;

import java.util.ArrayList;
import java.util.List;

import org.stockpymes.interfaces.ICrud;
import org.stockpymes.models.OrderFactory;
import org.stockpymes.models.Product;

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
	        var jsobj = JsonParser.parseString(result).getAsJsonObject();
	        var name = Utility.getValJson(jsobj, "name");
	        var category = Utility.getValJson(jsobj, "category");
	        var image = Utility.getValJson(jsobj, "image");
	        var price = Double.valueOf(Utility.getValJson(jsobj, "price"));
	        var priceToSell = Double.valueOf(Utility.getValJson(jsobj, "priceToSell"));
	        var pricePerUnit = Double.valueOf(Utility.getValJson(jsobj, "pricePerUnit"));
	        var quantity = Integer.valueOf(Utility.getValJson(jsobj, "quantity"));
	        product = new Product(id, name, category, image, price, priceToSell, pricePerUnit, quantity);
		}
		return product;
	}

	@Override
	public boolean delete(long id) {
		var response = Utility.requestDelete(_stockAPI, "products/"+id);
		return response != null;
	}

	@Override
	public boolean update(Product val) {
		var response = Utility.requestPut(_stockAPI, "products", val);
		return response != null;
	}
}
