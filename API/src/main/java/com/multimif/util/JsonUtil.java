package com.multimif.util;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

/**
 * @author Amaia Nazábal
 * @version 1.0
 * @since 1.0 10/20/16.
 */
public class JsonUtil<T> {
    public static String convertToJson(Object object) {
        String json;
        ObjectMapper mapper = new ObjectMapper();

        try {
            json = mapper.writeValueAsString(object);
        } catch (Exception ex) {
            json = "";
        }

        return json;
    }

    public static String convertListToJson(List list) {
        String json;
        StringWriter sw = new StringWriter();
        ObjectMapper mapper = new ObjectMapper();

        try {
            mapper.writeValue(sw, list);
            json = sw.toString();
            sw.close();
        } catch (Exception ex) {
            json = "";
        }

        return json;
    }

    public static String convertStringToJson(String str, String value) {
        return "{\"" + str + "\":\"" + value + "\"}";
    }


    /*public List<T> convertListToObjectJSON(String json) {
        List<T> list = new ArrayList();
        com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
        try {
            list = mapper.readValue(json, mapper.getTypeFactory().constructType(List.class,
                    this.getClass().getGenericSuperclass().getClass()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }*/

    public T convertToObjectJSON(String json, Class<T> instance){
        T object = null;
        try{
            object = new ObjectMapper().readValue(json, instance);
        }catch (IOException e){
            e.printStackTrace();

        }
        return object;
    }



}
