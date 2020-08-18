package org.stockpymes;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonObject;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author Alex P. Vega
 */
public class Utility {

	public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

	public static String getValJson(JsonObject js, String propName) {
		return js.get(propName).getAsString();
	}

	private static final String setRequest(String ref, String misc) {
		String requestStr = ref;
		requestStr = requestStr.endsWith("/") ? requestStr + misc : "/" + misc;
		return requestStr;
	}

	public static String requestGet(StockPymes stockAPI, String misc) {
		Request request = new Request.Builder().url(setRequest(stockAPI.apiRequestURL(), misc)).build();
		try (Response response = stockAPI.getHttpClient().newCall(request).execute()) {
			return response.body().string();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static <T> String requestPost(StockPymes stockAPI, String misc, T objectSender) {
		String json = createJson(objectSender.getClass(), objectSender).toString();
		RequestBody body = RequestBody.create(json, JSON);
		Request request = new Request.Builder().url(setRequest(stockAPI.apiRequestURL(), misc)).post(body).build();
		try (Response response = stockAPI.getHttpClient().newCall(request).execute()) {
			return response.body().string();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String requestDelete(StockPymes stockAPI, String misc) {
		Request request = new Request.Builder().url(setRequest(stockAPI.apiRequestURL(), misc)).delete().build();
		try (Response response = stockAPI.getHttpClient().newCall(request).execute()) {
			return response.body().string();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static <T> String requestPut(StockPymes stockAPI, String misc, T objectSender) {
		String json = createJson(objectSender.getClass(), objectSender).toString();
		RequestBody body = RequestBody.create(json, JSON);
		Request request = new Request.Builder().url(setRequest(stockAPI.apiRequestURL(), misc)).put(body).build();

		try (Response response = stockAPI.getHttpClient().newCall(request).execute()) {
			return response.body().string();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Map<String, String> mapMethods(Class<?> c) {
		var methds = new HashMap<String, String>();
		for (var cs : c.getMethods()) {
			var name = cs.getName();
			if (name.startsWith("get") && !name.equals("getClass")) {
				name = name.substring(3, name.length());
				var firstChar = "" + name.charAt(0);
				name = firstChar.toLowerCase() + name.substring(1, name.length());
				methds.put(name, cs.getName());
			}
		}
		return methds.size() > 0 ? methds : null;
	}

	

	public static <T> JsonObject createJson(Class<?> c, T prod) {
		JsonObject json = new JsonObject();
		var mappedMethods = Utility.mapMethods(c);
		for (var mn : mappedMethods.entrySet()) {
			try {
				String val = c.getMethod(mn.getValue()).invoke(prod).toString();
				json.addProperty(mn.getKey(), val);

			} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return json;
	}
}
