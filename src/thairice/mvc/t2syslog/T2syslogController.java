package thairice.mvc.t2syslog;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.apache.log4j.Logger;

import com.google.common.collect.Lists;
import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.plugin.cron4j.ITask;
import com.platform.constant.ConstantRender;
import com.platform.mvc.base.BaseController;

import thairice.interceptor.AdminLoginInterceptor;
import thairice.rpcjob.GrouthMonitorScheduleJob;
import thairice.rpcjob.JobStatusMdl;
import thairice.rpcjob.LandDroughtScheduleJob;
import thairice.rpcjob.LandYieldScheduleJob;
import thairice.rpcjob.PreProcessScheduleJob;

/**
 * XXX 管理 描述：
 * 
 * /jf/thairice/t2syslog /jf/thairice/t2syslog/save /jf/thairice/t2syslog/edit
 * /jf/thairice/t2syslog/update /jf/thairice/t2syslog/view
 * /jf/thairice/t2syslog/delete /thairice/t2syslog/add.html
 * 
 */
// @Controller(controllerKey = "/jf/thairice/t2syslog")
@Before(AdminLoginInterceptor.class)
public class T2syslogController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(T2syslogController.class);

	public static final String pthc = "/jf/thairice/t2syslog/";
	public static final String pthv = "/adm2018/t2syslog/";
	public static final String pthvm = "log_management.html";

	/**
	 * 列表 首次显示
	 */
	public void index() {
		// 提取前100条
		List<T2syslog> list = T2syslogService.service.SelectPage(0, 10);
		setAttr("list", list);
		setAttr("t2syslog", "active");
		renderWithPath(pthv + pthvm);
	}

	// 查询
	public void search() {
		// 查询数据
		renderJsonForTable(T2syslogService.service.SearchPage(getParamWithServerPage()));
	}

	/**
	 * 保存
	 */
	@Before(T2syslogValidator.class)
	public void save() {
		T2syslog t2syslog = getModel(T2syslog.class);
		// other set

		// t2syslog.save(); //guiid
		t2syslog.saveGenIntId(); // serial int id
		renderWithPath(pthv + "add.html");
	}

	/**
	 * 准备更新
	 */
	public void edit() {
		// T2syslog t2syslog = T2syslog.dao.findById(getPara()); //guuid
		T2syslog t2syslog = T2syslogService.service.SelectById(getParaToInt()); // serial
																				// int
																				// id
		setAttr("t2syslog", t2syslog);
		renderWithPath(pthv + "update.html");

	}

	/**
	 * 更新
	 */
	@Before(T2syslogValidator.class)
	public void update() {
		getModel(T2syslog.class).update();
		redirect(pthc);
	}

	/**
	 * 查看
	 */
	@Clear
	public void view() {
		// T2syslog t2syslog = T2syslog.dao.findById(getPara()); //guuid
		T2syslog t2syslog = T2syslogService.service.SelectById(getParaToInt()); // serial
																				// int
																				// id
		setAttr("t2syslog", t2syslog);
		renderWithPath(pthv + "view.html");
	}

	/**
	 * 删除
	 */
	public void delete() {
		// T2syslogService.service.delete("t2syslog", getPara() == null ? ids :
		// getPara()); //guuid
		T2syslogService.service.deleteById("t2syslog", getPara() == null ? ids : getPara()); // serial
																								// int
																								// id
		redirect(pthc);
	}

	public void setViewPath() {
		setAttr(ConstantRender.PATH_CTL_NAME, pthc);
		setAttr(ConstantRender.PATH_VIEW_NAME, pthv);
	}
	
	// ============算法处理状态
	public void  ajaxProcessStatus(){
		renderWithPath( "/adm2018/process_status.html");
		setAttr("ajaxProcessStatus", "active");
	}
	
	public void  ajaxProcessStatusData(){
		List<JobStatusMdl> processStatus = Arrays.asList(
				PreProcessScheduleJob.statusMdl
				, LandDroughtScheduleJob.statusMdl
				, GrouthMonitorScheduleJob.statusMdl
				, LandYieldScheduleJob.statusMdl);
		renderJson(processStatus);
	}
	
	public void mockProcessStatusData(){
		new Thread(()->{
			int cntAll = 10;
			int cntSuc = 8;
			int cntFail = 2;
			PreProcessScheduleJob.statusMdl.start(cntAll);
			LandDroughtScheduleJob.statusMdl.start(cntAll);
			GrouthMonitorScheduleJob.statusMdl.start(cntAll);
			LandYieldScheduleJob.statusMdl.start(cntAll);

			for (int i = 0; i < cntAll; i++) {
				if (i %2 == 0) {
					PreProcessScheduleJob.statusMdl.succOne();
					LandDroughtScheduleJob.statusMdl.succOne();
					GrouthMonitorScheduleJob.statusMdl.succOne();
					LandYieldScheduleJob.statusMdl.succOne();
				}else {
					PreProcessScheduleJob.statusMdl.failedOne();
					LandDroughtScheduleJob.statusMdl.failedOne();
					GrouthMonitorScheduleJob.statusMdl.failedOne();
					LandYieldScheduleJob.statusMdl.failedOne();
				}
				try {
					Thread.sleep(2*1000L);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			PreProcessScheduleJob.statusMdl.stop();
			LandDroughtScheduleJob.statusMdl.stop();
			GrouthMonitorScheduleJob.statusMdl.stop();
			LandYieldScheduleJob.statusMdl.stop();
			
		}).start();
	}
	
	// ==========job 调用
	private static PreProcessScheduleJob jobPre;
	private static GrouthMonitorScheduleJob jobGrowth;
	private static LandDroughtScheduleJob jobDrought;
	private static LandYieldScheduleJob jobYield;
	private static volatile int cntJobInvoke;
	public void runjob(){
		
		if (cntJobInvoke  > 10) {
			renderText("---");
			return ;
		}
		
		Integer idxJob = getParaToInt("jobid");
	
		ITask jobTask = null;
		
		switch(idxJob){
		case 1:
			// ----------预处理
			if (Objects.isNull(jobPre)) {
				jobPre = new PreProcessScheduleJob();
			}
			jobTask = jobPre;
			break;
		case 2: // ------------长势
			if (Objects.isNull(jobGrowth)) {
				jobGrowth = new GrouthMonitorScheduleJob();
			}
			jobTask = jobGrowth;
			break;
		case 3:  // ------------干旱
			if (Objects.isNull(jobDrought)) {
				jobDrought = new LandDroughtScheduleJob();
			}
			jobTask = jobDrought;
			break;
		case 4:  // ---------估产
			if (Objects.isNull(jobYield)) {
				jobYield = new LandYieldScheduleJob();
			}
			jobTask = jobYield;
			break;
			
		case 5: // ----------重新生成预处理表的采集时间的每年第几天
			Integer yearBeg = getParaToInt("ybeg");
			Integer yearEnd = getParaToInt("yend");
			String resRedo = PreProcessScheduleJob.redoDayNumsOfYear(yearBeg, yearEnd);
			renderText(resRedo);
			return ;
		}
		
		String resStr = "ok";
		if (Objects.isNull(jobTask)) {
			renderText("---");
		} else {
			try {
				cntJobInvoke ++;
				jobTask.run();
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(e);
				resStr = e.getMessage();
			}finally {
				cntJobInvoke --;
			}
			renderText(resStr);
		}
	}

}
