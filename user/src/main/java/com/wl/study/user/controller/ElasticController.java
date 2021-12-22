package com.wl.study.user.controller;

import com.wl.study.user.elasticsearch.bean.DocBean;
import com.wl.study.user.elasticsearch.service.DocService;
import io.swagger.annotations.*;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: elasticsearch
 * @Param:
 * @return:
 * @Author: wangliang
 * @Date: 2021/11/26
 */
@Api(tags = "elasticsearchController")
@RestController
@RequestMapping("/${prefix.url.elastic}")
public class ElasticController {
    private final static Logger log = LoggerFactory.getLogger(ElasticController.class);

    @Resource
    DocService docService;

    @Resource
    ElasticsearchRestTemplate elasticsearchRestTemplate;

    @ApiOperation(value = "初始化数据")
    @RequestMapping(value = "/init", method = RequestMethod.POST)
    @ApiResponses({@ApiResponse(code = 200, message = "保存", response = Object.class)})
    public void init() {
//        docService.createIndex();
        List<DocBean> list = new ArrayList<>();
        list.add(new DocBean(1L, "huawei", "phone", "华为手机", 1));
        list.add(new DocBean(2L, "huawei", "pc", "华为电脑", 2));
        list.add(new DocBean(3L, "huawei", "luyou", "华为路由器", 3));
        docService.saveAll(list);
    }

//    @ApiOperation(value = "断言测试")
//    @RequestMapping(value = "/assertTest", method = RequestMethod.POST)
//    @ApiResponses({@ApiResponse(code = 200, message = "保存", response = Object.class)})
//    public String init1(@RequestParam(required = false) String param) {
//        Assert.notNull(param, "参数为空");
//        return "true";
//    }

    @ApiOperation(value = "查询所有")
    @RequestMapping(value = "/all", method = RequestMethod.POST)
    @ApiResponses({@ApiResponse(code = 200, message = "查询所有", response = Object.class)})
    public Iterator<DocBean> all() {
        return docService.findAll();
    }

    @ApiOperation(value = "firstCode查询")
    @RequestMapping(value = "/findByFirstCode", method = RequestMethod.POST)
    @ApiResponses({@ApiResponse(code = 200, message = "firstCode查询", response = Object.class)})
    public Page<DocBean> findByFirstCode(@ApiParam(value = "编码") @RequestParam String code) {
        return docService.findByFirstCode(code);
    }

    @ApiOperation(value = "secordCode查询")
    @RequestMapping(value = "/findBySecordCode", method = RequestMethod.POST)
    @ApiResponses({@ApiResponse(code = 200, message = "secordCode查询", response = Object.class)})
    public Page<DocBean> findBySecordCode(@ApiParam(value = "编码") @RequestParam String code) {
        return docService.findBySecordCode(code);
    }

    @ApiOperation(value = "content查询")
    @RequestMapping(value = "/findByContent", method = RequestMethod.POST)
    @ApiResponses({@ApiResponse(code = 200, message = "content查询", response = Object.class)})
    public Object findByContent(@ApiParam(value = "内容") @RequestParam String content, @ApiParam(value = "类别") @RequestParam String type) {
        Pageable pageable = PageRequest.of(0, 10);
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                //设置查询条件，此处可以使用QueryBuilders创建多种查询
                .withQuery(QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("content", content)))
                .withQuery(QueryBuilders.boolQuery().should(QueryBuilders.matchQuery("content", type)))
                //分页
                .withPageable(pageable)
                //排序
                .withSort(SortBuilders.fieldSort("id").order(SortOrder.DESC))
                //高亮字段显示
                .withHighlightFields(new HighlightBuilder.Field("content"))
                //创建SearchQuery对象
                .build();
        SearchHits<DocBean> searchHitsList = elasticsearchRestTemplate.search(searchQuery, DocBean.class);
        return new PageImpl<DocBean>(searchHitsList.getSearchHits().stream().map(s -> s.getContent()).collect(Collectors.toList()), pageable, searchHitsList.getSearchHits().size());
//        return docService.findByContent(content, type);
    }
}
