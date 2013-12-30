package com.p2p.chat.client;

import com.p2p.chat.client.InfoEntry.InfoType;

/**
 * 工具类，用于建造信息条
 * @author Administrator
 *
 */
public class InfoEntryBuilder {

	/**
	 * 建造自己产生的信息条
	 * @param content
	 * @return
	 */
	public static TextInfoEntry buildSelfTextInfoEntry(String content){
		return new TextInfoEntry(content,InfoType.SELF);
	}
	/**
	 * 建造远方客户产生的信息条
	 * @param content
	 * @return
	 */
	public static TextInfoEntry buildRemoteTextInfoEntry(String content){
		return new TextInfoEntry(content,InfoType.REMOTE);		
	}
	/**
	 * 建造自动产生的信息条
	 * @param content
	 * @return
	 */
	public static TextInfoEntry buildAutoTextInfoEntry(String content){
		return new TextInfoEntry(content,InfoType.AUTO);		
	}
}
