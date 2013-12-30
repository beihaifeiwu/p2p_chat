package com.p2p.chat.Server;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;

import org.apache.log4j.Logger;

import com.p2p.chat.event.Event;

/**
 * 对Server进行包装的工具类
 * @author Administrator
 *
 */
public class ServerUtils {

	public static final Integer sendport = 8888;
	
	public static final Integer listenport = 8889;
	
	static Logger log = Logger.getLogger(ServerUtils.class);
	
	private Server server;

	/**
	 * 实现单例模式
	 */
	private static ServerUtils serverUtils = new ServerUtils();
	
	private ServerUtils(){
		try {
			this.server = new Server(sendport,listenport);
		} catch (IOException e) {
			log.warn("服务器建立失败",e);
			throw new RuntimeException(e);
		}
		log.info("服务器建立成功");
	}
	
	public static ServerUtils getSU(){
		return serverUtils;
	}
	/**
	 * 广播事件
	 * @param event
	 */
	public void broadcast(Event event){
		server.setBusy(true);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			event.setDirection(Event.Direction.CLIENT);
			oos.writeObject(event);
			byte[] buffer = bos.toByteArray();
			log.debug("广播数据写入缓存成功：" + event);
			server.broadcast(new DatagramPacket(buffer, buffer.length));
		} catch (IOException e) {
			log.warn("服务器发送广播失败",e);
			e.printStackTrace();
		}
		server.setBusy(false);
		log.info("服务器发送广播成功：" + event);
	}
	
	/**
	 * 发送包给指定用户
	 * @param event 事件信息
	 * @param ip 目的ip地址
	 */
	public void send(Event event,String ip){
		server.setBusy(true);
		event.setDirection(Event.Direction.CLIENT);
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(event);
			byte[] buffer = bos.toByteArray();
			log.debug("发送数据成功写入缓存：" + event);
			DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
			server.send(dp,ip);
		} catch (IOException e) {
			log.warn("发送数据失败", e);
			e.printStackTrace();
		}
		server.setBusy(false);
		log.info("发送数据成功：" + event);
	}
	/**
	 * 接收事件
	 * @return
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public Event receive() throws IOException, ClassNotFoundException{
		DatagramPacket dp = null;
		dp = server.receive();
		if(dp == null) return null;
		log.trace("收到数据包：length=" + dp.getLength() + " from=" + dp.getAddress().getHostAddress());
		ByteArrayInputStream bis = new ByteArrayInputStream(dp.getData());
		ObjectInputStream ois = new ObjectInputStream(bis);
		Object o = ois.readObject();
		log.info("服务器收到事件：" + o);
		return (Event) o;
	}
	/**
	 * 关闭服务器
	 */
	public void closeServer(){
		log.info("开始关闭服务器");
		this.server.close();
		log.info("结束关闭服务器");
	}
	/**
	 * 服务器是否繁忙
	 * @return
	 */
	public boolean isServerBusy(){
		return this.server.isBusy();
	}
}
