/*
 * Name: Bonita Li
 * File: DatabaseAccess.java
 * Other Files in this Project:
 * 		index.html
 * 		index.html
 * 		add.html
 * 		schema.sql
 * 		data.sql
 * 		stylesheet.css
 * 		Event.java
 * 		Transaction.java
 * 		MainController.java
 * 		DatabaseConfig.java
 */

/**
 * This class is the DatabaseAccess which handles retrieving from the database 
 * with SQL strings, inserting new rows into the database, and counting 
 * number rows. 
 */

package ca.sheridancollege.libon.a3_libon.database;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ca.sheridancollege.libon.a3_libon.beans.Event;
import ca.sheridancollege.libon.a3_libon.beans.Transaction;

@Repository
public class DatabaseAccess {

	@Autowired
	private NamedParameterJdbcTemplate jdbc;
	
	/**
	 * 
	 * @return Events list
	 */
	public List<Event> getEvents() {
		
		String sql = "SELECT * FROM events ORDER BY eventName;";
		List<Event> events = jdbc.query(sql, new BeanPropertyRowMapper<Event>(Event.class));
		return events;
	}
	
	/**
	 * 
	 * @param transaction Transaction model
	 * @return JDBC update with sql and params
	 */
	public int addTransaction(Transaction transaction) {
		
		String sql = "INSERT INTO transactions (event, numTickets, contact)"
				+ "VALUES ((SELECT id FROM events WHERE id = :event), "
				+ ":numTickets, :contact);";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("event", transaction.getEvent())
			.addValue("numTickets", transaction.getNumTickets())
			.addValue("contact", transaction.getContact());
		return jdbc.update(sql, params);
	}
	
	/**
	 * 
	 * @return Transactions list
	 */
	public List<Transaction> getTransactions() {
		
		String sql = "SELECT * FROM transactions;";
		List<Transaction> transactions = jdbc.query(sql, 
				new BeanPropertyRowMapper<Transaction>(Transaction.class));
		return transactions;
	}
	/**
	 * 
	 * @param id Id retrieves event by id
	 * @return Event object
	 */
	public Event getEvent(int id) {
		
		String sql = "SELECT * FROM events WHERE id=:id;";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", id);
		List<Event> events = jdbc.query(sql, params, 
				new BeanPropertyRowMapper<Event>(Event.class));
		if (events.size() > 0) {
			return events.get(0);
		} else {
			return null;
		}
	}
	
	/**
	 * 
	 * @return Event Map with Integer as key, String as value
	 */
	public Map<Integer, String> getEventMap() {
		
		String sql = "SELECT * FROM events;";
		HashMap<Integer, String> events = new HashMap<Integer, String>();
		List<Map<String, Object>> rows = jdbc.queryForList(sql, new HashMap());
		for(Map<String, Object> row: rows) {
			Event event = new Event();
			event.setId((Integer)(row.get("id")));
			event.setEventName((String)(row.get("eventName")));
			events.put(Integer.valueOf(event.getId()), event.getEventName());
		}
		return events;
	}
	
	/**
	 * 
	 * @return Count of transactions 
	 */
	public Long getTransactionsCount() {
		
		String sql = "SELECT COUNT (*) FROM transactions;";
		return jdbc.queryForObject(sql, new HashMap(), Long.TYPE);
	}
	
	
}
