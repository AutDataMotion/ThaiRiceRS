package thairice.mvc.t13region;

import com.platform.constant.ConstantRender;
import com.platform.mvc.base.BaseController;
import com.platform.mvc.base.BaseModel;

import java.util.List;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;

import thairice.constant.ConstantInitMy;


/**
 * XXX 管理	
 * 描述：
 * 
 * /jf/thairice/t13Region
 * /jf/thairice/t13Region/save
 * /jf/thairice/t13Region/edit
 * /jf/thairice/t13Region/update
 * /jf/thairice/t13Region/view
 * /jf/thairice/t13Region/delete
 * /thairice/t13Region/add.html
 * 
 */
//@Controller(controllerKey = "/jf/thairice/t13Region")
public class T13RegionController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(T13RegionController.class);

	public static final String pthc = "/jf/thairice/t13Region/";
	public static final String pthv = "/thairice/t13Region/";

	/**
	 * 列表
	 */
	public void index() {
		paging(ConstantInitMy.db_dataSource_main, splitPage, BaseModel.sqlId_splitPage_select, T13Region.sqlId_splitPage_from);
		renderWithPath(pthv+"list.html");
	}
	
	/**
	 * 保存
	 */
	@Before(T13RegionValidator.class)
	public void save() {
		T13Region t13Region = getModel(T13Region.class);
		//other set 
		
		//t13Region.save();		//guiid
		t13Region.saveGenIntId();	//serial int id
		renderWithPath(pthv+"add.html");
	}
	
	/**
	 * 准备更新
	 */
	public void edit() {
		//T13Region t13Region = T13Region.dao.findById(getPara());	//guuid
		T13Region t13Region = T13RegionService.service.SelectById(getParaToInt());		//serial int id
		setAttr("t13Region", t13Region);
		renderWithPath(pthv+"update.html");

	}
	
	/**
	 * 更新
	 */
	@Before(T13RegionValidator.class)
	public void update() {
		getModel(T13Region.class).update();
		redirect(pthc);
	}

	/**
	 * 查看
	 */
	public void view() {
		//T13Region t13Region = T13Region.dao.findById(getPara());	//guuid
		T13Region t13Region = T13RegionService.service.SelectById(getParaToInt());		//serial int id
		setAttr("t13Region", t13Region);
		renderWithPath(pthv+"view.html");
	}
	
	/**
	 * 删除
	 */
	public void delete() {
		//T13RegionService.service.delete("t13region", getPara() == null ? ids : getPara());	//guuid
		T13RegionService.service.deleteById("t13region", getPara() == null ? ids : getPara());	//serial int id
		redirect(pthc);
	}
	
	public void setViewPath(){
		setAttr(ConstantRender.PATH_CTL_NAME, pthc);
		setAttr(ConstantRender.PATH_VIEW_NAME, pthv);
	}
	@Clear
	public void getChildAreaCodeAndNameByParentAreaCode()
	{
		int parentAreaCode = getParaToInt("parentareaCode");
		List<T13Region> Regions = null;
		if(String.valueOf(parentAreaCode).length()>4)
		{
			Regions = T13RegionService.service.SelectById__Custom(parentAreaCode);		//serial int id
		}
		else {
			Regions = T13RegionService.service.SelectByparentId(parentAreaCode);		//serial int id
		}
		
		boolean flag = false;
		
		JSONObject jsonObject = new JSONObject();
		
		if(Regions.size()>0)
		{
			flag = true;
			JSONArray childRegions = new JSONArray();
			for(int i = 0;i<Regions.size();i++)
			{
				JSONObject childObject = new JSONObject();
				childObject.put("NAME", Regions.get(i).getName().toString());
				childObject.put("CODE", Regions.get(i).getId().toString());
				childRegions.add(childObject);
			}
			jsonObject.put("childRegions", childRegions);
		}
		else {
			flag = false;
		}
		jsonObject.put("flag", flag);
		//jsonObject.put("childRegions", flag);
		renderJson(jsonObject);
	}
}
