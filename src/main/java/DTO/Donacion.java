package DTO;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;

public class Donacion {
	
	@Id ObjectId id;
	public int amount;
	public String fecha;
	public String userId;
	
	public Donacion() {
		
	}
	
	public Donacion(int amount, String fecha, String userId) {
		super();
		this.amount = amount;
		this.fecha = fecha;
		this.userId = userId;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
