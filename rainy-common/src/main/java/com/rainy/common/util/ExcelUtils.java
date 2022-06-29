package com.rainy.common.util;

import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import org.springframework.http.HttpHeaders;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * excel 导出工具类
 *
 * @author renguangli
 * @date 2022/3/17 15:08
 */
public class ExcelUtils {

    private static final String EXCEL_CONTENT_TYPE = "application/vnd.ms-excel;charset=utf-8";
    private static final String CONTENT_DISPOSITION_VALUE = "attachment;filename={}";


    public static <T> void export(HttpServletResponse response, List<T> records, String fileName) throws IOException {
        response.setContentType(EXCEL_CONTENT_TYPE);
        // utf-8 编码
        String fn = new String(fileName.getBytes(StandardCharsets.UTF_8));
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, StrUtil.format(CONTENT_DISPOSITION_VALUE, fn));
        try (ExcelWriter writer = ExcelUtil.getWriter(); ServletOutputStream out = response.getOutputStream()) {
            writer.write(records, true);
            writer.flush(out, true);
        }
    }

}
