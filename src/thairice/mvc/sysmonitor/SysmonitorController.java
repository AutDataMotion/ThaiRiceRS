/**
 * ThaiRiceRS
 * create by zw at 2018年4月1日
 * version: v1.0
 */
package thairice.mvc.sysmonitor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.FileSystem;
import org.hyperic.sigar.FileSystemUsage;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.log.Logger;
import com.platform.constant.ConstantRender;
import com.platform.mvc.base.BaseController;

import csuduc.platform.util.ComUtil;
import thairice.interceptor.AdminLoginInterceptor;

/**
 * @author zw
 *
 */
@Before(AdminLoginInterceptor.class)
public class SysmonitorController extends BaseController {
	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(SysmonitorController.class);

	public static final String pthc = "/jf/thairice/sysmonitor/";
	public static final String pthv = "/adm2018/sysmonitor/";
	public static final String pthvm = "sysmonitor.html";

	// 获得cpu disk 数量
	private static int groupCpu;
	private static int cntCPU;
	private static int cntDisk;
	private static Double[] cpuInfo;
	private static CpuPerc[] cpuList;
	private static Long[] memInfo;
	private static ResSysmonitor resSysmonitor;
	static {
		try {
			Sigar sigar = new Sigar();
			cpuList = sigar.getCpuPercList();
			cntCPU = cpuList.length;
			groupCpu = Math.max(1, cntCPU / 4);
			cpuInfo = new Double[cpuList.length];

			memInfo = new Long[4];
			
			resSysmonitor = new ResSysmonitor();
			resSysmonitor.setCpus(cpuInfo);
			resSysmonitor.setMems(memInfo);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error(e.getLocalizedMessage());
			cpuList = null;
		}
	}

	//@Clear
	public void index() {
		// 获取cpu、内存 定时刷新
		// 磁盘信息 主动触发
		setAttr("groupCpu", groupCpu);
		List<MdlUsedInfo> diskInfos = getDiskInfoWindows();
		int cntDiskGroup = Math.max(1, diskInfos.size() / 4);
		setAttr("diskInfoList", diskInfos);
		setAttr("groupDisk", cntDiskGroup);
		renderWithPath(pthv + pthvm);
	}

	//@Clear
	public void ajaxGetCpuAndMem() {
		freshCpuData();
		freshMemData();
		renderJson(resSysmonitor);
	}

	private void freshCpuData() {
		try {
			Sigar sigar = new Sigar();
			cpuList = sigar.getCpuPercList();
			for (int i = 0; i < cpuList.length; i++) {
				cpuInfo[i] = ComUtil.formatDouble(cpuList[i].getCombined() * 100);
			}
		} catch (SigarException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error(e.getLocalizedMessage());
		}
	}

	private void freshMemData() {
		try {
			Sigar sigar = new Sigar();
			Mem mem = sigar.getMem();
			// 单位MB
			// 内存已使用量
			memInfo[0] = mem.getUsed() / 1024L / 1024L;
			// 内存剩余量
			memInfo[1] = mem.getFree() / 1024L / 1024L;
			// Swap swap = sigar.getSwap();
			// // swap已使用量
			// memInfo[2] = swap.getUsed() / 1024L / 1024L;
			// // swap剩余量
			// memInfo[3] = swap.getFree() / 1024L / 1024L;
		} catch (SigarException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error(e.getLocalizedMessage());
		}
	}

	//@Clear
	public void freshDiskData() {
		//renderJson(getDiskInfoLinux());
		renderJson(getDiskInfoWindows());
	}
	
	private static List<MdlUsedInfo> getDiskInfoWindows(){
		List<MdlUsedInfo> usedInfos = new LinkedList<>();
		Sigar sigar = new Sigar();
		FileSystem fslist[];
		try {
			fslist = sigar.getFileSystemList();
			MdlUsedInfo usedInfo = new MdlUsedInfo();
			for (int i = 0; i < fslist.length; i++) {
				FileSystem fs = fslist[i];
				usedInfo.setPath(fs.getDirName());
				FileSystemUsage usage = sigar.getFileSystemUsage(fs.getDirName());
				usedInfo.setTotal(usage.getTotal() / 1024);
				usedInfo.setUsed(usage.getUsed() / 1024);
				usedInfo.setIdle(usage.getAvail() / 1024);
				usedInfo.setRate(Integer.valueOf(ComUtil.formatDoubleToIntString(usage.getUsePercent() * 100D)));
			}
		} catch (SigarException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return usedInfos;
	}
	private static List<MdlUsedInfo> getDiskInfoLinux() {
		List<MdlUsedInfo> usedInfos = new LinkedList<>();
		try {
			Runtime rt = Runtime.getRuntime();
			Process p = rt.exec("df ");// df 查看硬盘空间 获取的单位是K
			BufferedReader in = null;
			try {
				in = new BufferedReader(new InputStreamReader(p.getInputStream()));
				String str = null;
				String[] strArray = null;
				int line = 0;
				while ((str = in.readLine()) != null) {
					line++;
					if (line < 2) {
						continue;
					}
					strArray = str.split(" ");
					MdlUsedInfo usedInfo = new MdlUsedInfo();
					int m = 0;
					for (String para : strArray) {
						if (para.trim().length() == 0)
							continue;
						++m;
						// 目前的服务器
						switch (m) {
						case 2:// total
							usedInfo.setTotal(Long.valueOf(para) / 1024);
							break;
						case 3: // used
							usedInfo.setUsed(Long.valueOf(para) / 1024);

							break;
						case 4:// idle
							usedInfo.setIdle(Long.valueOf(para) / 1024);

							break;
						case 5:// rate
							String digital = para.substring(0, para.length() - 1);
							usedInfo.setRate(Integer.valueOf(digital));

							break;
						case 6:// path
							usedInfo.setPath(para);

							break;
						default:
							break;
						}
					}
					usedInfos.add(usedInfo);
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.getLocalizedMessage());
			} finally {
				in.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getLocalizedMessage());
		}

		return usedInfos;
	}

	public static void main(String[] args) {
		List<MdlUsedInfo> usedInfos = getDiskInfoWindows();
		for (MdlUsedInfo item : usedInfos) {
			System.out.println(item);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.platform.mvc.base.BaseController#setViewPath()
	 */
	@Override
	protected void setViewPath() {
		// TODO Auto-generated method stub
		setAttr(ConstantRender.PATH_CTL_NAME, pthc);
		setAttr(ConstantRender.PATH_VIEW_NAME, pthv);
	}
}
