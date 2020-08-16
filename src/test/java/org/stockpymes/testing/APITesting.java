package org.stockpymes.testing;

import org.stockpymes.StockPymes;
import org.stockpymes.models.Product;

public class APITesting {

	public static void main(String[] args) {
		try(final StockPymes api = StockPymes.init("https://stockpymes.herokuapp.com/")){
			//var prod = new Product();
			var prodOp = api.getProductOption();
			Product prod = prodOp.findById(1);
			System.out.println(prod);
			System.out.println(prodOp.getAll().size());
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}
