package com.p2p.chat.event;

import com.p2p.chat.entity.User;

/**
 * 表示用户下线的事件
 * @author Administrator
 *
 */
public class LogoutEvent extends Event {

	private static final long serialVersionUID = 112573078249586289L;

	/**
	 * 下线的用户
	 */
	User user;

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	public LogoutEvent(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "LogoutEvent [user=" + user + ", direction=" + direction + "]";
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
		LogoutEvent other = (LogoutEvent) obj;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}
}
