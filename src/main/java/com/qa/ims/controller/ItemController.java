package com.qa.ims.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.persistence.dao.ItemDAO;
import com.qa.ims.persistence.domain.Item;
import com.qa.ims.utils.Utils;

public class ItemController implements CrudController<Item> {
	
	//TAKES IN THE ITEM INFO FOR CRUD FUNCTIONALITY
	public static final Logger LOGGER = LogManager.getLogger();

	private ItemDAO itemDAO;
	private Utils utils;

	public ItemController(ItemDAO itemDAO, Utils utils) {
		super();
		this.itemDAO = itemDAO;
		this.utils = utils;
	}
	
	//READS ALL ITEMS TO THE LOGGER
	@Override
	public List<Item> readAll() {
		List<Item> items = itemDAO.readAll();
		for (Item item : items) {
			LOGGER.info(item);
		}
		return items;
	}
	
	//CREATES AN ITEMS BY TAKING IN USER INPUT
	@Override
	public Item create() {
		LOGGER.info("Please enter an item name");
		String itemName = utils.getString();
		LOGGER.info("Please enter a price");
		double price = utils.getDouble();
		Item item = itemDAO.create(new Item(itemName, price));
		LOGGER.info("Item created");
		return item;
	}
	
	//UPDATES AN EXISTING ITEM BY TAKING IN USER INPUT
	@Override
	public Item update() {
		LOGGER.info("Please enter the id of the item you would like to update");
		Long id = utils.getLong();
		LOGGER.info("Please enter an item name");
		String itemName = utils.getString();
		LOGGER.info("Please enter a price");
		double price = utils.getDouble();
		Item item = itemDAO.update(new Item(id, itemName, price));
		LOGGER.info("Item Updated");
		return item;
	}
	
	//DELETES AN EXISTING ITEM BY ID
	@Override
	public int delete() {
		LOGGER.info("Please enter the id of the item you would like to delete");
		Long id = utils.getLong();
		return itemDAO.delete(id);
	}

}
