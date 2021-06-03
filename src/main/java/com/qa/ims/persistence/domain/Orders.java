package com.qa.ims.persistence.domain;

import java.util.ArrayList;
import java.util.List;

public class Orders {
	
	private Long orderId;
	private Long custId;
	private List<Item> itemOrders;
	
	
	public Orders(Long orderId, Long custId, List<Item> itemOrders) {
		this.setOrderId(orderId);
		this.setCustId(custId);
		this.setItemOrders(new ArrayList<Item>());
	}
	
	public Orders(Long custId, Long itemId) {
		this((long) 01, custId, new ArrayList<Item>());
		this.addItem(new Item(itemId));
	}
	
	public void addItem(Item item) {
		itemOrders.add(item);
	}
	
	public void removeItem(Item item) {
		itemOrders.remove(item);
	}


	public Long getOrderId() {
		return orderId;
	}


	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	
	public Long getItemId() {
		Long itemId = (long) 1;
		for(Item item: itemOrders) {
			itemId = item.getId();
			break;
		}
		return itemId;
	}


	public Long getCustId() {
		return custId;
	}


	public void setCustId(Long custId) {
		this.custId = custId;
	}


	public List<Item> getItemOrders() {
		return itemOrders;
	}


	public void setItemOrders(List<Item> itemOrders) {
		this.itemOrders = itemOrders;
	}
	
	

}
