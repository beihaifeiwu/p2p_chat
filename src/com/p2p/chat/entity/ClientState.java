package com.p2p.chat.entity;

/**
 * 客户端所处的状态
 * @author Administrator
 *
 */
public enum ClientState {

	/**
	 * 繁忙状态，用户在但正在处理其他事情
	 */
	BUSY,
	
	/**
	 * 离开状态，用户离开电脑，一会儿回来
	 */
	AWAY,
	
	/**
	 * 空闲状态，用户正在电脑前，并且没有事情做
	 */
	IDLE,
	
	/**
	 * 正在输入的状态
	 */
	TYPING;
}
