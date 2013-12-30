package com.p2p.chat.event;

import com.p2p.chat.entity.User;

/**
 * 表示登录事件的事件类
 * @author Administrator
 *
 */
public class LoginEvent extends Event {

	private static final long serialVersionUID = -4273880902924354708L;

	/**
	 * 登录的用户
	 */
	User user;

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	public LoginEvent(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "LoginEvent [user=" + user + ", direction=" + direction + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		LoginEvent other = (LoginEvent) obj;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}
	
}
