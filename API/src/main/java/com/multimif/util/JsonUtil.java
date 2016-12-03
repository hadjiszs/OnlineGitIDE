package com.multimif.util;

import com.multimif.controller.UserController;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Amaia Nazábal
 * @version 1.0
 * @since 1.0 10/20/16.
 */
public class JsonUtil<T> {

    private static final Logger LOGGER = Logger.getLogger(UserController.class.getName());

    /**
     * @param object
     * @return
     */
    public static String convertToJson(Object object) {
        String json;
        ObjectMapper mapper = new ObjectMapper();

        try {
            json = mapper.writeValueAsString(object);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            LOGGER.log(Level.FINE, ex.getMessage(), ex);
            json = "";
        }

        return json;
    }

    /**
     *
     * Cette méthode transforme la liste envoyé en une chaîne de characteres json
     *
     * @param list reçoit une liste d'objets
     * @return chaine de characteres avec le format json
     */
    public static String convertListToJson(List list) {
        String json;
        StringWriter sw = new StringWriter();
        ObjectMapper mapper = new ObjectMapper();

        try {
            mapper.writeValue(sw, list);
            json = sw.toString();
            sw.close();
        } catch (Exception ex) {
            LOGGER.log(Level.FINE, ex.getMessage(), ex);
            json = "";
        }

        return json;
    }

    /**
     *
     * Cette fonction transforme un parametre clé valeur en json
     *
     * @param str une clé
     * @param value une valeur
     * @return chaîne de characteres avec le format JSON.
     */
    public static String convertStringToJson(String str, String value) {
        return "{\"" + str + "\":\"" + value + "\"}";
    }

    /**
     *
     * Cette fonction transforme une chaîne de characteres dans un objet
     * du type de l'instance envoyé.
     *
     * @param json une chaîne de characteres en format json
     * @param instance une instance de la classe
     * @return un objet du type de l'instance envoyé.
     */
    public T convertToObjectJSON(String json, Class<T> instance){
        T object = null;
        try{
            object = new ObjectMapper().readValue(json, instance);
        }catch (IOException e){
            LOGGER.log(Level.FINE, e.getMessage(), e);

        }
        return object;
    }



}
