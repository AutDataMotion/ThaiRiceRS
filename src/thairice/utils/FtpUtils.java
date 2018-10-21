/**
 * <p>title:FtpUtils.java<／p>
 * <p>Description: <／p>
 * @date:2015年11月5日下午4:34:05
 * @author：ZhongwengHao email:zhongweng.hao@qq.com
 * @version 1.0
 */
package thairice.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.URL;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;
import org.eclipse.jetty.util.log.Log;

import com.jfinal.config.JFinalConfig;
import com.jfinal.plugin.activerecord.Db;

import csuduc.platform.util.networkCom.FTPClientConfigure;
import csuduc.platform.util.networkCom.FTPClientFactory;
import csuduc.platform.util.networkCom.FTPClientPool;
import thairice.config.MyConfig;
import thairice.constant.ConstantInitMy;
import thairice.entity.FtpInfoEntity;
import thairice.mvc.t2syslog.EnumT2sysLog;
import thairice.mvc.t2syslog.T2syslogService;
import thairice.mvc.t6org_data.T6org_data;
import thairice.mvc.t6org_data.T6org_dataService;
import thairice.mvc.t7pdt_data.T7pdt_dataService;

import static java.nio.file.StandardCopyOption.COPY_ATTRIBUTES;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.lang.*;

/**  
 * 创建时间：2015年11月5日 下午4:34:05  
 * 项目名称：zwplatform   
 * 文件名称：FtpUtils.java  
 * 类说明：  
 *
 * Modification History:   
 * Date        Author         Version      Description   
 * ----------------------------------------------------------------- 
 * 2015年11月5日     Zhongweng       1.0         1.0 Version
 * 2018年2月1日     zhuchaobin       1.1         1.1 Version  
 */
/**
 * <p>
 * Title: FtpUtils<／p>
 * <p>
 * Description: <／p>
 * 
 * @author ZhongwengHao
 * @date 2015年11月5日
 */
public class FtpUtils {
	private static Logger LOG = Logger.getLogger(FtpUtils.class);
	static int index = 0;// 下载文件列表中的第几个元素
	FTPClientFactory factory;
	FTPClientPool pool;
	FTPClient ftpClient;

	/**
	 * 获取FTPClient对象
	 * 
	 * @param ftpHost
	 *            FTP主机服务器
	 * @param ftpPassword
	 *            FTP 登录密码
	 * @param ftpUserName
	 *            FTP登录用户名
	 * @param ftpPort
	 *            FTP端口 默认为21
	 * @return
	 */

	public FtpUtils() {

	}

	public static FTPClient getFTPClient(String ftpHost, String ftpPassword, String ftpUserName, int ftpPort) {
		FTPClient ftpClient = null;
		try {
			ftpClient = new FTPClient();
			ftpClient.connect(ftpHost, ftpPort);// 连接FTP服务器
			ftpClient.login(ftpUserName, ftpPassword);// 登陆FTP服务器
			if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
				System.out.println("未连接到FTP，用户名或密码错误。");
				ftpClient.disconnect();
				ftpClient = null;
			} else {
				System.out.println("FTP连接成功。");
			}
		} catch (SocketException e) {
			e.printStackTrace();
			System.out.println("FTP的IP地址可能错误，请正确配置。");
			ftpClient = null;
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("FTP的端口错误,请正确配置。");
			ftpClient = null;
		}
		return ftpClient;
	}

	public FTPClient connectFtp(String ftpHost, int ftpPort, String ftpUserName, String ftpPassword) {
		FTPClient ftpClient = null;
		try {
			ftpClient = FtpUtils.getFTPClient(ftpHost, ftpPassword, ftpUserName, ftpPort);
			if (null == ftpClient) {
				System.out.println("登录失败！！！");
				return null;
			}
			ftpClient.setControlEncoding("UTF-8"); // 中文支持
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			ftpClient.enterLocalPassiveMode();
			ftpClient.setBufferSize(1024 * 8);
			return ftpClient;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public FTPClient connectFtp() {
		FTPClient ftpClient = null;
		FtpInfoEntity ftpInfo = getFtpInfo();
		;

		try {
			ftpClient = FtpUtils.getFTPClient(ftpInfo.getFtpHost(), ftpInfo.getFtpPassword(), ftpInfo.getFtpName(),
					ftpInfo.getFtpPort());
			if (null == ftpClient) {
				System.out.println("登录失败！！！");
				return null;
			}
			ftpClient.setControlEncoding("UTF-8"); // 中文支持
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			ftpClient.enterLocalPassiveMode();
			ftpClient.setBufferSize(1024 * 8);
			return ftpClient;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/*
	 * 使用连接池创建ftpClient
	 */
	public void getFTPClientByPool(String ftpHost, int ftpPort, String ftpNameString, String ftpPasswordString) {
		try {
			FTPClientConfigure config = new FTPClientConfigure();
			config.setHost(ftpHost);
			config.setPort(ftpPort);
			config.setUsername(ftpNameString);
			config.setPassword(ftpPasswordString);
			config.setEncoding("utf-8");
			config.setPassiveMode("false");
			config.setClientTimeout(30 * 1000);
			this.factory = new FTPClientFactory(config);
			this.pool = new FTPClientPool(factory);
			this.ftpClient = pool.borrowObject();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * 使用连接池连接ftpClient
	 */
	public void connectFtpByPool(String ftpHost, int ftpPort, String ftpNameString, String ftpPasswordString) {
		try {
			getFTPClientByPool(ftpHost, ftpPort, ftpNameString, ftpPasswordString);
			if (null == this.ftpClient) {
				System.out.println("登录失败！！！");
			}
			this.ftpClient.setControlEncoding("UTF-8"); // 中文支持
			this.ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			this.ftpClient.enterLocalPassiveMode();
			this.ftpClient.setBufferSize(1024 * 8);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * 返还一个连接
	 */
	public void disconnectFtpByPool() {
		try {
			this.pool.returnObject(this.ftpClient);
			System.out.println("断开连接!");
			ftpClient = null;
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	/**
	 * 去 服务器的FTP路径下上读取文件
	 * 
	 * @param ftpUserName
	 * @param ftpPassword
	 * @param ftpPath
	 * @param FTPServer
	 * @return
	 */
	public boolean downloadFile(FTPClient ftpClient, String ftpPath, String fileName, String localfilePath,
			String localfileName) {
		OutputStream output = null;
		try {
			ftpClient.changeWorkingDirectory(ftpPath);
			FTPFile[] remoteFiles;
			remoteFiles = ftpClient.listFiles();
			int indexFile = -1;
			if (remoteFiles != null) {
				System.out.println("---" + remoteFiles.length);
				for (int i = 0; i < remoteFiles.length; i++) {
					System.out.println("---" + remoteFiles[i].getName());
					if (remoteFiles[i].getName().equals(fileName)) {
						indexFile = i;
						break;
					}
				}
			} else {
				System.out.println(ftpPath + "目录为空");
				return false;
			}
			if (-1 == indexFile) {
				System.out.println("没有找到" + fileName + "文件");
				return false;
			}
			File localFile = new File(localfilePath + "/" + localfileName);
			long sizeFile = remoteFiles[indexFile].getSize();

			output = new FileOutputStream(localFile + "1.dat");
			System.out.println(ftpPath + fileName + "---" + sizeFile);
			ftpClient.retrieveFile(fileName, output);
			// ftpClient.deleteFile(ftpPath + fileName);
			output.flush();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			if (output != null) {
				output.close();
				System.out.println("关闭文件流!");
				output = null;
			}
			// if(ftpClient != null){
			// disconnectFtp(ftpClient);
			// }
			return false;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	public void disconnectFtp(FTPClient ftpClient) {
		try {
			ftpClient.logout();
			ftpClient.disconnect();
			System.out.println("断开连接!");
			ftpClient = null;
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

	/**
	 * 本地上传文件到FTP服务器
	 * 
	 * @param ftpPath
	 *            远程文件路径FTP
	 * @throws IOException
	 */
	public void uploadfile(String ftpHost, int ftpPort, String ftpUserName, String ftpPassword, String ftpPath,
			String ftpfileName, String localpath, String localfilename) {
		FTPClient ftpClient = null;
		System.out.println("开始上传文件到FTP.");
		System.out.format(
				"ftpHost:%s--port:%d--\nftpUserName:%s" + "--ftpPassword:%s--\nftpPath:%s--ftpfileName:%s\n"
						+ "localpath:%s--localfilename:%s",
				ftpHost, ftpPort, ftpUserName, ftpPassword, ftpPath, ftpfileName, localpath, localfilename);
		try {
			ftpClient = FtpUtils.getFTPClient(ftpHost, ftpPassword, ftpUserName, ftpPort);
			// 设置PassiveMode传输
			ftpClient.enterLocalPassiveMode();
			// 设置以二进制流的方式传输
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);

			File f = new File(localpath + localfilename);
			System.out.println("=========" + localpath + localfilename);
			InputStream in = new FileInputStream(f);
			ftpClient.storeFile(ftpPath + ftpfileName, in);
			System.out.println("---------" + ftpPath + ftpfileName);
			in.close();
			System.out.println("上传文件" + ftpfileName + "到FTP成功!");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ftpClient.disconnect();
				System.out.println("断开连接!");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 本地上传文件到FTP服务器
	 * 
	 * @param ftpPath
	 *            远程文件路径FTP
	 * @throws IOException
	 */
	public void upload(String ftpPath, String ftpUserName, String ftpPassword, String ftpHost, int ftpPort,
			String fileContent, String writeTempFielPath) {
		FTPClient ftpClient = null;
		System.out.println("开始上传文件到FTP.");
		try {
			ftpClient = FtpUtils.getFTPClient(ftpHost, ftpPassword, ftpUserName, ftpPort);
			// 设置PassiveMode传输
			ftpClient.enterLocalPassiveMode();
			// 设置以二进制流的方式传输
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			// 对远程目录的处理
			String remoteFileName = ftpPath;
			if (ftpPath.contains("/")) {
				remoteFileName = ftpPath.substring(ftpPath.lastIndexOf("/") + 1);
			}
			// FTPFile[] files = ftpClient.listFiles(new
			// String(remoteFileName));
			// 先把文件写在本地。在上传到FTP上最后在删除
			boolean writeResult = write(remoteFileName, fileContent, writeTempFielPath);
			if (writeResult) {
				File f = new File(writeTempFielPath + "/" + remoteFileName);
				InputStream in = new FileInputStream(f);
				ftpClient.storeFile(remoteFileName, in);
				in.close();
				System.out.println("上传文件" + remoteFileName + "到FTP成功!");
				f.delete();
			} else {
				System.out.println("写文件失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ftpClient.disconnect();
				System.out.println("断开连接!");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 把配置文件先写到本地的一个文件中取
	 * 
	 * @param ftpPath
	 * @param str
	 * @return
	 */
	public boolean write(String fileName, String fileContext, String writeTempFielPath) {
		try {
			System.out.println("开始写配置文件");
			File f = new File(writeTempFielPath + "/" + fileName);
			if (!f.exists()) {
				if (!f.createNewFile()) {
					System.out.println("文件不存在，创建失败!");
				}
			}
			BufferedWriter bw = new BufferedWriter(new FileWriter(f, true));
			bw.write(fileContext.replaceAll("\n", "\r\n"));
			bw.flush();
			bw.close();
			return true;
		} catch (Exception e) {
			LOG.error("写文件没有成功");
			e.printStackTrace();
			return false;
		}
	}

	public boolean isEmpty(FTPClient ftpClient, String ftpPath) {
		FTPFile[] remoteFiles;
		try {
			ftpClient.changeWorkingDirectory(ftpPath);
			remoteFiles = ftpClient.listFiles();
			if (remoteFiles.length < 3) { // 待确认
				return false;
			} else
				return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 数据归档
	 * 
	 * @param oldPath
	 *            目录oldPath/ZW下存放数据
	 * @param newPath
	 *            在待归档目录newPath下新建ZW文件夹（当且仅当不存在该文件夹时）存放数据
	 * @return
	 */
	public boolean copyFolder(String oldPath, String newPath) {
		// oldPath为待归档目录，分别以ZW/IALT/MWI新建子文件夹存放数据；newPath为实时归档目录
		File aFile = new File(oldPath);
		File[] fileListsFiles = aFile.listFiles();
		boolean ifGD = false;// 判断是否归档
		// 高度计归档
		for (int i = 0; i < fileListsFiles.length; i++) {
			if (fileListsFiles[i].getName().equals("IALT")) {
				// newPath = Parameters.guidangIALT;//设置归档目录，V盘为不实时归档,可以将归档目录写在Parameters文件里
				String newFilePathString = newPath + "\\IALT\\";
				java.io.File fileFolder = new java.io.File(newFilePathString);
				if (!fileFolder.exists()) {
					fileFolder.mkdirs();
				}
				File[] files = fileListsFiles[i].listFiles();
				for (int j = 0; j < files.length; j++) {
					String[] namesStrings = files[j].getName().split("_");
					if (namesStrings.length != 10) {// 数据命名方式错误时，不归档
						System.out.println(files[j].getName() + "命名方式错误，未归档");
						continue;
					}
					if ((namesStrings[3].equals("PLS")) && (namesStrings[4].equals("SCI"))
							&& (namesStrings[9].equals("0B.dat"))) {
						copyFile(files[j].getAbsolutePath(), newFilePathString + files[j].getName());
						ifGD = true;
					}
				}
			}
			// 宽波段归档
			else if (fileListsFiles[i].getName().equals("MWI")) {
				// newPath = Parameters.guidangMWI;//设置归档目录，V盘为不实时归档,可以将归档目录写在Parameters文件里
				String newFilePathString = newPath + "\\MWI\\";
				java.io.File fileFolder = new java.io.File(newFilePathString);
				if (!fileFolder.exists()) {
					fileFolder.mkdirs();
				}
				File[] files = fileListsFiles[i].listFiles();
				for (int j = 0; j < files.length; j++) {
					String[] namesStrings = files[j].getName().split("_");
					if (namesStrings.length != 10) {// 数据命名方式错误时，不归档
						System.out.println(files[j].getName() + "命名方式错误，未归档");
						continue;
					}

					if ((namesStrings[4].equals("IMG")) && (namesStrings[9].equals("0C.dat"))) {
						copyFile(files[j].getAbsolutePath(), newFilePathString + files[j].getName());
						ifGD = true;
					}
					if ((namesStrings[4].equals("AUX")) && (namesStrings[3].equals("WBI"))
							&& ((namesStrings[9].equals("0C.csv")) || (namesStrings[9].equals("0B.dat")))) {
						copyFile(files[j].getAbsolutePath(), newFilePathString + files[j].getName());
						ifGD = true;
					}
				}
			}
			// 紫外归档
			else if (fileListsFiles[i].getName().equals("ZW")) {
				// newPath = Parameters.guidangZW;//设置归档目录，V盘为不实时归档,可以将归档目录写在Parameters文件里
				String newFilePathString = newPath + "\\ZW\\";
				java.io.File fileFolder = new java.io.File(newFilePathString);
				if (!fileFolder.exists()) {
					fileFolder.mkdirs();
				}
				File[] files = fileListsFiles[i].listFiles();
				for (int j = 0; j < files.length; j++) {
					String[] namesStrings = files[j].getName().split("_");
					if (namesStrings.length != 10) {// 数据命名方式错误时，不归档
						System.out.println(files[j].getName() + "命名方式错误，未归档");
						continue;
					}
					if ((namesStrings[4].equals("IMG")) && (namesStrings[9].equals("0C.dat"))) {
						copyFile(files[j].getAbsolutePath(), newFilePathString + files[j].getName());
						ifGD = true;
					}
					if ((namesStrings[4].equals("AUX")) && (namesStrings[9].equals("0C.csv"))) {
						copyFile(files[j].getAbsolutePath(), newFilePathString + files[j].getName());
						ifGD = true;
					}
					if ((namesStrings[3].equals("DTD")) && (namesStrings[4].equals("TTC"))
							&& (namesStrings[9].equals("0C.csv"))) {
						copyFile(files[j].getAbsolutePath(), newFilePathString + files[j].getName());
						ifGD = true;
					}
				}
			}
		}
		if (ifGD == true) {
			System.out.println("复制完成！");
		} else {
			System.out.println("无待归档数据！");
		}
		return true;
	}

	public final static String correctPath(String path) {
		return path.replace('\\', '/');

	}

	/**
	 * 拷贝单个文件
	 * 
	 * @param oldPath
	 * @param newPath
	 * @return
	 */
	public boolean copyFile(String oldPath, String newPath) {
		// target 不存在则先创建
		CopyOption[] options = new CopyOption[] { REPLACE_EXISTING };
		// copy 失败则重复尝试3次
		int cnt = 3;
		while (cnt > 0) {
			try {
				System.out.println("文件从" + correctPath(oldPath) + "复制到" + correctPath(newPath) + "");
				Files.copy(Paths.get((oldPath)), Paths.get((newPath)), options);
				return true;
			} catch (IOException x) {
				System.out.println(String.format("Unable to copy file :\n from:%s \nto:%s \n errinfo: %s \n retry...",
						oldPath, newPath, x));
				cnt--;
			}

		}
		return false;

	}

	/**
	 * <p>
	 * Title: main<／p>
	 * <p>
	 * Description: <／p>
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		// final String ftpHost = "ladsweb.nascom.nasa.gov";
		// final String ftpNameString = "anonymous";
		// final String ftpPasswordString = "anonymous";

		final String ftpHost = "127.0.0.1";
		final String ftpNameString = "duc";
		final String ftpPasswordString = "duc";
		final int ftpPort = 21;
		final String ftpPath = "//";// ftp存储路径
		final String ftpFileName = "README";
		final String localfilePath = "D:\\test";
		final FtpUtils ftpUtils = new FtpUtils();
		final FTPClient ftpClient = ftpUtils.connectFtp(ftpHost, ftpPort, ftpNameString, ftpPasswordString);
		// ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
		// ftpClient.enterLocalPassiveMode();
		// ftpUtils.downloadFile(ftpClient, ftpPath, ftpFileName, localfilePath,
		// ftpFileName);
		String testStr = FileUtils.generateNewPath("/allData/6/MOD13Q1/$1/$2/", "01", null, "02");
		System.out.println(testStr);
		List fileList = detecRemFilDirList(ftpClient, testStr);
		for (int i = 0; i < fileList.size(); i++) {
			System.out.println("test:" + fileList.get(i));
			String fileDir = (String) fileList.get(i);
			// 更新数据库信息
			MyConfig config2 = new MyConfig();
			config2.configPlugin(null);
			Db.update("update T6org_data set id = ?", "22");
			//
			// // 数据库查询
			// List<T6org_data> a = T6org_data.dao.find(sql);

			System.out.println("数据库更新成功");
			// T6org_dataService sev = new T6org_dataService();
			// T6org_data t6 = sev.SelectById(1);

			downloadFile2(ftpClient, fileDir, "d://");
		}
		dectRemFleFnsh(ftpClient, "/allData/6/MOD13Q1/2017/001/MOD13Q1.A2017001.h34v08.006.2017020220023.hdf");

		// ftpUtils.disconnectFtp(ftpClient);
		// final String localPath = "D:\\testData1031\\";//本地存储路径
		// final String copyPath = "D:\\testDataDest";
		// ftpUtils.copyFolder(localPath, copyPath);

		// final FTPClient fClient = ftpClient.connectFtp("192.168.2.201",21,
		// "user", "user");
		// boolean res = false;
		// fileStructList = ftpClient.getSizeLists(fClient, ftpPath);
		// final long timeInterval = 5*1000;
		// Runnable runnable = new Runnable() {
		// boolean isNotEmpty = true;
		// int i = 0;
		// @Override
		// public void run() {
		// // TODO Auto-generated method stub
		// while (true) {//isNotEmpty
		// FTPClient fClient = ftpClient.connectFtp("192.168.2.201",21,
		// "user", "user");
		// System.out.println("第"+i+"个线程");
		// i++;
		// try {
		// Thread.sleep(timeInterval);
		// } catch (Exception e) {
		// // TODO: handle exception
		// }
		// ftpClient.downloadFile(fClient,
		// ftpPath, localPath);
		//// ftpClient.copyFolder(localPath,serverPathString);
		//// isNotEmpty = ftpClient.isEmpty(fClient, ftpPath);
		// }
		// }
		// };
		// Thread thread = new Thread(runnable);
		// try {
		// Thread.sleep(timeInterval);
		// } catch (InterruptedException e) {
		// // TODO 自动生成的 catch 块
		// e.printStackTrace();
		// }
		// thread.start();
		//
		// ftpClient.getSizeLists(fClient, ftpPath);

		// ！！！！connection reset 异常，则需要检查客户端和服务器端关闭防火墙

		// res = ftpClient.downloadFile( "192.168.0.70",21,
		// "anonymous", "anonymous",
		// "/", "plugin_hj.jar",
		// "D:/ftplocal", "plugin_hj.jar");
		//
		// if (res) {
		// System.out.println("下载成功！！");
		// }else{
		// System.out.println("下载失败！！");
		// }
		// ftpClient
		// .uploadfile("192.168.0.70", 21, "anonymous", "anonymous",
		// "/localUser","plugin_SYY.jar","D:/ftplocal", "plugin_SYY.jar");
	}

	/**
	 * 扫描远程ftp目录下文件列表
	 * 
	 * @param ftpClient
	 * @param ftpPath
	 * @author zhuchaobin
	 * @return List remoteFilePathList
	 * @throws InterruptedException
	 */
	public static List<T6org_data> detecRemFilDirList(FTPClient ftpClient, String ftpPath) {
		LOG.debug("扫描远程ftp目录下文件列表开始，参数ftpClient=" + ftpClient + "ftpPath=" + ftpPath);
		List remoteFilePathList = new ArrayList();
		List<T6org_data> T6org_data_list = new ArrayList<T6org_data>();
		try {
			Map remoteFilePathMap = new HashMap<String, Long>();
			boolean changDirRlt = ftpClient.changeWorkingDirectory(ftpPath);
			if (!changDirRlt) {
				// 路径不存在
				return null;
			}
			FTPFile[] remoteFiles;
			ftpClient.enterLocalPassiveMode();
			remoteFiles = ftpClient.listFiles();
			if (remoteFiles != null) {
				LOG.debug("远程目录下文件数remoteFiles.length：" + remoteFiles.length);
				for (int i = 0; i < remoteFiles.length; i++) {
					String remoteFileName = remoteFiles[i].getName();
					remoteFiles[i].getTimestamp();
					// System.out.println("第一次检测到文件:"+ ftpPath + remoteFileName);
					// 判定是否为泰国境内条带
					if (FileUtils.isThairHV(remoteFileName)) {
						remoteFilePathMap.put(remoteFileName, remoteFiles[i].getSize());
					} else {
						LOG.debug("非泰国境内条带:" + remoteFileName);
					}
				}
				// return remoteFilePathList;
			} else {
				LOG.debug(ftpPath + "目录下没有文件");
				return null;
			}
			LOG.debug("暂停20毫秒.");
			Thread.sleep(20);
			// 第二次检测目录下文件并比对文件个数和文件大小
			ftpClient.enterLocalPassiveMode();
			changDirRlt = ftpClient.changeWorkingDirectory(ftpPath);
			if (!changDirRlt) {
				// 路径不存在
				LOG.debug("远程目录：" + ftpPath + "不存在");
				return null;
			} else {
				remoteFiles = ftpClient.listFiles();
				if (remoteFiles != null) {
					System.out.println("---" + remoteFiles.length);
					for (int i = 0; i < remoteFiles.length; i++) {
						String remoteFileName = remoteFiles[i].getName();
						// 判定是否为泰国境内条带
						if (FileUtils.isThairHV(remoteFileName)) {
							Long secSize = remoteFiles[i].getSize();
							Long fstSize = (Long) remoteFilePathMap.get(remoteFileName);
							if (fstSize.equals(secSize)) {
								remoteFilePathList.add(ftpPath + remoteFileName);
								T6org_data org_data = new T6org_data();
								// 解析文件属性
								org_data = FileUtils.parseOrgDataDir(ftpPath + remoteFileName);
								if (null != org_data) {
									// 文件大小
									org_data.setSize_(Float.parseFloat(String.valueOf(secSize)));
									T6org_data_list.add(org_data);
								}
							} else {
								// 两次比对文件大小不一致
								LOG.debug("两次比对文件大小不一致");
							}
						} else {
							LOG.debug("非泰国境内条带:" + remoteFileName);
						}
					}
					return T6org_data_list;
				} else {
					LOG.debug(ftpPath + "目录下没有文件");
					return null;
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOG.error("扫描远程ftp目录下文件列表发生异常:");
			e.printStackTrace();
		}
		return T6org_data_list;
	}

	/**
	 * 扫描远程Url中下文件列表信息
	 * 
	 * @param ftpPath
	 * @param
	 * @author zhuchaobin
	 * @return List remoteFilePathList
	 * @throws InterruptedException
	 */
	public static List<T6org_data> detecUrlFilDirList(String remoteCsvPath) {
		LOG.debug("扫描远程ftp目录下文件列表开始，参数remoteCsvPath=" + remoteCsvPath);
		List remoteFilePathList = new ArrayList();
		List<T6org_data> T6org_data_list = new ArrayList<T6org_data>();
		try {
			Map remoteFilePathMap = new HashMap<String, Long>();
			String line = null;
			String url = remoteCsvPath;
			BufferedReader in = null;
			try {
				in = new BufferedReader(
					new InputStreamReader(new URL(url).openConnection().getInputStream(), "GB2312"));
			} catch (Exception e) {
				LOG.error("读取csv url发生异常：" + url);
				e.printStackTrace();
				return null;
			}
			// GB2312可以根据需要替换成要读取网页的编码
			while ((line = in.readLine()) != null) {
				String[] fileInfoArr = line.split(",");
				if (3 == fileInfoArr.length) {
					String fileName = fileInfoArr[0];
					String fileSize = fileInfoArr[2];
					// 判定是否为泰国境内条带
					if (FileUtils.isThairHV(fileName)) {
						T6org_data org_data = new T6org_data();
						// 解析文件属性
						org_data = FileUtils.parseOrgDataDir(remoteCsvPath.substring(0, remoteCsvPath.length()-4) + "//" + fileName);
						if (null != org_data) {
							// 文件大小
							org_data.setSize_(Float.parseFloat(String.valueOf(fileSize)));
							T6org_data_list.add(org_data);
						}
					} else {
					//	LOG.debug("非泰国境内条带:" + fileName);
					}
				} else {
					// 文件名信息有误
				}
			}
			return T6org_data_list;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOG.error("扫描远程ftp目录下文件列表发生异常:");
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 检测ftp远程文件大小是否增长，若不增长认为文件已经写入完毕
	 * 
	 * @param ftpUserName
	 * @param ftpPassword
	 * @param ftpPath
	 * @param FTPServer
	 * @return
	 */
	public static boolean dectRemFleFnsh(FTPClient ftpClient, String ftpDir) {
		OutputStream output = null;
		try {
			FTPFile remoteFile;
			boolean test = ftpClient.changeWorkingDirectory(ftpDir);
			// ftpClient.
			FTPFile[] remoteFiles;
			remoteFiles = ftpClient.listFiles();
			int indexFile = -1;
			if (remoteFiles != null) {
				System.out.println("---" + remoteFiles.length);
				for (int i = 0; i < remoteFiles.length; i++) {
					// System.out.println("---"+remoteFiles[i].getName());
				}
			} // else {
				// System.out.println(ftpPath + "目录为空");
				// return false;
				// }
				// if (-1 == indexFile) {
				// System.out.println("没有找到" + fileName + "文件");
				// return false;
				// }
				// File localFile = new File(localfilePath+"/"+localfileName);
				// long sizeFile = remoteFiles[indexFile].getSize();
				//
				// output = new FileOutputStream(localFile + "1.dat");
				// System.out.println(ftpPath + fileName + "---" + sizeFile);
				// ftpClient.retrieveFile(fileName, output);
				//// ftpClient.deleteFile(ftpPath + fileName);
				// output.flush();
				// } catch (IOException e1) {
				// // TODO Auto-generated catch block
				// e1.printStackTrace();
				// }
				//
				// try {
				// if (output != null) {
				// output.close();
				// System.out.println("关闭文件流!");
				// output = null;
				// }
				//// if(ftpClient != null){
				//// disconnectFtp(ftpClient);
				//// }
				// return false;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return true;
	}

	/**
	 * 去 服务器的FTP路径下上读取文件
	 * 
	 * @param ftpUserName
	 * @param ftpPassword
	 * @param ftpPath
	 * @param FTPServer
	 * @return
	 */
	public static boolean downloadFile2(FTPClient ftpClient, String ftpDir, String localfilePath) {
		OutputStream output = null;
		ftpDir = ftpDir.trim();
		String fileName = ftpDir.substring(ftpDir.lastIndexOf("/") + 1);
		String localfileName = fileName;
		String ftpPath = ftpDir.substring(0, ftpDir.lastIndexOf("/"));
		try {
			ftpClient.setControlEncoding("UTF-8"); // 中文支持
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			ftpClient.enterLocalPassiveMode();

			ftpClient.changeWorkingDirectory(ftpPath);
			// FTPFile[] remoteFiles;
			// remoteFiles = ftpClient.listFiles();
			// int indexFile = -1;
			// if (remoteFiles != null) {
			// System.out.println("---" + remoteFiles.length);
			// for (int i = 0; i < remoteFiles.length; i++) {o
			// System.out.println("---"+remoteFiles[i].getName());
			// if (remoteFiles[i].getName().equals(fileName)) {
			// indexFile = i;
			// break;
			// }
			// }
			// } else {
			// System.out.println(ftpPath + "目录为空");
			// return false;
			// }
			// if (-1 == indexFile) {
			// System.out.println("没有找到" + fileName + "文件");
			// return false;
			// }
			File localFile = new File(localfilePath + "/" + localfileName);
			// long sizeFile = remoteFiles[indexFile].getSize();

			output = new FileOutputStream(localFile);
			// System.out.println(ftpPath + fileName + "---" + sizeFile);
			// ftpClient.retrieveFile(fileName, output);
			ftpClient.retrieveFile(new String(fileName.getBytes("GBK"), "ISO-8859-1"), output);
			// ftpClient.deleteFile(ftpPath + fileName);
			// output.flush();
			output.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * 去 服务器的FTP路径下上读取文件
	 * 
	 * @param ftpUserName
	 * @param ftpPassword
	 * @param ftpPath
	 * @param FTPServer
	 * @return
	 */
	public static boolean wgetDownload(String fileName, String urlPre, String localfilePath) {
		String command = "";
		try {
//			String commandPre = "D:\\NASA数据下载\\wget-1.19.4-win64\\wget -e robots=off -m -np -R .html,.tmp -nH --cut-dirs=3 \"https://ladsweb.modaps.eosdis.nasa.gov/archive/allData/6/MOD11A2/2001/177/";
			String commandPre = "D:\\NASA数据下载\\wget-1.19.4-win64\\wget -e robots=off -m -np -R .html,.tmp -nH --cut-dirs=3 ";
			String commandRear = " --header \"Authorization: Bearer F6D6658C-B0E6-11E8-B53A-D0F1F792DC2C \" -P " + localfilePath;	
			command = commandPre + "\"" + urlPre + fileName + "\"" + commandRear;
			LOG.debug("执行系统调用，命令：" + command);
			Runtime.getRuntime().exec(command);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			LOG.debug("执行系统调用发生异常，命令：" + command);
			e1.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 从参数明细表中获取ftp信息
	 * 
	 * @param ftpUserName
	 * @param ftpPassword
	 * @param ftpPath
	 * @param FTPServer
	 * @return
	 */
	public static FtpInfoEntity getFtpInfo() {
		try {
			FtpInfoEntity ftpInfoEntity = new FtpInfoEntity();
			ftpInfoEntity.setFtpHost(ParamUtils.getParam(ParamUtils.PC_FTP_AUTO_DWLD, ParamUtils.PD_SRCFTP_ADR));
			ftpInfoEntity.setFtpName(ParamUtils.getParam(ParamUtils.PC_FTP_AUTO_DWLD, ParamUtils.PD_SRCFTP_USR_NM));
			ftpInfoEntity.setFtpPassword(ParamUtils.getParam(ParamUtils.PC_FTP_AUTO_DWLD, ParamUtils.PD_SRCFTP_PSWD));
			ftpInfoEntity.setFtpPort(
					Integer.parseInt(ParamUtils.getParam(ParamUtils.PC_FTP_AUTO_DWLD, ParamUtils.PD_SRCFTP_PT)));
			ftpInfoEntity.setLocalfilePath(ParamUtils.getParam(ParamUtils.PC_FTP_AUTO_DWLD, ParamUtils.FILE_STRG_ADR));
			return ftpInfoEntity;
		} catch (Exception e) {
			LOG.error("从参数明细表中获取ftp信息发生异常：" + e);
			return null;
		}
	}

	public static void initScanFtp() {
		try {
			LOG.debug("自动任务FTP初始化文件扫描开始.");
			// 获取FTP初始化文件扫描参数
			// 初始化开关
			String inlzSwtc = ParamUtils.getParam(ParamUtils.PC_FTP_AUTO_DWLD, ParamUtils.INLZ_SWTC);
			// 初始数据开始日期
			String inlzStDt = ParamUtils.getParam(ParamUtils.PC_FTP_AUTO_DWLD, ParamUtils.INLZ_STDT);
			// 初始数据结束日期
			String inlzEdDt = ParamUtils.getParam(ParamUtils.PC_FTP_AUTO_DWLD, ParamUtils.INLZ_EDDT);
			LOG.debug("FTP初始化文件扫描参数:开关=" + inlzSwtc + "扫描开始日期=" + inlzStDt + "扫描结束日期=" + inlzEdDt);
			// 参数校验
			if (StringUtils.isBlank(inlzSwtc)) {
				LOG.error("初始化开关为空，自动任务FTP初始化文件扫描失败");
				return;
			}
			if (StringUtils.isBlank(inlzStDt)) {
				LOG.error("初始化开始日期为空，自动任务FTP初始化文件扫描失败");
				return;
			}
			if (StringUtils.isBlank(inlzEdDt)) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				inlzEdDt = sdf.format(new Date());
				LOG.error("初始化结束日期为空，自动任务FTP初始化文件扫描结束日期取当天日期：" + inlzEdDt);
			}
			// 如果开关为打开则开始逐天扫描
			if (DataConstants.FLAGE_ON.equals(inlzSwtc)) {
				// 初始化完成关闭开关
				if (ParamUtils.updateParam(ParamUtils.PC_FTP_AUTO_DWLD, ParamUtils.INLZ_SWTC,
						DataConstants.FLAGE_OFF)) {
					LOG.info("初始化完成关闭开关关闭成功");
				} else {
					LOG.error("初始化完成关闭开关关闭失败");
				}
				List<Date> dateList = new ArrayList<Date>();
				try {
					dateList = DatesUtils.getBetweenDates(inlzStDt, inlzEdDt);
				} catch (ParseException e) {
					LOG.error("获取初始化时间区间失败:" + e);
					LOG.error("自动任务FTP初始化文件扫描失败.");
					return;
				}
				if (null != dateList) {
					final FtpUtils ftpUtils = new FtpUtils();
					final FTPClient ftpClient = ftpUtils.connectFtp();
					for (Date eleDate : dateList) {
						Calendar cal = Calendar.getInstance();
						List<String> remotePathList = ParamUtils.getParamList(ParamUtils.PC_FTP_AUTO_DWLD,
								ParamUtils.PD_SRCFTP_ROOT_CTLG);
						if (null != remotePathList) {
							for (String remotePath : remotePathList) {
								remotePath = FileUtils.generateNewPath(remotePath, "01", eleDate, DataConstants.DOWNLOAD_TYPE_FTP);
								List<T6org_data> T6org_data_list = FtpUtils.detecRemFilDirList(ftpClient, remotePath);
								if (null != T6org_data_list) {
									for (int i = 0; i < T6org_data_list.size(); i++) {
										T6org_data orgDataObj = T6org_data_list.get(i);
										if (null != orgDataObj) {
											// 检查该文件信息本地是否已经存在
											String sql = "select * from T6org_data t where t.download_path = '"
													+ orgDataObj.getDownload_path() + "' and t.name_ = '"
													+ orgDataObj.getName_() + "'";
											LOG.debug("检查远程文件信息本地是否存在：" + sql);
											List<T6org_data> rltList = Db.use(ConstantInitMy.db_dataSource_main)
													.query(sql);
											// 若远程文件信息本地不存在，则记录新文件信息
											if (rltList == null || rltList.size() == 0) {
												orgDataObj.saveGenIntId();
											} else {
												LOG.debug("文件信息本地已经写入，文件：" + orgDataObj.getDownload_path()
														+ orgDataObj.getName_());
											}
										}
									}
								}
							}
						}
					}
					ftpClient.disconnect();
				}
			} else {
				LOG.info("FTP初始化文件扫描开关为关闭");
			}
		} catch (Exception e) {
			LOG.debug("FTP初始化文件扫描发生异常:" + e);
		}
	}

	public static void initScanHttp() {
		// TODO Auto-generated method stub
		try {
			LOG.debug("自动任务WGET初始化文件扫描开始.");
			T2syslogService.addLog(EnumT2sysLog.INFO, DataConstants.SYS_USER_ID, DataConstants.SYS_USER_NM, "Init ftp scan", "WGET initialization file scan starting!");
			// 获取WGET初始化文件扫描参数
			// 初始化开关
			String inlzSwtc = ParamUtils.getParam(ParamUtils.PC_FTP_AUTO_DWLD, ParamUtils.INLZ_SWTC);
			// 初始数据开始日期
			String inlzStDt = ParamUtils.getParam(ParamUtils.PC_FTP_AUTO_DWLD, ParamUtils.INLZ_STDT);
			// 初始数据结束日期
			String inlzEdDt = ParamUtils.getParam(ParamUtils.PC_FTP_AUTO_DWLD, ParamUtils.INLZ_EDDT);
			LOG.debug("WGET初始化文件扫描参数:开关=" + inlzSwtc + "扫描开始日期=" + inlzStDt + "扫描结束日期=" + inlzEdDt);
			// 参数校验
			if(StringUtils.isBlank(inlzSwtc)) {
				LOG.error("初始化开关为空，自动任务WGET初始化文件扫描失败");
	            T2syslogService.addLog(EnumT2sysLog.ERROR_S, DataConstants.SYS_USER_ID, DataConstants.SYS_USER_NM, "Init ftp scan", "Switch for init cann't be null!");
				return;
			}
			if(StringUtils.isBlank(inlzStDt)) {
				LOG.error("初始化开始日期为空，自动任务WGET初始化文件扫描失败");
	            T2syslogService.addLog(EnumT2sysLog.ERROR_S, DataConstants.SYS_USER_ID, DataConstants.SYS_USER_NM, "Init ftp scan", "Start Date cann't be null!");
				return;
			}
			if(StringUtils.isBlank(inlzEdDt)) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				inlzEdDt = sdf.format(new Date());
				T2syslogService.addLog(EnumT2sysLog.WARN, DataConstants.SYS_USER_ID, DataConstants.SYS_USER_NM, "Init ftp scan", "End Date is null!");
				LOG.debug("初始化结束日期为空，自动任务WGET初始化文件扫描结束日期取当天日期：" + inlzEdDt);
			}
			// 如果开关为打开则开始逐天扫描
			if (DataConstants.FLAGE_ON.equals(inlzSwtc)) {
				// 初始化完成关闭开关
//				if (ParamUtils.updateParam(ParamUtils.PC_FTP_AUTO_DWLD, ParamUtils.INLZ_SWTC,
//						DataConstants.FLAGE_OFF)) {
//					LOG.info("初始化完成关闭开关关闭成功");
//				} else {
//					LOG.error("初始化完成关闭开关关闭失败");
//				}
				List<Date> dateList = new ArrayList<Date>();
				try {
					dateList = DatesUtils.getBetweenDates(inlzStDt, inlzEdDt);
				} catch (ParseException e) {
					LOG.error("获取初始化时间区间失败:" + e);
					LOG.error("自动任务WGET初始化文件扫描失败.");
					return;
				}
				if (null != dateList) {
					//
					final FtpUtils ftpUtils = new FtpUtils();
					// 倒着排序，最新的数据优先被下载，20181021
					Collections.reverse(dateList);
					for (Date eleDate : dateList) {
						// 更新初始数据开始日期
						inlzStDt = ParamUtils.getParam(ParamUtils.PC_FTP_AUTO_DWLD, ParamUtils.INLZ_STDT);						
						String dateStr = new SimpleDateFormat("yyyyMMdd").format(eleDate);
						if(dateStr.compareTo(inlzStDt) > 0) {
							ParamUtils.updateParam(ParamUtils.PC_FTP_AUTO_DWLD, ParamUtils.INLZ_STDT, dateStr);
						}

						Calendar cal = Calendar.getInstance();
						List<String> remotePathList = ParamUtils.getParamList(ParamUtils.PC_FTP_AUTO_DWLD,
								ParamUtils.PD_SRCFTP_ROOT_CTLG);
						if (null != remotePathList) {
							for (String remoteCsvPath : remotePathList) {
								remoteCsvPath = FileUtils.generateNewPath(remoteCsvPath, "01", eleDate, DataConstants.DOWNLOAD_TYPE_WGET);
								if(StringUtils.isBlank(remoteCsvPath))
									continue;
								List<T6org_data> T6org_data_list = FtpUtils.detecUrlFilDirList(remoteCsvPath);
								if (null != T6org_data_list) {
									for (int i = 0; i < T6org_data_list.size(); i++) {
										T6org_data orgDataObj = T6org_data_list.get(i);
										if (null != orgDataObj) {
											// 检查该文件信息本地是否已经存在
//											String sql = "select * from T6org_data t where t.download_path = '"
//													+ orgDataObj.getDownload_path() + "' and t.name_ = '"
//													+ orgDataObj.getName_() + "'";
											String sql = "select * from T6org_data t where t.name_ = '"
													+ orgDataObj.getName_() + "'";
											LOG.debug("检查远程文件信息本地是否存在：" + sql);
											List<T6org_data> rltList = Db.use(ConstantInitMy.db_dataSource_main)
													.query(sql);
											// 若远程文件信息本地不存在，则记录新文件信息
											if (rltList == null || rltList.size() == 0) {
												orgDataObj.saveGenIntId();
											} else {
												LOG.debug("文件信息本地已经写入，文件：" + orgDataObj.getDownload_path()
														+ orgDataObj.getName_());
											}
										}
									}
								} else {
									LOG.debug("远程目录" + remoteCsvPath + "下返回文件列表为空");
								}
							}
						}
					}
				}
			} else {
				T2syslogService.addLog(EnumT2sysLog.INFO, DataConstants.SYS_USER_ID, DataConstants.SYS_USER_NM, "Init ftp scan", "WGET initialization file scan switch is off!");
				LOG.info("WGET初始化文件扫描开关为关闭");
			}
		} catch (Exception e) {
			T2syslogService.addLog(EnumT2sysLog.ERROR_N, DataConstants.SYS_USER_ID, DataConstants.SYS_USER_NM, "Init ftp scan", "WGET initialization file scan abnormal!" + e);
			LOG.error("WGET初始化文件扫描发生异常:" + e);
		}
	}
	
	/*
	 * zhuchaobin, 201809-6, 判断有多少wget下载进程
	 * 个数maxDoloadProcessNums参数化
	 * > maxDoloadProcessNums 返回true，否则false
	 */
	public static boolean isDownloadProcessBusy(){
		boolean flag=false;
		try{
			Process p = Runtime.getRuntime().exec( "cmd /c tasklist ");
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			InputStream os = p.getInputStream();
			byte b[] = new byte[256];
			while(os.read(b)> 0)
			baos.write(b);
			String s = baos.toString();
			String str1 = s;
			// System.out.println(s);
			//方法1：替换法
			str1=str1.replace("wget",""); //将字符串中i替换为空,创建新的字符串
			Integer numsOfWgetProc = s.length()-str1.length();//两者之差为i出现次数
			return numsOfWgetProc > 5 ? true:false;
		}catch(java.io.IOException ioe){
		}
		return flag;
	}
	
	public static void autoWgetdownload() {
		try {
			//格式化时间 
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time=sdf.format(new Date());
			System.out.println("定时自动扫描待下载文件并启动wget下载"+time);
			T2syslogService.addLog(EnumT2sysLog.INFO, DataConstants.SYS_USER_ID, DataConstants.SYS_USER_NM, "Download remote ftp files", "Automatically scan files to be downloaded and start ftp download...");
			final String localfilePath = ParamUtils.getParam(ParamUtils.PC_FTP_AUTO_DWLD, ParamUtils.FILE_STRG_ADR);

			// 查询待下载文件列表:未下载+下载失败
			String sql = "select * from T6org_data t where t.status_ = '" + DataConstants.NOT_DOWNLOAD + "' or t.status_ = '" + DataConstants.DOWNLOAD_FAIL + "'";
			LOG.debug("查询待下载文件列表：" + sql);
			List<T6org_data> rltList = T6org_data.dao.find(sql);
			if(null != rltList) {
				for(int i = 0; i < rltList.size(); i ++) {
					T6org_data org_data = rltList.get(i);
					String ftpDir = (String) (org_data.getDownload_path()) + "//" + org_data.getName_();
					org_data.setDload_start_time(new Timestamp(System.currentTimeMillis()));
					org_data.setStatus_(DataConstants.DOWNLOAD_ING);
					org_data.update();
					// 下载后存放到本地归档根目录
					if(!FileUtils.folderCheckAndMake(localfilePath)) {
						LOG.error("检测并创建下载文件保存路径失败！路径：" + localfilePath);
					}
					while(FtpUtils.isDownloadProcessBusy()) {
						Integer sleepInteval = 1000;
						LOG.debug("autoHttpDownload SLEEP:" + sleepInteval);
						Thread.sleep(sleepInteval);
					}
					if(FtpUtils.wgetDownload(org_data.getName_(), org_data.getDownload_path(), localfilePath)) {
						LOG.debug("wget下载文件成功!");
						org_data.setStatus_(DataConstants.DOWNLOAD_ING);
						org_data.setDload_start_time(new Timestamp(System.currentTimeMillis()));
						org_data.setUserid(DataConstants.SYS_USER_ID);
						org_data.setStorage_path(localfilePath);
						org_data.update();
					} else {
						LOG.error("wget下载文件失败!");
						org_data.setStatus_(DataConstants.DOWNLOAD_FAIL);
						org_data.setDload_start_time(new Timestamp(System.currentTimeMillis()));
						org_data.setDload_end_time(new Timestamp(System.currentTimeMillis()));
						org_data.setUserid(DataConstants.SYS_USER_ID);
						org_data.setStorage_path(localfilePath);
						org_data.update();
					}						
				}		
			} else {
				LOG.debug("待下载文件列表为空");
			}
		} catch(Exception e) {
			LOG.error("定时自动扫描待下载文件并启动wget下载发生异常：" + e);
			T2syslogService.addLog(EnumT2sysLog.ERROR_N, DataConstants.SYS_USER_ID, DataConstants.SYS_USER_NM, "Download remote ftp files", "Automatically scan files to be downloaded and start ftp download abnormal!" + e);
		}
	}
	
}
