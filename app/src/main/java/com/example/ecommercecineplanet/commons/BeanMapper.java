package com.example.ecommercecineplanet.commons;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonDeserializationContext;
import android.util.Base64;

import java.lang.reflect.Type;

public class BeanMapper {

    public static <T> T fromJson(String jsonAsString, Class<T> beanClass) {
        return new Gson().fromJson(jsonAsString, beanClass);
    }

    public static <T> T fromJson(String jsonAsString, Type beanType) {
        return new Gson().fromJson(jsonAsString, beanType);
    }

    public static String toJson(Object bean) {
        return toJson(bean, true);
    }

    private static String toJson(Object bean, boolean prettyPrint) {
        Gson gson = prettyPrint
                ? new GsonBuilder().setPrettyPrinting().serializeNulls().create()
                : new GsonBuilder().registerTypeHierarchyAdapter(
                byte[].class,
                new ByteArrayToBase64TypeAdapter()
        ).serializeNulls().create();
        return gson.toJson(bean);
    }

    public static class ByteArrayToBase64TypeAdapter implements JsonSerializer<byte[]>, JsonDeserializer<byte[]> {

        @Override
        public byte[] deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return Base64.decode(json.getAsString(), Base64.DEFAULT);
        }

        @Override
        public JsonElement serialize(byte[] src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(Base64.encodeToString(src, Base64.DEFAULT));
        }
    }
}
