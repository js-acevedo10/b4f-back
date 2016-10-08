package DTO;

import org.mongodb.morphia.annotations.Entity;

@Entity
public class Admin extends User {
	
	public Admin(){
		super();
	}
	
	public Admin(String name, String password, String email) {
		this.name = name;
		this.password = password;
		this.email = email;
	}
	
}
