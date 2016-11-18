package Util;

import org.codehaus.jackson.map.ObjectMapper;

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
        ObjectMapper mapper = new ObjectMapper();

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
        ObjectMapper mapper = new ObjectMapper();

        try{
            mapper.writeValue(sw, list);
            json = sw.toString();
            sw.close();
        }catch(Exception ex){
            json = "";
        }

        return json;
    }

    public static String convertStringToJson(String str, String value){
        return "{\""+ str + "\":\"" + value + "\"}";
    }

    public List<T> convertToObjectJSON(String json){
        List<T> list = new ArrayList();
        ObjectMapper mapper = new ObjectMapper();
        try {
            list = mapper.readValue(json, mapper.getTypeFactory().constructType(List.class,
                    this.getClass().getGenericSuperclass().getClass()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }
}
