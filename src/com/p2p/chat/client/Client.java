package com.p2p.chat.client;

import java.io.Serializable;
import java.util.List;

import com.p2p.chat.entity.ClientState;

/**
 * Client的生命周期协议
 * @author Administrator
 *
 */
public interface Client extends Serializable {

	/**
	 * 生命周期的第一阶段：初始化
	 * 进行client正常通信前的准备工作
	 */
	public void doInit();
	
	/**
	 * 生命周期的中间阶段：处理输入
	 * 对client端的用户输入进行处理，通常是通过网络将数据发送出去
	 */
	public void doInput(InfoEntry infoEntry);
	
	/**
	 * 生命周期的中间阶段：处理输出
	 * 对远程客户端的传输过来的数据进行处理，通常是显示到相应的端口
	 */
	public void doOutput(InfoEntry infoEntry);
	
	/**
	 * 生命周期的最后阶段：结束阶段
	 * 做一些结束前的清理工作
	 */
	public void doEnd();
	
	/**
	 * 辅助方法，获取信息列表
	 * @return
	 */
	public List<InfoEntry> getInfoEntryList();
	/**
	 * 获取用户自己的客户端状态
	 * @return
	 */
	public ClientState getSelfState();
	/**
	 * 设置用户自己客户端的状态
	 * @param selfState
	 */
	public void setSelfState(ClientState selfState);
	/**
	 * 获得远端客户端的实例状态
	 * @return
	 */
	public ClientState getRemoteState();
	/**
	 * 设置远端客户端的实例状态
	 * @param remoteState
	 */
	public void setRemoteState(ClientState remoteState);
	
}
