package DTO;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity
public class Reparacion extends BikesObject {
	
	@Id
	private ObjectId id;
	
	public int fee;
	
	public String fixName;
	
	public Reparacion(){
		super();
	}
	
	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}
	
	public int getFee() {
		return fee;
	}

	public void setFee(int fee) {
		this.fee = fee;
	}
	
	public String getFixName() {
		return fixName;
	}

	public void setFixName(String fixName) {
		this.fixName = fixName;
	}

}
