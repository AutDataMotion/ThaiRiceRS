package thairice.mvc.t7pdt_data;

import com.platform.constant.ConstantRender;
import com.platform.mvc.base.BaseController;
import com.platform.mvc.base.BaseModel;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.plugin.activerecord.Page;

import thairice.constant.ConstantInitMy;
import thairice.entity.ResultEntity;
import thairice.mvc.t3user.T3user;
import thairice.mvc.t6org_data.T6org_data;
import thairice.utils.DataConstants;


/**
 * XXX 管理	
 * 描述：
 * 
 * /jf/thairice/t7pdt_data
 * /jf/thairice/t7pdt_data/save
 * /jf/thairice/t7pdt_data/edit
 * /jf/thairice/t7pdt_data/update
 * /jf/thairice/t7pdt_data/view
 * /jf/thairice/t7pdt_data/delete
 * /thairice/t7pdt_data/add.html
 * 
 */
//@Controller(controllerKey = "/jf/thairice/t7pdt_data")
public class T7pdt_dataController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger LOG = Logger.getLogger(T7pdt_dataController.class);

	public static final String pthc = "/jf/thairice/t7pdt_data/";
	public static final String pthv = "/thairice/t7pdt_data/";

	/**
	 * 列表
	 */
	public void index() {
		String flag  = "";// 1原始数据查询 2产品数据查询 9失败
		try{
			setAttr("path", "/ui/thairice/");
			// 处理结果
			ResultEntity res = null;
			// 取页面请求参数
//			// 数据类型
			String datatype = getPara("datatype", "").trim();
			flag = getPara("flag", "").trim();
			// 数据状态
			String status = getPara("status", "").trim();
			// 数据采集开始
			String collectTimeSt = getPara("starttime", "").trim();
			// 数据采集结束
			String collectTimeEd = getPara("endtime", "").trim();
			// 默认查询第一个sheet
			if (StringUtils.isBlank(flag))
				flag = "2";
			// 返回页面上次查询的条件参数
			setAttr("flag", flag);
			setAttr("starttime2", collectTimeSt);
			setAttr("endtime2", collectTimeEd);
			setAttr("datatype_eng", DataConstants.PDT_DATA_TYPE.get(datatype));
			setAttr("datatype", datatype);
			// 返回翻页查询条件参数
			String urlParas = "?datatype=" + datatype + "&flag=" + flag + "&starttime=" + collectTimeSt + "&endtime=" + collectTimeEd;
			setAttr("urlParas", urlParas);
			// 拼接动态sql语句
			String sql = "from t7pdt_data t where 1=1 ";
			if (!StringUtils.isBlank(datatype) && (!"00".equals(datatype))) {
				sql += (" and t.type_ = '" + datatype + "'");
				setAttr("selected2" + datatype, "selected='selected'");
			}
			if (!StringUtils.isBlank(collectTimeSt)) {
				sql += (" and date_format(t.collect_time ,'%Y-%m-%d' ) >= '" + collectTimeSt + "'");
			}
			if (!StringUtils.isBlank(collectTimeEd)) {
				sql += (" and date_format(t.collect_time ,'%Y-%m-%d' ) <= '" + collectTimeEd + "'");
			}
			Page pageT6 = T6org_data.dao.paginate(getParaToInt(0, 1), 10,
					"select t.name_, FORMAT(t.size_/(1000*1000), 1) as size_, date_format(t.dload_end_time ,'%Y-%m-%d %H:%m:%s') as dload_end_time,t.row_column, "
							+ "(CASE t.type_ " + "when '01' then 'NDVI_1'\r\n" + "when '02' then 'NDVI_02'\r\n"
							+ "when '03' then 'LST'\r\n" + "when '04' then 'CLASS'\r\n" + "when '05' then 'LANDSAT'"
							+ " ELSE '' END) AS type_, " + "(case t.status_ " + "when '01' then 'Download success'\r\n"
							+ "when '02' then 'Download failed'\r\n" + "when '03' then 'Downloading'\r\n"
							+ "when '04' then 'Process success'\r\n" + "when '05' then 'Process failed'\r\n"
							+ "when '06' then 'Processing'\r\n" + "when '07' then 'Not download'\r\n"
							+ " else '' end) as status_, date_format(t.collect_time ,'%Y-%m-%d' ) as collect_time, t.id ",
					"from t6org_data t order by t.collect_time");
			Page pageT7 = T7pdt_data.dao.paginate(getParaToInt(0, 1), 10, 
					"select t.id,t.type_, "
					+ "(case t.type_  when '01' then 'Area monitoring'\r\n"
							+ "when '02' then 'Drought monitoring'\r\n" 
							+ "when '03' then 'Production estimates'\r\n"
							+ "when '04' then 'Condition monitoring'\r\n" 
							+ " else '' end) as type_,"
					+ "t.sample_path, t.product_path,date_format(t.collect_time ,'%Y-%m-%d' ) as collect_time, date_format(t.generate_time ,'%Y-%m-%d %H:%m:%s') as generate_time, t.zone_code, t.fail_code",
					sql + " order by t.collect_time");
			setAttr("T6orgDataPage", pageT6);
			setAttr("T7pdtDataPage", pageT7);
			/*
			 * res = new ResultEntity("0001"); LOG.debug(res.getDesc()); renderJson(res);
			 */
			setAttr("page_head", "Data management");
			setAttr("tab01_active", "");
			setAttr("tab02_active", "active");
			setAttr("data01_content_fade", "tab-pane fade");
			setAttr("data02_content_fade", "tab-pane fade in active");
			renderWithPath("/adm2018/data_management.html");
		} catch (Exception e) {
			setAttr("flag", "9");
			LOG.error("查询数据产品信息发生异常:" + e);
			renderWithPath("/adm2018/data_management.html");
		}
	}
	
	/**
	 * 批量删除
	 */
	public void batchDelete() {
		// 处理结果
		ResultEntity res = null;
		try {
			String[] ds = getParaValues("dataSet");
			for(String id : ds) {
				T7pdt_data.dao.deleteById(id);
			}
			LOG.debug("批量删除产品数据文件正常结束");
			res = new ResultEntity("0000");
			renderJson(res);
		} catch (Exception e) {
			res = new ResultEntity("0015");
			renderJson(res);
			LOG.error("批量删除产品数据文件发生异常：" + e);
		}
	}
	
	/**
	 * 保存
	 */
	@Before(T7pdt_dataValidator.class)
	public void save() {
		T7pdt_data t7pdt_data = getModel(T7pdt_data.class);
		//other set 
		
		//t7pdt_data.save();		//guiid
		t7pdt_data.saveGenIntId();	//serial int id
		renderWithPath(pthv+"add.html");
	}
	
	/**
	 * 准备更新
	 */
	public void edit() {
		//T7pdt_data t7pdt_data = T7pdt_data.dao.findById(getPara());	//guuid
		T7pdt_data t7pdt_data = T7pdt_dataService.service.SelectById(getParaToInt());		//serial int id
		setAttr("t7pdt_data", t7pdt_data);
		renderWithPath(pthv+"update.html");

	}
	
	/**
	 * 更新
	 */
	@Before(T7pdt_dataValidator.class)
	public void update() {
		getModel(T7pdt_data.class).update();
		redirect(pthc);
	}

	/**
	 * 查看
	 */
	public void view() {
		//T7pdt_data t7pdt_data = T7pdt_data.dao.findById(getPara());	//guuid
		T7pdt_data t7pdt_data = T7pdt_dataService.service.SelectById(getParaToInt());		//serial int id
		setAttr("t7pdt_data", t7pdt_data);
		renderWithPath(pthv+"view.html");
	}
	
	/**
	 * 删除
	 */
	public void delete() {
		//T7pdt_dataService.service.delete("t7pdt_data", getPara() == null ? ids : getPara());	//guuid
		T7pdt_dataService.service.deleteById("t7pdt_data", getPara() == null ? ids : getPara());	//serial int id
		redirect(pthc);
	}
	
	public void setViewPath(){
		setAttr(ConstantRender.PATH_CTL_NAME, pthc);
		setAttr(ConstantRender.PATH_VIEW_NAME, pthv);
	}
	@Clear
	public void get_pdt_data_year()
	{
		T3user user = getSessionAttr("user");
		Timestamp startTime = user.getPrdt_EfDt();
		Timestamp endTime = user.getPD_ExDat();
		
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
		String startTimeStr = sdf.format(startTime);  //用户选择的服务起始时间
		String endTimeStr = sdf.format(endTime);  //用户选择的服务结束时间
		
		String provinceCode = getPara("provinceCode", "").trim();
		String productKind_code = getPara("productKind_code", "").trim();
		List<T7pdt_data> t7pdt_data = T7pdt_dataService.service.SelectByZone_code(provinceCode,productKind_code,startTimeStr,endTimeStr);
		renderJson(t7pdt_data);
	}
	@Clear
	public void get_pdt_data_year_month()
	{
		T3user user = getSessionAttr("user");
		Timestamp startTime = user.getPrdt_EfDt();
		Timestamp endTime = user.getPD_ExDat();
		
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
		String startTimeStr = sdf.format(startTime);  //用户选择的服务起始时间
		String endTimeStr = sdf.format(endTime);  //用户选择的服务结束时间
		
		String provinceCode = getPara("provinceCode", "").trim();
		String productKind_code = getPara("productKind_code", "").trim();
		String year =  getPara("year", "").trim();
		
		List<T7pdt_data> t7pdt_data = T7pdt_dataService.service.SelectByZone_code_year(provinceCode,productKind_code,startTimeStr,endTimeStr,year);
		renderJson(t7pdt_data);
	}
	@Clear
	public void get_pdt_data_year_month_day()
	{
		T3user user = getSessionAttr("user");
		Timestamp startTime = user.getPrdt_EfDt();
		Timestamp endTime = user.getPD_ExDat();
		
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
		String startTimeStr = sdf.format(startTime);  //用户选择的服务起始时间
		String endTimeStr = sdf.format(endTime);  //用户选择的服务结束时间
		
		String provinceCode = getPara("provinceCode", "").trim();
		String productKind_code = getPara("productKind_code", "").trim();
		String year =  getPara("year", "").trim();
		String month =  getPara("month", "").trim();
		
		List<T7pdt_data> t7pdt_data = T7pdt_dataService.service.SelectByZone_code_year_month(provinceCode,productKind_code,startTimeStr,endTimeStr,year,month);
		renderJson(t7pdt_data);
	}
}
