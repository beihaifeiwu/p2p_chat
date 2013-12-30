package com.p2p.chat.client;

import java.util.Date;

/**
 * 文本信息类
 * @author Administrator
 *
 */
public class TextInfoEntry extends AbstractInfoEntry {

	private static final long serialVersionUID = -8686295363211613008L;
	
	String content;

	@Override
	public Object getContent() {
		return content;
	}

	public TextInfoEntry(String content,InfoType infoType) {
		this.content = content;
		this.date = new Date();
		this.infoType = infoType;
	}

}
