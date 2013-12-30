package com.p2p.chat.container;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.log4j.Logger;

import com.p2p.chat.event.Event;

/**
 * 存放所有客户和server端产生的事件
 * @author Administrator
 *
 */
public class EventContainer {
	
	static Logger log = Logger.getLogger(EventContainer.class);
	
	/**
	 * 实现单例模式
	 */
	private static EventContainer eventContainer = new EventContainer();
	
	private EventContainer(){
		log.trace("建立事件容器");
	}
	
	public static EventContainer getEC(){
		return eventContainer;
	}

	/**
	 * 存放事件的队列
	 */
	Queue<Event> eventQueue = new ConcurrentLinkedQueue<>();
	
	/**
	 * 队列是否被锁定，锁定后不能向队列中添加事件
	 */
	boolean isLocked = false;
	
	/**
	 * 将事件压入队列
	 * @param event
	 */
	public synchronized void push(Event event){
		if(event != null && !isLocked()){
			eventQueue.add(event);
			log.trace("向事件队列中压入事件：" + event);
		}else{
			log.info("事件队列已被锁定");
		}
	}
	/**
	 * 将事件弹出队列
	 * @return
	 */
	public Event pop(){
		log.trace("从事件队列中弹出事件：" + eventQueue.peek());
		return eventQueue.poll();
	}
	/**
	 * 事件容器是否为空
	 * @return
	 */
	public boolean isEmpty(){
		return eventQueue.isEmpty();
	}
	/**
	 * 清空事件容器中的所有事件
	 */
	public void clear(){
		log.trace("清空事件队列");
		eventQueue.clear();
	}

	public boolean isLocked() {
		return isLocked;
	}

	public synchronized void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}
}
