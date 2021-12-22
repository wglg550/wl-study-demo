package com.wl.study.user.elasticsearch.service.impl;

import com.wl.study.user.elasticsearch.bean.DocBean;
import com.wl.study.user.elasticsearch.dao.DocRepository;
import com.wl.study.user.elasticsearch.service.DocService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;

@Service
public class DocServiceImpl implements DocService {

    @Resource
    ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Resource
    DocRepository docRepository;

    Pageable pageable = PageRequest.of(0, 10);

    @Override
    public void createIndex() {
        elasticsearchRestTemplate.createIndex(DocBean.class);
    }

    @Override
    public void deleteIndex(String index) {
        elasticsearchRestTemplate.deleteIndex(index);
    }

    @Override
    public void save(DocBean docBean) {
        docRepository.save(docBean);
    }

    @Override
    public void saveAll(List<DocBean> list) {
        docRepository.saveAll(list);
    }

    @Override
    public Iterator<DocBean> findAll() {
        return docRepository.findAll().iterator();
    }

    @Override
    public Page<DocBean> findByContent(String content, String type) {
        return docRepository.findByContent(content, type, pageable);
    }

    @Override
    public Page<DocBean> findByFirstCode(String firstCode) {
        return docRepository.findByFirstCode(firstCode, pageable);
    }

    @Override
    public Page<DocBean> findBySecordCode(String secordCode) {
        return docRepository.findBySecordCode(secordCode, pageable);
    }
}
