package org.stockpymes;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.stockpymes.models.StatusResponse;

import com.google.gson.JsonObject;

/**
 * @author Alex P. Vega
 */
public class Utility {
	
	protected static final ResponseHandler<String> DEFINED_RESPONSE_HANDLER = (response) ->{
		int status = response.getStatusLine().getStatusCode();
		if(status >= 200 && status < 300) {
			HttpEntity entity = response.getEntity();
	        return entity != null ? EntityUtils.toString(entity) : null;
		}
		return null;
	};
	
	protected static final ResponseHandler<StatusResponse> STATUS_RESPONSE_HANDLER = (response) ->{
		int status = response.getStatusLine().getStatusCode();
		if(status >= 200 && status < 300) {
			HttpEntity entity = response.getEntity();
	        return entity != null ? StatusResponse.OK : StatusResponse.FAIL;
		}
		return StatusResponse.FAIL;
	};
	
	public static String getValJson(JsonObject js, String propName) {
		return js.get(propName).getAsString();
	}
	
	private static final String setRequest(String ref, String misc) {
		String requestStr = ref;
		requestStr = requestStr.endsWith("/") ? requestStr + misc : "/"+misc;
		return requestStr;
	}
	
	public static String requestGet(StockPymes stockAPI,String misc) {
		HttpGet request = new HttpGet(setRequest(stockAPI.apiRequestURL(), misc));
		request.addHeader(HttpHeaders.USER_AGENT, "CLIENT");
		try(var response = stockAPI.getHttpClient().execute(request);){
			var entity = response.getEntity();
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
	
	public static <T> String requestPost(StockPymes stockAPI, String misc, T objectSender){
		String result = "";
        HttpPost post = new HttpPost(setRequest(stockAPI.apiRequestURL(), misc));
        post.setHeader("Accept", "application/json");
        post.setHeader("Content-type", "application/json");
        
        String json = createJson(objectSender.getClass(), objectSender).toString();
        try {
			StringEntity se = new StringEntity(json);
			post.setEntity(se);
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        try (var response = stockAPI.getHttpClient().execute(post)){
            result = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
			e.printStackTrace();
		}

        return result;
	}
	
	public static String requestDelete(StockPymes stockAPI, String misc) {
		HttpDelete delete = new HttpDelete(setRequest(stockAPI.apiRequestURL(), misc));
		String result = null;
		try (var response = stockAPI.getHttpClient().execute(delete)){
             result = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static <T> String requestPut(StockPymes stockAPI, String misc, T objectSender) {
		HttpPut put = new HttpPut(setRequest(stockAPI.apiRequestURL(), misc));
		put.setHeader("Accept", "application/json");
		put.setHeader("Content-type", "application/json");
		String json = createJson(objectSender.getClass(), objectSender).toString();
		String result = null;
		try {
			StringEntity stringEntity = new StringEntity(json);
			put.setEntity(stringEntity);
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		try (var response = stockAPI.getHttpClient().execute(put)){
            result = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static Map<String, String> mapMethods(Class<?> c){
		var methds = new HashMap<String, String>();
		for(var cs : c.getMethods()) {
			var name = cs.getName();
			if(name.startsWith("get") && !name.equals("getClass")){
				name = name.substring(3, name.length());
				var firstChar = ""+name.charAt(0);
				name = firstChar.toLowerCase() + name.substring(1, name.length());
				methds.put(name, cs.getName());
			}
		}
		return methds.size() > 0 ? methds : null;
	}
	
	public static <T> List<BasicNameValuePair> createParams(Class<?> c, T prod){
		List<BasicNameValuePair> params = new ArrayList<>();
		var mappedMethods = Utility.mapMethods(c);
		for(var mn : mappedMethods.entrySet()) {
			try {
				String val = c.getMethod(mn.getValue()).invoke(prod).toString();
				String k = mn.getKey();
				params.add(new BasicNameValuePair(k, val));
			} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return params;
	}
	
	public static <T> JsonObject createJson(Class<?> c, T prod){
		JsonObject json = new JsonObject();
		var mappedMethods = Utility.mapMethods(c);
		for(var mn : mappedMethods.entrySet()) {
			try {
				String val = c.getMethod(mn.getValue()).invoke(prod).toString();
				json.addProperty(mn.getKey(), val);
				
			} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return json;
	}
}
