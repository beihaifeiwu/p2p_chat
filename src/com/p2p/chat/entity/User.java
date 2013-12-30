package com.p2p.chat.entity;

import java.io.Serializable;

/**
 * 用户实体类，代表已上线用户的数据信息
 * @author Administrator
 *
 */
public class User implements Serializable {

	private static final long serialVersionUID = 7034954717486425721L;

	/**
	 * 用户昵称
	 */
	String nickname;
	
	/**
	 * 用户ip
	 */
	String ip;

	/**
	 * @return the nickname
	 */
	public String getNickname() {
		return nickname;
	}

	/**
	 * @param nickname the nickname to set
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	/**
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * @param ip the ip to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ip == null) ? 0 : ip.hashCode());
		result = prime * result
				+ ((nickname == null) ? 0 : nickname.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (ip == null) {
			if (other.ip != null)
				return false;
		} else if (!ip.equals(other.ip))
			return false;
		if (nickname == null) {
			if (other.nickname != null)
				return false;
		} else if (!nickname.equals(other.nickname))
			return false;
		return true;
	}

	public User(String nickname, String ip) {
		this.nickname = nickname;
		this.ip = ip;
	}

	@Override
	public String toString() {
		return "User [nickname=" + nickname + ", ip=" + ip + "]";
	}
	
}
