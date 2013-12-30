package com.p2p.chat.main;

import java.io.PrintStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.p2p.chat.container.ClientContainer;
import com.p2p.chat.container.EventContainer;
import com.p2p.chat.container.UserContainer;
import com.p2p.chat.entity.User;
import com.p2p.chat.event.EventBuilder;
import com.p2p.chat.event.ExitEvent;
import com.p2p.chat.event.LoginEvent;
import com.p2p.chat.event.LogoutEvent;
import com.p2p.chat.processor.DispatchEventProcessor;
import com.p2p.chat.processor.ExitProcessor;
import com.p2p.chat.processor.LoginProcessor;
import com.p2p.chat.processor.LogoutProcessor;
import com.p2p.chat.processor.ReceiveEventProcessor;

import java.util.concurrent.ExecutorService;

/**
 * 显示客户端，黑窗口版
 * @author Administrator
 *
 */
public class ConsoleImpl {
	
	static enum Menu{
		LOGIN("发布上线信息"),
		LOGOUT("发布下线信息"),
		SHOW_ALL_USER("显示所有在线用户"),
		EXIT("退出p2p—Chat");
		
		String description;

		public String getDescription() {
			return description;
		}

		private Menu(String description) {
			this.description = description;
		}
		
	}
	
	/**
	 * 执行事件监听与事件处理的线程池
	 */
	ExecutorService exe = Executors.newFixedThreadPool(2);

	/**
	 * 事件容器，其中包括所有的待处理事件
	 */
	EventContainer eventContainer = EventContainer.getEC();
	/**
	 * 用户容器，其中包括所有的在线用户
	 */
	UserContainer userContainer = UserContainer.getUC();
	/**
	 * 抽象客户端容器，其中包括所有正在通信的客户
	 */
	ClientContainer clientContainer = ClientContainer.getCC();
	
	PrintStream out = System.out;
	
	Scanner in = new Scanner(System.in);
	
	/**
	 * 是否已登录
	 */
	boolean isLogin = false;
	
	/**
	 * 当前登录用户
	 */
	User currentUser = null;
	/**
	 * 主循环是否运行
	 */
	boolean isRunning = false;
	
	/**
	 * 初始化执行环境
	 */
	public void init(){
		exe.execute(new DispatchEventProcessor());
		exe.execute(new ReceiveEventProcessor());
	}
	/**
	 * 打印可执行菜单
	 */
	public void displayMenu(){
		out.println(">>>>>>>>>>>>>>>>可执行菜单<<<<<<<<<<<<<<<<");
		for(Menu menu : Menu.values()){
			out.println((menu.ordinal() + 1) + ", " + menu.getDescription());
		}
		out.println(">>>>>>>>>>>>>>>>********<<<<<<<<<<<<<<<<");
		out.print("请选择您要执行的操作：");
	}
	/**
	 * 登录命令的处理
	 */
	public void login(){
		if(isLogin){
			out.println("您已经登录了");
			return;
		}
		User user = null;
		out.print("请输入您的登录昵称：");
		String nickname = in.next();
		String ip = null;
		try {
			ip = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		user = new User(nickname, ip);
		LoginEvent event = EventBuilder.buildLoginEvent(user);
		eventContainer.push(event);
		this.currentUser = user;
		this.isLogin = true;
	}
	
	/**
	 * 显示所有在线用户处理
	 */
	public void showAllUser(){
		out.println((this.currentUser != null ? this.currentUser.getNickname() : "您还没有登录") + " O(∩_∩)O");
		out.println("------------------------");
		for(User user : userContainer){
			out.println(user);
		}
	}
	/**
	 * 退出命令处理
	 */
	public void exit(){
		out.println("您确定要退出？(Y/N)");
		String input = in.next();
		if(input.equalsIgnoreCase("y") || input.equalsIgnoreCase("yes")){
			if(isLogin){
				logout();
			}
			ExitEvent event = EventBuilder.buildExitEvent();
			eventContainer.push(event);
			exe.shutdownNow();
			isRunning = false;
			try {
				exe.awaitTermination(60, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 读取输入的菜单选项
	 * @return
	 */
	public int readMenu(){
		int menu = 0;
		while(true){
			if(in.hasNextInt()){
				menu = in.nextInt();
				if(menu >= 1 && menu <= Menu.values().length){
					break;
				}
			}
			out.println("不是正确的命令，请重新输入：");
			in.next();
		}
		return menu - 1;
	}
	/**
	 * 界面执行函数
	 */
	public void run(){
		isRunning = true;
		while(isRunning){
			displayMenu();
			int menuOrder = readMenu();
			switch(Menu.values()[menuOrder]){
			case LOGIN:
				login();
				break;
			case SHOW_ALL_USER:
				showAllUser();
				break;
			case LOGOUT:
				logout();
				break;
			case EXIT:
				exit();
				break;
			default:
			}
			
		}
	}
	/**
	 * 下线命令处理
	 */
	private void logout() {
		if(isLogin){
			LogoutEvent event = EventBuilder.buildLogoutEvent(currentUser);
			eventContainer.push(event);
			out.println("下线成功");
		}else{
			out.println("您已经下线，请先登录！");
		}
		isLogin = false;
		this.currentUser = null;
	}
	public static void main(String[] args){
		//注册事件类型和相应的执行器
		DispatchEventProcessor.register(LoginEvent.class, new LoginProcessor());
		DispatchEventProcessor.register(LogoutEvent.class, new LogoutProcessor());
		DispatchEventProcessor.register(ExitEvent.class, new ExitProcessor());
		
		ConsoleImpl conImpl = new ConsoleImpl();
		conImpl.init();
		conImpl.run();
	}
}
