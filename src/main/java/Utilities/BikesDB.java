package Utilities;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import DTO.Client;
import DTO.Meta;
import DTO.Penalty;

public class BikesDB {
	
	private static String MONGO_URI = System.getenv("MONGODB_URI"); 
	private static String MONGO_DB = System.getenv("MONGODB_NAME");
	
	final static Morphia morphia = new Morphia();
	static BikesDatastore datastore;
	static Boolean c = false;
	static Gson gson;
	private static long consolidated = 0;
	
	private static Thread bg_job;
	
	public static Datastore getDatastore() {
		if(datastore == null) {
			morphia.mapPackage("DTO");
			MongoClientURI mouri = new MongoClientURI(MONGO_URI);
			datastore = new BikesDatastore(morphia.createDatastore(new MongoClient(mouri), MONGO_DB));
		}
		datastore.ensureCaps();
		datastore.ensureIndexes();
		if (bg_job == null && (consolidated == 0 || System.currentTimeMillis()-consolidated > 21600000)){
			bg_job = new Thread(new Runnable() {
				
				@Override
				public void run() {
					Meta monthly = datastore.get(Meta.class, new ObjectId("5837f7189e3e2b06f46cbf04"));
					Calendar c = Calendar.getInstance();
					c.setTime(monthly.getDate());
					c.add(Calendar.MONTH, 1);
					if (c.getTime().before(new Date())){
						//Needs monthly consolidate
						//Update consolidate
						List<Client> clients = BikesDB.getDatastore().createQuery(Client.class).asList();
						if(clients != null && !clients.isEmpty()) {
							for (Client client : clients) {
								penalize(client);
							}
						}
						monthly.setDate(new Date());
						datastore.save(monthly);
					}
					
					Meta daily = datastore.get(Meta.class, new ObjectId("5837fb989e3e2b06f46cbf06"));
					c.setTime(daily.getDate());
					c.add(Calendar.DAY_OF_YEAR, 1);
					if (c.getTime().before(new Date())){
						//Needs daily consolidate
						//Update consolidate
						List<Client> clients = BikesDB.getDatastore().createQuery(Client.class).asList();
						if(clients != null && !clients.isEmpty()) {
							for (Client client : clients) {
								suspend(client);
							}
						}
						daily.setDate(new Date());
						datastore.save(daily);
					}
					
				}
			});
		}
		else{
			if (!bg_job.isAlive()){
				bg_job = null;
				consolidated = System.currentTimeMillis();
			}
		}
		return datastore;
	}
	
	
	//Helper methods for Bg Tasks
	private static void penalize(Client client){
		Double points = client.getPoints();
		client.setPoints(0);
		Penalty penalty = null;
		
		if (points > 50){
			double prev = client.getBonus();
			client.setBonus(prev+(points*100));
			datastore.save(client);
			return;
		}
		else if (points >= 0){		
			return;
		}
		else if (points < 0){
			penalty = new Penalty();
			penalty.setPending(true);
			penalty.setSuspensionStart(new Date());

			if (points > -100){
				penalty.setFee(0.0);
				penalty.setSuspensionEnd(new Date());
				penalty.setTrainingRequiered(true);
				penalty.setPending(false);
			}
			else if (points > -200){
				penalty.setFee((-1)*points*500);
				penalty.setSuspensionEnd(new Date());
				penalty.setTrainingRequiered(false);
			}
			else{
				penalty.setFee((-1)*points*7000);
				Calendar c = Calendar.getInstance();
				c.setTime(new Date());
				c.add(Calendar.YEAR, 3);
				penalty.setSuspensionEnd(c.getTime());
				penalty.setTrainingRequiered(false);
				client.setSuspended(true);
			}
		}
		
		if (penalty != null){
			if (penalty.getFee() < 0 || penalty.getSuspensionEnd().before(penalty.getSuspensionStart())){
				return;
			}
			datastore.save(client);
			penalty.setClient(client);
			datastore.save(penalty);
			return;
		}
	}
	
	private static void suspend(Client client){
		if (client.getSuspendAfter() != null){
			if (client.isSuspended()){
				client.setSuspendAfter(null);
			}
			else if (client.getSuspendAfter().before(new Date())){
				client.setSuspended(true);
				client.setSuspendAfter(null);
			}
			datastore.save(client);
		}
	}

}