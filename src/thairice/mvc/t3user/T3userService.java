package thairice.mvc.t3user;

import java.math.BigInteger;
import java.util.List;

import org.apache.log4j.Logger;

import com.jfinal.aop.Enhancer;
import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.platform.mvc.base.BaseService;

public class T3userService extends BaseService {

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(T3userService.class);

	public static final T3userService service = Enhancer.enhance(T3userService.class);

	public T3user SelectById(BigInteger id) {
		T3user mdl = T3user.dao.findFirst("select * from t3user where id=?", id);
		return mdl;
	}

	/**
	 * 查询改用户的未读消息(小红点提示)
	 *
	 * @param id
	 * @return
	 */
	public long getCount(BigInteger id) {
		long count = Db.queryLong(
				"SELECT COUNT(*)AS c FROM thairice.r4message_send s LEFT JOIN thairice.t3user u ON u.id=s.receive_userid WHERE u.id=? and s.status_='01'",
				id);
		System.out.println("count:" + count);
		return count;
	}

	/**
	 * '
	 *
	 * @param userName
	 *            用户名
	 * @param passWord
	 *            密码
	 * @return
	 * @throws Exception
	 */
	public Result login(Controller c, String userName, String passWord) {
		// 1.取用户
		T3user user = T3user.dao.findFirst("select * from t3user where account=? and type_='01'" , userName);
		if (user == null) {
			return new Result(0, "Account does not exist, please re-enter!");
		}
		// 2.验证密码
		if (!user.getStr(T3user.column_pwd).equals(passWord)) {
			return new Result(0, "Your password is incorrect, please re-enter!");
		}
		// 3.账户状态
		String status = user.getStr(T3user.column_status_);
		if (status.equals("1")) {
			return new Result(0, "Your account is still under review!");
		}
		if (status.equals("3")) {
			return new Result(0, "Your account is authorized to fail!");
		}
		if (status.equals("4")) {
			return new Result(0, "Your account has expired!");
		}
		if (user.getInt("activation") == 0) {
			return new Result(0,
					"Your account has not been activated. Please go to the email and click the link to activate");
		}
		c.setSessionAttr("user", user);
		return new Result(1, "Login Successful");
	}

	public List<T3user> selectUsers() {
		return T3user.dao.find("select * from t3user where type_='01'");
	}

	public List<T3user> selectUsersByChoose(String ids) {
		return T3user.dao.find("select * from t3user where type_='01' and id in(" + ids + ")");
	}

	public List<T3user> selectOperators() {
		return T3user.dao.find("select * from t3user where type_='02'");
	}

	public List<T3user> selectOperatorsByChoose(String ids) {
		return T3user.dao.find("select * from t3user where type_='02' and id in(" + ids + ")");
	}

	public boolean valiUserName(String userName) {
		// TODO Auto-generated method stub
		T3user user = T3user.dao.findFirst("select * from t3user where account=?", userName);
		return user == null ? true : false;
	}

	public boolean valiMailBox(String mailBox) {
		// TODO Auto-generated method stub
		T3user user = T3user.dao.findFirst("select * from t3user where email=?", mailBox);
		return user == null ? true : false;
	}
	public boolean valiMailBox2(String mailBox) {
		// TODO Auto-generated method stub
		T3user user = T3user.dao.findFirst("select * from t3user where email=?", mailBox);
		if(user!=null) {
		    if(user.getStatus_().equals("03")||user.getStatus_().equals("04")) {
			    return true;
			}   
		}
		return user == null ? true : false;
	}

	public boolean valiMailBoxForFindPass(String mailBox) {
		T3user user = T3user.dao.findFirst("select * from t3user where email=?", mailBox);
		return user == null ? false : true;
	}

	public T3user getByEmail(String email) {
		// TODO Auto-generated method stub
		T3user mdl = T3user.dao.findFirst("select * from t3user where email=?", email);
		return mdl;
	}

	/*********************************************************************** 后端 ***************************************/
	/**
	 * 后台登陆
	 *
	 * @param account
	 *            只允许系统管理员或操作员登录后台
	 * @return
	 */
	public T3user login(String account) {
		return T3user.dao.findFirst("select * from t3user where account=? and (type_='02' or type_='03')",
				account);
	}

	/**
	 * 操作员管理分页查询
	 *
	 * @param page
	 *            当前页
	 * @param row
	 *            分页大小
	 * @param name
	 *            模糊搜索名称
	 * @param orderColumn
	 *            排序字段
	 * @param orderDir
	 *            排序desc|asc
	 * @return
	 */
	public Page<T3user> selectByPage(int page, int row, String name, String orderColumn, String orderDir) {
		StringBuilder sb = new StringBuilder();
		sb.append(" FROM t3user  WHERE 1=1");
		// 多条件模糊查询
		if (StrKit.notBlank(name)) {
			sb.append(" AND CONCAT(IFNULL(name_,''),IFNULL(phone,''),IFNULL(email,''))  LIKE '%"
					+ name.trim().toLowerCase() + "%'");
		}
		sb.append(" AND type_='02'");
		sb.append(" ORDER BY " + orderColumn + " " + orderDir);
		return T3user.dao.paginate(page, row, "select *", sb.toString());
	}

	/**
	 * 用户管理分页查询
	 *
	 * @param page
	 * @param row
	 * @param userId
	 *            用户ID
	 * @param min
	 *            最小时间范围
	 * @param max
	 *            最大时间范围
	 * @param region
	 *            地区
	 * @param audit
	 *            审核状态
	 * @param orderColumn
	 *            排序字段
	 * @param orderDir
	 *            排序desc|asc
	 * @return
	 */
	public Page<T3user> selectUsersByPage(int page, int row, int userId, String name, String min, String max,
			String region, String audit,String type, String orderColumn, String orderDir) {
		StringBuilder sb = new StringBuilder();
		sb.append(" FROM t3user  WHERE 1=1");
		if (userId > 0) {
			sb.append(" AND id=" + userId);
		}
		if (StrKit.notBlank(name)) {
			sb.append(" AND CONCAT(IFNULL(name_,''),IFNULL(phone,''),IFNULL(email,''))  LIKE '%"
					+ name.trim().toLowerCase() + "%'");
		}
		if (StrKit.notBlank(min)) {
			max = StrKit.isBlank(max) ? TimeUtil.getNow() : max;
			sb.append(" AND  create_time BETWEEN '" + min + "'" + " AND DATE_ADD('" + max + "',interval 1 day)");
		}
		if (StrKit.notBlank(audit)) {
			sb.append(" AND status_='" + audit + "'");
		}
		if (StrKit.notBlank(type)) {
			sb.append(" AND PD_TpCd LIKE '%"+type+"%'");
		}
		sb.append(" AND type_='01'");
		sb.append(" ORDER BY " + orderColumn + " " + orderDir);
		return T3user.dao.paginate(page, row, "select *", sb.toString());
	}

	/**
	 * 删除单个或多个
	 *
	 * @param ids
	 * @return
	 */
	public int deletes(String ids) {
		int rows = Db.update(" DELETE FROM thairice.t3user WHERE id in (" + ids + ")");
		return rows;
	}

}
