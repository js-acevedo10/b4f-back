package DTO;

import java.util.Date;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Reference;

public class Rental {
	@Id
	public ObjectId id;

	@Reference
	public Bike bike;

	@Reference
	public Client client;
	
	public Date rentDate;

	public Rental() {

	}

	public Rental(Bike bike, Client client, Date rentDate) {
		this.bike = bike;
		this.client = client;
		this.rentDate = rentDate;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public Bike getBike() {
		return bike;
	}

	public void setBike(Bike bike) {
		this.bike = bike;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}


}
