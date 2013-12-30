package com.p2p.chat.client;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.p2p.chat.entity.ClientState;
import com.p2p.chat.entity.User;

/**
 * 代表客户端的实例，负责与服务器通信以及维护通信状态
 * @author Administrator
 *
 */
public class ClientImpl implements Client {

	private static final long serialVersionUID = 6634884987460497895L;

	/**
	 * 通信开始的时间
	 */
	Date startTime;
	
	/**
	 * 通信的对方
	 */
	User remoter;
	
	/**
	 * 客户状态类
	 */
	ClientState selfState;
	/**
	 * 远方客户状态
	 */
	ClientState remoteState;
	/**
	 * 信息条列表
	 */
	List<InfoEntry> infoEntries;
	/**
	 * @return the startTime
	 */
	public Date getStartTime() {
		return startTime;
	}
	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	/**
	 * @return the remoter
	 */
	public User getRemoter() {
		return remoter;
	}
	/**
	 * @param remoter the remoter to set
	 */
	public void setRemoter(User remoter) {
		this.remoter = remoter;
	}
	
	/**
	 * @return the selfState
	 */
	public ClientState getSelfState() {
		return selfState;
	}
	/**
	 * @param selfState the selfState to set
	 */
	public void setSelfState(ClientState selfState) {
		this.selfState = selfState;
	}
	/**
	 * @return the remoteState
	 */
	public ClientState getRemoteState() {
		return remoteState;
	}
	/**
	 * @param remoteState the remoteState to set
	 */
	public void setRemoteState(ClientState remoteState) {
		this.remoteState = remoteState;
	}
	public ClientImpl(User remoter) throws IOException {
		this.remoter = remoter;
		this.infoEntries = new LinkedList<>();
	}

	@Override
	public String toString() {
		return "Client [startTime=" + startTime + ", remoter=" + remoter
				+ ", selfState=" + selfState + ", remoteState=" + remoteState
				+ "]";
	}
	@Override
	public void doInit() {
		this.remoteState = ClientState.IDLE;
		this.selfState = ClientState.IDLE;
		
	}
	@Override
	public void doInput(InfoEntry infoEntry) {
		this.infoEntries.add(infoEntry);
		
	}
	@Override
	public void doOutput(InfoEntry infoEntry) {
		this.infoEntries.add(infoEntry);
		
	}
	@Override
	public void doEnd() {
		
		
	}
	@Override
	public List<InfoEntry> getInfoEntryList() {
		return this.infoEntries;
	}
	
}
