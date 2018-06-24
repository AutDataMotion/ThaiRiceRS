package thairice.autotask;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.jfinal.aop.Duang;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.cron4j.ITask;

import thairice.constant.ConstantInitMy;
import thairice.mvc.r4message_send.R4message_send;
import thairice.mvc.t3user.T3user;
import thairice.mvc.t3user.T3userService;
import thairice.mvc.t3user.TimeUtil;
import thairice.mvc.t8message.T8message;

/**
 * 
 * @description 用户的服务到期提醒
 */
public class overdueRemind extends Controller implements ITask {

	@Override
	public void run() {
		List<T3user> list = Duang.duang(T3userService.class).selectUsers();
		for (T3user t3user : list) {
/*			long day = TimeUtil.dateDiff(t3user.getTimestamp("Prdt_EfDt").toString(),
					t3user.getTimestamp("PD_ExDat").toString(), "yyyy-MM-dd HH:mm:ss", "d");*/
			
			long day = TimeUtil.dateDiff(TimeUtil.getNow(),t3user.getTimestamp("PD_ExDat").toString(), "yyyy-MM-dd HH:mm:ss", "d");
			//用户过期将其设置为04标识
			if(day<0) {
			    Db.use(ConstantInitMy.db_dataSource_main).update("UPDATE t3user SET status_='04' WHERE id=?", t3user.getBigInteger("id"));
			}
			// 距离服务到期还有7天提醒用户续费
			if (day <0||day ==30||day ==10||day ==3) {
				// 避免重复发送
				List<T8message> message_list = T8message.dao.find("select * from t8message where send_userid=? and flag=3",
						t3user.getBigInteger("id"));
				if (message_list.size()<4) {
					T8message t8message = new T8message();
					t8message.set("flag", 3);
					t8message.setContent("Your product service is ready to expire at:" + t3user.getTimestamp("PD_ExDat").toString() + ", In order to not affect your use, please continue to pay as soon as possible.");
					t8message.setAdd_time(new Timestamp(new Date().getTime()));
					t8message.setSend_userid(t3user.getBigInteger("id"));
					t8message.setType_("01");
					t8message.use(ConstantInitMy.db_dataSource_main).saveGenIntId();
					R4message_send send = new R4message_send();
					send.set("message_id", t8message.getLong("id"));
					send.set("receive_userid", t3user.getBigInteger("id"));
					send.set("status_", "01");
					System.out.println(send);
					send.use(ConstantInitMy.db_dataSource_main).saveGenIntId();
				}
			}
		}

	}

	@Override
	public void stop() {
		System.out.println("zx task is over");
	}
}
