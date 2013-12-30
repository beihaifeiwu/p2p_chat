package com.p2p.chat.processor;

import org.apache.log4j.Logger;

import com.p2p.chat.Server.ServerUtils;
import com.p2p.chat.container.UserContainer;
import com.p2p.chat.entity.User;
import com.p2p.chat.event.LoginEvent;

/**
 * 集中处理所有的login事件
 * @author Administrator
 *
 */
public class LoginProcessor extends AbstractProcessor {

	static Logger log = Logger.getLogger(LoginProcessor.class);
	
	/**
	 * 处理向外界发送的Login通知
	 * @param event
	 */
	public void doSend(){
		log.trace("开始向外广播上线信息：" + event);
		ServerUtils.getSU().broadcast(event);
		log.trace("结束向外广播上线信息：" + event);
	}
	/**
	 * 处理从外界接受到的login通知
	 * @param event
	 */
	public void doReceive(){
		log.trace("开始处理收到的上线信息：" + event);
		User user = ((LoginEvent)event).getUser();
		UserContainer uc = UserContainer.getUC();
		if(!uc.contains(user)){
			uc.add(user.getIp(), user);
			log.info("成功将上线的用户添加进入容器：" + user);
		}else{
			log.info("用户已经存在容器中：" + user);
		}
	}

}
