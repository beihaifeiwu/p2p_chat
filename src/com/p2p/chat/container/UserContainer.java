package com.p2p.chat.container;


import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.p2p.chat.entity.User;

/**
 * 存放用户的容器
 * @author Administrator
 *
 */
public class UserContainer implements Iterable<User> {
	
	/**
	 * 实现单例模式
	 */
	private static UserContainer uc = new UserContainer();
	
	private UserContainer(){
		
	}
	
	public static UserContainer getUC(){
		return uc;
	}

	/**
	 * 维护所有的用户列表
	 */
	Map<String,User> map = new ConcurrentHashMap<>();
	
	/**
	 * 根据索引获得相应的用户引用
	 * @param key 用户的ip地址
	 * @return
	 */
	public User get(String key){
		return map.get(key);
	}

	/**
	 * 实现Iterable接口中的方法以便遍历所有用户
	 */
	@Override
	public Iterator<User> iterator() {
		return map.values().iterator();
	}
	
	/**
	 * 添加用户到列表中
	 * @param user
	 */
	public void add(String ip,User user){
		if(!map.containsValue(user)){
			map.put(ip, user);
		}
	}
	
	/**
	 * 从用户列表中移除相应的用户
	 * @param user
	 */
	public void remove(String ip){
		map.remove(ip);
	}
	
	/**
	 * 容器中是否存在user
	 * @param user
	 * @return
	 */
	public boolean contains(User user){
		return map.containsValue(user);
	}
}
