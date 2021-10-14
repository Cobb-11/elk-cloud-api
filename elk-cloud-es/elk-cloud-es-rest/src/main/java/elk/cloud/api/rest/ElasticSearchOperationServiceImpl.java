package elk.cloud.api.rest;

import elk.cloud.api.dto.SearchDTO;
import elk.cloud.api.dto.WriteRequest;
import elk.cloud.api.service.ElasticSearchOperationService;
import elk.cloud.api.service.ElasticSearchService;
import elk.cloud.api.vo.SearchVO;
import elk.cloud.api.vo.WriteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
public class ElasticSearchOperationServiceImpl implements ElasticSearchOperationService {



    private final ElasticSearchService elasticSearchService;

    public ElasticSearchOperationServiceImpl(ElasticSearchService elasticSearchService) {
        this.elasticSearchService = elasticSearchService;
    }


    @Override
    @ResponseBody
    public WriteResponse wrtie(@RequestBody WriteRequest request) throws Exception {
        if (Objects.isNull(request) || CollectionUtils.isEmpty(request.get_source())){
            throw new Exception("入参为空！");
        }

        List<IndexRequest> resutList = new ArrayList<>();
        request.get_source().forEach(s -> {
            IndexRequest indexRequest = new IndexRequest();
            indexRequest.source(s);
            resutList.add(indexRequest);
        });


        elasticSearchService.writeToEs(request.get_index(),resutList);

        return new WriteResponse();
    }

    @Override
    @ResponseBody
    public SearchVO search(@RequestBody SearchDTO dto){
        SearchVO vo = new SearchVO();
        List<Map<String, Object>> search = elasticSearchService.search(dto.getIndex(), dto.getQueryMap());
        vo.setResultList(search);
        return vo;
    }
}
