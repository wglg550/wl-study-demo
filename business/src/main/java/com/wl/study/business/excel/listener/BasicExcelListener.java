package com.wl.study.business.excel.listener;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.CellExtra;
import com.alibaba.fastjson.JSONObject;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.LinkedMultiValueMap;

import javax.validation.constraints.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public abstract class BasicExcelListener<T> extends AnalysisEventListener<T> {
    private final static Logger log = LoggerFactory.getLogger(BasicExcelListener.class);

    /**
     * 批处理阈值2000
     */
    private static int BATCH_COUNT = 2000;
    public static final String SUCCESS = "success";
    public static final String ERROR = "error";
    private LinkedMultiValueMap<String, T> list;

    public BasicExcelListener() {
        this.list = new LinkedMultiValueMap<>(BATCH_COUNT);
    }

    public BasicExcelListener(int batchCount) {
        BATCH_COUNT = batchCount;
        this.list = new LinkedMultiValueMap<>(BATCH_COUNT);
    }

    public abstract void handle(LinkedMultiValueMap<String, T> dataList);

    @Override
    public void invoke(T o, AnalysisContext analysisContext) {
//        if (validData(o, analysisContext) || (analysisContext.readRowHolder().getRowIndex() == 2001 || analysisContext.readRowHolder().getRowIndex() == 7001)) {
        if (validData(o, analysisContext)) {
            list.add(ERROR, o);
        } else {
            list.add(SUCCESS, o);
        }
        if (list.size() >= BATCH_COUNT) {
            handle(list);
            list.clear();
        }
    }

//    @Override
//    public void invokeHead(Map<Integer, ReadCellData<?>> headMap, AnalysisContext context) {
//        log.info("表头:{}", JSONObject.toJSONString(headMap));
//    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        log.info("表头:{}", JSONObject.toJSONString(headMap));
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("所有数据解析完成!");
        handle(list);
    }

    @Override
    public void onException(Exception exception, AnalysisContext context) {
        log.info("onException is come in!");
    }

    @Override
    public void extra(CellExtra extra, AnalysisContext context) {
        log.info("extra is come in!");
    }

    private void extendBasicExcelField(T o, AnalysisContext analysisContext, List<String> errorMessage) {
        try {
            Field rowField = o.getClass().getField("row");
            rowField.setAccessible(true);
            rowField.set(o, analysisContext.readRowHolder().getRowIndex());

            Field sheetField = o.getClass().getField("sheet");
            sheetField.setAccessible(true);
            sheetField.set(o, analysisContext.readSheetHolder().getSheetNo() + 1);

            Field errorMessageMapField = o.getClass().getField("errorMessage");
            errorMessageMapField.setAccessible(true);
            errorMessageMapField.set(o, errorMessage);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            log.error("请求出错:", e);
        }
    }

    private boolean validData(T o, AnalysisContext analysisContext) {
        List<String> errorMessage = new ArrayList<>();
        Field[] fields = o.getClass().getDeclaredFields();
        Object object = null;
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            try {
                object = fields[i].get(o);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            ExcelProperty excelProperty = fields[i].getDeclaredAnnotation(ExcelProperty.class);
            if (Objects.isNull(excelProperty)) {
                continue;
            }
            Annotation[] annotations = fields[i].getDeclaredAnnotations();
            NotBlank notBlank = null;
            Length length = null;
            Min min = null;
            Max max = null;
            DecimalMin decimalMin = null;
            DecimalMax decimalMax = null;
            NotNull notNull = null;
            Null isnull = null;
            NotEmpty notEmpty = null;
            Size size = null;
            Range range = null;
            AssertTrue assertTrue = null;
            AssertFalse assertFalse = null;
            for (Annotation annotation : annotations) {
                if (annotation instanceof NotBlank) {
                    notBlank = (NotBlank) annotation;
                    if (Objects.isNull(object) || Objects.equals(object, " ")) {
                        errorMessage.add("列名[" + excelProperty.value()[0] + "]:" + notBlank.message());
                    }
                } else if (annotation instanceof Length) {
                    length = (Length) annotation;
                    if (Objects.nonNull(object) && (object.toString().length() < length.min() || object.toString().length() > length.max())) {
                        errorMessage.add("列名[" + excelProperty.value()[0] + "]:" + length.message());
                    }
                } else if (annotation instanceof Min) {
                    min = (Min) annotation;
                    if (Objects.nonNull(object) && Long.parseLong(object.toString()) < min.value()) {
                        errorMessage.add("列名[" + excelProperty.value()[0] + "]:" + min.message());
                    }
                } else if (annotation instanceof Max) {
                    max = (Max) annotation;
                    if (Objects.nonNull(object) && Long.parseLong(object.toString()) > max.value()) {
                        errorMessage.add("列名[" + excelProperty.value()[0] + "]:" + max.message());
                    }
                } else if (annotation instanceof DecimalMin) {
                    decimalMin = (DecimalMin) annotation;
                    if (Objects.nonNull(object) && new BigDecimal(object.toString()).compareTo(new BigDecimal(decimalMin.value())) == -1) {
                        errorMessage.add("列名[" + excelProperty.value()[0] + "]:" + decimalMin.message());
                    }
                } else if (annotation instanceof DecimalMax) {
                    decimalMax = (DecimalMax) annotation;
                    if (Objects.nonNull(object) && new BigDecimal(object.toString()).compareTo(new BigDecimal(decimalMax.value())) == 1) {
                        errorMessage.add("列名[" + excelProperty.value()[0] + "]:" + decimalMax.message());
                    }
                } else if (annotation instanceof NotNull) {
                    notNull = (NotNull) annotation;
                    if (Objects.isNull(object)) {
                        errorMessage.add("列名[" + excelProperty.value()[0] + "]:" + notNull.message());
                    }
                } else if (annotation instanceof Null) {
                    isnull = (Null) annotation;
                    if (Objects.nonNull(object)) {
                        errorMessage.add("列名[" + excelProperty.value()[0] + "]:" + isnull.message());
                    }
                } else if (annotation instanceof NotEmpty) {
                    notEmpty = (NotEmpty) annotation;
                    if (Objects.isNull(object)) {
                        errorMessage.add("列名[" + excelProperty.value()[0] + "]:" + notEmpty.message());
                    }
                } else if (annotation instanceof Size) {
                    size = (Size) annotation;
                    if (Objects.nonNull(object) && (object.toString().length() < size.min() || object.toString().length() > size.max())) {
                        errorMessage.add("列名[" + excelProperty.value()[0] + "]:" + size.message());
                    }
                } else if (annotation instanceof Range) {
                    range = (Range) annotation;
                    if (Objects.nonNull(object) && (Long.parseLong(object.toString()) < range.min() || Long.parseLong(object.toString()) > range.max())) {
                        errorMessage.add("列名[" + excelProperty.value()[0] + "]:" + range.message());
                    }
                } else if (annotation instanceof AssertTrue) {
                    assertTrue = (AssertTrue) annotation;
                    if (Objects.nonNull(object) && Boolean.valueOf(object.toString())) {
                        errorMessage.add("列名[" + excelProperty.value()[0] + "]:" + assertTrue.message());
                    }
                } else if (annotation instanceof AssertFalse) {
                    assertFalse = (AssertFalse) annotation;
                    if (Objects.nonNull(object) && !Boolean.valueOf(object.toString())) {
                        errorMessage.add("列名[" + excelProperty.value()[0] + "]:" + assertFalse.message());
                    }
                }
            }
            if (errorMessage.size() > 0) {
                extendBasicExcelField(o, analysisContext, errorMessage);
            }
        }
        return errorMessage.size() > 0 ? true : false;
    }

    //可重写的方法：
//	void invoke(T data, AnalysisContext context); //处理一行数据
//	void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) //处理表头的一行数据
//	void extra(CellExtra extra, AnalysisContext context); //获取单元格的额外信息
//	void doAfterAllAnalysed(AnalysisContext context) //全部读取结束后的操作
//	boolean hasNext(AnalysisContext context); //是否读取下一行
//	void onException(Exception exception, AnalysisContext context) //发生异常时调用
}
