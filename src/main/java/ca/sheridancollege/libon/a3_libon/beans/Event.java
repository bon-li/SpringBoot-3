package ca.sheridancollege.libon.a3_libon.beans;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String eventName;
	private double ticketCost;
	
	public Event(String eventName, double cost) {
		setEventName(eventName);
		setTicketCost(cost);
	}
}