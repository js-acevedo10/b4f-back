package DTO;

import java.util.List; 
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Reference;

import Utilities.LatLng;


@Entity
public class RentPlace extends BikesObject {
	@Id
	private ObjectId id;
	
	private String address;
	private String name;
	private int storingCapacity;
	private int reservations;
	
	private LatLng coordinates;
	
	@Reference 
    private List<Bike> bikes;   
	public RentPlace(){
		super();
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStoringCapacity() {
		return storingCapacity;
	}

	public void setStoringCapacity(int storingCapacity) {
		this.storingCapacity = storingCapacity;
	}

	public List<Bike> getBikes() {
		return bikes;
	}

	public void setBikes(List<Bike> bikes) {
		this.bikes = bikes;
	}
	public void addBike(Bike addB)
	{
		bikes.add(addB);
	}
	public void removeBike(Bike addB)
	{
		for (Bike bike : bikes) {
			if (bike.getBrand().equals(addB.getBrand()) && bike.getId().equals(addB.getId())){
				bikes.remove(bike);
				return;
			}
		}
		reservations++;
	}

	public int getReservations() {
		return reservations;
	}

	public void setReservations(int reservations) {
		this.reservations = reservations;
	}

	public LatLng getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(LatLng coordinates) {
		this.coordinates = coordinates;
	}
	
	

}
