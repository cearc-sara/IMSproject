package com.qa.ims.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.persistence.dao.OrdersDAO;
import com.qa.ims.persistence.domain.Orders;
import com.qa.ims.utils.Utils;

public class OrderController implements CrudController<Orders> {
	
	//TAKES IN THE ORDER INFO FOR CRUD FUNCTIONALITY
		public static final Logger LOGGER = LogManager.getLogger();

		private OrdersDAO orderDAO;
		private Utils utils;

		public OrderController(OrdersDAO orderDAO, Utils utils) {
			super();
			this.orderDAO = orderDAO;
			this.utils = utils;
		}

		//READS ALL ORDERS TO THE LOGGER
		@Override
		public List<Orders> readAll() {
			List<Orders> orders = orderDAO.readAll();
			for (Orders order : orders) {
				LOGGER.info(order);
			}
			return orders;
		}
		
		//CREATES AN ORDER BY TAKING IN USER INPUT
		@Override
		public Orders create() {
			LOGGER.info("Please enter a customer id");
			Long custId = utils.getLong();
			LOGGER.info("Please enter an item id to enter into the order");
			Long itemId = utils.getLong();
			Orders order = orderDAO.create(new Orders(custId, itemId));
			LOGGER.info("Order created");
			return order;
		}
		
		//UPDATES AN EXISTING ORDER BY TAKING IN USER INPUT
		@Override
		public Orders update() {
			LOGGER.info("Please enter the id of the order you would like to update");
			Long orderId = utils.getLong();
			LOGGER.info("Please enter an item id you would like to add to your order");
			Long addItem = utils.getLong();
			
			Orders order = orderDAO.update(new Orders(orderId, addItem));
			LOGGER.info("Item Updated");
			return order;
		}
		
		//DELETES AN EXISTING ORDER BY ID
		@Override
		public int delete() {
			LOGGER.info("Please enter the id of the order you would like to delete");
			Long id = utils.getLong();
			return orderDAO.delete(id);
		}
}
