package io.openliberty.guides.inventory.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Properties;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonString;
import javax.json.JsonValue;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.annotation.*;
import javax.json.bind.*;

@Provider
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Priority(1)
public class PropertiesReader implements MessageBodyReader<Properties>, MessageBodyWriter<Properties> {

    @Override
    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        System.out.println("AJM: will read prop data from stream?");
        return true;
    }

    @Override
    public Properties readFrom(Class<Properties> type, Type genericType, Annotation[] annotations, MediaType mediaType,
            MultivaluedMap<String, String> httpHeaders, InputStream entityStream)
            throws IOException, WebApplicationException {

        // JsonReader jr = Json.createReader(entityStream);
        Jsonb jsonb = JsonbBuilder.create();
        Properties props = jsonb.fromJson(entityStream, Properties.class);
        System.out.println("AJM: props obj just read in - \n" + props.toString());

        /*
         * JsonObject json = jr.readObject();
         * 
         * Properties retVal = new Properties();
         * 
         * json.keySet().forEach(key -> { JsonValue value = json.get(key); if
         * (!JsonValue.NULL.equals(value)) { if (value.getValueType() !=
         * JsonValue.ValueType.STRING) { throw new
         * IllegalArgumentException("Non-String JSON prop value found in payload.  Sample data is more than this sample can deal with.  It's not intended to handle any payload."
         * ); } JsonString jstr = (JsonString)value;
         * 
         * retVal.setProperty(key, jstr.getString()); } }); return retVal;
         */
        return props;
    }

    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public void writeTo(Properties t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType,
            MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream)
            throws IOException, WebApplicationException {
        // TODO Auto-generated method stub
        System.out.println("AJM: in the write method, will need to write out this -> \n" + t.toString());
        Jsonb jsonb = JsonbBuilder.create();
        String propStr = jsonb.toJson(t);
        System.out.println("AJM: just wrote out this prop obj -> \n" + propStr);
    }

}
