package com.p2p.chat.processor;

import org.apache.log4j.Logger;

import com.p2p.chat.Server.ServerUtils;
import com.p2p.chat.container.UserContainer;
import com.p2p.chat.entity.User;
import com.p2p.chat.event.LogoutEvent;

public class LogoutProcessor extends AbstractProcessor {

	static Logger log = Logger.getLogger(LogoutProcessor.class);

	@Override
	protected void doSend() {
		log.trace("开始广播下线信息：" + event);
		UserContainer.getUC().remove(((LogoutEvent)event).getUser().getIp());
		log.info("从用户容器中移除" + ((LogoutEvent)event).getUser());
		ServerUtils.getSU().broadcast(event);
		log.trace("结束广播下线信息：" + event);		
	}

	@Override
	protected void doReceive() {
		log.trace("处理收到的下线信息：" + event);
		LogoutEvent event = (LogoutEvent) getEvent();
		User user = event.getUser();
		UserContainer uc = UserContainer.getUC();
		if(uc.contains(user)){
			uc.remove(user.getIp());
			log.info("用户从容器中移除：" + user);
		}else{
			log.info("用户在容器中不存在：" + user);			
		}
	}

}
