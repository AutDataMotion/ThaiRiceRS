package thairice.mvc.t10pdt_report;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.aop.Duang;
import com.jfinal.plugin.activerecord.Page;
import com.platform.constant.ConstantRender;
import com.platform.mvc.base.BaseController;

import csuduc.platform.util.ReportUtil;
import thairice.mvc.t3user.T3user;
import thairice.mvc.t3user.T3userService;


/**
 * XXX 管理	
 * 描述：
 * 
 * /jf/thairice/t10pdt_report
 * /jf/thairice/t10pdt_report/save
 * /jf/thairice/t10pdt_report/edit
 * /jf/thairice/t10pdt_report/update
 * /jf/thairice/t10pdt_report/view
 * /jf/thairice/t10pdt_report/delete
 * /thairice/t10pdt_report/add.html
 * 
 */
//@Controller(controllerKey = "/jf/thairice/t10pdt_report")
public class T10pdt_reportController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(T10pdt_reportController.class);

	public static final String pthc = "/jf/thairice/t10pdt_report/";
	public static final String pthv = "/thairice/t10pdt_report/";
	//数据产品的存储
//	public static final String productDataPath = "E:/thairiceproduct/";
	//arcgisServer的工作路径
//	public static final String arcgisserver_shp_workspacePath = "E:/arcgisserver_shp_workspace/";

	public String m_reportType = null;//01-doc 02-pdf
	private static final thairice.mvc.t3user.T3userService srv = Duang.duang(T3userService.class);
	public enum m_ProductKind {  
		Area("Area", "01"), Growth("Growth", "02"), Yield("Yield", "03"), Drought("Drought", "04");  
	    // 成员变量  
	    private String name;  
	    private String index;  
	    // 构造方法  
	    private m_ProductKind(String name, String index) {  
	        this.name = name;  
	        this.index = index;  
	    }  
	    // 普通方法  
	    public static String getName(String index) {  
	        for (m_ProductKind c : m_ProductKind.values()) {  
	            if (c.getIndex().equals(index)) {  
	                return c.name;  
	            }  
	        }  
	        return null;  
	    }
	    public static String getIndex(String name) {  
	        for (m_ProductKind c : m_ProductKind.values()) {  
	            if (c.getName().equals(name)) {  
	                return c.index;  
	            }  
	        }  
	        return null;  
	    }  
	    // get set 方法  
	    public String getName() {  
	        return name;  
	    }  
	    public void setName(String name) {  
	        this.name = name;  
	    }  
	    public String getIndex() {  
	        return index;  
	    }  
	    public void setIndex(String index) {  
	        this.index = index;  
	    }  
	} 
	
	/**
	 * 列表
	 */
	@Clear
	public void index() {
		//paging(ConstantInitMy.db_dataSource_main, splitPage, BaseModel.sqlId_splitPage_select, T10pdt_report.sqlId_splitPage_from);
		//renderWithPath(pthv+"list.html");
		//renderWithPath(pthv+"FeatureLayer.html");
		T3user user = getSessionAttr("user");
		String[]ad = null;
		
		if(null != user) {
			ad=user.getStr("area").split(" ");
			setAttr("province", ad[0]);
			setAttr("city", ad[1]);
			setAttr("area", ad[2]);
			setAttr("user", srv.SelectById(user.getBigInteger("id")));
			setAttr("count", srv.getCount(user.getBigInteger("id")));
		}
		Page page  = T10pdt_report.dao.paginate(getParaToInt(0, 1), 10, "select t.start_time, t.end_time, t.zone_code,(case t.pdt_type \r\n" + 
				"when '01' then 'Area monitoring' \r\n" + 
				"when '02' then 'Growth monitoring'\r\n" + 
				"when '03' then 'Estimated production'\r\n" + 
				"when '04' then 'Drought monitoring'\r\n" + 
				"else ''\r\n" + 
				"end) as pdt_type, t.suffix", "from T10pdt_report order by id asc");
		setAttr("blogPage",page );
		setAttr("PersonInfoOrMyreport", 1);
		setAttr("queryAllParm", "active");
		renderWithPath("/f/t3user/self_center.html");
	}
	
	/**
	 * 保存
	 */
	/*
	@Before(T10pdt_reportValidator.class)
	public void save() {
		T10pdt_report t10pdt_report = getModel(T10pdt_report.class);
		//other set 
//		t10pdt_report.setUserid(new BigInteger("000000"));
//		
//		t10pdt_report.setAdd_time(Timestamp.valueOf("2018-03-18 00:00:00"));
//		t10pdt_report.setCollect_time(Timestamp.valueOf("2018-03-19 00:00:00"));
//		t10pdt_report.setZone_code(72);
//		t10pdt_report.setCrop_type("01");
//		t10pdt_report.setPdt_type("03");
		//t10pdt_report.save();		//guiid
		t10pdt_report.saveGenIntId();	//serial int id
		renderWithPath(pthv+"add.html");
	}
	*/
	@Clear
	public boolean saveReport2Database(T10pdt_report model)
	{
		T10pdt_report t10pdt_report = getModel(T10pdt_report.class);
		t10pdt_report.setAttrs(model);
		return t10pdt_report.saveGenIntId();	//serial int id
	}
	/**
	 * 准备更新
	 */
	public void edit() {
		//T10pdt_report t10pdt_report = T10pdt_report.dao.findById(getPara());	//guuid
		T10pdt_report t10pdt_report = T10pdt_reportService.service.SelectById(getParaToInt());		//serial int id
		setAttr("t10pdt_report", t10pdt_report);
		setAttr("queryAllParm", "active");
		renderWithPath(pthv+"update.html");

	}
	
	/**
	 * 更新
	 */
	@Before(T10pdt_reportValidator.class)
	public void update() {
		getModel(T10pdt_report.class).update();
		redirect(pthc);
	}

	/**
	 * 查看
	 */
	public void view() {
		//T10pdt_report t10pdt_report = T10pdt_report.dao.findById(getPara());	//guuid
		T10pdt_report t10pdt_report = T10pdt_reportService.service.SelectById(getParaToInt());		//serial int id
		setAttr("t10pdt_report", t10pdt_report);
		setAttr("queryAllParm", "active");
		renderWithPath(pthv+"view.html");
	}
	
	/**
	 * 删除
	 */
	@Clear
	public void delete() {
		//T10pdt_reportService.service.delete("t10pdt_report", getPara() == null ? ids : getPara());	//guuid
//		T10pdt_reportService.service.deleteById("t10pdt_report", getPara() == null ? ids : getPara());	//serial int id
//		redirect(pthc);
		String id = getPara("id");
		if(ReportUtil.deleteReportFileByFileUrl(getCxt(),getPara("filepath")))//删除文件
		{
			if(T10pdt_report.dao.deleteById(getPara("id") == null ? ids : id))//删除数据库记录
			{
				redirect(pthc);
			}
		}
//		if(T10pdt_report.dao.deleteById(getPara() == null ? ids : getPara()))
//		{
//			String filepath = getPara("filepath");
//		}
		
	}
	
	public void setViewPath(){
		setAttr(ConstantRender.PATH_CTL_NAME, pthc);
		setAttr(ConstantRender.PATH_VIEW_NAME, pthv);
	}
	
	@Clear
	public void generateRoprt() {
		String userID = getPara("userID");
		String ProductKind = getPara("ProductKind");
		String productDate = getPara("productDate");
		String areaCode = getPara("areaCode");
		
		String reprotType = getPara("reprotType");
		String mapPicUrl = getPara("mapPicUrl");
		String legendPic_str = getPara("legendPic_str");
		String staPic_str = getPara("staPic_str");
		
		String staData = getPara("staData");
		
		Map<Object,Object> dataMap = new HashMap<Object,Object>();
		
		if(ProductKind.equals("Area"))
		{
			List<ReportUtil.Area_Yield> Arealist = new ArrayList<ReportUtil.Area_Yield>();
			Arealist = ReportUtil.getArea_Yield(staData);
			dataMap.put("Arealist", Arealist);
		}
		if(ProductKind.equals("Yield"))
		{
			List<ReportUtil.Area_Yield> Yieldlist = new ArrayList<ReportUtil.Area_Yield>();
			Yieldlist = ReportUtil.getArea_Yield(staData);
			dataMap.put("Yieldlist", Yieldlist);
		}
		if(ProductKind.equals("Drought"))
		{
			List<ReportUtil.Drought> Droughtlist = new ArrayList<ReportUtil.Drought>();
			Droughtlist = ReportUtil.getDrought(staData);
			dataMap.put("Droughtlist", Droughtlist);
			
		}
		//System.out.println(staData);
		//System.out.println(Arealist.get(0).getName());
		//System.out.println(Arealist.get(0).getValue());
		//InputStream inputStream = ReportUtil.getInputStreamByGet(mapPicUrl);
		//String mapPic_str = ReportUtil.getImageStrByGet(mapPicUrl);
		//
		
	    dataMap.put("titleKind", ProductKind);
	    //dataMap.put("description", "test");
	    dataMap.put("District", areaCode);
	    
	    
	    Date day = new Date();
	    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String nowTime = df.format(day);
	      
	    dataMap.put("Time", productDate); 
	    //dataMap.put("bywho", "lyf"); 
	    //dataMap.put("mapPic", mapPic_str);  
//	    dataMap.put("legendPic", legendPic_str); 
//	    dataMap.put("staPic", staPic_str); 
	    
		String mapPic_str = null;
		
		String reportUrl = null;
		String exportResult = null;
		if(reprotType.equals("doc"))
		{
			m_reportType = "01";
			
			String temImg = ReportUtil.generateTempMapImage(mapPicUrl,"maptemp.png",userID,ProductKind);
			mapPic_str = ReportUtil.getImageStr(temImg);
			dataMap.put("mapPic", mapPic_str);
			
			dataMap.put("legendPic", legendPic_str); 
		    dataMap.put("staPic", staPic_str); 
		    
		    exportResult = ReportUtil.exportSimpleWord(dataMap,userID,nowTime,ProductKind,areaCode,productDate);
			
		}
		if(reprotType.equals("pdf"))
		{
			m_reportType = "02";
			
			mapPic_str = mapPicUrl;
			dataMap.put("mapPic", mapPic_str);
			
			//String tempImgPath = ReportUtil.getWebContentPath()+"files/productReport/pdf/";
			
			if(ReportUtil.generateImage(legendPic_str,"legendPic.png",userID,ProductKind))
			{
				dataMap.put("legendPic", "legendPic.png"); 
			   
			}
			if(ReportUtil.generateImage(staPic_str,"staPic.png",userID,ProductKind))
			{
				dataMap.put("staPic", "staPic.png"); 
			}
			
			
			
			exportResult = ReportUtil.export2Pdf(dataMap,userID,nowTime,ProductKind,areaCode,productDate);
			
		}
		
		if(exportResult!=null)
		{
			T10pdt_report t10pdt_report = getModel(T10pdt_report.class);
			
			t10pdt_report.setUserid(new BigInteger(userID));
			t10pdt_report.setAdd_time(Timestamp.valueOf(nowTime));
			t10pdt_report.setCollect_time(Timestamp.valueOf(productDate+" 00:00:00"));
			t10pdt_report.setZone_code(Integer.valueOf(areaCode));
			t10pdt_report.setCrop_type("01");
			t10pdt_report.setPdt_type(m_ProductKind.getIndex(ProductKind));
			t10pdt_report.setSuffix(m_reportType);
			reportUrl = getCxt()+exportResult;
			t10pdt_report.setDownload_path(reportUrl);
			
			if(saveReport2Database(t10pdt_report))//保存到数据库
			{
				setAttr("reportUrl", reportUrl);
				renderJson();
			}
			
			
			//redirect(pthc+"save");
		}
		else
		{
			redirect(getCxt()+"/view/common/404.html");
		}
		
		
	    //String reportUrl = getCxt()+ReportUtil.exportSimpleWord(dataMap,"","");
//	    String reportUrl = getCxt()+ReportUtil.export2Pdf(dataMap,"","");
	    
	    //File file = new File("E:/mapPic.png");
	    //ReportUtil.saveData(inputStream, file);
		//String legendPic_str = legendPicUrl.substring(legendPicUrl.indexOf(",")+1);
	    //ReportUtil.generateImage(legendPic_str,"E:/legendPic.png");
		
		//String staPic_str = staPicUrl.substring(staPicUrl.indexOf(",")+1);
	    //ReportUtil.generateImage(staPic_str,"E:/staPic.png");
		
//		System.out.println(mapPicUrl);
//		System.out.println(legendPic_str);
//		System.out.println(staPic_str);
		//exportSimpleWord(staPicUrl);
//		String cxt = getCxt();
//		setAttr("cxt", cxt);
		
	}
	@Clear
	public void CopyProductData2Workspace()
	{
		String productKind = getPara("productKind");
		String productDate = getPara("productDate");
		String prov_code = getPara("prov_code");
		boolean result = ReportUtil.getProductDataAndCopy2Workspace(productKind,productDate,prov_code);
		setAttr("result",result);
		renderJson();
	}
	
}
