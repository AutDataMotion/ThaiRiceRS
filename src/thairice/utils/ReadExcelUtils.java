package thairice.utils;
import java.io.FileInputStream;  
import java.io.FileNotFoundException;  
import java.io.IOException;  
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;  
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;  
import org.apache.poi.ss.usermodel.Cell;  
import org.apache.poi.ss.usermodel.DateUtil;  
import org.apache.poi.ss.usermodel.Row;  
import org.apache.poi.ss.usermodel.Sheet;  
import org.apache.poi.ss.usermodel.Workbook;  
import org.apache.poi.xssf.usermodel.XSSFWorkbook;  
import org.slf4j.Logger;  
import org.slf4j.LoggerFactory;  
  
/** 
 * 读取Excel 
 *  
 * @author zengwendong 
 */  
public class ReadExcelUtils {  
    private Logger logger = LoggerFactory.getLogger(ReadExcelUtils.class);  
    private Workbook wb;  
    private Sheet sheet;  
    private Row row;  
  
    public ReadExcelUtils(String filepath) {  
        if(filepath==null){  
            return;  
        }  
        String ext = filepath.substring(filepath.lastIndexOf("."));  
        try {  
            InputStream is = new FileInputStream(filepath);  
            if(".xls".equals(ext)){  
                wb = new HSSFWorkbook(is);  
            }else if(".xlsx".equals(ext)){  
                wb = new XSSFWorkbook(is);  
            }else{  
                wb=null;  
            }  
        } catch (FileNotFoundException e) {  
            logger.error("FileNotFoundException", e);  
        } catch (IOException e) {  
            logger.error("IOException", e);  
        }  
    }  
      
    /** 
     * 读取Excel表格表头的内容 
     *  
     * @param InputStream 
     * @return String 表头内容的数组 
     * @author zengwendong 
     */  
    public String[] readExcelTitle() throws Exception{  
        if(wb==null){  
            throw new Exception("Workbook对象为空！");  
        }  
        sheet = wb.getSheetAt(0);  
        row = sheet.getRow(0);  
        // 标题总列数  
        int colNum = row.getPhysicalNumberOfCells();  
        System.out.println("colNum:" + colNum);  
        String[] title = new String[colNum];  
        for (int i = 0; i < colNum; i++) {  
            // title[i] = getStringCellValue(row.getCell((short) i));  
            title[i] = row.getCell(i).getCellFormula();  
        }  
        return title;  
    }  
  
    /** 
     * 读取Excel数据内容 
     *  
     * @param InputStream 
     * @return Map 包含单元格数据内容的Map对象 
     * @author zengwendong 
     */  
    public Map<Integer, Map<Integer,Object>> readExcelContent() throws Exception{  
        if(wb==null){  
            throw new Exception("Workbook对象为空！");  
        }  
        Map<Integer, Map<Integer,Object>> content = new HashMap<Integer, Map<Integer,Object>>();  
          
        sheet = wb.getSheetAt(0);  
        // 得到总行数  
        int rowNum = sheet.getLastRowNum();  
        row = sheet.getRow(0);  
        int colNum = row.getPhysicalNumberOfCells();  
        // 正文内容应该从第二行开始,第一行为表头的标题  
        for (int i = 1; i <= rowNum; i++) {  
            row = sheet.getRow(i);  
            int j = 0;  
            Map<Integer,Object> cellValue = new HashMap<Integer, Object>();  
            while (j < colNum) {  
                Object obj = getCellFormatValue(row.getCell(j));  
                cellValue.put(j, obj);  
                j++;  
            }  
            content.put(i, cellValue);  
        }  
        return content;  
    }  
  
    /** 
     *  
     * 根据Cell类型设置数据 
     *  
     * @param cell 
     * @return 
     * @author zengwendong 
     */  
    private Object getCellFormatValue(Cell cell) {  
        Object cellvalue = "";  
        if (cell != null) {  
            // 判断当前Cell的Type  
            switch (cell.getCellType()) {  
            case Cell.CELL_TYPE_NUMERIC:// 如果当前Cell的Type为NUMERIC  
            case Cell.CELL_TYPE_FORMULA: {  
                // 判断当前的cell是否为Date  
                if (DateUtil.isCellDateFormatted(cell)) {  
                    // 如果是Date类型则，转化为Data格式  
                    // data格式是带时分秒的：2013-7-10 0:00:00  
                    // cellvalue = cell.getDateCellValue().toLocaleString();  
                    // data格式是不带带时分秒的：2013-7-10  
                    Date date = cell.getDateCellValue();  
                    cellvalue = date;  
                } else {// 如果是纯数字  
  
                    // 取得当前Cell的数值  
                    cellvalue = String.valueOf(cell.getNumericCellValue());  
                }  
                break;  
            }  
            case Cell.CELL_TYPE_STRING:// 如果当前Cell的Type为STRING  
                // 取得当前的Cell字符串  
                cellvalue = cell.getRichStringCellValue().getString();  
                break;  
            default:// 默认的Cell值  
                cellvalue = "";  
            }  
        } else {  
            cellvalue = "";  
        }  
        return cellvalue;  
    }  
  
    public static void main(String[] args) {  
        try {  
            String filepath = "D:\\DUC\\泰国农业遥感v20180108\\数据库设计20180505_V1.5.xlsx";  
            ReadExcelUtils excelReader = new ReadExcelUtils(filepath);  
            // 对读取Excel表格标题测试  
              
            // 对读取Excel表格内容测试  
            Map<Integer, Map<Integer,Object>> map = excelReader.readExcelContent();  
            System.out.println("获得Excel表格的内容:");  
            String preTblNm = "";
            String currTblNm = "";
            String preTblNmcN = "";
            String currTblNmcN = "";
            String crtTblSql = "";
    		String unicPk = "";
            for (int i = 1; i <= map.size(); i++) { 
            	Map<Integer,Object> eleMap = map.get(i);
//            	for (int j = 0; j <= eleMap.size(); j++) {
            		currTblNm = (String) eleMap.get(1);
            		currTblNmcN = (String) eleMap.get(2);
            		// 字段
            		String currColumnEng = (String) eleMap.get(9);
            		String currColumnType = (String) eleMap.get(8);
            		String currColumnChg = (String) eleMap.get(7);
            		String currColumnNull = (String) eleMap.get(6);
            		String currColumnPk = (String) eleMap.get(5);
            		String isUnique = (String) eleMap.get(4);
            		isUnique = isUnique.equalsIgnoreCase("Y")? " UNIQUE " : "";
            		String isAutoIncrease = (String) eleMap.get(3);
            		isAutoIncrease = isAutoIncrease.equalsIgnoreCase("Y")? " AUTO_INCREMENT " : "";
            		currColumnNull = currColumnNull.equalsIgnoreCase("Y")? " NOT NULL " : "";

            		if(!preTblNm.equals(currTblNm)) {
            			if(!StringUtils.isBlank(preTblNm)) {//非第一次，结束上一个表的建表语句
            				// 主键的处理
            	            if(!StringUtils.isBlank(unicPk)) {// 有主键
                	            unicPk = (String) unicPk.subSequence(0, unicPk.length() -1);
            	            	unicPk = "\tPRIMARY KEY(" + unicPk + ")\r\n";
            	            	crtTblSql += unicPk;
            	            } else {
            	            	crtTblSql = crtTblSql.substring(0, crtTblSql.length() - 3);
            	            	crtTblSql += "\r\n";
            	            }
            	            crtTblSql += (")\r\nCOMMENT='" + preTblNmcN + "',");
            				crtTblSql += "\r\nENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;";         
            			}
                		unicPk = "";
            			preTblNm = currTblNm;
            			preTblNmcN = currTblNmcN;
            			crtTblSql += ("\r\n\r\nDROP TABLE IF EXISTS " +  currTblNm + ";\r\nCREATE TABLE " + currTblNm + " (\r\n");
//                   		crtTblSql += ("\t" + currColumnEng + " " + currColumnType + " "+ currColumnNull + " COMMENT '" + currColumnChg + "'");
            		} 
            		if(currColumnPk.equalsIgnoreCase("Y")) {
            			unicPk += (currColumnEng + ",");
            		}
            			crtTblSql += ( "\t" + currColumnEng + " " + currColumnType + " "+ isUnique + currColumnNull + isAutoIncrease + " COMMENT'" + currColumnChg + "'" + ",\r\n");
//            	}
            } 
			// 主键的处理
            if(!StringUtils.isBlank(unicPk)) {// 有主键
                unicPk = (String) unicPk.subSequence(0, unicPk.length() -1);
            	unicPk = "\tPRIMARY KEY(" + unicPk + ")\r\n";
            	crtTblSql += unicPk;
            } else {
            	crtTblSql = crtTblSql.substring(0, crtTblSql.length() - 3);
            	crtTblSql += "\r\n";
            }
            //
            crtTblSql += (")\r\nCOMMENT='" + currTblNmcN + "',");
			crtTblSql += "\r\nENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;";   
        	System.out.println(crtTblSql);
            
//            generate();
        } catch (FileNotFoundException e) {  
            System.out.println("未找到指定路径的文件!");  
            e.printStackTrace();  
        }catch (Exception e) {  
            e.printStackTrace();  
        }  
    } 
    
    /** 
     *  
     * 根据Cell类型设置数据 
     *  
     * @param cell 
     * @return 
     * @author zengwendong 
     */  
    private static void generate() {  
        try {  
            String filepath = "d:\\333.xlsx";  
            ReadExcelUtils excelReader = new ReadExcelUtils(filepath);  
            // 对读取Excel表格标题测试  
//          String[] title = excelReader.readExcelTitle();  
//          System.out.println("获得Excel表格的标题:");  
//          for (String s : title) {  
//              System.out.print(s + " ");  
//          }  
              
            // 对读取Excel表格内容测试  
            Map<Integer, Map<Integer,Object>> map = excelReader.readExcelContent();  
            System.out.println("获得Excel表格的内容:");  
            List<String> lines = new ArrayList<String>();
            String function = "";
            String url = "";
            for (int i = 1; i <= map.size(); i++) {
            	String line = (String) map.get(i).get(0);
            	if(1 == i) {
            		url = line;
            		function = line.substring(line.lastIndexOf("/") + 1);
            	} else {
            		lines.add(line);
            	}
//                System.out.println(map.get(i));
            }
            List<String> contents = new ArrayList<String>();
            contents.add("<script type=\"text/javascript\">");
            contents.add("function " + function + "(flag){	");
            contents.add("    	$.ajax({\r\n" + 
            		"    		  type: 'POST',");
            contents.add("url: '" + url + "',");          
            contents.add("    		  data: {flag:flag},\r\n" + 
            		"    		  success: function(res){\r\n" + 
            		"    			if(res.code == \"0000\"){");
            contents.add("");
            contents.add("");
            contents.add("");
            for(int i = 0; i < contents.size(); i ++ )
            	System.out.println(contents.get(i));
        } catch (FileNotFoundException e) {  
            System.out.println("未找到指定路径的文件!");  
            e.printStackTrace();  
        }catch (Exception e) {  
            e.printStackTrace();  
        }  
    }
}  

