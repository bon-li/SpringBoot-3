CREATE TABLE events (
	id INT PRIMARY KEY AUTO_INCREMENT,
	eventName VARCHAR(255),
	ticketCost DECIMAL(6,2)
);

CREATE TABLE transactions (
	txnId INT PRIMARY KEY AUTO_INCREMENT,
	event INT,
	numTickets INT,
	contact VARCHAR(255),
	FOREIGN KEY (event) REFERENCES events(id)
);
