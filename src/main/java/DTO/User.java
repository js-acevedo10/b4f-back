package DTO;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;

public class User extends BikesObject {
	
	@Id
	public ObjectId id;
	
	public String name, password, email;
	
	public User() {
		//Es necesario tener al menos el constructor vacío en el modelo de cada objeto
	}

	public User(String name, String password, String email) {
		super();
		this.name = name;
		this.password = password;
		this.email = email;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
