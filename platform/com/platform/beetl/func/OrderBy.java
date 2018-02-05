package com.platform.beetl.func;

import org.apache.log4j.Logger;
import org.beetl.core.Context;
import org.beetl.core.Function;

import com.platform.dto.SplitPage;

/**
 * 分页列表排序函数
 * @author DHJ
 */
public class OrderBy implements Function {

	private static Logger log = Logger.getLogger(OrderBy.class);
	
	/**
	 * 分页列表排序
	 */
	@Override
	public Object call(Object[] arg, Context context) {
		if(arg.length != 1 || null == arg[0]){
			log.error("分页列表排序标签，参数不正确");
			return "";
		}
		
		String orderLaber = null;// 排序列
		String orderColunm = null;// 排序条件
		String orderMode = null;// 排序方式
		
		try {
			orderLaber = (String) arg[0];
			SplitPage splitPage = (SplitPage) context.getGlobal("splitPage");
			
			orderColunm = splitPage.getOrderColunm();
			orderMode = splitPage.getOrderMode();
		} catch (Exception e) {
			log.error("分页列表排序标签，获取参数异常：" + e.getMessage());
			return "";
		}

		log.debug("分页列表排序，orderLaber=" + orderLaber + "， orderColunm=" + orderColunm + "，orderMode= " + orderMode);
		
		if(null != orderMode && orderLaber.equals(orderColunm)){
			if(orderMode.equals("asc")){
				return "<img src='/files/images/platform/order/asc.gif' />";
			}else if(orderMode.equals("desc")){
				return "<img src='/files/images/platform/order/desc.gif' />";
			}
		}
		
		return "";
	}

}
