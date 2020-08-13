package org.stockpymes.models;

/**
 * @author Alex P. Vega
 */
public class Product {
	private Long id;
	private String name;
	private String category;
	private String image;
	private Double price;
	private Double priceToSell;
	private Double pricePerUnit;
	private Integer quantity;
	
	public Product() {
		// TODO Auto-generated constructor stub
	}

	public Product(Long id, String name, String category, String image, Double price, Double priceToSell,
			Double pricePerUnit, Integer quantity) {
		super();
		this.id = id;
		this.name = name;
		this.category = category;
		this.image = image;
		this.price = price;
		this.priceToSell = priceToSell;
		this.pricePerUnit = pricePerUnit;
		this.quantity = quantity;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getPriceToSell() {
		return priceToSell;
	}

	public void setPriceToSell(Double priceToSell) {
		this.priceToSell = priceToSell;
	}

	public Double getPricePerUnit() {
		return pricePerUnit;
	}

	public void setPricePerUnit(Double pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
}
