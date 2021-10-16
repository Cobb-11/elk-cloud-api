package elk.cloud.api.rest;

import elk.cloud.api.bean.SearchBingResult;
import elk.cloud.api.bean.SearchBingResut;
import elk.cloud.api.dto.SearchBingRequest;
import elk.cloud.api.dto.SearchDevLogDTO;
import elk.cloud.api.dto.SearchDevLogRequest;
import elk.cloud.api.service.SearchService;
import elk.cloud.api.service.SearchToBingService;
import elk.cloud.api.service.SearchToElasticSearchService;
import elk.cloud.api.vo.SearchBingResponse;
import elk.cloud.api.vo.SearchDevLogResponse;
import elk.cloud.api.vo.SearchDevLogVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class SearchController implements SearchService {

    private final Logger logger= LoggerFactory.getLogger(this.getClass());

    private final SearchToBingService searchToBingService;

    private final SearchToElasticSearchService searchToElasticSearchService;

    public SearchController(SearchToBingService searchToBingService, SearchToElasticSearchService searchToElasticSearchService) {
        this.searchToBingService = searchToBingService;
        this.searchToElasticSearchService = searchToElasticSearchService;
    }

    @Override
    public String getEsIndex(){
        return "{\n" +
                "  \"name\" : \"LAPTOP-IGJ13LTB\",\n" +
                "  \"cluster_name\" : \"elasticsearch\",\n" +
                "  \"cluster_uuid\" : \"j53BU-TGT96_QcCCnxO4rg\",\n" +
                "  \"version\" : {\n" +
                "    \"number\" : \"7.14.0\",\n" +
                "    \"build_flavor\" : \"default\",\n" +
                "    \"build_type\" : \"zip\",\n" +
                "    \"build_hash\" : \"dd5a0a2acaa2045ff9624f3729fc8a6f40835aa1\",\n" +
                "    \"build_date\" : \"2021-07-29T20:49:32.864135063Z\",\n" +
                "    \"build_snapshot\" : false,\n" +
                "    \"lucene_version\" : \"8.9.0\",\n" +
                "    \"minimum_wire_compatibility_version\" : \"6.8.0\",\n" +
                "    \"minimum_index_compatibility_version\" : \"6.0.0-beta1\"\n" +
                "  },\n" +
                "  \"tagline\" : \"You Know, for Search\"\n" +
                "}";
    }

    @Override
    public SearchBingResponse searchBing(@RequestBody SearchBingRequest request){
        SearchBingResponse response = new SearchBingResponse();
        long startTime = System.currentTimeMillis();
        long time = 0;

        List<SearchBingResut> resutList = new ArrayList<>();
        try {
            resutList = searchToBingService.searchBing(request.getKeyWord(),request.getType());
            if(!CollectionUtils.isEmpty(resutList)){
                List<SearchBingResult> results = new ArrayList<>();
                for (SearchBingResut searchBingResut:resutList){
                    SearchBingResult result = new SearchBingResult();
                    result.setImgUrl(searchBingResut.getImgUrl());
                    result.setKeyWord(searchBingResut.getKeyWord());
                    result.setTitle(searchBingResut.getTitle());
                    result.setUrl(searchBingResut.getUrl());

                    results.add(result);
                }
                response.setResuts(results);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            response.setSuccess(false);
            response.setErrorMessage(e.getMessage());
        }catch (Throwable throwable){
            logger.error(throwable.getMessage());
            throwable.printStackTrace();
            response.setSuccess(false);
            response.setErrorMessage(throwable.getMessage());
        }
        /*finally {

        }*/
        long endTime = System.currentTimeMillis();
        time= endTime - startTime;

        response.setTime(time);

        return response;
    }

    @Override
    public List<SearchDevLogResponse> searchDevLog(@RequestBody SearchDevLogRequest request){
        List<SearchDevLogResponse> responses = new ArrayList<>();
        SearchDevLogDTO dto = new SearchDevLogDTO();
        BeanUtils.copyProperties(request,dto);
        List<SearchDevLogVO> voList = searchToElasticSearchService.searchDevLog(dto);

        if(!CollectionUtils.isEmpty(voList)){
            voList.forEach(searchDevLogVO -> {
                SearchDevLogResponse response = new SearchDevLogResponse();
                BeanUtils.copyProperties(searchDevLogVO,response);
                responses.add(response);
            });
        }

        return responses;
    }
}
