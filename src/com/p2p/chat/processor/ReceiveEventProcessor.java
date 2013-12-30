package com.p2p.chat.processor;

import java.io.IOException;
import org.apache.log4j.Logger;

import com.p2p.chat.Server.ServerUtils;
import com.p2p.chat.container.EventContainer;
import com.p2p.chat.event.Event;

public class ReceiveEventProcessor implements Runnable {
	
	static Logger log = Logger.getLogger(ReceiveEventProcessor.class);

	ServerUtils su = ServerUtils.getSU();
	EventContainer ec = EventContainer.getEC();
	
	@Override
	public void run() {
		log.info("端口事件监听处理器开始执行");
		while(!Thread.interrupted()){
			try {
				Event event = su.receive();
				ec.push(event);
				log.info("成功接收数据报并压入事件队列：" + event);
			} catch (ClassNotFoundException e) {
				log.warn("接收数据报信息出错",e);
				//e.printStackTrace();
			} catch (IOException e) {
				log.warn("接收数据报出错 或 服务器关闭",e);
				//e.printStackTrace();
			}
		}
		log.info("端口事件监听处理器结束执行");
	}

}
