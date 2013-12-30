package com.p2p.chat.client;

import java.io.Serializable;
import java.util.Date;

/**
 * 每次通信的信息条，实现此接口的信息必须是Client可视的
 * @author Administrator
 *
 */
public interface InfoEntry extends Serializable {
	/**
	 * 识别信息条的产生类型
	 * @author Administrator
	 *
	 */
	public static enum InfoType{
		SELF,REMOTE,AUTO
	}
	/**
	 * 识别信息内容的类型
	 * @author Administrator
	 *
	 */
	public static enum ContentType{
		TEXT
	}
	/**
	 * 获取信息产生的类型
	 * @return
	 */
	public InfoType getInfoType();
	/**
	 * 获取信息内容的类型
	 * @return
	 */
	public ContentType getContentType();
	/**
	 * 获取消息条中的内容
	 * @return
	 */
	public Object getContent();
	/**
	 * 获取消息产生的时间
	 * @return
	 */
	public Date getGenerateTime();
}
