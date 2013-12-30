package com.p2p.chat.event;

import com.p2p.chat.entity.User;
import com.p2p.chat.event.Event.Direction;

/**
 * 事件建造者
 * @author Administrator
 *
 */
public class EventBuilder {
	
	/**
	 * 建造一个登录事件，事件往服务器端流动
	 * @param user 登陆的用户
	 * @return 建造好的登录事件
	 */
	public static LoginEvent buildLoginEvent(User user){
		LoginEvent event = null;
		event = new LoginEvent(user);
		event.setDirection(Direction.SERVER);
		return event;
	}
	/**
	 * 建造一个下线事件，事件往服务器端流动
	 * @param user
	 * @return
	 */
	public static LogoutEvent buildLogoutEvent(User user){
		LogoutEvent event = null;
		event = new LogoutEvent(user);
		event.setDirection(Direction.SERVER);
		return event;
	}

	/**
	 * 建造一个退出事件，事件往服务器端流动
	 * @return
	 */
	public static ExitEvent buildExitEvent(){
		ExitEvent event = new ExitEvent();
		event.setDirection(Direction.SERVER);
		return event;
	}
}
