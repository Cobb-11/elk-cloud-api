package elk.cloud.api.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class XmlWrapperMapper {

    private static XmlMapper mapper = new XmlMapper();

    public static String toXml(Object object){
        String xml = "";
        try {
            xml = mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return xml;
    }

    public static <T> T fromXml(String xml,Class<T> tClass){
        T t = null;
        try {
            t = mapper.readValue(xml,tClass);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return t;
    }
}
