package com.p2p.chat.container;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.p2p.chat.client.Client;
import com.p2p.chat.entity.User;

/**
 * 存放所有Client对象的容器
 * @author Administrator
 *
 */
public class ClientContainer implements Serializable,Iterable<Client> {

	private static final long serialVersionUID = -5267703862906425534L;
	
	/**
	 * 实现单例模式
	 */
	private static ClientContainer cc = new ClientContainer();
	
	private ClientContainer(){
		
	}
	
	public static ClientContainer getCC(){
		return cc;
	}

	/**
	 * 维护所有的client，保证其生命周期正常进行
	 */
	Map<User, Client> map = new ConcurrentHashMap<>();
	
	/**
	 * 向map中添加用户客户对
	 * @param user
	 * @param client
	 */
	public void addPair(User user,Client client){
		if(!map.containsKey(user)){
			map.put(user, client);
		}
	}
	/**
	 * 从map中移除用户客户对
	 * @param user
	 */
	public void removePair(User user){
		map.remove(user);
	}

	/**
	 * 查看map中是否存在user的client
	 * @param user
	 * @return
	 */
	public boolean contains(User user){
		return map.containsKey(user);
	}
	/**
	 * 实现Iterable接口，用于遍历所有值
	 */
	@Override
	public Iterator<Client> iterator() {
		return map.values().iterator();
	}

	
}
