package thairice.mvc.t3user;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Properties;
import java.util.Vector;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.apache.commons.lang.StringUtils;

import com.jfinal.kit.PropKit;

/**
 * 邮件发送工具
 */
public class Mail {

	private Address[] to = null;
	private Address[] cc = null;
	private Address[] bcc = null;
	private String from = "";
	private String title = "";
	private String content = "";
	private String smtpHost = "";
	private int smtpPort = 25;
	private String content_type = MODE_TEXT;
	private String htmlMailDesc = "";

	private String smtpUser = "";
	private String smtpPassword = "";
	private boolean isAuthenticationSMTP = false;

	private Vector vFiles = new Vector();
	private Vector vURLs = new Vector();

	public Mail() {
	}

	/**
	 * 设置SMTP服务器，使用默认端口
	 * 
	 * @param server
	 *            SMTP服务器IP
	 */
	public void setSmtpHost(String server) {
		this.smtpHost = server;
	}

	/**
	 * 设置SMTP服务端口
	 * 
	 * @param server
	 *            SMTP服务器IP
	 * @param port
	 *            端口
	 */
	public void setSmtpHost(String server, int port) {
		this.smtpHost = server;
		this.smtpPort = port;
	}

	/**
	 * 设置收件人地地址
	 * 
	 * @param aEmail
	 *            收件人Email地址
	 */
	public void setTo(String aEmail) {
		String[] s = new String[1];
		s[0] = aEmail;
		this.to = getAddress(s);
	}

	/**
	 * 设置多个收件人地址
	 * 
	 * @param Emails
	 *            收件人Email地址
	 */
	public void setTo(String[] Emails) {
		this.to = getAddress(Emails);
	}

	/**
	 * 设置抄送人的地址
	 * 
	 * @param aEmail
	 *            抄送人地址
	 */
	public void setCC(String aEmail) {
		String[] s = new String[1];
		s[0] = aEmail;
		this.cc = getAddress(s);
	}

	/**
	 * 设置多个抄送人地址
	 * 
	 * @param Emails
	 *            抄送人地址
	 */
	public void setCC(String[] Emails) {
		this.cc = getAddress(Emails);
	}

	/**
	 * 设置地址
	 * 
	 * @param aEmail
	 *            地址
	 */

	public void setBCC(String aEmail) {
		String[] s = new String[1];
		s[0] = aEmail;
		this.bcc = getAddress(s);
	}

	/**
	 * 设置多个地址
	 * 
	 * @param Emails
	 *            地址
	 */
	public void setBCC(String[] Emails) {
		this.bcc = getAddress(Emails);
	}

	/**
	 * 设置发件人地地址
	 * 
	 * @param aEmail
	 *            发件人地址
	 */
	public void setFrom(String aEmail) {
		// if(!isValidEmailAddress(aEmail)){
		// throw new MyException("Invalid Email Address");
		// }
		this.from = aEmail;
	}

	/**
	 * 设置邮件主题
	 * 
	 * @param mailTitle
	 *            邮件主题
	 */
	public void setSubject(String mailTitle) {
		this.title = mailTitle;
	}

	/**
	 * 设置邮件文字内容
	 * 
	 * @param mailContent
	 *            邮件文字内容
	 */
	public void setBody(String mailContent) {
		this.content = mailContent;
	}

	/**
	 * 设置邮件字符类型
	 * 
	 * @param contentType
	 *            请从静态变量TEXT和HTML中
	 */
	public void setContentType(String contentType) {
		this.content_type = contentType;
	}

	/**
	 * 设置HTML格式邮件在一般模式下显示的说明
	 * 
	 * @param desc
	 *            说明文字
	 */
	public void setHtmlMailDesc(String desc) {
		this.htmlMailDesc = desc;
	}

	/**
	 * 设置SMTP服务器用户认证
	 * 
	 * @param username
	 *            用户账号
	 * @param password
	 *            密码
	 */
	public void setSmtpAuthentication(String username, String password) {
		this.smtpUser = username;
		this.smtpPassword = password;
		this.isAuthenticationSMTP = true;
	}

	/**
	 * 添加附件
	 * 
	 * @param afile
	 *            本地文件
	 */
	public void addAttachment(File afile) {
		vFiles.add(afile);
	}

	/**
	 * 添加附件
	 * 
	 * @param fileURL
	 *            文件URL
	 */
	public void addAttachment(URL fileURL) {
		vURLs.add(fileURL);
	}

	/**
	 * 标示邮件是否附带附件
	 * 
	 * @return
	 */
	public boolean hasAttachment() {
		return vFiles.size() + vURLs.size() > 0;
	}

	/**
	 * 发送邮件
	 */
	public void send() {
		try {
			Properties server = new Properties();
			if (StringUtils.isEmpty(this.smtpHost)) {
				throw new NullPointerException("Please set SMTP host");
			} else {
				server.put("mail.smtp.host", this.smtpHost);
			}
			server.put("mail.smtp.port", String.valueOf(this.smtpPort));
			if (this.isAuthenticationSMTP) {
				server.put("mail.smtp.auth", "true");
			}
			Session conn = Session.getInstance(server, null);

			MimeMessage msg = new MimeMessage(conn);
			if (StringUtils.isEmpty(this.from)) {
				throw new NullPointerException("Please set FROM address");
			} else {
				msg.setFrom(new InternetAddress(this.from));
			}
			if (this.to != null) {
				msg.setRecipients(Message.RecipientType.TO, this.to);
			}
			// else {
			// throw new NullPointerException("Please set TO address");
			// }
			if (this.cc != null) {
				msg.setRecipients(Message.RecipientType.CC, this.cc);
			}
			if (this.bcc != null) {
				msg.setRecipients(Message.RecipientType.BCC, this.bcc);
			}
			sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
			try {
				msg.setSubject(MimeUtility.encodeText(title, MimeUtility.mimeCharset("gb2312"), null));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			// + "?=");
			if (!hasAttachment()) {
				// 如果没有带附件
				if (this.isHtmlModeMail()) {
					// 是HTML格式的邮件
					if (!this.hasHtmlDesc()) {
						msg.setContent(this.content, this.content_type);
					} else {
						Multipart mp = new MimeMultipart();
						MimeBodyPart mbp = null;

						mbp = new MimeBodyPart();
						mbp.setContent(this.content, this.content_type);
						mp.addBodyPart(mbp);

						mbp = new MimeBodyPart();
						mbp.setContent(this.htmlMailDesc, this.MODE_TEXT);
						mp.addBodyPart(mbp);

						msg.setContent(mp);
					}
				} else {
					// 是文本格式的邮件
					msg.setText(this.content);
				}

			} else {
				// 有附件
				Multipart mp = new MimeMultipart();
				MimeBodyPart mbp = null;
				// 邮件正文
				for (int i = 0; i < vFiles.size(); i++) {
					mbp = new MimeBodyPart();
					File file = (File) vFiles.get(i);
					FileDataSource fds = new FileDataSource(file);
					mbp.setDataHandler(new DataHandler(fds));
					mbp.setFileName(file.getName());
					mp.addBodyPart(mbp);
				}
				for (int i = 0; i < vURLs.size(); i++) {
					mbp = new MimeBodyPart();
					URL url = (URL) vURLs.get(i);
					// URLDataSource uds=new URLDataSource(url);
					mbp.setDataHandler(new DataHandler(url));
					mbp.setFileName(url.getFile());
					mp.addBodyPart(mbp);
				}

				mbp = new MimeBodyPart();
				mbp.setContent(this.content, this.content_type);
				mp.addBodyPart(mbp);

				if (this.isHtmlModeMail() && this.hasHtmlDesc()) {
					mbp = new MimeBodyPart();
					mbp.setContent(this.htmlMailDesc, this.MODE_TEXT);
					mp.addBodyPart(mbp);
				}

				msg.setContent(mp);
			}
			msg.saveChanges();
			if (this.isAuthenticationSMTP) {
				Transport transport = conn.getTransport("smtp");
				transport.connect(this.smtpHost, this.smtpUser, this.smtpPassword);
				transport.sendMessage(msg, msg.getAllRecipients());
				transport.close();
			} else {
				Transport.send(msg, msg.getAllRecipients());
			}

		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	public boolean isValidEmailAddress(String email) {
		if (StringUtils.isEmpty(email))
			return false;
		if (email.indexOf("@") > 0)
			return !email.endsWith("@");
		return false;
	}

	private Address[] getAddress(String[] add) {
		Address[] a = new Address[add.length];
		for (int i = 0; i < add.length; i++) {
			try {
				a[i] = new InternetAddress(add[i]);
			} catch (AddressException ex) {
				ex.printStackTrace();
			}
		}
		return a;
	}

	public boolean isHtmlModeMail() {
		return this.content_type.equals(this.MODE_HTML);
	}

	public boolean hasHtmlDesc() {
		if (!this.isHtmlModeMail())
			return false;
		return !StringUtils.isEmpty(this.htmlMailDesc);
	}

	/**
	 * 普通模式
	 */
	public static final String MODE_TEXT = "text/plain;charset=gb2312";

	/**
	 * HTML模式
	 */
	public static final String MODE_HTML = "text/html;charset=gb2312";

	/**
	 * 
	 * @param subject
	 *            邮件主题
	 * @param content
	 *            邮件内容
	 * @param receiver
	 *            接收人
	 * @param type
	 *            邮件字符类型TEXT和HTML
	 * 
	 * @return
	 */
	public static boolean sendEmail(String subject, String content, String receiver, String type) {
		try {
			Mail mail = new Mail();
			mail.setSmtpHost(PropKit.get("config.mail.host"));
			String mailFrom = PropKit.get("config.mail.from");
			String password = PropKit.get("config.mail.password");
			mail.setFrom(mailFrom);
			mail.setSmtpAuthentication(mailFrom, password);
			mail.setTo(receiver);/** 发给谁 **/
			mail.setContentType(type);
			mail.setSubject(subject);/** 邮件主题 **/
			mail.setBody(content);/** 邮件内容 **/
			mail.send();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
