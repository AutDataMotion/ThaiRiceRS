package com.platform.beetl.tag;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.beetl.core.Tag;

import com.platform.constant.ConstantWebContext;
import com.platform.mvc.dict.Dict;
import com.platform.plugin.I18NPlugin;

/**
 * 字典输出标签：支持select、radio、checkbox
 * 
 * @author DHJ
 */
public class DictTag extends Tag {

	private static Logger log = Logger.getLogger(DictTag.class);

	@SuppressWarnings("unchecked")
	@Override
	public void render() {
		try {
			Map<String, String> param = (Map<String, String>) args[1];

			log.debug("获取标签属性");

			String type = param.get("type") == null ? "" : param.get("type");
			
			String id = param.get("id") == null ? "" : param.get("id");
			String name = param.get("name") == null ? "" : param.get("name");
			
			String class_ = param.get("class") == null ? "" : param.get("class");
			String style = param.get("style") == null ? "" : param.get("style");

			String number = param.get("number") == null ? "" : param.get("number");
			String defaultnumber = param.get("defaultnumber") == null ? "" : param.get("defaultnumber");

			log.debug("字典标签属性：type=" + type + "，id=" + id + "，name=" + name + "，class_=" + class_
					+ "，style=" + style + "，number=" + number + "，defaultnumber=" + defaultnumber);
			
			if(type.equals("")){
				ctx.byteWriter.writeString(select(id, name, class_, style, number, defaultnumber));
				
			}else if(type.equals("select")){
				ctx.byteWriter.writeString(select(id, name, class_, style, number, defaultnumber));
				
			}else if(type.equals("radio")){
				ctx.byteWriter.writeString(radio(id, name, class_, style, number, defaultnumber));
				
			}else if(type.equals("checkbox")){
				ctx.byteWriter.writeString(checkbox(id, name, class_, style, number, defaultnumber));
				
			} else {
				ctx.byteWriter.writeString(select(id, name, class_, style, number, defaultnumber));
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 下拉标签
	 * @param id
	 * @param name
	 * @param class_
	 * @param style
	 * @param number
	 * @param defaultnumber
	 * @return
	 */
	private String select(String id, String name, String class_, String style, String number, String defaultnumber){
		StringBuilder sb = new StringBuilder();
		if (null != id && !id.isEmpty()) {
			sb.append("<select id=\"").append(id).append("\" name=\"").append(name);
			sb.append("\" class=\"").append(class_).append("\" style=\"").append(style).append("\" >");
		} else {
			sb.append("<select name=\"").append(name).append("\" class=\"").append(class_);
			sb.append("\" style=\"").append(style).append("\" >");
		}

		Dict parentDict = Dict.dao.cacheGet(number);
		String parentI18n = parentDict.getStr("i18n");
		
		String val = "val";
		if(null != parentI18n && parentI18n.equals("1")){
			String localePram = (String) ctx.getGlobal(ConstantWebContext.request_localePram);
			val += I18NPlugin.i18n(localePram);
		}
		List<Dict> dictList = Dict.dao.cacheGetChild(number);

		for (Dict dict : dictList) {
			String status = dict.getStr("status");
			String numbersTemp = dict.getStr("numbers");
			String namesTemp = dict.getStr("names");
			String valueTemp = dict.getStr(val);
			
			if(null == status || status.equals("0")){
				log.debug("字典" + numbersTemp + "已经停用");
				continue;
			}
			
			if (null != defaultnumber && null != valueTemp && defaultnumber.equals(numbersTemp)) {// 默认选中
				sb.append("<option value=\"").append(valueTemp).append("\" selected=\"selected\">");
				sb.append(namesTemp);
				sb.append("</option>");

			} else {
				sb.append("<option value=\"").append(valueTemp).append("\">");
				sb.append(namesTemp);
				sb.append("</option>");
			}
		}
		sb.append("</select>");
		
		return sb.toString();
	}

	/**
	 * 单选
	 * @param id
	 * @param name
	 * @param class_
	 * @param style
	 * @param number
	 * @param defaultnumber
	 * @return
	 */
	private String radio(String id, String name, String class_, String style, String number, String defaultnumber){
		StringBuilder sb = new StringBuilder();
		
		Dict parentDict = Dict.dao.cacheGet(number);
		String parentI18n = parentDict.getStr("i18n");
		
		String val = "val";
		if(null != parentI18n && parentI18n.equals("1")){
			String localePram = (String) ctx.getGlobal(ConstantWebContext.request_localePram);
			val += I18NPlugin.i18n(localePram);
		}
		List<Dict> dictList = Dict.dao.cacheGetChild(number);

		for (Dict dict : dictList) {
			String status = dict.getStr("status");
			String numbersTemp = dict.getStr("numbers");
			String namesTemp = dict.getStr("names");
			String valueTemp = dict.getStr(val);
			if(null == status || status.equals("0")){
				log.debug("字典" + numbersTemp + "已经停用");
				continue;
			}
			if (null != defaultnumber && null != valueTemp && defaultnumber.equals(numbersTemp)) {// 默认选中
				sb.append(namesTemp).append("<input type=\"radio\" id=\"").append(id).append(dictList.indexOf(dict)).append("\" name=\"").append(name)
				.append("\" value=\"").append(valueTemp).append("\" class=\"").append(class_).append("\" style=\"").append(style).append("\" checked=\"checked\" >");
				
			} else {
				sb.append(namesTemp).append("<input type=\"radio\" id=\"").append(id).append(dictList.indexOf(dict)).append("\" name=\"").append(name)
				.append("\" value=\"").append(valueTemp).append("\" class=\"").append(class_).append("\" style=\"").append(style).append("\" >");
			}
		}
		sb.append("</select>");
		
		return sb.toString();
	}

	/**
	 * 多选
	 * @param id
	 * @param name
	 * @param class_
	 * @param style
	 * @param number
	 * @param defaultnumber
	 * @return
	 */
	private String checkbox(String id, String name, String class_, String style, String number, String defaultnumber){
		StringBuilder sb = new StringBuilder();
		
		Dict parentDict = Dict.dao.cacheGet(number);
		String parentI18n = parentDict.getStr("i18n");
		
		String val = "val";
		if(null != parentI18n && parentI18n.equals("1")){
			String localePram = (String) ctx.getGlobal(ConstantWebContext.request_localePram);
			val += I18NPlugin.i18n(localePram);
		}
		List<Dict> dictList = Dict.dao.cacheGetChild(number);

		for (Dict dict : dictList) {
			String status = dict.getStr("status");
			String numbersTemp = dict.getStr("numbers");
			String namesTemp = dict.getStr("names");
			String valueTemp = dict.getStr(val);
			if(null == status || status.equals("0")){
				log.debug("字典" + numbersTemp + "已经停用");
				continue;
			}
			if (null != defaultnumber && null != valueTemp && defaultnumber.equals(numbersTemp)) {// 默认选中
				sb.append(namesTemp).append("<input type=\"checkbox\" id=\"").append(id).append(dictList.indexOf(dict)).append("\" name=\"").append(name)
				.append("\" value=\"").append(valueTemp).append("\" class=\"").append(class_).append("\" style=\"").append(style).append("\" checked=\"checked\" >");
				
			} else {
				sb.append(namesTemp).append("<input type=\"checkbox\" id=\"").append(id).append(dictList.indexOf(dict)).append("\" name=\"").append(name)
				.append("\" value=\"").append(valueTemp).append("\" class=\"").append(class_).append("\" style=\"").append(style).append("\" >");
			}
		}
		sb.append("</select>");
		
		return sb.toString();
	}
}
