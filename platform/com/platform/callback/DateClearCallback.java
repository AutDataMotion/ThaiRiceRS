package com.platform.callback;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import com.jfinal.plugin.activerecord.ICallback;

/**
 * 存储过程调用
 * @author DHJ  dongcb678@163.com
 */
public class DateClearCallback implements ICallback {
	
	private String ctype;

	@Override
	public Object call(Connection conn) throws SQLException {
		CallableStatement proc = null;

		proc = conn.prepareCall("{ call clearData(?) }");
		
		proc.setString(1, ctype);
		
		boolean bool = proc.execute();
		
//		ResultSet rs = (ResultSet) proc.getObject(2); // 处理返回数据
//		rs.getString("ids");
		
		return bool;
	}

	public String getCtype() {
		return ctype;
	}

	public void setCtype(String ctype) {
		this.ctype = ctype;
	}
	
}
