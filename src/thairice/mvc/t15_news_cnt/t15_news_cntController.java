package thairice.mvc.t15_news_cnt;

import com.platform.constant.ConstantRender;
import com.platform.mvc.base.BaseController;
import com.platform.mvc.base.BaseModel;

import java.math.BigInteger;
import java.util.List;

import org.apache.log4j.Logger;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Page;

import thairice.constant.ConstantInitMy;
import thairice.entity.ResultEntity;
import thairice.mvc.t16_pdt_sample.t16_pdt_sample;
import thairice.mvc.t1parameter.T1parameter;
import thairice.mvc.t2syslog.EnumT2sysLog;
import thairice.mvc.t2syslog.T2syslogService;
import thairice.mvc.t3user.T3user;
import thairice.utils.ParamUtils;


/**
 * XXX 管理	
 * 描述：
 * 
 * /jf/thairice/t15_news_cnt
 * /jf/thairice/t15_news_cnt/save
 * /jf/thairice/t15_news_cnt/edit
 * /jf/thairice/t15_news_cnt/update
 * /jf/thairice/t15_news_cnt/view
 * /jf/thairice/t15_news_cnt/delete
 * /thairice/t15_news_cnt/add.html
 * 
 */
//@Controller(controllerKey = "/jf/thairice/t15_news_cnt")
public class t15_news_cntController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger LOG = Logger.getLogger(t15_news_cntController.class);

	public static final String pthc = "/jf/thairice/t15_news_cnt/";
	public static final String pthv = "/thairice/t15_news_cnt/";

	/**
	 * 列表
	 */
	public void index() {
/*		paging(ConstantInitMy.db_dataSource_main, splitPage, BaseModel.sqlId_splitPage_select, t15_news_cnt.sqlId_splitPage_from);
		renderWithPath(pthv+"list.html");*/		
		String sql = "select t.title, t.content, date_format(t.editTime ,'%Y/%m/%d') as editTime from t15_news_cnt t order by t.rank asc ";
		List<t15_news_cnt> newsList = t15_news_cnt.dao.find(sql);
		if((newsList != null) && (newsList.size()>= 8)) {
			for(int i = 0; i <8; i ++) {
				setAttr("news" + (i+1), newsList.get(i).getTitle());
				LOG.debug(newsList.get(i).getTitle());
			}
		}
		// 产品信息展示
		Page pageT16  = t16_pdt_sample.dao.paginate(getParaToInt(0, 1), 10, "select * from t16_pdt_sample t order by t.id asc ", "");
		// 联系我们展示
		String address = ParamUtils.getParam("10000005", "001");
		String telphone = ParamUtils.getParam("10000005", "002");
		String mobile = ParamUtils.getParam("10000005", "003");
		String email = ParamUtils.getParam("10000005", "004");
		//		log.info("查询报表返回结果" + JSON.toJSONString(page));

		setAttr("pageT16",pageT16);
		setAttr("address",address);
		setAttr("telphone",telphone);
		setAttr("mobile",mobile);
		setAttr("email",email);
		setAttr("t15_news_cnt", "active");
		renderWithPath("/adm2018/news_management.html");
	}
	
	/**
	 * 保存
	 */
	@Before(t15_news_cntValidator.class)
	public void save() {
		// 处理结果
		ResultEntity res = null;
		try {
/*				String news1 = getPara("news1");
				String news2 = getPara("news2");
				String news3 = getPara("news3");
				String news4 = getPara("news4");
				String news5 = getPara("news5");
				String news6 = getPara("news6");
				String news7 = getPara("news7");
				String news8 = getPara("news8");*/
				
				String sql = "select t.title, t.content, date_format(t.editTime ,'%Y/%m/%d') as editTime from t15_news_cnt t order by t.rank asc ";
				List<t15_news_cnt> newsList = t15_news_cnt.dao.find(sql);
				if((newsList != null) && (newsList.size() > 0)) {
					for(int i = 0; i < newsList.size(); i ++) {
						String newsTitle = getPara("news" + i);
						LOG.info("newsTitle=" + newsTitle);
						LOG.info("newsList.get(i).getTitle()=" + newsList.get(i).getTitle());
						if(!newsList.get(i).getTitle().equals(newsTitle)) {
							t15_news_cnt newsCnt = new t15_news_cnt();
							newsCnt.setNewsid(new Long((long)i));
							newsCnt.setTitle(newsTitle);
							newsCnt.update();
						}
					}
				}
				
/*				t15_news_cnt newsCnt = new t15_news_cnt();
				newsCnt.setNewsid(1l);
				newsCnt.setTitle(news1);
				newsCnt.update();
				newsCnt.setNewsid(2l);
				newsCnt.setTitle(news2);
				newsCnt.update();
				newsCnt.setNewsid(3l);
				newsCnt.setTitle(news3);
				newsCnt.update();
				newsCnt.setNewsid(4l);
				newsCnt.setTitle(news4);
				newsCnt.update();
				newsCnt.setNewsid(5l);
				newsCnt.setTitle(news5);
				newsCnt.update();
				newsCnt.setNewsid(6l);
				newsCnt.setTitle(news6);
				newsCnt.update();
				newsCnt.setNewsid(7l);
				newsCnt.setTitle(news7);
				newsCnt.update();
				newsCnt.setNewsid(8l);
				newsCnt.setTitle(news8);
				newsCnt.update();*/

				LOG.debug("更新新闻成功");
				res = new ResultEntity("0000");
				// 写入日志
		 //       T3user user = getSessionAttr("user");
		 //       T2syslogService.addLog(EnumT2sysLog.INFO, user.getId(), user.getAccount(), "Update news content", "Successful");
				setAttr("t15_news_cnt", "active");
				renderJson(res);
				return;

		} catch (Exception e) {
			LOG.error("更新新闻发生异常");
			res = new ResultEntity("0016");
			// 写入日志
	     //   T3user user = getSessionAttr("user");
	     //   T2syslogService.addLog(EnumT2sysLog.INFO, user.getId(), user.getAccount(), "Update news content", res.getDesc());
			renderJson(res);
			return;
		}
	}
	
	/**
	 * 准备更新
	 */
	public void edit() {
		//t15_news_cnt t15_news_cnt = t15_news_cnt.dao.findById(getPara());	//guuid
		t15_news_cnt t15_news_cnt = t15_news_cntService.service.SelectById(getParaToInt());		//serial int id
		setAttr("t15_news_cnt", t15_news_cnt);
		renderWithPath(pthv+"update.html");

	}
	
	/**
	 * 更新
	 */
	@Before(t15_news_cntValidator.class)
	public void update() {
		getModel(t15_news_cnt.class).update();
		redirect(pthc);
	}

	/**
	 * 查看
	 */
	public void view() {
		//t15_news_cnt t15_news_cnt = t15_news_cnt.dao.findById(getPara());	//guuid
		t15_news_cnt t15_news_cnt = t15_news_cntService.service.SelectById(getParaToInt());		//serial int id
		setAttr("t15_news_cnt", t15_news_cnt);
		renderWithPath(pthv+"view.html");
	}
	
	/**
	 * 删除
	 */
	public void delete() {
		//t15_news_cntService.service.delete("t15_news_cnt", getPara() == null ? ids : getPara());	//guuid
		t15_news_cntService.service.deleteById("t15_news_cnt", getPara() == null ? ids : getPara());	//serial int id
		redirect(pthc);
	}
	
	public void setViewPath(){
		setAttr(ConstantRender.PATH_CTL_NAME, pthc);
		setAttr(ConstantRender.PATH_VIEW_NAME, pthv);
	}
	
}
