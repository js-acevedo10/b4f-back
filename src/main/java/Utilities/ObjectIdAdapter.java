package Utilities;

import java.lang.reflect.Type;

import org.bson.types.ObjectId;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonDeserializer;


public class ObjectIdAdapter {

	public static final GsonBuilder gsonBuilder = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            .registerTypeAdapter(ObjectId.class, new JsonSerializer<ObjectId>() {
                @Override
                public JsonElement serialize(ObjectId src, Type typeOfSrc, JsonSerializationContext context) {
                    return new JsonPrimitive(src.toHexString());
                }

            })
            .registerTypeAdapter(ObjectId.class, new JsonDeserializer<ObjectId>() {
                @Override
                public ObjectId deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                    return new ObjectId(json.getAsString());
                }
            });

    public static Gson getGson() {
        return gsonBuilder.create();
    }
}
