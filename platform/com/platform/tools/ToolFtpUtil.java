/**
 * <p>title:ToolFtpUtil.java<／p>
 * <p>Description: <／p>
 * @date:2016年3月16日上午12:15:20
 * @author：ZhongwengHao email:zhongweng.hao@qq.com
 * @version 1.0
 */
package com.platform.tools;

import com.platform.mvc.upload.Upload;

import csuduc.platform.util.networkCom.FtpUtils;

/**
 * 创建时间：2016年3月16日 上午12:15:20
 * 项目名称：DUCPlatFromWeb
 * 文件类型：ToolFtpUtil.java
 * 类说明：
 *
 *  
 *修改日志：
 * Date			Author		Version		Description
 *---------------------------------------------------
 *2016年3月16日		Zhongweng	1.0			1.0Version
 */

/**
 * <p>
 * Title: ToolFtpUtil<／p>
 * <p>
 * Description: <／p>
 * 
 * @author ZhongwengHao
 * @date 2016年3月16日
 */
public class ToolFtpUtil {
	public String ftp_ip;

	public int ftp_port;

	public String ftp_username;

	public String ftp_pwd;
	public String ftp_path_remote;
	public String ftp_path_local;

	public String filenameR;
	public String filenameL;

	public void init(String aftp_ip, int aftp_port, String aftp_username,
			String aftp_pwd, String aftp_path_remote, String aftp_path_local) {
		ftp_ip = aftp_ip;
		ftp_port = aftp_port;
		ftp_username = aftp_username;
		ftp_pwd = aftp_pwd;
		ftp_path_remote = aftp_path_remote;
		ftp_path_local = aftp_path_local;
	}

	public boolean Upload(String filenameRemote, String filenameLocal) {

		this.filenameR = new String(filenameRemote);
		this.filenameL = new String(filenameLocal);
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				FtpUtils ftpClient = new FtpUtils();
				ftpClient.uploadfile(ftp_ip, ftp_port, ftp_username, ftp_pwd,
						ftp_path_remote, filenameR, ftp_path_local, filenameL);
			}
		}).start();

		return true;
	}

	public boolean UploadSnyc(String filenameRemote, String filenameLocal) {

		this.filenameR = new String(filenameRemote);
		this.filenameL = new String(filenameLocal);
		// TODO Auto-generated method stub
		FtpUtils ftpClient = new FtpUtils();
		ftpClient.uploadfile(ftp_ip, ftp_port, ftp_username, ftp_pwd,
				ftp_path_remote, filenameR, ftp_path_local, filenameL);

		return true;
	}

	public boolean Download(String filenameRemote, String filenameLocal) {
		// ！！！！connection reset 异常，则需要检查客户端和服务器端关闭防火墙

		this.filenameR = new String(filenameRemote);
		this.filenameL = new String(filenameLocal);
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				FtpUtils ftpClient = new FtpUtils();
				ftpClient.downloadFile(ftp_ip, ftp_port, ftp_username, ftp_pwd,
						ftp_path_remote, filenameR, ftp_path_local, filenameL);
			}
		}).start();

		return true;
	}

}
