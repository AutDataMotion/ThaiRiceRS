package thairice.utils;

import org.apache.log4j.Logger;

public class AutoOriginDataArchThread extends Thread{
	private static Logger LOG = Logger.getLogger(AutoOriginDataArchThread.class);
    // 重写run方法，run方法的方法体就是现场执行体  
    public void run()  
    {  
    	LOG.info("AutoOriginDataArchThread:启动自动归档待归档文件线程.");
    	while(true) {
    		try {
	    		// 归档待归档的文件到服务器
	    		LOG.info("AutoOriginDataArchThread:开始自动归档待归档的文件到服务器...");
	//    		FtpUtils.autoWgetArch();
	    		String localfilePathPre = ParamUtils.getParam(ParamUtils.PC_FTP_AUTO_DWLD, ParamUtils.FILE_STRG_ADR);
	    		FileUtils.prepareTestDataDir(localfilePathPre, localfilePathPre);
	    		// sleep一段时间，单位毫秒，暂定10分钟
	    		try {
	    			Integer sleepSpan = 3;
					LOG.debug("AutoOriginDataArchThread:WGET原始文件归档线程暂停" + sleepSpan + "分钟.");
					Thread.sleep(sleepSpan * 60 * 1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    		try {
	    			Integer sleepSpan = 7;
					LOG.debug("AutoOriginDataArchThread:WGET原始文件归档线程暂停" + sleepSpan + "分钟.");
					Thread.sleep(sleepSpan * 60 * 1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		} catch (Exception e) {
    			LOG.error("AutoOriginDataArchThread:自动归档待归档文件线程出现异常" + e);
    		}
    	}
    }  
}
