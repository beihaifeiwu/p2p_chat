package com.p2p.chat.processor;

import org.apache.log4j.Logger;

import com.p2p.chat.Server.ServerUtils;
import com.p2p.chat.container.EventContainer;

/**
 * 退出命令处理器，关闭服务器
 * @author Administrator
 *
 */
public class ExitProcessor extends AbstractProcessor {
	
	static Logger log = Logger.getLogger(ExitProcessor.class);

	@Override
	protected void doSend() {
		log.trace("开始关闭服务器,并锁定事件队列:" + event);
		EventContainer.getEC().setLocked(true);
		do{
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				log.warn("退出处理器等待被打断：", e);
			}
		}while(ServerUtils.getSU().isServerBusy());
		ServerUtils.getSU().closeServer();
		log.info("服务器已经关闭：" + event);
	}

	@Override
	protected void doReceive() {
		doSend();
	}

}
