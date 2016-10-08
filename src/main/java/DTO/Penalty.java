package DTO;

import java.util.Date;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Reference;

@Entity
public class Penalty extends BikesObject {
	
	@Id
	private ObjectId id;
	
	@Reference
	private Client client;
	
	private Double fee;
	private Date suspensionStart;
	private Date suspensionEnd;
	private Boolean trainingRequiered;
	private Boolean pending;

	public Penalty(){
		super();
	}

	public Penalty(Double fee, Date suspensionStart, Date suspensionEnd, Boolean trainingRequiered) {
		super();
		this.fee = fee;
		this.suspensionStart = suspensionStart;
		this.suspensionEnd = suspensionEnd;
		this.trainingRequiered = trainingRequiered;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Double getFee() {
		return fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}

	public Date getSuspensionStart() {
		return suspensionStart;
	}

	public void setSuspensionStart(Date suspensionStart) {
		this.suspensionStart = suspensionStart;
	}

	public Date getSuspensionEnd() {
		return suspensionEnd;
	}

	public void setSuspensionEnd(Date suspensionEnd) {
		this.suspensionEnd = suspensionEnd;
	}

	public Boolean getTrainingRequiered() {
		return trainingRequiered;
	}

	public void setTrainingRequiered(Boolean trainingRequiered) {
		this.trainingRequiered = trainingRequiered;
	}

	public Boolean getPending() {
		return pending;
	}

	public void setPending(Boolean pending) {
		this.pending = pending;
	}
	
	
	
	
}
