/**
 * ThaiRiceRS
 * create by zw at 2018年11月3日
 * version: v1.0
 */
package thairice.rpcjob;

import java.io.Serializable;

/**
 * @author zw
 *
 */
public class JobStatusMdl implements Serializable {

	private volatile Integer status;
	private volatile Integer cntTodo;
	private volatile Integer cntDoing;
	private volatile Integer cntSuc;
	private volatile Integer cntFailed;

	public JobStatusMdl() {
		init();
	}
	
	public void init(){
		status = 0;
		cntTodo = 0;
		cntDoing = 0;
		cntSuc = 0;
		cntFailed = 0;
	}

	public void start() {
		init();
		status = 1;
	}

	public synchronized void start(Integer cntAll) {
		start();
		cntTodo = cntAll - 1;
		cntDoing = 1;
	}

	public synchronized void succOne() {
		if(cntTodo > 1){
			cntTodo -= 1;
		} else {
			cntDoing = 0;
		}
		cntSuc += 1;
	}

	public synchronized void failedOne() {
		if(cntTodo > 1){
			cntTodo -= 1;
		} else {
			cntDoing = 0;
		}
		cntFailed += 1;
	}

	public void stop() {
		status = 0;
	}

	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * @return the cntTodo
	 */
	public Integer getCntTodo() {
		return cntTodo;
	}

	/**
	 * @param cntTodo
	 *            the cntTodo to set
	 */
	public void setCntTodo(Integer cntTodo) {
		this.cntTodo = cntTodo;
	}

	/**
	 * @return the cntSuc
	 */
	public Integer getCntSuc() {
		return cntSuc;
	}

	/**
	 * @param cntSuc
	 *            the cntSuc to set
	 */
	public void setCntSuc(Integer cntSuc) {
		this.cntSuc = cntSuc;
	}

	/**
	 * @return the cntFailed
	 */
	public Integer getCntFailed() {
		return cntFailed;
	}

	/**
	 * @param cntFailed
	 *            the cntFailed to set
	 */
	public void setCntFailed(Integer cntFailed) {
		this.cntFailed = cntFailed;
	}

	/**
	 * @return the cntDoing
	 */
	public Integer getCntDoing() {
		return cntDoing;
	}

	/**
	 * @param cntDoing the cntDoing to set
	 */
	public void setCntDoing(Integer cntDoing) {
		this.cntDoing = cntDoing;
	}
	
}
