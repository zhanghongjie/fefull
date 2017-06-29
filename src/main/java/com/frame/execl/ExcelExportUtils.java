package com.frame.execl;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;

import com.frame.execl.text.TextRender;
import com.frame.execl.text.VelocityTextRender;
import com.frame.utils.Fun;


/**
 * execl导出工具类
 *
 * @author zhanghj
 *
 * @param <T>
 */
public class ExcelExportUtils<T> {

private HSSFWorkbook workbook;

	private HSSFCellStyle headStyle;

	private HSSFCellStyle bodyStyle;

	private HSSFCellStyle bodyStyle4Number;

	public ExcelExportUtils(){
		this.workbook = new HSSFWorkbook();

		this.headStyle = workbook.createCellStyle();
		this.headStyle = ExcelStyle.setHeadStyle(workbook, headStyle);

		this.bodyStyle = workbook.createCellStyle();
		this.bodyStyle = ExcelStyle.setBodyStyle(workbook, bodyStyle);

		this.bodyStyle4Number = workbook.createCellStyle();
		this.bodyStyle4Number = ExcelStyle.setBodyStyle4Number(workbook, bodyStyle4Number);
	}

	/**
	 * 功能说明: 入参判断
	 * @param sheetName
	 * @param dataset
	 * @param classes
	 * @return
	 * @throws Exception
	 */
	public HSSFWorkbook exportExcel(String sheetName, List<T> dataset,
			Class<?> classes,Object[] argsArr) {
		Field filed[] = null;
		if (Fun.eqNull(sheetName)){
			sheetName = "report";
		}
		if(null != classes){
			filed = classes.getDeclaredFields();
		}else{
			Iterator<T> its = dataset.iterator();
			T ts = (T) its.next();
		    filed = ts.getClass().getDeclaredFields();
		}

		return this.exportExcel(sheetName, dataset, filed, argsArr);
	}

	/**
	 * 标题、内容导出
	 * @param sheetName
	 * @param titleList
	 * @param dataList
	 * @return
	 */
	public HSSFWorkbook exportExcel(String sheetName, List<String> titleList, List<List<T>> dataList){
		try {
			HSSFSheet sheet = null;
			// 添加工作表
			int sheetContentCount = 0;
			int sheetNum = 1;
            if ((dataList.size() > ExportSettingContants.SHEET_DATA_NUM)) {
                if (dataList.size() % ExportSettingContants.SHEET_DATA_NUM == 0) {
                    sheetNum = dataList.size() / ExportSettingContants.SHEET_DATA_NUM;
                }
                else {
                    sheetNum = dataList.size() / ExportSettingContants.SHEET_DATA_NUM + 1;
                }
            }
            // 遍历产生sheet
			for (int s = 0; s < sheetNum; s++) {
				sheet = workbook.createSheet(sheetName + "_" + s);
				sheet.setDefaultColumnWidth(18); //设置默认列宽
                // 添加工作表标题
    			HSSFRow row = sheet.createRow(0);
    			for (int i = 0; i < titleList.size(); i++) {
    				HSSFCell cell = row.createCell(i);
    				cell.setCellStyle(headStyle);
    				HSSFRichTextString text = new HSSFRichTextString(titleList.get(i));
    				cell.setCellValue(text);
    			}
    			// 添加工作表内容
    			for (int j = 0; j < ExportSettingContants.SHEET_DATA_NUM; j++) {
    				if (sheetContentCount == dataList.size()) {
                        break;
                    }
    				row = sheet.createRow(j + 1); //第一行为标题行, 从第二行开始写内容
    				List<T> rowList = (List<T>) dataList.get(j + ExportSettingContants.SHEET_DATA_NUM*s);
    				for (int k = 0; k < rowList.size(); k++) {
    					createBodyCell(workbook, row, k, (null==rowList.get(k))?"":rowList.get(k));
    				}
    				sheetContentCount++;
    			}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return workbook;
	}

	/**
	 * 功能说明: 导出Excel
	 * @param sheetName
	 * @param dataset
	 * @param filed
	 * @return
	 */
	@SuppressWarnings("all")
	private HSSFWorkbook exportExcel(String sheetName, List<T> dataset,
			Field filed[], Object[] argsArr) {
		try {
			HSSFSheet sheet = null;

			HSSFCellStyle headStyle = workbook.createCellStyle();
			headStyle = ExcelStyle.setHeadStyle(workbook, headStyle);

			TextRender textRender = new VelocityTextRender();
			Map<String, Object> context = new HashMap<String, Object>();
			context.put(ExportSettingContants.ARGS_STRING, argsArr);

			List<Object[]> exportMetas = new ArrayList<Object[]>();
			for (int i = 0; i < filed.length; i++) {
				Field f = filed[i];
				ExcelAnnotation exa = f.getAnnotation(ExcelAnnotation.class);
				if (exa != null) {
					String _exportName = exa.exportName();
					String _pattern = exa.pattern();
					Integer _order = Integer.valueOf(exa.order());
					boolean _isSensitive = exa.isSensitive();
					boolean _isMerged = exa.isMerged();
					// 判断标题是否有变量
					if(null != argsArr && argsArr.length > 0 && _exportName.indexOf(ExportSettingContants.ARGS_STRING) != -1 ){
						_exportName = textRender.render(_exportName, context);
					}
					// 添加到标题List
					exportMetas.add(new Object[] { f.getName(), _exportName,
							_pattern, _order, _isSensitive, _isMerged });
				}
			}
			Collections.sort(exportMetas, new Comparator<Object[]>() {
				/** 根据元注释order 排列顺序 */
				public int compare(Object[] o1, Object[] o2) {
					Integer order1 = (Integer) o1[3];
					Integer order2 = (Integer) o2[3];
					return order1.compareTo(order2);
				}
			});
			 // 需合并的展示列
            List mergeCellIndexList = new ArrayList<Integer>();
            for (int i = 0; i < exportMetas.size(); i++) {
				if((Boolean)exportMetas.get(i)[5]){
					mergeCellIndexList.add(i);
				}
            }
			// 添加工作表
			int sheetContentCount = 0;
			int sheetNum = 1;
            if ((dataset.size() > ExportSettingContants.SHEET_DATA_NUM)) {
                if (dataset.size() % ExportSettingContants.SHEET_DATA_NUM == 0) {
                    sheetNum = dataset.size() / ExportSettingContants.SHEET_DATA_NUM;
                }
                else {
                    sheetNum = dataset.size() / ExportSettingContants.SHEET_DATA_NUM + 1;
                }
            }
            // 遍历产生sheet
			for (int s = 0; s < sheetNum; s++) {
				sheet = workbook.createSheet(sheetName + "_" + s);
				sheet.setDefaultColumnWidth(18); //设置默认列宽
                // 添加工作表标题
    			HSSFRow row = sheet.createRow(0);
    			for (int i = 0; i < exportMetas.size(); i++) {
    				HSSFCell cell = row.createCell(i);
    				cell.setCellStyle(headStyle);
    				HSSFRichTextString text = new HSSFRichTextString((String) exportMetas.get(i)[1]);
    				cell.setCellValue(text);
    			}
    			// 添加工作表内容
    			for (int j = 0; j < ExportSettingContants.SHEET_DATA_NUM; j++) {
    				if (sheetContentCount == dataset.size()) {
                        break;
                    }
    				row = sheet.createRow(j + 1); //第一行为标题行, 从第二行开始写内容
    				T t = (T) dataset.get(j + ExportSettingContants.SHEET_DATA_NUM*s);
    				for (int k = 0; k < exportMetas.size(); k++) {
    					HSSFCell cell = row.createCell(k);
    					Object value = this.getObject(t,(String) exportMetas.get(k)[0]);
    					Object fValue = this.getValue(value, exportMetas.get(k));
    					createBodyCell(workbook, row, k, fValue);
    				}
    				sheetContentCount++;
    			}
    			//合并单元格操作
    			if(mergeCellIndexList.size() > 0){
    				mergedRegion(mergeCellIndexList,sheet);
    			}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return workbook;
	}

	/**
	 * 功能说明:反射获取bean属性值
	 *
	 * @param value
	 * @param meta
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	private Object getObject(T t,String filedName) throws Exception{
		String getMethodName = "get"+ filedName.substring(0, 1).toUpperCase() + filedName.substring(1);
		Class<?> tCls = t.getClass();
		Method getMethod = tCls.getMethod(getMethodName,new Class[] {});
		Object value = getMethod.invoke(t, new Object[] {});
		return value;
	}

	/**
	 * 功能说明: 类型对应值转化
	 *
	 * @param value
	 * @param meta
	 * @return
	 */
	private Object getValue(Object value, Object[] meta) {
		if (value == null)
			return "";
		if (value instanceof Boolean) {
			return (Boolean)value ? "是" : "否";
		} else if (value instanceof Date) {
			String pattern = (String) meta[2];
			if (StringUtils.isBlank(pattern))
				pattern = "yyyy-MM-dd";
			return new SimpleDateFormat(pattern).format((Date) value);
		} else {
			return (Boolean)meta[4] ? value.toString().replaceAll("\\S", "*") : value;
		}
	}

	/**
	 * 功能说明: 合并单元格
	 * @param mergeCellIndexList
	 * @param sheet
	 */
	@SuppressWarnings("all")
	private void mergedRegion(List mergeCellIndexList,HSSFSheet sheet){
		int startRowIndex = sheet.getFirstRowNum()+1,
			  endRowIndex = sheet.getFirstRowNum()+1;
		for (int m = sheet.getFirstRowNum()+1; m <= sheet.getLastRowNum(); m++) {
			String preCellVal = sheet.getRow(m).getCell((Integer)mergeCellIndexList.get(0)).getStringCellValue();//目前以待合并的某个列值相同作为合并条件,其余需合并列一并处理
			int n = (m < sheet.getLastRowNum()) ? m+1 : m;
			String nextCellVal = sheet.getRow(n).getCell((Integer)mergeCellIndexList.get(0)).getStringCellValue();
			if(m!=n && preCellVal.equals(nextCellVal)){
				endRowIndex = sheet.getRow(n).getRowNum();
			}else{
				for (int l = 0; l < mergeCellIndexList.size(); l++) {
					sheet.addMergedRegion(new CellRangeAddress(startRowIndex, endRowIndex, (Integer)mergeCellIndexList.get(l), (Integer)mergeCellIndexList.get(l)));
				}
				startRowIndex = endRowIndex + 1;
				endRowIndex = endRowIndex + 1;
			}
		}
	}

	/**
	 * 创建单元格
	 * @param row 行对象
	 * @param col 列号
	 * @param value 值
	 * @param style 单元格样式
	 */
	private void createBodyCell(HSSFWorkbook workbook, HSSFRow row, int col, Object value) {
		HSSFCell cell = null;
		cell = row.createCell(col);

		if(value instanceof String){
			cell.setCellValue((String)value);
			cell.setCellStyle(bodyStyle);
		}else if(value instanceof Date){
			cell.setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((Date) value));
			cell.setCellStyle(bodyStyle);
		}else if(value instanceof Calendar){
			cell.setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((Calendar) value));
			cell.setCellStyle(bodyStyle);
		}else if(value instanceof Integer){
			cell.setCellValue((Integer)value);
			cell.setCellStyle(bodyStyle4Number);
		}else if(value instanceof Long){
			cell.setCellValue((Long)value);
			cell.setCellStyle(bodyStyle4Number);
		}else if(value instanceof Float){
			cell.setCellValue((Float)value);
			cell.setCellStyle(bodyStyle4Number);
		}else if(value instanceof Double){
			cell.setCellValue((Double)value);
			cell.setCellStyle(bodyStyle4Number);
		}else if(value instanceof BigDecimal){
			cell.setCellValue(((BigDecimal) value).doubleValue());
			cell.setCellStyle(bodyStyle4Number);
		}
	}

}