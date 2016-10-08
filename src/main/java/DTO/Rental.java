package DTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Reference;

public class Rental extends BikesObject {
	@Id
	public ObjectId id;

	@Reference
	public Bike bike;

	@Reference
	public Client client;
	
	public Date rentDate;
	
	public List<String> allowedUsers;
	
	public boolean delivered;
	
	@Reference
	public RentPlace origin;
	
	@Reference
	public RentPlace drop;

	public Rental() {
		super();
		allowedUsers = new ArrayList<String>();
	}

	public Rental(Bike bike, Client client, Date rentDate, RentPlace origin) {
		this.bike = bike;
		this.client = client;
		this.rentDate = rentDate;
		this.origin = origin;
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

	public Date getRentDate() {
		return rentDate;
	}

	public void setRentDate(Date rentDate) {
		this.rentDate = rentDate;
	}

	public List<String> getAllowedUsers() {
		return allowedUsers;
	}

	public void setAllowedUsers(List<String> allowedUsers) {
		this.allowedUsers = allowedUsers;
	}

	public void addAllowedUser(String mail){
		this.allowedUsers.add(mail);
	}

	public boolean isDelivered() {
		return delivered;
	}

	public void setDelivered(boolean delivered) {
		this.delivered = delivered;
	}

	public RentPlace getOrigin() {
		return origin;
	}

	public void setOrigin(RentPlace origin) {
		this.origin = origin;
	}

	public RentPlace getDrop() {
		return drop;
	}

	public void setDrop(RentPlace drop) {
		this.drop = drop;
	}
	
	

}
