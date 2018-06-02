package thairice.mvc.t6org_data;

import com.platform.constant.ConstantRender;
import com.platform.mvc.base.BaseController;
import com.platform.mvc.base.BaseModel;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Page;

import thairice.constant.ConstantInitMy;
import thairice.entity.ResultEntity;
import thairice.mvc.t7pdt_data.T7pdt_data;
import thairice.utils.DataConstants;


/**
 * XXX 管理	
 * 描述：
 * 
 * /jf/thairice/t6org_data
 * /jf/thairice/t6org_data/save
 * /jf/thairice/t6org_data/edit
 * /jf/thairice/t6org_data/update
 * /jf/thairice/t6org_data/view
 * /jf/thairice/t6org_data/delete
 * /thairice/t6org_data/add.html
 * 
 */
//@Controller(controllerKey = "/jf/thairice/t6org_data")
public class T6org_dataController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger LOG = Logger.getLogger(T6org_dataController.class);

	public static final String pthc = "/jf/thairice/t6org_data/";
	public static final String pthv = "/thairice/t6org_data/";

	/**
	 * 查询原始数据 zhuchaobin 2018-03-24
	 */
	public void index() {
		String flag  = "";// 1原始数据查询 2'面积'产品数据查询,3'长势'产品数据查询,4'估产'产品数据查询,5'分割'产品数据查询   9失败
		try{
		        setAttr("t6org_data", "active"); 
			setAttr("path", "/ui/thairice/");
			// 处理结果
			ResultEntity res = null;
			// 取页面请求参数
			// 数据类型
			String dataType = getPara("dataType", "").trim();
			flag = getPara("flag", "").trim();
			// 数据状态
			String status = getPara("status", "").trim();
			// 数据采集开始
			String collectTimeSt = getPara("starttime", "").trim();
			// 数据采集结束
			String collectTimeEd = getPara("endtime", "").trim();
			// 默认查询第一个sheet
			if (StringUtils.isBlank(flag))
				flag = "1";
			// 返回页面上次查询的条件参数
			setAttr("flag", flag);
			setAttr("starttime", collectTimeSt);
			setAttr("endtime", collectTimeEd);
			setAttr("status_eng", DataConstants.ORG_DATA_STATUS.get(status));
			setAttr("status", status);
			// 返回翻页查询条件参数
			String urlParas = "?status=" + status + "&flag=" + flag + "&starttime=" + collectTimeSt + "&endtime=" + collectTimeEd;
			setAttr("urlParas", urlParas);
			// 拼接动态sql语句
			String sql = "from t6org_data t where 1=1 ";
			if (!StringUtils.isBlank(status)) {
				sql += (" and t.status_ = '" + status + "'");
			}
			if (!StringUtils.isBlank(collectTimeSt)) {
				sql += (" and date_format(t.collect_time ,'%Y-%m-%d' ) >= '" + collectTimeSt + "'");
			}
			if (!StringUtils.isBlank(collectTimeEd)) {
				sql += (" and date_format(t.collect_time ,'%Y-%m-%d' ) <= '" + collectTimeEd + "'");
			}
			Page pageT6 = T6org_data.dao.paginate(getParaToInt(0, 1), 12,
					"select t.name_, FORMAT(t.size_/(1000*1000), 1) as size_, date_format(t.dload_end_time ,'%Y-%m-%d %H:%m:%s') as dload_end_time,t.row_column, "
							+ "(CASE t.type_ " + "when '01' then 'NDVI_1'\r\n" + "when '02' then 'NDVI_02'\r\n"
							+ "when '03' then 'LST'\r\n" + "when '04' then 'CLASS'\r\n" + "when '05' then 'LANDSAT'"
							+ " ELSE '' END) AS type_, " + "(case t.status_ " + "when '01' then 'Download success'\r\n"
							+ "when '02' then 'Download failed'\r\n" + "when '03' then 'Downloading'\r\n"
							+ "when '04' then 'Process success'\r\n" + "when '05' then 'Process failed'\r\n"
							+ "when '06' then 'Processing'\r\n" + "when '07' then 'Not download'\r\n"
							+ " else '' end) as status_, date_format(t.collect_time ,'%Y-%m-%d' ) as collect_time, t.id ",
					sql + " order by t.collect_time");
			Page pageT7 = T7pdt_data.dao.paginate(getParaToInt(0, 1), 12, "select *",
					" from t7pdt_data t order by t.collect_time");
			setAttr("T6orgDataPage", pageT6);
			setAttr("T7pdtDataPage", pageT7);
			/*
			 * res = new ResultEntity("0001"); LOG.debug(res.getDesc()); renderJson(res);
			 */
			setAttr("page_head", "Data management");
			setAttr("queryAllParm", "active");
			renderWithPath("/adm2018/data_management.html");
		} catch (Exception e) {
			setAttr("flag", "9");
			LOG.error("查询原始数据文件发生异常!" + e);
			setAttr("queryAllParm", "active");
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
				T6org_data.dao.deleteById(id);
			}
			LOG.debug("批量删除原始数据文件正常结束");
			res = new ResultEntity("0000");
			renderJson(res);
		} catch (Exception e) {
			res = new ResultEntity("0014");
			renderJson(res);
			LOG.error("批量删除原始数据文件发生异常：" + e);
		}
	}
	
	/**
	 * 保存
	 */
	@Before(T6org_dataValidator.class)
	public void save() {
		T6org_data t6org_data = getModel(T6org_data.class);
		//other set 
		
		//t6org_data.save();		//guiid
		t6org_data.saveGenIntId();	//serial int id
		setAttr("queryAllParm", "active");
		renderWithPath(pthv+"add.html");
	}
	
	/**
	 * 准备更新
	 */
	public void edit() {
		//T6org_data t6org_data = T6org_data.dao.findById(getPara());	//guuid
		T6org_data t6org_data = T6org_dataService.service.SelectById(getParaToInt());		//serial int id
		setAttr("t6org_data", t6org_data);
		setAttr("queryAllParm", "active");
		renderWithPath(pthv+"update.html");

	}
	
	/**
	 * 更新
	 */
	@Before(T6org_dataValidator.class)
	public void update() {
		getModel(T6org_data.class).update();
		redirect(pthc);
	}

	/**
	 * 查看
	 */
	public void view() {
		//T6org_data t6org_data = T6org_data.dao.findById(getPara());	//guuid
		T6org_data t6org_data = T6org_dataService.service.SelectById(getParaToInt());		//serial int id
		setAttr("t6org_data", t6org_data);
		setAttr("queryAllParm", "active");
		renderWithPath(pthv+"view.html");
	}
	
	/**
	 * 删除
	 */
	public void delete() {
		//T6org_dataService.service.delete("t6org_data", getPara() == null ? ids : getPara());	//guuid
		T6org_dataService.service.deleteById("t6org_data", getPara() == null ? ids : getPara());	//serial int id
		redirect(pthc);
	}
	
	public void setViewPath(){
		setAttr(ConstantRender.PATH_CTL_NAME, pthc);
		setAttr(ConstantRender.PATH_VIEW_NAME, pthv);
	}
	
}
