package DTO;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;

public class BikeType {
	
	@Id
	public ObjectId id;
	
	private String idString;
	
	public String name;
    
    public int capacity;
    
    
	
	public BikeType() {
		//Es necesario tener al menos el constructor vacío en el modelo de cada objeto
	}

	public BikeType(String name, int capacity) {
		super();
		this.name = name;
		this.capacity = capacity;
	}

	public String getId() {
		return id.toHexString();
	}

	public void setId(ObjectId id) {
		this.id = id;
		this.idString = id.toHexString();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public String getIdString() {
		return idString;
	}

	public void setIdString(String idString) {
		this.idString = idString;
	}

}
