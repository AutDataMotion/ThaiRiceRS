<<<<<<< HEAD
package thairice.mvc.t3user;


import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;



public class ExportService{
	
	public static ExportService service = new ExportService();

	/**
	 * 导出数据
	 * @param response
	 * @param request
	 * @param records
	 */
	public <M extends Model<M>> void export(String name, String tb,HttpServletResponse response, HttpServletRequest request,  List<M> records) {
		List<ExcelExportUtil.Pair> titles = new ArrayList<ExcelExportUtil.Pair>();
		List<Record> list = Db.find("show full columns from "+tb);
		for(Record r : list) {
			String value = r.get("Comment");
			String key = r.get("Field");
			
			titles.add(new ExcelExportUtil.Pair(key, value));
		}
		ExcelExportUtil.exportByRecord(response, request, name, titles , records);
	}
	/**
	 * 自定义导出数据
	 * @param response
	 * @param request
	 * @param records
	 */
	public <M extends Model<M>> void exportDiy(List<ExcelExportUtil.Pair> titles,String name, String tb,HttpServletResponse response, HttpServletRequest request,  List<M> records) {
	
		ExcelExportUtil.exportByRecord(response, request, name, titles , records);
	}
}
=======
package thairice.mvc.t3user;


import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;



public class ExportService{
	
	public static ExportService service = new ExportService();

	/**
	 * 导出数据
	 * @param response
	 * @param request
	 * @param records
	 */
	public <M extends Model<M>> void export(String name, String tb,HttpServletResponse response, HttpServletRequest request,  List<M> records) {
		List<ExcelExportUtil.Pair> titles = new ArrayList<ExcelExportUtil.Pair>();
		List<Record> list = Db.find("show full columns from "+tb);
		for(Record r : list) {
			String value = r.get("Comment");
			String key = r.get("Field");
			
			titles.add(new ExcelExportUtil.Pair(key, value));
		}
		ExcelExportUtil.exportByRecord(response, request, name, titles , records);
	}
}
>>>>>>> master
