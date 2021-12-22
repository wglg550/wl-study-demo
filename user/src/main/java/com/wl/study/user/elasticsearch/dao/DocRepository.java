package com.wl.study.user.elasticsearch.dao;

import com.wl.study.user.elasticsearch.bean.DocBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface DocRepository extends ElasticsearchRepository<DocBean, Long> {

    //默认的注释
    @Query("{\"bool\":{\"must\":[{\"match\":{\"content\":\"?0\"}},{\"bool\":{\"should\":[{\"match\":{\"content\":\"?1\"}}]}}]}}")
    Page<DocBean> findByContent(String content, String type, Pageable pageable);

    @Query("{\"bool\" : {\"must\" : {\"field\" : {\"firstCode.keyword\" : \"?\"}}}}")
    Page<DocBean> findByFirstCode(String firstCode, Pageable pageable);

    @Query("{\"bool\" : {\"must\" : {\"field\" : {\"secordCode.keyword\" : \"?\"}}}}")
    Page<DocBean> findBySecordCode(String secordCode, Pageable pageable);
}
