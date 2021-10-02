package elk.cloud.api.utils;

import org.apache.commons.lang.StringUtils;

public enum SearchTypeEnum {

    BING("bing","https://cn.bing.com/search?q=","必应"),

    BAIDU("baidu","https://www.baidu.com/s?wd=","百度");

    private String engin;
    private String url;
    private String name;

    SearchTypeEnum(String engin, String url, String name) {

        this.engin = engin;
        this.name = name ;
        this.url = url;
    }

    public String getEngin() {
        return engin;
    }

    public void setEngin(String engin) {
        this.engin = engin;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static SearchTypeEnum getSearchTypeByEngin(String engin){
        SearchTypeEnum[] values = SearchTypeEnum.values();
        for(SearchTypeEnum typeEnum:values){
            if(StringUtils.equals(engin,typeEnum.getEngin())){
                return typeEnum;
            }
        }
        return null;
    }
}
