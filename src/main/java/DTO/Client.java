package DTO;

import java.util.Date;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Reference;

@Entity
public class Client extends User {

	public double points;
	public boolean suspended;
	
	@Reference
	private Bike reserverdBike;
	
	private Date suspendAfter;
	
	private double bonus;
	
	public Client() {
		super();
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
		if (suspendAfter != null && suspendAfter.before(new Date())){
			this.suspended = true;
		}else{
			this.suspended = suspended;
		}
	}
	
	@Override
	public String toString() {
		return this.id + " - " + this.name;
	}

	public Bike getReserverdBike() {
		return reserverdBike;
	}

	public void setReserverdBike(Bike reserverdBike) {
		if (this.getModifiedAt() != null && this.getModifiedAt().getTime()+(1000*3600*2) < new Date().getTime()){
			this.reserverdBike = null;
		}else{
			this.reserverdBike = reserverdBike;
		}
	}

	public Date getSuspendAfter() {
		return suspendAfter;
	}

	public void setSuspendAfter(Date suspendAfter) {
		this.suspendAfter = suspendAfter;
	}

	public double getBonus() {
		return bonus;
	}

	public void setBonus(double bonus) {
		this.bonus = bonus;
	}
	
	
	
}
