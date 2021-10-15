package elk.cloud.api.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonWrapperMapper {

    private static ObjectMapper mapper = new ObjectMapper();

    public static String toString(Object object){

        String json = new String();
        try {
            json = mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return json;
    }

    public static <T>T fromString(String json,Class<T> tClass){
        T t = null;
        try {
            t = mapper.readValue(json,tClass);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return  t;
    }
}
