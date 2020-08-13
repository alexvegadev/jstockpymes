package org.stockpymes;

import java.io.Closeable;
import java.io.IOException;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * @author Alex P. Vega
 */
public class StockPymes implements Closeable {
	private final String _apiRequestURL;
	private final CloseableHttpClient httpClient;
	
	private StockPymes(String apiString) {
		this._apiRequestURL = apiString;
		httpClient = HttpClients.createDefault();
	}
	
	
	public ClientOption getClientOption() {
		return new ClientOption(this);
	}
	
	public ProductOption getProductOption() {
		return new ProductOption(this);
	}
	
	public static StockPymes init(String apiRequestURL) {
		return new StockPymes(apiRequestURL);
	}
	
	protected CloseableHttpClient getHttpClient() {
		return httpClient;
	}
	
	public String apiRequestURL() {
		return _apiRequestURL;
	}


	@Override
	public void close() throws IOException {
		httpClient.close();
	}
}
