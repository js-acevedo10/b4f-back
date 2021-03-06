package DTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Reference;

public class Bike extends BikesObject {
	
	@Id
	public ObjectId id;
	
	@Reference
	public BikeType bikeType;
	
	public List<String> history;
	
	public List<String> rentals;
	
	public String brand;
    
    public Boolean damaged, available;
    
    public Date reserveDate;
	
    public Boolean reserve;
    
    public Boolean mantenimiento;

	public Boolean getReserve() {
		return reserve;
	}

	public void setReserve(Boolean reserve) {
		Date actualDate = new Date();
    	if(reserveDate!=null&&(reserveDate.getTime()+7200000)<actualDate.getTime())
    	{
       		this.reserve = true;
    	}
    	else{
    		this.reserve=false;
    	}
	}

	public Bike() {
		//Es necesario tener al menos el constructor vacío en el modelo de cada objeto
		super();
		this.reserveDate=null;
		this.reserve =false;
		this.mantenimiento = false;
        this.history = new ArrayList<String>();
        this.rentals = new ArrayList<String>();
	}

	public Bike(String brand, BikeType bikeType, Boolean damaged, Boolean available) {
		super();
		this.brand = brand;
		this.bikeType = bikeType;
		this.damaged = damaged;
        this.available = available;
        this.reserveDate=null;
        this.history = new ArrayList<String>();
        this.rentals = new ArrayList<String>();
        this.reserve = false;
        this.mantenimiento = false;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public BikeType getBikeType() {
		return bikeType;
	}

	public void setBikeType(BikeType bikeType) {
		this.bikeType = bikeType;
	}

    public Boolean getDamaged() {
        return damaged;
    }
    
    public void setDamaged(Boolean damaged) {
        this.damaged = damaged;
    }
    
    public Boolean getAvailable() {
        return available;
    }
    
    public void setAvailable(Boolean available) {
        this.available = available;
    }
    
    public Boolean getMantenimiento() {
        return mantenimiento;
    }
    
    public void setMantenimiento(Boolean available) {
        this.mantenimiento = available;
    }
    
    
    public Date getReserveDate() {
    	return reserveDate;
    }
    
    public void setReserveDate(Date reserveDate) {
    	this.reserveDate = reserveDate;
    }
    
    public List<String> getHistory() {
    	return history;
    }
    
    public void setHistory(List<String> history) {
    	this.history = history;
    }
    
    public void addRentPlace(String rent){
    	this.history.add(rent);
    }
    
    public List<String> getRentals() {
    	return history;
    }
    
    public void setRentals(List<String> history) {
    	this.history = history;
    }
    
    public void addRental(String rent){
    	this.rentals.add(rent);
    }
}
