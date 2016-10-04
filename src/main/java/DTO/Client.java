package DTO;

import org.mongodb.morphia.annotations.Entity;

@Entity
public class Client extends User {

	public double points;
	public boolean suspended;
	
	public Client() {
		
	}

	public Client(double points, boolean suspended, String name, String password, String email) {
		super();
		this.points = points;
		this.suspended = suspended;
		this.name = name;
		this.password = password;
		this.email = email;
	}

	public double getPoints() {
		return points;
	}

	public boolean isSuspended() {
		return suspended;
	}

	public void setPoints(double points) {
		this.points = points;
	}

	public void setSuspended(boolean suspended) {
		this.suspended = suspended;
	}
	
	@Override
	public String toString() {
		return this.id + " - " + this.name;
	}
}
