package com.wl.study.business.util;

import com.wl.study.business.annotation.ExcelImportTempleteVaild;
import com.wl.study.business.annotation.ExcelNote;
import com.wl.study.business.util.excel.ExcelCallback;
import com.wl.study.business.util.excel.ExcelError;
import com.wl.study.business.util.excel.ExcelWriter;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.LinkedMultiValueMap;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description: excel util
 * @Param:
 * @return:
 * @Author: wangliang
 * @Date: 2020/4/19
 */
public class ExcelUtil {
    private final static Logger log = LoggerFactory.getLogger(ExcelUtil.class);
    private static final String DEFALUT_CONTENT_TYPE = "application/vnd.ms-excel";
    private static final String DEFALUT_EXT = ".xlsx";

    /**
     * excel多sheet
     *
     * @param fileName
     * @param sheetNames
     * @param dataClass
     * @param dataset
     * @param response
     */
    public static void excelExportBatchSheet(String fileName, List<String> sheetNames, List<Class<?>> dataClass, List<Collection<?>> dataset, HttpServletResponse response) throws Exception {
        log.debug("导出Excel开始...");
        response.setHeader("Content-Disposition", "inline;filename=" + URLEncoder.encode(fileName, "UTF-8") + DEFALUT_EXT);
        response.setContentType(DEFALUT_CONTENT_TYPE);
        ServletOutputStream outputStream = response.getOutputStream();

        ExcelWriter excelExporter = new ExcelWriter();
        for (int i = 0; i < dataClass.size(); i++) {
            Class clazz = dataClass.get(i);
            String sheetName = sheetNames.get(i);
            Collection collection = dataset.get(i);
            Sheet sheet = excelExporter.createSheet(clazz, sheetName);
            excelExporter.write(collection, sheet);
        }
        excelExporter.getWorkbook().write(outputStream);
        outputStream.flush();
        outputStream.close();
        log.debug("导出Excel结束");
    }

    /**
     * 导出excel
     *
     * @param fileName
     * @param dataClass
     * @param dataset
     * @param response
     * @throws Exception
     */
    public static void excelExport(String fileName, Class<?> dataClass,
                                   Collection<?> dataset, HttpServletResponse response) throws Exception {
        log.debug("导出Excel开始...");
        response.setHeader("Content-Disposition", "inline;filename=" + URLEncoder.encode(fileName, "UTF-8") + DEFALUT_EXT);
        response.setContentType(DEFALUT_CONTENT_TYPE);
        ServletOutputStream outputStream = response.getOutputStream();

        ExcelWriter excelExporter = new ExcelWriter(dataClass, "sheet1");
        excelExporter.write(dataset, outputStream);
        outputStream.flush();
        outputStream.close();
        log.debug("导出Excel结束");
    }

    /**
     * 生成excel
     *
     * @param dataClass
     * @param dataset
     * @param outputStream
     * @throws Exception
     */
    public static void excelMake(Class<?> dataClass, Collection<?> dataset, OutputStream outputStream) throws Exception {
        log.debug("生成Excel开始...");
        ExcelWriter excelExporter = new ExcelWriter(dataClass, "sheet1");
        excelExporter.write(dataset, outputStream);
        log.debug("生成Excel结束");
    }

    /**
     * excel读取
     *
     * @param inputStream
     * @param clazz
     * @param callback
     * @return
     * @throws NoSuchFieldException
     * @throws IOException
     */
    public static List<LinkedMultiValueMap<Integer, Object>> excelReader(InputStream inputStream, List<Class<?>> clazz, ExcelCallback callback) throws NoSuchFieldException, IOException {
        Object o = null;
        try {
            log.info("开始读取excel里的数据");
            long start = System.currentTimeMillis();
            //用流的方式先读取到你想要的excel的文件
            //获取整个excel
            XSSFWorkbook xb = new XSSFWorkbook(inputStream);
            int sheets = xb.getNumberOfSheets();
            List<LinkedMultiValueMap<Integer, Object>> finalOList = new ArrayList<>();
            List<LinkedMultiValueMap<Integer, String>> finalColumnNameList = new ArrayList<>();
            List<LinkedMultiValueMap<Integer, ExcelError>> finalExcelErrorList = new ArrayList<>();
            for (int y = 0; y < sheets; y++) {
                //获取第一个表单sheet
                XSSFSheet sheet = xb.getSheetAt(y);
                //获取最后一行
                int lastrow = sheet.getLastRowNum();
                //循环行数依次获取列数
                LinkedMultiValueMap<Integer, Object> oList = new LinkedMultiValueMap<>();
                LinkedMultiValueMap<Integer, String> columnNameList = new LinkedMultiValueMap<>();
                LinkedMultiValueMap<Integer, ExcelError> excelErrorList = new LinkedMultiValueMap<>();
                for (int i = 0; i < lastrow + 1; i++) {
                    //获取哪一行i
                    Row row = sheet.getRow(i);
                    if (Objects.nonNull(row)) {
                        //获取这一行的第一列
                        int firstcell = row.getFirstCellNum();//从第二行开始获取
                        //获取这一行的最后一列
                        int lastcell = row.getLastCellNum();
                        o = clazz.get(y).newInstance();
                        Field[] fields = o.getClass().getDeclaredFields();
                        ExcelImportTempleteVaild excelImportTempleteVaild = o.getClass().getDeclaredAnnotation(ExcelImportTempleteVaild.class);
                        if (Objects.nonNull(excelImportTempleteVaild) && excelImportTempleteVaild.value() && lastcell > fields.length) {
                            throw new Exception("导入文件和模版不一致");
                        }
                        boolean extend = fields[fields.length - 1].getName().contains("extendColumn");
                        for (int j = firstcell; j < lastcell; j++) {
                            //获取第j列
                            Cell cell = row.getCell(j);
                            if (i == 0) {
                                columnNameList.add(y, String.valueOf(cell));
                            } else {
                                if (Objects.nonNull(cell)) {
                                    Object obj = convert(cell);
                                    if (extend) {
                                        if (j < fields.length - 1) {
                                            fields[j].setAccessible(true);
                                            fields[j].set(o, obj);
                                            Annotation annotation = fields[j].getAnnotation(NotNull.class);
                                            ExcelNote note = fields[j].getAnnotation(ExcelNote.class);
                                            if (Objects.isNull(obj) && Objects.nonNull(annotation)) {
                                                excelErrorList.add(y, new ExcelError(j + 1, "excel第" + (y + 1) + "个sheet第" + (j + 1) + "行[" + note.value() + "]为空"));
                                            }
                                        } else {
                                            Field field = null;
                                            if (j == fields.length - 1) {
                                                field = fields[j];
                                            } else {
                                                field = fields[fields.length - 1];
                                            }
                                            field.setAccessible(true);
                                            Map map = (Map) field.get(o);
                                            if (Objects.isNull(map)) {
                                                map = new LinkedHashMap<>();
                                            }
                                            map.put(columnNameList.get(y).get(j), obj);
                                            field.set(o, map);
                                        }
                                    } else {
                                        fields[j].setAccessible(true);
                                        fields[j].set(o, obj);
                                        Annotation annotation = fields[j].getAnnotation(NotNull.class);
                                        ExcelNote note = fields[j].getAnnotation(ExcelNote.class);
                                        if (Objects.isNull(obj) && Objects.nonNull(annotation)) {
                                            excelErrorList.add(y, new ExcelError(i + 1, "excel第" + (y + 1) + "个sheet第" + (i + 1) + "行[" + note.value() + "]为空"));
                                        }
                                    }
                                }
                            }
                        }
                        if (i > 0) {
                            oList.add(y, o);
                        }
                    }
                }
                if (oList.size() == 0 && finalOList.size() == 0) {
                    throw new Exception("excel没有数据");
                }
                if (oList.size() > 0) {
                    finalOList.add(oList);
                    finalColumnNameList.add(columnNameList);
                    if (excelErrorList.size() > 0) {
                        finalExcelErrorList.add(excelErrorList);
                    }
                }
                log.info("读取了{}条数据", oList.get(y).size());
            }
            long end = System.currentTimeMillis();
            log.info("读取excel里的数据结束,============耗时============:{}秒", (end - start) / 1000);
            return callback.callback(finalOList, finalColumnNameList, finalExcelErrorList);
        } catch (Exception e) {
            log.error("请求出错:", e);
            if (e instanceof IllegalArgumentException) {
                String errorColumn = e.getMessage();
                if (errorColumn.indexOf("Can not set java.lang.String field") != -1 && errorColumn.indexOf("to java.lang.Long") != -1) {
                    String column = errorColumn.substring(errorColumn.indexOf("Can not set java.lang.String field") + 1, errorColumn.indexOf("to java.lang.Long"));
                    column = column.substring(column.lastIndexOf(".") + 1, column.length());
                    Field field = o.getClass().getDeclaredField(column.trim());
                    ExcelNote note = field.getAnnotation(ExcelNote.class);
                    throw new IllegalArgumentException("excel列[" + note.value() + "]为非文本格式");
                } else {
                    throw new RuntimeException(e.getMessage());
                }
            } else {
                throw new RuntimeException(e.getMessage());
            }
        } finally {
            if (Objects.nonNull(inputStream)) {
                inputStream.close();
            }
        }
    }

    /**
     * 类型转换
     *
     * @param cell
     * @return
     */
    public static Object convert(Cell cell) {
        switch (cell.getCellTypeEnum()) {
            case NUMERIC:
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    SimpleDateFormat sdf;
                    // 验证short值
                    if (cell.getCellStyle().getDataFormat() == 14) {
                        sdf = new SimpleDateFormat("yyyy/MM/dd");
                    } else if (cell.getCellStyle().getDataFormat() == 21) {
                        sdf = new SimpleDateFormat("HH:mm:ss");
                    } else if (cell.getCellStyle().getDataFormat() == 22) {
                        sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    } else {
                        throw new RuntimeException("日期格式错误!!!（请将excel单元格设置为文本格式或输入正确格式的日期）");
                    }
                    Date date = cell.getDateCellValue();
                    return sdf.format(date);
                } else {//处理数值格式
                    cell.setCellType(CellType.STRING);
                    return String.valueOf(cell.getRichStringCellValue().getString());
                }
            case STRING:
                return cell.getStringCellValue();
            case FORMULA:
                return cell.getCellFormula();
            case BOOLEAN:
                return cell.getBooleanCellValue();
            case ERROR:
                return cell.getErrorCellValue();
        }
        return null;
    }

    /**
     * 校验属性
     *
     * @param obj
     * @param index
     * @param sheetIndex
     * @return
     * @throws IllegalAccessException
     */
    public static List<ExcelError> checkExcelField(Object obj, int index, int sheetIndex) throws IllegalAccessException {
        List<ExcelError> excelErrorList = new ArrayList<>();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(true);
            Annotation annotation = field.getAnnotation(NotNull.class);
            ExcelNote note = field.getAnnotation(ExcelNote.class);
            if (Objects.isNull(field.get(obj)) && Objects.nonNull(annotation)) {
                excelErrorList.add(new ExcelError(index + 1, "excel第" + (sheetIndex + 1) + "个sheet第" + (index + 1) + "行[" + note.value() + "]为空"));
            }
        }
        return excelErrorList;
    }
}