package com.p2p.chat.processor;

import com.p2p.chat.event.Event;

/**
 * 处理器的超类
 * @author Administrator
 *
 */
public abstract class AbstractProcessor implements Runnable {

	/**
	 * 处理器要处理的事件
	 */
	protected Event event;

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}
	
	@Override
	public void run() {
		switch(event.getDirection()){
		case CLIENT:
			doReceive();
			break;
		case SERVER:
			doSend();
			break;
		default:
			break;
		}
	}

	/**
	 * 处理要发出的消息
	 */
	protected abstract void doSend();
	
	/**
	 * 处理收到的消息
	 */
	protected abstract void doReceive();

}
