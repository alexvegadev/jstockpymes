package org.stockpymes.models;

import org.stockpymes.Utility;

import com.google.gson.JsonObject;

/**
 * @author Alex P. Vega
 */
public class Provider {
	private Long id;
	private String providerName;
	private boolean json;

	public Provider(Long id, String providerName) {
		super();
		this.id = id;
		this.providerName = providerName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}
	
	/**
	 * Switch or sets json variable to return or not an json using toString() method.
	 */
	public void json() {
		this.json = !this.json;
	}

	@Override
	public String toString() {
		if (json) {
			JsonObject mapped = Utility.createJson(getClass(), this);
			return mapped != null ? mapped.toString() : super.toString();
		}
		return super.toString();
	}

}
