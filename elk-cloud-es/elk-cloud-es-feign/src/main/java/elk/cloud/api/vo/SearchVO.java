package elk.cloud.api.vo;

import java.util.List;
import java.util.Map;

public class SearchVO {

    private List<Map<String,Object>> resultList;

    public List<Map<String, Object>> getResultList() {
        return resultList;
    }

    public void setResultList(List<Map<String, Object>> resultList) {
        this.resultList = resultList;
    }
}
