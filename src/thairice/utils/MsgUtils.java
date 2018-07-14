package thairice.utils;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;
import org.apache.log4j.Logger;
import org.eclipse.jetty.util.log.Log;

import com.jfinal.kit.PropKit;

import thairice.entity.EmailEntity;
import thairice.entity.MsgEntity;
import thairice.mvc.r4message_send.R4message_send;
import thairice.mvc.t1parameter.T1parameter;
import thairice.mvc.t3user.T3user;
import thairice.mvc.t8message.T8message;

public class MsgUtils {
	private static Logger LOG = Logger.getLogger(MsgUtils.class);

	/**
	 * @Name:sendMsg
	 * @Description:发送单条消息
	 * @author: zhuchaobin
	 * @date: 2018年3月11日 上午10:48:44
	 * 
	 */
	public static void sendMsg(BigInteger send_userid, BigInteger userid, String subject, String msg)
			throws Exception {
		try {
			T8message nweMgs = new T8message();
			R4message_send msgSend = new R4message_send();
			// 系统消息时间
			nweMgs.setAdd_time(new Timestamp(System.currentTimeMillis()));
			// 系统消息内容
			// 发送人ID
			// 消息类型代码
			// 群发标志
			nweMgs.setSend_userid(userid);
		} catch (Exception e) {
			LOG.error("消息发送发生异常:" + e);
		}
	}

	/**
	 * 发送消息通知
	 * 
	 * @param MsgEntity： 消息发送实体
	 * send_userid:消息发送人ID userid：消息接收人ID subject：消息题目  msg:消息内容 flag:群发标志 templId:模板编号
	 * @throws Exception
	 */
	public static void sendMsgs(MsgEntity msgEntity)
			throws Exception {
		try {
			T8message nweMgs = new T8message();
			// 系统消息时间
			nweMgs.setAdd_time(new Timestamp(System.currentTimeMillis()));
			// 系统消息内容
			// 判定是否是模板类消息
			if(!StringUtils.isBlank(msgEntity.getTemplateId())) {
				// 根据不同消息模板填充消息内容
				String msgTemplage = ParamUtils.getParam("", msgEntity.getTemplateId());
				if("".equals(msgEntity.getTemplateId())){// 消息模板1
					msgTemplage = msgTemplage.replace("$1", "");
					msgTemplage = msgTemplage.replace("$2", "");
					msgTemplage = msgTemplage.replace("$3", "");
				} else if("".equals(msgEntity.getTemplateId())){// 消息模板2
					msgTemplage = msgTemplage.replace("$1", "");
					msgTemplage = msgTemplage.replace("$2", "");
					msgTemplage = msgTemplage.replace("$3", "");
				} else if("".equals(msgEntity.getTemplateId())){// 消息模板3
					msgTemplage = msgTemplage.replace("$1", "");
					msgTemplage = msgTemplage.replace("$2", "");
					msgTemplage = msgTemplage.replace("$3", "");
				} 
			}
			// 发送人ID
			nweMgs.setSend_userid(msgEntity.getSend_userid());
			// 消息类型代码
			nweMgs.setType_(msgEntity.getType_());
			// 群发标志
			nweMgs.setFlag(msgEntity.getFlag());
			nweMgs.saveGenIntId();
			// 记录发送状态表
			if("1".equals(msgEntity.getFlag())) {			// 群发
				// 获取所有待接收用户ID列表
				String sql = "select * from T3user t";
				LOG.debug("获取所有待接收用户ID列表：" + sql);
				List<T3user> rltList = T3user.dao.find(sql);
				for(T3user user : rltList) {
					R4message_send msgSend = new R4message_send();
					msgSend.setReceive_userid((BigInteger)user.getId());
					msgSend.setStatus_("0");// 0：未读取
					msgSend.update();
					// 刷新界面用户未读消息数
				}
			} else {			// 非群发
				R4message_send msgSend = new R4message_send();
				msgSend.setReceive_userid(msgEntity.getReceive_userid());
				msgSend.setStatus_("0");// 0：未读取
				msgSend.update();
				// 刷新界面用户未读消息数
			}
		} catch (Exception e) {
			LOG.error("消息发送发生异常：" + e);
		}
	}

	/**
	 * 读取消息通知:返回消息内容，并设置发送状态为已发送 1
	 * @param ： usrId：用户ID msgId:消息ID
	 * 
	 * @throws Exception
	 */
	public static String sendMsgs(BigInteger usrId, String msgId) {
		// 查询消息内容
		String sql = "select * from T8message t where t.id = '" + msgId + "'";
		LOG.debug("根据消息ID查询消息内容：" + sql);
		T8message t8message = T8message.dao.findFirst(sql);
		String msgContent = "";
		if(null != t8message) {
			msgContent = t8message.getContent();
			LOG.debug("根据消息ID查询消息内容：" + msgContent);
		} else
			return null;
		// 设置发送发送状态
		R4message_send msgSend = new R4message_send();
		msgSend.setReceive_userid(usrId);
		msgSend.setStatus_("1");// 0：未读取
		msgSend.update();
		// 刷新用户界面未读消息数
		return msgContent;
	}

	public static void main(String[] args) throws Exception {
//		 String str = "MOD13Q1.A2001033.h00v08.006.2015141152020.hdf";
		 String str = "MOD13Q1|A2001033|h00v08|006|2015141152020|hdf";
		 String[] fileAttr = str.split("\\|");
		 System.out.println(fileAttr.length);
		// for(int i = 0; i < 100; i ++)
		// System.out.println(getRadSix());
	}
}
