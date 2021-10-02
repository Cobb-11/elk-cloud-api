package elk.cloud.api.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import elk.cloud.api.bean.BingSearchResut;

@Service
public class CollectDataService {

    private static final String URL="https://cn.bing.com/search?q=elasticsearch";


    private static final int TIME_OUT = 10000;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ElasticSearchService elasticSearchService;

    public List<BingSearchResut> collectData() throws Exception {
        Document document = Jsoup.parse(new URL(URL), TIME_OUT);

        logger.info(document.toString());

        List<BingSearchResut> data = new ArrayList<>();
        if(Objects.nonNull(document)){
            Elements li = document.getElementsByTag("li");
            if(Objects.nonNull(li)){
                li.forEach(element -> {
                    BingSearchResut resut =new BingSearchResut();
                    Elements cites = element.getElementsByTag("cite");
                    if(Objects.nonNull(cites) && cites.size()>0){
                        String url= cites.get(0).text();
                        logger.info("链接地址：{}",url);
                        resut.setUrl(url);
                    }

                    Elements h2 = element.getElementsByTag("h2");
                    if(Objects.nonNull(h2) && h2.size()>0){
                        String title = h2.get(0).getElementsByTag("a").text();
                        logger.info("标题：{}",title);
                        resut.setTitle(title);
                    }

                    Elements img = element.getElementsByTag("img");
                    if(Objects.nonNull(img) && img.size()>0){
                        String imgUrl = img.get(0).attr("src");
                        logger.info("图片：{}",imgUrl);
                        resut.setImgUrl(imgUrl);
                    }

                    data.add(resut);
                });
            }
        }

        return data;

    }

    /**
     *
     * @throws Exception
     */
    public void sendToEs() throws Exception {
        elasticSearchService.putData2Es(this.collectData());
    }
}
