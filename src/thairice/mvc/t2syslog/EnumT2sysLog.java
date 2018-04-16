/**
 * ThaiRiceRS
 * create by zw at 2018年3月31日
 * version: v1.0
 */
package thairice.mvc.t2syslog;

/**
 * @author zw
 *
 */
public enum EnumT2sysLog {
		ERROR_S("1", "严重错误"),
		ERROR_N("2", "一般错误"),
		WARN("3", "一般警告"),
		INFO("4", "正常信息");
		private String id;
		private String name;
		
		private EnumT2sysLog(String aid, String aname){
			id = aid;
			name = aname;
		}

		public static String getNameById(String aid){
			for (EnumT2sysLog item : EnumT2sysLog.values()) {
				if (item.getId().equals(aid)) {
					return item.getName();
				}
			}
			// 没找到则返回为正常信息
			return INFO.getName();
		}
		
		/**
		 * @return the id
		 */
		public String getId() {
			return id;
		}

		/**
		 * @param id the id to set
		 */
		public void setId(String id) {
			this.id = id;
		}

		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * @param name the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}
}
