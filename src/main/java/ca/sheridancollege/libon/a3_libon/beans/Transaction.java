package ca.sheridancollege.libon.a3_libon.beans;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private int event;
	private int numTickets;
	private String contact;
}