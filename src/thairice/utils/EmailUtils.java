package thairice.utils;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;
import org.apache.log4j.Logger;
import org.eclipse.jetty.util.log.Log;

import com.jfinal.kit.PropKit;

import thairice.entity.EmailEntity;
import thairice.mvc.t1parameter.T1parameter;
import thairice.mvc.t3user.T3user;

public class EmailUtils {
		private static Logger LOG = Logger.getLogger(EmailUtils.class);
	   private static String host = "smtp.163.com";
	   private static int port = 25;
//	   private static String userName = "17611505056@163.com";
//	   private static String password = "a@1234567";
	   private static String userName = "nanjing2007@163.com";
	   private static String password = "lingyun625107";

	   /**
	    * 初始化发送邮件参数
	    * 
	    * @throws Exception
	    */
	   public static void initEmailParm(){
		   
	   }
	   /**
	    * 发送文本邮件
	    * @param to:收件人邮箱地址  subject：邮件题目  msg：邮件内容
	    * @throws Exception
	    */
	   public static boolean sendTextMail(String to, String subject, String msg) throws Exception
	   {
		  try {
		      SimpleEmail mail = new SimpleEmail();
		      // 设置邮箱服务器信息
		      mail.setSmtpPort(port);
		      mail.setHostName(host);
		      // 设置密码验证器
		      mail.setAuthentication(userName, password);
		      // 设置邮件发送者
		      mail.setFrom(userName);
		      // 设置邮件接收者
		      mail.addTo(to);
		      // 设置邮件编码
		      mail.setCharset("UTF-8");
		      // 设置邮件主题
		      mail.setSubject(subject);
		      // 设置邮件内容
		      mail.setMsg(msg);
		      // 设置邮件发送时间
		      mail.setSentDate(new Date());
		      // 发送邮件
		      mail.send();
		      return true;
		  } catch(Exception e) {
			  LOG.error("邮件发送发生异常：" + e);
			  return false;
		  }
	   }

	   /**
	    * 发送Html邮件
	    * 
	    * @throws Exception
	    */
	   public void sendHtmlMail(String to, String subject, String msg) throws Exception
	   {
	      HtmlEmail mail = new HtmlEmail();
	      // 设置邮箱服务器信息
	      mail.setSmtpPort(port);
	      mail.setHostName(host);
	      // 设置密码验证器
	      mail.setAuthentication(userName, password);
	      // 设置邮件发送者
	      mail.setFrom(userName);
	      // 设置邮件接收者
	      mail.addTo(to);
	      // 设置邮件编码
	      mail.setCharset("UTF-8");
	      // 设置邮件主题
	      mail.setSubject(subject);
	      // 设置邮件内容
	      mail.setHtmlMsg(msg);
//	            "<html><body><img src='http://avatar.csdn.net/A/C/A/1_jianggujin.jpg'/><div>this is a HTML email.</div></body></html>");
	      // 设置邮件发送时间
	      mail.setSentDate(new Date());
	      // 发送邮件
	      mail.send();
	   }

	   /**
	    * 发送内嵌图片邮件
	    * 
	    * @throws Exception
	    */
	   public void sendImageMail(String to, String subject, String msg) throws Exception
	   {
	      HtmlEmail mail = new HtmlEmail();
	      // 设置邮箱服务器信息
	      mail.setSmtpPort(port);
	      mail.setHostName(host);
	      // 设置密码验证器
	      mail.setAuthentication(userName, password);
	      // 设置邮件发送者
	      mail.setFrom(userName);
	      // 设置邮件接收者
	      mail.addTo(to);
	      // 设置邮件编码
	      mail.setCharset("UTF-8");
	      // 设置邮件主题
	      mail.setSubject(subject);
	      mail.embed(new File("1_jianggujin.jpg"), "image");
	      // 设置邮件内容
//	      String htmlText = "<html><body><img src='cid:image'/><div>this is a HTML email.</div></body></html>";
//	      mail.setHtmlMsg(htmlText);
	      mail.setHtmlMsg(msg);
	      // 设置邮件发送时间
	      mail.setSentDate(new Date());
	      // 发送邮件
	      mail.send();
	   }

	   /**
	    * 发送附件邮件
	    * 
	    * @throws Exception
	    */
	   public void sendAttachmentMail(String to) throws Exception
	   {
	      MultiPartEmail mail = new MultiPartEmail();
	      // 设置邮箱服务器信息
	      mail.setSmtpPort(port);
	      mail.setHostName(host);
	      // 设置密码验证器
	      mail.setAuthentication(userName, password);
	      // 设置邮件发送者
	      mail.setFrom(userName);
	      // 设置邮件接收者
	      mail.addTo(to);
	      // 设置邮件编码
	      mail.setCharset("UTF-8");
	      // 设置邮件主题
	      mail.setSubject("Test Email");

	      mail.setMsg("this is a Attachment email.this email has a attachment!");
	      // 创建附件
	      EmailAttachment attachment = new EmailAttachment();
	      attachment.setPath("1_jianggujin.jpg");
	      attachment.setDisposition(EmailAttachment.ATTACHMENT);
	      attachment.setName("1_jianggujin.jpg");
	      mail.attach(attachment);

	      // 设置邮件发送时间
	      mail.setSentDate(new Date());
	      // 发送邮件
	      mail.send();
	   }

	   /**
	    * 发送内嵌图片和附件邮件
	    * 
	    * @throws Exception
	    */
	   public void sendImageAndAttachmentMail(String to) throws Exception
	   {
	      HtmlEmail mail = new HtmlEmail();
	      // 设置邮箱服务器信息
	      mail.setSmtpPort(port);
	      mail.setHostName(host);
	      // 设置密码验证器
	      mail.setAuthentication(userName, password);
	      // 设置邮件发送者
	      mail.setFrom(userName);
	      // 设置邮件接收者
	      mail.addTo(to);
	      // 设置邮件编码
	      mail.setCharset("UTF-8");
	      // 设置邮件主题
	      mail.setSubject("Test Email");
	      mail.embed(new File("1_jianggujin.jpg"), "image");
	      // 设置邮件内容
	      String htmlText = "<html><body><img src='cid:image'/><div>this is a HTML email.</div></body></html>";
	      mail.setHtmlMsg(htmlText);
	      // 创建附件
	      EmailAttachment attachment = new EmailAttachment();
	      attachment.setPath("1_jianggujin.jpg");
	      attachment.setDisposition(EmailAttachment.ATTACHMENT);
	      attachment.setName("1_jianggujin.jpg");
	      mail.attach(attachment);
	      // 设置邮件发送时间
	      mail.setSentDate(new Date());
	      // 发送邮件
	      mail.send();
	   }

	   /** 
	    * 产生随机的六位数 
	    * @return 
	    */  
	   public static String getRadSix(){  
	       Random rad=new Random();
	       String radStr = rad.nextInt(1000000)+"";
	       if(6 == radStr.length())
	    	   return radStr;
	       else
	    	   return getRadSix();
	   } 
	   
		public static boolean findPwd(String account) throws Exception {
			try {
				// 获取参数找回密码邮件模板
				String msgTemplate = ParamUtils.getParam("10000002", "001");
				// 查找用户姓名和用户邮箱
				String sql = "select * from T3user t where t.account = '" + account + "'";
				LOG.debug("根据用户账户查找用户信息：" + sql);				
				T3user user = T3user.dao.findFirst(sql);
				msgTemplate = msgTemplate.replace("$1", user.getName_());
				msgTemplate = msgTemplate.replace("$2", getRadSix());
				Calendar calendar= Calendar.getInstance();
				calendar.set(Calendar.MINUTE, 30);
				SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
				String expriTime = dateFormat.format(calendar.getTime());
				msgTemplate = msgTemplate.replace("$3", expriTime);
				// 更新用户信息：验证码，验证码失效
				sendTextMail(user.getEmail(), "泰国农业遥感系统找回密码", msgTemplate);
				return true;
			} catch(Exception e) {
				LOG.error("找回用户密码发生异常" + e);
				return false;
			}
		}
		
		public static void main(String[] args) throws Exception {
			// String str = "MOD13Q1.A2001033.h00v08.006.2015141152020.hdf";
			// String[] fileAttr = str.split("\\.");
			// System.out.println(fileAttr.length);
//			for(int i = 0; i < 100; i ++)
//				System.out.println(getRadSix());
			
			String msgTemplate = ParamUtils.getParam("10000002", "001");
			msgTemplate.replace("$1", "杨涛");
			msgTemplate.replace("$2", getRadSix());
			sendTextMail("nanjing2007@163.com", "泰国农业遥感系统找回密码", msgTemplate);
		}
}
