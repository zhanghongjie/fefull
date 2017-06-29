package com.frame.quartz;

public class QuartzKey {
	long id;
	String name;
	String group;

	public QuartzKey(long id, String name) {
		this(id, name, "default");
	}

	public QuartzKey(long id, String name, String group) {
		this.id = id;
		this.name = name;
		this.group = group;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGroup() {
		return this.group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String toString() {
		return "id:" + this.id + ",name:" + this.name + ",group:" + this.group;
	}

	public boolean equals(Object o) {
		if ((o != null) && ((o instanceof QuartzKey))) {
			QuartzKey quartzKey = (QuartzKey) o;
			return (this.id == quartzKey.getId())
					&& (this.name.equals(quartzKey.getName()))
					&& (this.group.equals(quartzKey.getGroup()));
		}
		return super.equals(o);
	}
}