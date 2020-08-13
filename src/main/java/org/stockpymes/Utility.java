package org.stockpymes;

import java.io.IOException;
import java.io.Reader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import com.google.gson.JsonObject;

/**
 * @author Alex P. Vega
 */
class Utility {
	
	public static String readAll(Reader rd) throws IOException {
	    StringBuilder sb = new StringBuilder();
	    int cp;
	    while ((cp = rd.read()) != -1) {
	      sb.append((char) cp);
	    }
	    return sb.toString();
	 }
	
	public static String getValJson(JsonObject js, String propName) {
		return js.get(propName).getAsString();
	}
	
	public static String requestGet(StockPymes stockAPI,String misc) {
		String requestStr = stockAPI.apiRequestURL();
		requestStr = requestStr.endsWith("/") ? requestStr + misc : "/"+misc;
		HttpGet request = new HttpGet(requestStr);
		request.addHeader(HttpHeaders.USER_AGENT, "CLIENT");
		try(CloseableHttpResponse response = stockAPI.getHttpClient().execute(request);){
			HttpEntity entity = response.getEntity();
            if (entity != null) {
                // return it as a String
                final String result = EntityUtils.toString(entity);
                return result;
            }
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void requestPost(StockPymes stockAPI) throws ClientProtocolException, IOException {
		HttpGet request = new HttpGet("https://httpbin.org/get");

        // add request headers
        request.addHeader("custom-key", "mkyong");
        request.addHeader(HttpHeaders.USER_AGENT, "Googlebot");

        try (CloseableHttpResponse response = stockAPI.getHttpClient().execute(request)) {

            // Get HttpResponse Status
            System.out.println(response.getProtocolVersion());              // HTTP/1.1
            System.out.println(response.getStatusLine().getStatusCode());   // 200
            System.out.println(response.getStatusLine().getReasonPhrase()); // OK
            System.out.println(response.getStatusLine().toString());        // HTTP/1.1 200 OK

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                // return it as a String
                String result = EntityUtils.toString(entity);
                System.out.println(result);
            }

        }
	}
}
