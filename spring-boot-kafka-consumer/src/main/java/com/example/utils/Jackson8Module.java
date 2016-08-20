package com.example.utils;

import java.io.IOException;
import java.util.function.Function;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class Jackson8Module extends SimpleModule {

    /**
	 * 
	 */
	private static final long serialVersionUID = 7437137907314201874L;

	public <T> void addCustomSerializer(Class<T> cls, SerializeFunction<T> serializeFunction){
        JsonSerializer<T> jsonSerializer = new JsonSerializer<T>(){
            @Override
            public void serialize(T t, JsonGenerator jgen, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
                serializeFunction.serialize(t, jgen);
            }
        };
        addSerializer(cls,jsonSerializer);
    }

    public <T> void addStringSerializer(Class<T> cls, Function<T,String> serializeFunction){
        JsonSerializer<T> jsonSerializer = new JsonSerializer<T>(){
            @Override
            public void serialize(T t, JsonGenerator jgen, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
                String val = serializeFunction.apply(t);
                jgen.writeString(val);
            }
        };
        addSerializer(cls,jsonSerializer);
    }

    public static interface SerializeFunction<T>{
        public void serialize(T t, JsonGenerator jgen) throws IOException, JsonProcessingException;
    }
}
