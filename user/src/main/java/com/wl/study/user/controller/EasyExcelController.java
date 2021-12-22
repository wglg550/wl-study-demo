package com.wl.study.user.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.wl.study.business.excel.dto.EasyExcelStudentDto;
import com.wl.study.business.excel.dto.EasyTestSheetDto;
import com.wl.study.business.excel.dto.ExcelStudentDto;
import com.wl.study.business.excel.listener.BasicExcelListener;
import com.wl.study.business.util.ExcelUtil;
import com.wl.study.business.util.JacksonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: easyExcel
 * @Param:
 * @return:
 * @Author: wangliang
 * @Date: 2021/11/26
 */
@Api(tags = "easyExcelController")
@RestController
@RequestMapping("/${prefix.url.excel}")
public class EasyExcelController {
    private final static Logger log = LoggerFactory.getLogger(EasyExcelController.class);

    String fileName = "/Users/king/Downloads/student1.xlsx";
    String fileName1 = "/Users/king/Downloads/student_write.xlsx";

    @ApiOperation(value = "easyexcel读取excel")
    @RequestMapping(value = "/read", method = RequestMethod.POST)
    @ApiResponses({@ApiResponse(code = 200, message = "保存", response = Object.class)})
    public void easyexcelRead() {
        log.info("============easyexcel准备开始读取excel数据============");
        long start = System.currentTimeMillis();
//        LinkedMultiValueMap<String, EasyExcelStudentDto> easyExcelStudentDtoList = new LinkedMultiValueMap<>();
        LinkedMultiValueMap<String, EasyTestSheetDto> easyTestSheetDtoList = new LinkedMultiValueMap<>();

        //多sheet读取
        ExcelReader excelReader = EasyExcel.read(fileName).build();
//        ReadSheet readSheet1 = EasyExcel.readSheet(0).head(EasyExcelStudentDto.class).registerReadListener(new BasicExcelListener<EasyExcelStudentDto>() {
//            @Override
//            public void handle(LinkedMultiValueMap<String, EasyExcelStudentDto> dataList) {
//                easyExcelStudentDtoList.addAll(dataList);
//            }
//        }).headRowNumber(1).build();

        ReadSheet readSheet2 = EasyExcel.readSheet(1).head(EasyTestSheetDto.class).registerReadListener(new BasicExcelListener<EasyTestSheetDto>() {
            @Override
            public void handle(LinkedMultiValueMap<String, EasyTestSheetDto> dataList) {
                easyTestSheetDtoList.addAll(dataList);
            }
        }).headRowNumber(1).build();

//        excelReader.read(readSheet1, readSheet2);
        excelReader.read(readSheet2);
        excelReader.finish();

        //单sheet读取
//        EasyExcel.read(fileName, EasyExcelStudentDto.class, new BasicExcelListener<EasyExcelStudentDto>() {
//
//            @Override
//            public void handle(LinkedMultiValueMap<String, EasyExcelStudentDto> dataList) {
//                easyExcelStudentDtoList.addAll(dataList);
//            }
//        }).headRowNumber(1).sheet(0).doRead();

//        log.info("sheet1读取到{}条数据", easyExcelStudentDtoList.get(BasicExcelListener.SUCCESS).size());
//        log.info("sheet1读取到{}条数据", easyExcelStudentDtoList.get(1).size());
//        log.info("sheet1成功的数据:{}", JacksonUtil.parseJson(easyExcelStudentDtoList.get(BasicExcelListener.SUCCESS)));
//        log.info("sheet1错误的数据:{}", JacksonUtil.parseJson(easyExcelStudentDtoList.get(BasicExcelListener.ERROR)));
//        log.info("sheet1读取到{}条成功数据", easyExcelStudentDtoList.get(BasicExcelListener.SUCCESS).size());
//        log.info("sheet1读取到{}条错误数据", easyExcelStudentDtoList.get(BasicExcelListener.ERROR).size());

        log.info("sheet2成功的数据:{}", JacksonUtil.parseJson(easyTestSheetDtoList.get(BasicExcelListener.SUCCESS)));
        log.info("sheet2错误的数据:{}", JacksonUtil.parseJson(easyTestSheetDtoList.get(BasicExcelListener.ERROR)));
        log.info("sheet2读取到{}条成功数据", easyTestSheetDtoList.get(BasicExcelListener.SUCCESS).size());
        log.info("sheet2读取到{}条错误数据", easyTestSheetDtoList.get(BasicExcelListener.ERROR).size());
        long end = System.currentTimeMillis();
        log.info("============耗时============:{}秒", (end - start) / 1000);
    }

    @ApiOperation(value = "easyexcel写excel")
    @RequestMapping(value = "/easyexcel/write", method = RequestMethod.POST)
    @ApiResponses({@ApiResponse(code = 200, message = "保存", response = Object.class)})
    public void easyexcelWrite() {
        log.info("============easyexcel准备开始写excel数据============");
        long start = System.currentTimeMillis();
        LinkedMultiValueMap<String, EasyExcelStudentDto> easyExcelStudentDtoList = new LinkedMultiValueMap<>();
        LinkedMultiValueMap<String, EasyTestSheetDto> easyTestSheetDtoList = new LinkedMultiValueMap<>();

        //多sheet读取
        ExcelReader excelReader = EasyExcel.read(fileName).build();
//        ReadSheet readSheet1 = EasyExcel.readSheet(0).head(EasyExcelStudentDto.class).registerReadListener(new BasicExcelListener<EasyExcelStudentDto>() {
//            @Override
//            public void handle(LinkedMultiValueMap<String, EasyExcelStudentDto> dataList) {
//                easyExcelStudentDtoList.addAll(dataList);
//            }
//        }).headRowNumber(1).build();

        ReadSheet readSheet2 = EasyExcel.readSheet(1).head(EasyTestSheetDto.class).registerReadListener(new BasicExcelListener<EasyTestSheetDto>() {
            @Override
            public void handle(LinkedMultiValueMap<String, EasyTestSheetDto> dataList) {
                easyTestSheetDtoList.addAll(dataList);
            }
        }).headRowNumber(1).build();

//        excelReader.read(readSheet1, readSheet2);
        excelReader.read(readSheet2);
        excelReader.finish();

        List<List<String>> head = new ArrayList<>();
        List<String> headTitle0 = new ArrayList<String>();
        headTitle0.add("日期");
        List<String> headTitle1 = new ArrayList<String>();
        headTitle1.add("编码");
        List<String> headTitle2 = new ArrayList<String>();
        headTitle2.add("年龄");
        List<String> headTitle3 = new ArrayList<String>();
        headTitle3.add("姓名");
        List<String> headTitle4 = new ArrayList<String>();
        headTitle4.add("分数");
        List<String> headTitle5 = new ArrayList<String>();
        headTitle5.add("批次");

        head.add(headTitle0);
        head.add(headTitle1);
        head.add(headTitle2);
        head.add(headTitle3);
        head.add(headTitle4);
        head.add(headTitle5);

        ExcelWriter excelWriter = EasyExcel.write(fileName1).build();
        //固定表头
//        WriteSheet writeSheet1 = EasyExcel.writerSheet(0, "sheet1").head(EasyTestSheetDto.class).build();
        //动态表头
        WriteSheet writeSheet1 = EasyExcel.writerSheet(0, "sheet1").head(head).build();
//        WriteSheet writeSheet2 = EasyExcel.writerSheet(1, "sheet2").head(EasyTestSheetDto.class).build();
//        excelWriter.write(easyExcelStudentDtoList.get(BasicExcelListener.SUCCESS), writeSheet1);
        excelWriter.write(easyTestSheetDtoList.get(BasicExcelListener.SUCCESS), writeSheet1);
        excelWriter.finish();

        //        //单sheet读取
//        EasyExcel.read(fileName, EasyExcelStudentDto.class, new BasicExcelListener<EasyExcelStudentDto>() {
//            @Override
//            public void handle(LinkedMultiValueMap<String, EasyExcelStudentDto> dataList) {
//                easyExcelStudentDtoList.addAll(dataList);
//            }
//        }).headRowNumber(1).sheet(0).doRead();
//
//        //单个sheet页写入
//        EasyExcel.write(fileName1, EasyExcelStudentDto.class).sheet("模板1").doWrite(easyExcelStudentDtoList.get(BasicExcelListener.SUCCESS));
        long end = System.currentTimeMillis();
        log.info("============耗时============:{}秒", (end - start) / 1000);
    }

    @ApiOperation(value = "poiexcel读取excel")
    @RequestMapping(value = "/poi/read", method = RequestMethod.POST)
    @ApiResponses({@ApiResponse(code = 200, message = "保存", response = Object.class)})
    public void poiRead() throws IOException, NoSuchFieldException {
        log.info("============poiexcel准备开始读取excel数据============");
        long start = System.currentTimeMillis();
        File file = new File(fileName);
        List<LinkedMultiValueMap<Integer, Object>> finalList = ExcelUtil.excelReader(new FileInputStream(file), Lists.newArrayList(ExcelStudentDto.class), (finalExcelList, finalColumnNameList, finalExcelErrorList) -> {
            if (finalExcelErrorList.size() > 0) {
                throw new Exception(JSONObject.toJSONString(finalExcelErrorList));
            }
            return finalExcelList;
        });
        log.info("读取到{}条数据", finalList.size());
        long end = System.currentTimeMillis();
        log.info("============耗时============:{}秒", (end - start) / 1000);
    }
}
