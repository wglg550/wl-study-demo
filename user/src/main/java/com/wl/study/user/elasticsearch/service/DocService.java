package com.wl.study.user.elasticsearch.service;

import com.wl.study.user.elasticsearch.bean.DocBean;
import org.springframework.data.domain.Page;

import java.util.Iterator;
import java.util.List;

public interface DocService {

    void createIndex();

    void deleteIndex(String index);

    void save(DocBean docBean);

    void saveAll(List<DocBean> list);

    Iterator<DocBean> findAll();

    Page<DocBean> findByContent(String content, String type);

    Page<DocBean> findByFirstCode(String firstCode);

    Page<DocBean> findBySecordCode(String secordCode);
}
