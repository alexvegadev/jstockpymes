package org.stockpymes.testing;

import org.stockpymes.StockPymes;

public class APITesting {

	public static void main(String[] args) {
		final StockPymes api = StockPymes.init("http://localhost:4040/");
		var prods = api.getProductOption().getProducts();
		System.out.println(prods.get(0).getPrice());
	}

}
