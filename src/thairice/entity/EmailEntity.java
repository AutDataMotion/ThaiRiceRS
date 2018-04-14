package thairice.entity;

public class EmailEntity {
	private static String host;
	private static int port;
	private static String userName;
	private static String password;
	private static String to;
	private static String subject;
	private static String msg;

	public static String getHost() {
		return host;
	}

	public static void setHost(String host) {
		EmailEntity.host = host;
	}

	public static int getPort() {
		return port;
	}

	public static void setPort(int port) {
		EmailEntity.port = port;
	}

	public static String getUserName() {
		return userName;
	}

	public static void setUserName(String userName) {
		EmailEntity.userName = userName;
	}

	public static String getPassword() {
		return password;
	}

	public static void setPassword(String password) {
		EmailEntity.password = password;
	}

	public static String getTo() {
		return to;
	}

	public static void setTo(String to) {
		EmailEntity.to = to;
	}

	public static String getSubject() {
		return subject;
	}

	public static void setSubject(String subject) {
		EmailEntity.subject = subject;
	}

	public static String getMsg() {
		return msg;
	}

	public static void setMsg(String msg) {
		EmailEntity.msg = msg;
	}

}
