/**
 * ThaiRiceRS
 * create by zw at 2018年3月17日
 * version: v1.0
 */
package thairice.mvc.comm;

import java.io.Serializable;
import java.util.Map;

import csuduc.platform.util.ReflectionUtils;

/**
 * @author zw
 *
 */
public class ParamComm implements Serializable{
	private Long id;
	private String name1;
	private String name2;
	private String name3;
	private String name4;

	private String name5;
	private String name6;
	private String name7;
	private String name8;

	private String exportall;

	
	private String name9;
	private String name10;
	private String name11;
	private String name12;
	

	
	private Integer draw;
	private Integer pageIndex;
	private Integer pageSize;
	private Long total;
	
	ParamComm(){}

	
	
	/**
	 * @return the total
	 */
	public Long getTotal() {
		return total;
	}



	/**
	 * @param total the total to set
	 */
	public void setTotal(Long total) {
		this.total = total;
	}



	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the name1
	 */
	public String getName1() {
		return name1;
	}

	/**
	 * @param name1 the name1 to set
	 */
	public void setName1(String name1) {
		this.name1 = name1;
	}

	/**
	 * @return the name2
	 */
	public String getName2() {
		return name2;
	}

	/**
	 * @param name2 the name2 to set
	 */
	public void setName2(String name2) {
		this.name2 = name2;
	}

	/**
	 * @return the name3
	 */
	public String getName3() {
		return name3;
	}

	/**
	 * @param name3 the name3 to set
	 */
	public void setName3(String name3) {
		this.name3 = name3;
	}

	/**
	 * @return the pageIndex
	 */
	public Integer getPageIndex() {
		return pageIndex;
	}

	/**
	 * @param pageIndex the pageIndex to set
	 */
	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	/**
	 * @return the pageSize
	 */
	public Integer getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
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
	 * @return the name4
	 */
	public String getName4() {
		return name4;
	}



	/**
	 * @param name4 the name4 to set
	 */
	public void setName4(String name4) {
		this.name4 = name4;
	}

	


	/**
	 * @return the name5
	 */

	@Override
	public String toString() {
		return "ParamComm [id=" + id + ", name1=" + name1 + ", name2=" + name2 + ", name3=" + name3 + ", name4=" + name4
				+", name5=" + name5 + ", name6=" + name6 + ", name7=" + name7 + ", name8=" + name8 + ", exportall=" + exportall
				+ ", draw=" + draw + ", pageIndex=" + pageIndex + ", pageSize=" + pageSize + ", total=" + total + "]";

	}




	public String getName5() {
		return name5;
	}




	/**
	 * @param name5 the name5 to set
	 */

	public void setName5(String name5) {
		this.name5 = name5;
	}




	/**
	 * @return the name6
	 */

	public String getName6() {
		return name6;
	}




	/**
	 * @param name6 the name6 to set
	 */

	public void setName6(String name6) {
		this.name6 = name6;
	}




	/**
	 * @return the name7
	 */

	public String getName7() {
		return name7;
	}




	/**
	 * @param name7 the name7 to set
	 */

	public void setName7(String name7) {
		this.name7 = name7;
	}




	/**
	 * @return the name8
	 */

	public String getName8() {
		return name8;
	}




	/**
	 * @param name8 the name8 to set
	 */

	public void setName8(String name8) {
		this.name8 = name8;
	}




	public String getExportall() {
		return exportall;
	}
	/**
	 * @return the name9
	 */
	public String getName9() {
		return name9;
	}



	/**
	 * @param name9 the name9 to set
	 */
	public void setName9(String name9) {
		this.name9 = name9;

	}




	public void setExportall(String exportall) {
		this.exportall = exportall;
	}
	
	

	/**
	 * @return the name10
	 */
	public String getName10() {
		return name10;
	}



	/**
	 * @param name10 the name10 to set
	 */
	public void setName10(String name10) {
		this.name10 = name10;
	}



	/**
	 * @return the name11
	 */
	public String getName11() {
		return name11;
	}



	/**
	 * @param name11 the name11 to set
	 */
	public void setName11(String name11) {
		this.name11 = name11;
	}



	/**
	 * @return the name12
	 */
	public String getName12() {
		return name12;
	}



	/**
	 * @param name12 the name12 to set
	 */
	public void setName12(String name12) {
		this.name12 = name12;
	}


}
