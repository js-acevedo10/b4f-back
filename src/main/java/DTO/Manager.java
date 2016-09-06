package DTO;

import org.mongodb.morphia.annotations.Entity;

@Entity
public class Manager extends User {
	
	public Manager() {
		
	}
	
	public Manager(String name, String password, String email) {
		this.name = name;
		this.password = password;
		this.email = email;
	}

}
