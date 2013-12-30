package com.p2p.chat.client;

import java.io.IOException;

import com.p2p.chat.entity.User;

/**
 * 抽象客户端建造器
 * @author Administrator
 *
 */
public class ClientBuilder {

	/**
	 * 建造一个客户端实例
	 * @param remoter 对方的用户实例
	 * @return
	 * @throws IOException
	 */
	public static Client buildClient(User remoter) throws IOException{
		
		return new ClientImpl(remoter);
	}
	
}
