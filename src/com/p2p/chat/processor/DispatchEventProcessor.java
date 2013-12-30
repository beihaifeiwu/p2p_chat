package com.p2p.chat.processor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.p2p.chat.container.EventContainer;
import com.p2p.chat.event.Event;

import java.util.concurrent.ExecutorService;

import org.apache.log4j.Logger;

/**
 * 事件分发处理器、将相应的事件交给相应的处理器
 * @author Administrator
 *
 */
public class DispatchEventProcessor implements Runnable {
	
	static Logger log = Logger.getLogger(DispatchEventProcessor.class);

	/**
	 * 保存事件类型与相应处理器的映射关系
	 */
	@SuppressWarnings("rawtypes")
	static Map<Class,AbstractProcessor> map = new HashMap<>();
	/**
	 * 运行处理器的线程池
	 */
	ExecutorService exe = Executors.newFixedThreadPool(5);
	/**
	 * 存放事件的容器
	 */
	EventContainer ec = EventContainer.getEC();
	/**
	 * 向事件与处理器映射中添加映射
	 * @param clazz
	 * @param run
	 */
	public static void register(Class<? extends Event> clazz,AbstractProcessor run){
		if(!map.containsKey(clazz)){
			map.put(clazz, run);
			log.trace("成功向事件分发处理器中注册关系：" + clazz.getSimpleName() + "<===>" + run.getClass().getSimpleName());
		}
	}
	
	/**
	 * 锁定并清空所有队列中的事件
	 */
	public void clearEC(){
		log.info("开始清空事件队列中的所有事件");
		ec.setLocked(true);
		while(!ec.isEmpty()){
			Event event = ec.pop();
			AbstractProcessor pro = map.get(event.getClass());
			pro.setEvent(event);
			exe.execute(pro);
		}
		log.info("队列中的事件已经清空");
	}
	
	/**
	 * 等待并关闭线程池
	 * @param pool
	 */
	void shutdownAndAwaitTermination(ExecutorService pool) {
		pool.shutdown(); // Disable new tasks from being submitted
		try {
			// Wait a while for existing tasks to terminate
			if (!pool.awaitTermination(60, TimeUnit.SECONDS)) {
				pool.shutdownNow(); // Cancel currently executing tasks
				// Wait a while for tasks to respond to being cancelled
				if (!pool.awaitTermination(60, TimeUnit.SECONDS))
					log.warn("Pool did not terminate");
			}
		} catch (InterruptedException ie) {
			// (Re-)Cancel if current thread also interrupted
			pool.shutdownNow();
			// Preserve interrupt status
			Thread.currentThread().interrupt();
		}
	}

	@Override
	public void run() {
		log.info("事件分发处理器开始执行");
		while(!Thread.interrupted()){
			if(ec.isEmpty()) continue;
			Event event = ec.pop();
			AbstractProcessor pro = map.get(event.getClass());
			pro.setEvent(event);
			exe.execute(pro);
		}
		if(Thread.interrupted()){
			clearEC();
		}
		List<Runnable> list = exe.shutdownNow();
		log.info("正在执行的后台线程：" + list);
		shutdownAndAwaitTermination(exe);
		log.info("事件分发处理器结束执行");
	}

}
