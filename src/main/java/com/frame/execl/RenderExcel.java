package com.frame.execl;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.frame.utils.Fun;
import com.jfinal.render.Render;

public class RenderExcel extends Render{
	public static final Logger logger = LoggerFactory.getLogger(RenderExcel.class);
	public static final String EXCEL_TYPE = "application/vnd.ms-excel";
	private List<String> titleList;
	private List<?> dataList;
	private String fileName;
	private String sheetName;


	public RenderExcel(List<String> titleList, List<?> dataList) {
		this.titleList = titleList;
		this.dataList = dataList;
	}
	@Override
	public void render() {
		ServletOutputStream out = null;
		fileName = Fun.eqNull(fileName) ? UUID.randomUUID().toString() + ".xls" : fileName;
		sheetName = Fun.eqNull(sheetName) ? "report" : sheetName;
		try {
			response.setContentType(EXCEL_TYPE);
	        response.setHeader("Content-Disposition", (new StringBuilder("attachment; filename=\"")).append(new String(fileName.getBytes(), "ISO8859-1")).append("\"").toString());
			setDisableCacheHeader(response);
			HSSFWorkbook workbook = new ExcelExportUtils().exportExcel(sheetName, titleList, dataList);
			out = response.getOutputStream();
			workbook.write(out);
			out.flush();

		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(out != null){
				try {
					out.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * 设置禁止客户端缓存的Header.
	 */
	public static void setDisableCacheHeader(HttpServletResponse response) {
		//Http 1.0 header
		response.setDateHeader("Expires", 1L);
		response.addHeader("Pragma", "no-cache");
		//Http 1.1 header
		response.setHeader("Cache-Control", "no-cache, no-store, max-age=0");
	}
	public List<?> getDataList() {
		return dataList;
	}
	public void setDataList(List<?> dataList) {
		this.dataList = dataList;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getSheetName() {
		return sheetName;
	}
	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}
	public static String getExcelType() {
		return EXCEL_TYPE;
	}
	public List<String> getTitleList() {
		return titleList;
	}
	public void setTitleList(List<String> titleList) {
		this.titleList = titleList;
	}

}
