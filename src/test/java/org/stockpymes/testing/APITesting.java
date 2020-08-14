package org.stockpymes.testing;

import org.stockpymes.StockPymes;
import org.stockpymes.models.Product;

public class APITesting {

	public static void main(String[] args) {
		final StockPymes api = StockPymes.init("http://localhost:4040/");
		//var prod = new Product();
		var prodOp = api.getProductOption();
		
		System.out.println(prodOp.createProduct(new Product(0L, "Coca Cola", "Bebida", "none", 85.0, 95.0, 95.0, 1)));
		//System.out.println(prods.get(0).getPrice());
	}

}
