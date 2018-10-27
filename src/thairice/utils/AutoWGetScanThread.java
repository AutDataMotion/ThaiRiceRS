package thairice.utils;

import org.apache.log4j.Logger;

public class AutoWGetScanThread extends Thread{
	private static Logger LOG = Logger.getLogger(AutoWGetScanThread.class);
    // 重写run方法，run方法的方法体就是现场执行体  
    public void run()  
    {  
    	LOG.info("AutoWGetScanThread:启动WGET原始文件扫描线程.");
    	try {
	    	while(true) {
	    		// 扫描 从今天到最早的时间中所有日期，检测有数据的情况
	    		LOG.info("AutoWGetScanThread:开始WGET扫描待下载原始文件...");
		   		 FtpUtils.initScanHttp();
	    		// sleep一段时间，单位毫秒，暂定10分钟
	    		try {
	    			Integer sleepSpan = 10;
					LOG.debug("AutoWGetScanThread：自动原始文件扫描线程暂停" + sleepSpan + "分钟.");
					Thread.sleep(sleepSpan * 60 * 1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	}
    	} catch (Exception e) {
    		LOG.error("AutoWGetScanThread:自动原始文件扫描线程出现异常" + e);
    	}
    }  
}
