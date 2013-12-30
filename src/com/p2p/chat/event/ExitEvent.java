package com.p2p.chat.event;

/**
 * 退出系统事件此事件被发送并执行时，关闭服务器
 * @author Administrator
 *
 */
public class ExitEvent extends Event{
	private static final long serialVersionUID = 5520180714073945830L;

	Long startTime = System.currentTimeMillis();

	public Long getStartTime() {
		return startTime;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((startTime == null) ? 0 : startTime.hashCode());
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
		ExitEvent other = (ExitEvent) obj;
		if (startTime == null) {
			if (other.startTime != null)
				return false;
		} else if (!startTime.equals(other.startTime))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ExitEvent [startTime=" + startTime + "]";
	}
	
}
