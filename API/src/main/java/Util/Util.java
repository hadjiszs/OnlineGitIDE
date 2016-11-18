package Util;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by amaia.nazabal on 10/20/16.
 */
public class Util<T> {
    public static String convertToJson(Object object){
        String json;
        org.codehaus.jackson.map.ObjectMapper mapper = new org.codehaus.jackson.map.ObjectMapper();

        try{
            json =  mapper.writeValueAsString(object);
        }catch(Exception ex){
            json = "";
        }

        return json;
    }

    public static String convertListToJson(List list){
        String json;
        StringWriter sw = new StringWriter();
        org.codehaus.jackson.map.ObjectMapper mapper = new org.codehaus.jackson.map.ObjectMapper();

        try{
            mapper.writeValue(sw, list);
            json = sw.toString();
            sw.close();
        }catch(Exception ex){
            json = "";
        }

        return json;
    }

    public List<T> convertToObjectJSON(String json){
        List<T> list = new ArrayList();
        com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
        try {
            list = mapper.readValue(json, mapper.getTypeFactory().constructType(List.class,
                    this.getClass().getGenericSuperclass().getClass()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }

}
