package DTO;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;

public class BikeType extends BikesObject {
	
	@Id
	public ObjectId id;
		
	public String name;
    
    public int capacity;
    
    public String imageURL;
    
    
	
	public BikeType() {
		//Es necesario tener al menos el constructor vacío en el modelo de cada objeto
	}

	public BikeType(String name, int capacity, String imageURL) {
		super();
		this.name = name;
		this.capacity = capacity;
        this.imageURL = imageURL;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
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
    
    public void setImageURL (String imageURL) {
        this.imageURL = imageURL;
    }
    
    public String getImageURL () {
        return imageURL;
    }

}
