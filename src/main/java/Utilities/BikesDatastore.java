package Utilities;

import java.util.List;
import java.util.Map;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.MapreduceResults;
import org.mongodb.morphia.MapreduceType;
import org.mongodb.morphia.aggregation.AggregationPipeline;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.QueryFactory;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MapReduceCommand;
import com.mongodb.MongoClient;
import com.mongodb.WriteConcern;
import com.mongodb.WriteResult;

import DTO.BikesObject;


public class BikesDatastore implements Datastore{

	public Datastore ds;
	
	public BikesDatastore(Datastore ds) {
		this.ds = ds;
	}
	
	@Override
	public <T> Query<T> createQuery(Class<T> collection){
		return ds.createQuery(collection).field("deleted").equal(false);
	}
	
	@Override
	public <T, V> T get(Class<T> clazz, V id){
//		T ob =  ds.get(clazz, id);
//		if (((BikesObject)ob).deleted()){
//			return null;
//		}
		return ds.get(clazz, id);
	}
	
	@Override
	public <T> Key<T> save(T entity){
		return ds.save(entity);
	}


	@Override
	public AggregationPipeline createAggregation(Class source) {
		return ds.createAggregation(source);
	}


	@Override
	public <T> UpdateOperations<T> createUpdateOperations(Class<T> clazz) {
		return ds.createUpdateOperations(clazz);
	}


	@Override
	public <T, V> WriteResult delete(Class<T> clazz, V id) {
		return ds.delete(clazz, id);
	}


	@Override
	public <T, V> WriteResult delete(Class<T> clazz, Iterable<V> ids) {
		return ds.delete(clazz, ids);
	}


	@Override
	public <T> WriteResult delete(Query<T> query) {
		return ds.delete(query);
	}


	@Override
	public <T> WriteResult delete(Query<T> query, WriteConcern wc) {
		return ds.delete(query, wc);
	}


	@Override
	public <T> WriteResult delete(T entity) {
		return ds.delete(entity);
	}


	@Override
	public <T> WriteResult delete(T entity, WriteConcern wc) {
		return ds.delete(entity, wc);
	}


	@Override
	public void ensureCaps() {
		ds.ensureCaps();
	}


	@Override
	public <T> void ensureIndex(Class<T> clazz, String fields) {
		ds.ensureIndex(clazz, fields);
	}


	@Override
	public <T> void ensureIndex(Class<T> clazz, String name, String fields, boolean unique, boolean dropDupsOnCreate) {
		ds.ensureIndex(clazz, name, fields, unique, dropDupsOnCreate);
	}


	@Override
	public void ensureIndexes() {
		ds.ensureIndexes();
	}


	@Override
	public void ensureIndexes(boolean background) {
		ds.ensureIndexes(background);
	}


	@Override
	public <T> void ensureIndexes(Class<T> clazz) {
		ds.ensureIndexes(clazz);
	}


	@Override
	public <T> void ensureIndexes(Class<T> clazz, boolean background) {
		ds.ensureIndexes(clazz, background);
	}


	@Override
	public Key<?> exists(Object keyOrEntity) {
		return ds.exists(keyOrEntity);
	}


	@Override
	public <T> Query<T> find(Class<T> clazz) {
		return ds.find(clazz);
	}


	@Override
	public <T, V> Query<T> find(Class<T> clazz, String property, V value) {
		return ds.find(clazz, property, value);
	}


	@Override
	public <T, V> Query<T> find(Class<T> clazz, String property, V value, int offset, int size) {
		return ds.find(clazz, property, value, offset, size);
	}


	@Override
	public <T> T findAndDelete(Query<T> query) {
		return ds.findAndDelete(query);
	}


	@Override
	public <T> T findAndModify(Query<T> query, UpdateOperations<T> operations) {
		return ds.findAndModify(query, operations);
	}


	@Override
	public <T> T findAndModify(Query<T> query, UpdateOperations<T> operations, boolean oldVersion) {
		return ds.findAndModify(query, operations, oldVersion);
	}


	@Override
	public <T> T findAndModify(Query<T> query, UpdateOperations<T> operations, boolean oldVersion,
			boolean createIfMissing) {
		return ds.findAndModify(query, operations, oldVersion, createIfMissing);
	}


	@Override
	public <T, V> Query<T> get(Class<T> clazz, Iterable<V> ids) {
		return ds.get(clazz, ids);
	}


	@Override
	public <T> T get(T entity) {
		return ds.get(entity);
	}


	@Override
	public <T> T getByKey(Class<T> clazz, Key<T> key) {
		return ds.getByKey(clazz, key);
	}


	@Override
	public <T> List<T> getByKeys(Class<T> clazz, Iterable<Key<T>> keys) {
		return ds.getByKeys(clazz, keys);
	}


	@Override
	public <T> List<T> getByKeys(Iterable<Key<T>> keys) {
		return ds.getByKeys(keys);
	}


	@Override
	public DBCollection getCollection(Class<?> clazz) {
		return ds.getCollection(clazz);
	}


	@Override
	public <T> long getCount(T entity) {
		return ds.getCount(entity);
	}


	@Override
	public <T> long getCount(Class<T> clazz) {
		return ds.getCount(clazz);
	}


	@Override
	public <T> long getCount(Query<T> query) {
		return ds.getCount(query);
	}


	@Override
	public DB getDB() {
		return ds.getDB();
	}


	@Override
	public WriteConcern getDefaultWriteConcern() {
		return ds.getDefaultWriteConcern();
	}


	@Override
	public void setDefaultWriteConcern(WriteConcern wc) {
		ds.setDefaultWriteConcern(wc);
	}


	@Override
	public <T> Key<T> getKey(T entity) {
		return ds.getKey(entity);
	}


	@Override
	public MongoClient getMongo() {
		return ds.getMongo();
	}


	@Override
	public QueryFactory getQueryFactory() {
		return ds.getQueryFactory();
	}


	@Override
	public void setQueryFactory(QueryFactory queryFactory) {
		ds.setQueryFactory(queryFactory);
	}


	@Override
	public <T> MapreduceResults<T> mapReduce(MapreduceType type, Query q, String map, String reduce, String finalize,
			Map<String, Object> scopeFields, Class<T> outputType) {
		return ds.mapReduce(type, q, map, reduce, finalize, scopeFields, outputType);
	}


	@Override
	public <T> MapreduceResults<T> mapReduce(MapreduceType type, Query q, Class<T> outputType,
			MapReduceCommand baseCommand) {
		return ds.mapReduce(type, q, outputType, baseCommand);
	}


	@Override
	public <T> Key<T> merge(T entity) {
		return ds.merge(entity);
	}


	@Override
	public <T> Key<T> merge(T entity, WriteConcern wc) {
		return ds.merge(entity, wc);
	}


	@Override
	public <T> Query<T> queryByExample(T example) {
		return ds.queryByExample(example);
	}


	@Override
	public <T> Iterable<Key<T>> save(Iterable<T> entities) {
		return ds.save(entities);
	}


	@Override
	public <T> Iterable<Key<T>> save(Iterable<T> entities, WriteConcern wc) {
		return ds.save(entities, wc);
	}


	@Override
	public <T> Iterable<Key<T>> save(T... entities) {
		return ds.save(entities);
	}


	@Override
	public <T> Key<T> save(T entity, WriteConcern wc) {
		return ds.save(entity, wc);
	}


	@Override
	public <T> UpdateResults update(T entity, UpdateOperations<T> operations) {
		return ds.update(entity, operations);
	}


	@Override
	public <T> UpdateResults update(Key<T> key, UpdateOperations<T> operations) {
		return ds.update(key, operations);
	}


	@Override
	public <T> UpdateResults update(Query<T> query, UpdateOperations<T> operations) {
		return ds.update(query, operations);
	}


	@Override
	public <T> UpdateResults update(Query<T> query, UpdateOperations<T> operations, boolean createIfMissing) {
		return ds.update(query, operations, createIfMissing);
	}


	@Override
	public <T> UpdateResults update(Query<T> query, UpdateOperations<T> operations, boolean createIfMissing,
			WriteConcern wc) {
		return ds.update(query, operations, createIfMissing, wc);
	}


	@Override
	public <T> UpdateResults updateFirst(Query<T> query, UpdateOperations<T> operations) {
		return ds.updateFirst(query, operations);
	}


	@Override
	public <T> UpdateResults updateFirst(Query<T> query, UpdateOperations<T> operations, boolean createIfMissing) {
		return ds.updateFirst(query, operations, createIfMissing);
	}


	@Override
	public <T> UpdateResults updateFirst(Query<T> query, UpdateOperations<T> operations, boolean createIfMissing,
			WriteConcern wc) {
		return ds.updateFirst(query, operations, createIfMissing, wc);
	}


	@Override
	public <T> UpdateResults updateFirst(Query<T> query, T entity, boolean createIfMissing) {
		return ds.updateFirst(query, entity, createIfMissing);
	}
	
}
