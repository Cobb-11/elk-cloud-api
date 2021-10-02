package elk.cloud.api.utils;

import elk.cloud.api.bean.SearchBingResut;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SearchUtil {

    private static final int TIME_OUT = 10000;

    public  List<SearchBingResut> search(String keyWord,String type) throws IOException {
        SearchTypeEnum searchTypeEnum = SearchTypeEnum.getSearchTypeByEngin(type);
        if(Objects.isNull(searchTypeEnum)){
            return null;
        }

        String searchUrl = searchTypeEnum.getUrl()+keyWord;

        URL uri = new URL(searchUrl);
        Document document = Jsoup.parse(uri, TIME_OUT);

        List<SearchBingResut> data = new ArrayList<>();
        if(Objects.nonNull(document)){
            Elements li = document.getElementsByTag("li");
            if(Objects.nonNull(li)){
                for(Element element : li){
                    SearchBingResut resut =new SearchBingResut();
                    resut.setKeyWord(keyWord);
                    Elements cites = element.getElementsByTag("cite");
                    if(Objects.nonNull(cites) && cites.size()>0){
                        String url= cites.get(0).text();
                        resut.setUrl(url);
                    }

                    Elements h2 = element.getElementsByTag("h2");
                    if(Objects.nonNull(h2) && h2.size()>0){
                        String title = h2.get(0).getElementsByTag("a").text();
                        resut.setTitle(title);
                    }

                    Elements img = element.getElementsByTag("img");
                    if(Objects.nonNull(img) && img.size()>0){
                        String imgUrl = img.get(0).attr("src");
                        resut.setImgUrl(imgUrl);
                    }

                    data.add(resut);
                }
            }
        }
        return data;
    }
}
