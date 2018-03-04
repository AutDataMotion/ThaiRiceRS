package thairice.entity;
public class FtpInfoEntity {
	private String ftpHost;
	private String ftpName;
	private String ftpPassword;
	private int ftpPort;
	private String localfilePath;
	public String getFtpHost() {
		return ftpHost;
	}
	public void setFtpHost(String ftpHost) {
		this.ftpHost = ftpHost;
	}
	public String getFtpName() {
		return ftpName;
	}
	public void setFtpName(String ftpName) {
		this.ftpName = ftpName;
	}
	public String getFtpPassword() {
		return ftpPassword;
	}
	public void setFtpPassword(String ftpPassword) {
		this.ftpPassword = ftpPassword;
	}
	public int getFtpPort() {
		return ftpPort;
	}
	public void setFtpPort(int ftpPort) {
		this.ftpPort = ftpPort;
	}
	public String getLocalfilePath() {
		return localfilePath;
	}
	public void setLocalfilePath(String localfilePath) {
		this.localfilePath = localfilePath;
	}
	
}
