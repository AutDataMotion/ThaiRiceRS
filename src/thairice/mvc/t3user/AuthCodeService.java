package thairice.mvc.t3user;

import java.math.BigInteger;
import java.util.Random;

import com.jfinal.kit.HashKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import thairice.constant.ConstantInitMy;
import thairice.entity.Result;

/**
 * 授权码业务 用于一切需要授权的业务，例如： 1：邮件激活 2：密码找回 3：未来一切需要授权码的场景
 */
public class AuthCodeService {

	/**
	 * 激活账号
	 * 
	 * @param authCodeId
	 * @return
	 */
	public Result activate(String authCodeId) {
		Record record = Db.findFirst(" select * from thairice.auth_code where id=?", authCodeId);
		if (record == null) {
			return new Result(0, "Authorization code does not exist");
		}
		if (record.getLong("expireAt") < System.currentTimeMillis()) {
			return new Result(0, "Authorization code has expired");
		}
		T3user t3user = new T3user();
		t3user.set("id", record.get("userId"));
		t3user.set("activation", 1);
		boolean row = t3user.update();
		if (row) {
			// 激活后删除授权码
			Db.update("delete from thairice.auth_code where id=?", authCodeId);
			return new Result(1, "Activated successfully");
		} else {
			return new Result(1, "Activation fails");
		}

	}

	/**
	 * 重置密码
	 * 
	 * @param authCodeId
	 * @param pwd
	 * @return
	 */
	public Result reset_pass(String authCodeId, String pwd) {
		Record record = Db.findFirst(" select * from thairice.auth_code where id=?", authCodeId);
		if (record == null) {
			return new Result(0, "Authorization code does not exist");
		}
		if (record.getLong("expireAt") < System.currentTimeMillis()) {
			return new Result(0, "Authorization code has expired");
		}
		T3user t3user = new T3user();
		t3user.set("id", record.get("userId"));
		t3user.set("pwd", pwd);
		boolean row = t3user.update();
		if (row) {
			// 使用后删除授权码
			//Db.update("delete from thairice.auth_code where id=?", authCodeId);
			return new Result(1, "Recovery success");
		} else {
			return new Result(1, "Recovery failed");
		}
	}

	/**
	 * 
	 * @param userId
	 *            用户id
	 * @param authType
	 *            授权类型
	 * @param expireTime
	 *            授权码过期时长，过期时长是指授权码自创建时间起直到过期的时间长度，单位为秒
	 * @return
	 */
	public String createAuthCode(BigInteger userId, int authType, long expireTime) {
		long expireAt = System.currentTimeMillis() + (expireTime * 1000);
		Record record = new Record();
		String authCode = HashKit.md5("" + new Random().nextInt(1589745));
		record.set("id", authCode);
		record.set("userId", userId);
		record.set("type", authType);
		record.set("expireAt", expireAt);
		boolean flag = Db.use(ConstantInitMy.db_dataSource_main).save("auth_code", record);
		if (flag) {
			return authCode;
		} else {
			return null;
		}
	}
}
