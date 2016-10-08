package Utilities;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class BikesDB {
	
	private static String MONGO_URI = System.getenv("MONGODB_URI"); 
	private static String MONGO_DB = System.getenv("MONGODB_NAME");
	
	final static Morphia morphia = new Morphia();
	static Datastore datastore;
	static Boolean c = false;
	static Gson gson;
	
	public static Datastore getDatastore() {
		if(datastore == null) {
			morphia.mapPackage("DTO");
			MongoClientURI mouri = new MongoClientURI(MONGO_URI);
			datastore = morphia.createDatastore(new MongoClient(mouri), MONGO_DB);
		}
		datastore.ensureCaps();
		datastore.ensureIndexes();
		return datastore;
	}
	

}