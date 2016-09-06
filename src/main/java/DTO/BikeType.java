package DTO;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;

public class BikeType {
	
	@Id
	public String id;
		
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
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

}
