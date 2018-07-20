/**
 * PureSport
 * create by zw at 2018年4月28日
 * version: v1.0
 */
package thairice.mvc.comm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.mysql.fabric.xmlrpc.base.Array;
import com.platform.mvc.base.BaseModel;

/**
 * @author zw
 *
 */
public class ResListPageComm<T> implements Serializable{
	private List<T> data;
	private List<String> options = new ArrayList<>();
	private List<String> files = new ArrayList<>();
	private Integer draw ;
	private String recordsTotal;
	private String recordsFiltered;
	
	public ResListPageComm(){}
	
	public ResListPageComm(List<T>  tableList){
		
		data = CollectionUtils.isEmpty(tableList)?Collections.EMPTY_LIST:(List) tableList;

	}

	/**
	 * @return the data
	 */
	public List<T> getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(List<T> data) {
		this.data = data;
	}

	/**
	 * @return the options
	 */
	public List<String> getOptions() {
		return options;
	}

	/**
	 * @param options the options to set
	 */
	public void setOptions(List<String> options) {
		this.options = options;
	}

	/**
	 * @return the files
	 */
	public List<String> getFiles() {
		return files;
	}

	/**
	 * @param files the files to set
	 */
	public void setFiles(List<String> files) {
		this.files = files;
	}

	/**
	 * @return the draw
	 */
	public Integer getDraw() {
		return draw;
	}

	/**
	 * @param draw the draw to set
	 */
	public void setDraw(Integer draw) {
		this.draw = draw;
	}

	/**
	 * @return the recordsTotal
	 */
	public String getRecordsTotal() {
		return recordsTotal;
	}

	/**
	 * @param recordsTotal the recordsTotal to set
	 */
	public void setRecordsTotal(String recordsTotal) {
		this.recordsTotal = recordsTotal;
		this.recordsFiltered = recordsTotal;
	}

	/**
	 * @return the recordsFiltered
	 */
	public String getRecordsFiltered() {
		return recordsFiltered;
	}

	/**
	 * @param recordsFiltered the recordsFiltered to set
	 */
	public void setRecordsFiltered(String recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}
	
	
}
