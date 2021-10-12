package com.example.excel.utils;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


@Slf4j
public class ExcelUtil {
    private static Sheet initSheet;

    static {
        initSheet = new Sheet(1, 0);
        initSheet.setSheetName("sheet");
        // set the auto width
        initSheet.setAutoWidth(Boolean.TRUE);
    }

    public static void downLoadExcel(String fileName, HttpServletResponse response, Workbook workbook) {
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/octet-stream;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    // read the data less than 1000 rows
    public static List<Object> readLessThan1000Row(String filePath) {
        return readLessThan1000RowBySheet(filePath, null);
    }

    /**
     * headLineMun the row begins to read, default is 0
     *
     * @param filePath the absolute path
     * @param sheet    the page of sheet, the default 1
     * @return
     */
    private static List<Object> readLessThan1000RowBySheet(String filePath, Sheet sheet) {
        if (!StringUtils.hasText(filePath)) {
            return null;
        }
        sheet = sheet != null ? sheet : initSheet;
        InputStream fileStream = null;
        try {
            fileStream = new FileInputStream(filePath);
            return EasyExcelFactory.read(fileStream, sheet);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            try {
                if (fileStream != null) {
                    fileStream.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    public static List<Object> readMoreThan1000RowBySheet(String filePath) {
        return readMoreThan1000RowBySheet(filePath, null);
    }

    public static List<Object> readMoreThan1000RowBySheet(String filePath, Sheet sheet) {
        if (!StringUtils.hasText(filePath)) {
            return null;
        }
        sheet = sheet != null ? sheet : initSheet;
        InputStream fileStream = null;
        try {
            fileStream = new FileInputStream(filePath);
            ExcelListener excelListener = new ExcelListener();
            EasyExcelFactory.readBySax(fileStream, sheet, excelListener);
            return excelListener.getDatas();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            try {
                if (fileStream != null) {
                    fileStream.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    public static List<Object> readMoreThan1000RowBySheet(InputStream inputStream, Sheet sheet) {
        sheet = sheet != null ? sheet : initSheet;
        ExcelListener excelListener = new ExcelListener();
        EasyExcelFactory.readBySax(inputStream, sheet, excelListener);
        return excelListener.getDatas();
    }

    /**
     * generate excel
     *
     * @param filePath absolute path
     * @param data data source
     * @param head table head
     */
    public static void writeBySimple(String filePath, List<List<Object>> data, List<String> head) {
        writeBySimple(filePath, data, head, null);
    }

    public static void writeBySimple(String filePath, List<List<Object>> data, List<String> head, Sheet sheet) {
        sheet = sheet != null ? sheet : initSheet;

    }

    /**
     * each time analyse a row will use invoke
     * after the whole analyse return doAfterAllAnalysed method
     */
    public static class ExcelListener extends AnalysisEventListener {

        private List<Object> datas = new ArrayList<>();

        public List<Object> getDatas() {
            return datas;
        }

        @Override
        public void invoke(Object o, AnalysisContext analysisContext) {
            if (o != null) {
                datas.add(o);
            }
        }

        @Override
        public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        }
    }
}
