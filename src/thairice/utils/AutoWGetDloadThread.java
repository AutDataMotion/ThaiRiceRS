package thairice.utils;

import org.apache.log4j.Logger;

public class AutoWGetDloadThread extends Thread{
	private static Logger LOG = Logger.getLogger(AutoWGetDloadThread.class);
    // 重写run方法，run方法的方法体就是现场执行体  
    public void run()  
    {  
    	LOG.info("AutoWGetDloadThread:启动自动下载待下载文件线程.");
    	while(true) {
    		try {
	    		try {
	    			Integer sleepSpan = 3;
					LOG.debug("AutoWGetDloadThread:WGET原始文件下载线程暂停" + sleepSpan + "分钟.");
					Thread.sleep(sleepSpan * 60 * 1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    		// 下载待下载的文件到服务器
	    		LOG.info("AutoWGetDloadThread:开始自动下载待下载的文件到服务器...");
	    		FtpUtils.autoWgetdownload();
	    		// sleep一段时间，单位毫秒，暂定10分钟
	    		try {
	    			Integer sleepSpan = 7;
					LOG.debug("AutoWGetDloadThread:WGET原始文件下载线程暂停" + sleepSpan + "分钟.");
					Thread.sleep(sleepSpan * 60 * 1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		} catch (Exception e) {
    			LOG.error("AutoWGetDloadThread:自动下载待下载文件线程出现异常" + e);
    		}
    	}
    }  
}
