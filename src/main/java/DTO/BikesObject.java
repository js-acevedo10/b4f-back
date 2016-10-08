package DTO;

import java.util.Date;

public class BikesObject {

	private Boolean deleted;
	private Date createdAt;
	private Date modifiedAt;
	
	public BikesObject() {
		deleted = false;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getModifiedAt() {
		return modifiedAt;
	}

	public void setModifiedAt(Date modifiedAt) {
		this.modifiedAt = modifiedAt;
	}
	
	public void delete(){
		this.deleted = true;
	}
	public void modify(){
		this.modifiedAt = new Date();
	}
	public void create(){
		this.createdAt = new Date();
	}
	
	public boolean deleted(){
		return this.deleted;
	}
}
