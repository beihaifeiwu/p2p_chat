package com.p2p.chat.Server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;

import org.apache.log4j.Logger;

/**
 * 负责监听端口、发送消息以及接收消息
 * @author Administrator
 *
 */
public class Server {
	
	static Logger log = Logger.getLogger(Server.class);
	
	/**
	 * 数据报套接字，发送广播和消息
	 */
	DatagramSocket sender;
	/**
	 * 数据报套接字，监听此端口并接受数据报
	 */
	DatagramSocket listener;
	/**
	 * 服务器是否繁忙
	 */
	boolean busy = false;
	
	public boolean isBusy() {
		return busy;
	}
	public synchronized void setBusy(boolean busy) {
		this.busy = busy;
	}
	/**
	 * 服务器构造函数
	 * @param sendport 发送端口
	 * @throws IOException 
	 */
	public Server(int sendport,int listenport) throws IOException{
		sender = new DatagramSocket(sendport,InetAddress.getLocalHost());
		listener = new DatagramSocket(listenport,InetAddress.getLocalHost());
		
	}
	/**
	 * 将报文广播出去
	 * @param dp
	 * @throws IOException
	 */
	public synchronized void broadcast(DatagramPacket dp) throws IOException{
		log.trace("为数据包设置端口号：" + ServerUtils.listenport);
		dp.setSocketAddress(new InetSocketAddress("255.255.255.255", ServerUtils.listenport));
		sender.setBroadcast(true);
		setBusy(true);
		sender.send(dp);
		setBusy(false);
		sender.setBroadcast(false);
	}
	/**
	 * 发送报文
	 * @param dp
	 * @throws IOException
	 */
	public synchronized void send(DatagramPacket dp,String ip) throws IOException{
		dp.setSocketAddress(new InetSocketAddress(ip, ServerUtils.listenport));
		setBusy(true);
		sender.send(dp);
		setBusy(false);
	}
	/**
	 * 接收报文
	 * @return
	 * @throws IOException 
	 */
	public DatagramPacket receive() throws IOException{
		byte[] buffer = new byte[1024 * 4];
		DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
		listener.receive(dp);
		return dp;
	}
	/**
	 * 关闭相应的UDP套接字
	 */
	public void close(){
		sender.close();
		listener.close();
	}

}
