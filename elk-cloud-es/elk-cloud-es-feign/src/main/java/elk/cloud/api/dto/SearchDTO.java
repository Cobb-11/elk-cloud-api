package elk.cloud.api.dto;

import java.util.Map;

public class SearchDTO {

    private String index;

    private Map<String,Object> queryMap;

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public Map<String, Object> getQueryMap() {
        return queryMap;
    }

    public void setQueryMap(Map<String, Object> queryMap) {
        this.queryMap = queryMap;
    }
}
