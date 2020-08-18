package org.stockpymes.testing;

import org.stockpymes.StockPymes;

public class APITesting {

	public static void main(String[] args) {
		try(final StockPymes api = StockPymes.init("https://stockpymes.herokuapp.com/")){
			//var prod = new Product();
			var prodOp = api.getProductOption();
			System.out.println(prodOp.findById(1).getName());
			System.out.println(prodOp.getAll().size());
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}
