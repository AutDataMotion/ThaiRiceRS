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
		ERROR_S("1", "serious error"),
		ERROR_N("2", "normal error"),
		WARN("3", "warnning"),
		INFO("4", "information");
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
			return aid;
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
