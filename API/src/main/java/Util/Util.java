package Util;

import org.codehaus.jackson.map.ObjectMapper;
import java.util.List;
import java.io.StringWriter;
/**
 * Created by amaia.nazabal on 10/20/16.
 */
public class Util {
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
}
