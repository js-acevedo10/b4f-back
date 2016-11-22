package Utilities;

import java.io.Serializable;

public class LatLng implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8533506251528507940L;
	
	
	private Double latitude;
	private Double longitude;
	
	public LatLng(){
		
	}
	
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	
	

}
