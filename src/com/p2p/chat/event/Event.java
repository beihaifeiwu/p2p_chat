package com.p2p.chat.event;

import java.io.Serializable;

/**
 * 服务器和客户端产生事件的超类
 * @author Administrator
 *
 */
public abstract class Event implements Serializable {
	
	private static final long serialVersionUID = 4519475275538154432L;
	
	/**
	 * 事件发往的方向
	 * @author Administrator
	 *
	 */
	public static enum Direction {CLIENT,SERVER}
	
	/**
	 * 标识事件发往的方向
	 */
	Direction direction;

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	@Override
	public String toString() {
		return "Event [direction=" + direction + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((direction == null) ? 0 : direction.hashCode());
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
		Event other = (Event) obj;
		if (direction != other.direction)
			return false;
		return true;
	}
}
