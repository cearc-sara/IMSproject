package com.qa.ims.persistence.domain;

public class Item {
	
	private Long id;
	private String itemName;
	private double price;
	
	public Item(String itemName, double price) {
		this.setItemName(itemName);
		this.setPrice(price);
	}
	
	public Item(Long id, String itemName, double price) {
		this.setId(id);
		this.setItemName(itemName);
		this.setPrice(price);
	}
	
	public Item(Long id) {
		this.setId(id);
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	
	public String getItemName() {
		return itemName;
	}
	
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	@Override
	public String toString() {
		return "id: " + id + " Item Name: " + itemName + " Price: " + price + "$";
	}
}
