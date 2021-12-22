package com.wl.study.business.util.excel;

import org.springframework.util.LinkedMultiValueMap;

import java.util.List;

/**
 * @Description: excel回调
 * @Param:
 * @return:
 * @Author: wangliang
 * @Date: 2020/4/19
 */
public interface ExcelCallback {

    /**
     * excel回调方法
     *
     * @param finalList
     * @param finalColumnNameList
     * @param finalExcelErrorList
     * @return
     * @throws IllegalAccessException
     */
    public List<LinkedMultiValueMap<Integer, Object>> callback(List<LinkedMultiValueMap<Integer, Object>> finalList, List<LinkedMultiValueMap<Integer, String>> finalColumnNameList, List<LinkedMultiValueMap<Integer, ExcelError>> finalExcelErrorList) throws Exception;
}
