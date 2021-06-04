package com.qa.ims.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.persistence.domain.Item;
import com.qa.ims.persistence.domain.Orders;
import com.qa.ims.utils.DBUtils;

public class OrdersDAO implements Dao<Orders> {

	public static final Logger LOGGER = LogManager.getLogger();

	//READ ALL ORDERS
	@Override
	public List<Orders> readAll() {
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement
						.executeQuery("SELECT * FROM items JOIN item_order " 
								+ "ON items.id = item_order.item_id "
								+ "JOIN orders ON orders.id = item_order.order_id " 
								+ "ORDER BY orders.id");) {
			Orders orders;
			List<Orders> order = new ArrayList<Orders>();

			do {
				
				resultSet.next();
				orders = modelFromResultSet(resultSet);
				order.add(orders);
			} while (resultSet.next());

			return order;
		} catch (SQLException e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return new ArrayList<>();
	}
	
	//READS LATEST ORDER
		public Orders readLatest() {
			try (Connection connection = DBUtils.getInstance().getConnection();
					Statement statement = connection.createStatement();
					ResultSet resultSet = statement.executeQuery("SELECT * FROM orders ORDER BY id DESC LIMIT 1");) {
				resultSet.next();
				return modelFromResultSet(resultSet);
			} catch (Exception e) {
				LOGGER.debug(e);
				LOGGER.error(e.getMessage());
			}
			return null;
		}
		

	//READS AN ORDER BY ID
		@Override
		public Orders read(Long id) {
			try (Connection connection = DBUtils.getInstance().getConnection();
					PreparedStatement statement = connection.prepareStatement("SELECT * FROM "
							+ "orders JOIN item_orders ON "
							+ "orders.id = item_order.order_id"
							+ "JOIN items ON items.id = item_order.item_id"
							+ " WHERE id = ?");) {
				statement.setLong(1, id);
				try (ResultSet resultSet = statement.executeQuery();) {
					resultSet.next();
					return modelFromResultSet(resultSet);
				}
			} catch (Exception e) {
				LOGGER.debug(e);
				LOGGER.error(e.getMessage());
			}
			return null;
		}

	//CREATES AN ORDER
	@Override
	public Orders create(Orders order) {
		try (Connection connection = DBUtils.getInstance().getConnection();){
				PreparedStatement statement = connection.prepareStatement("INSERT INTO "
						+ "orders(customer_id) Values(?)");
				statement.setLong(1, order.getCustId());
				statement.executeUpdate();
				
				Orders lastOrder = readLatest();
				statement = connection.prepareStatement("INSERT INTO item_order(item_id, order_id) "
						+ "VALUES(?, ?)");
				statement.setLong(2, lastOrder.getOrderId());
				statement.setLong(1, order.getItemId());
				statement.executeUpdate();
				
				return lastOrder;
		
			
		}catch(Exception e){
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	//UPDATES AN ORDER
	@Override
	public Orders update(Orders order) {
		try (Connection connection = DBUtils.getInstance().getConnection();){
			PreparedStatement statement = 
					connection.prepareStatement("INSERT INTO item_order(order_id, item_id)"
							+ "VALUES(?, ?");
			statement.setLong(1, order.getOrderId());
			statement.setLong(2, order.getItemId());
			statement.executeUpdate();
		}catch(Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	//DELETES AN ORDER BY ID
	@Override
	public int delete(long id) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement("DELETE FROM item_order "
						+ "WHERE order_id = ?");){
			statement.setLong(1, id);
			return statement.executeUpdate();
		}catch(Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return 0;
	}

	@Override
	public Orders modelFromResultSet(ResultSet resultSet) throws SQLException {
		List<Item> itemOrder = new ArrayList<Item>();
		Long custId = resultSet.getLong("customer_id");
		Long orderId = resultSet.getLong("id");
		
		return new Orders(orderId, custId, itemOrder);
	}

}
