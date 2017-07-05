package com.chandlertu.energy.managing;

import java.io.Serializable;
import java.util.Date;

public class EnergyManaging implements Serializable {

	private static final long serialVersionUID = -5414137382413263662L;

	private Integer id;
	private Date startTime;
	private Date endTime;
	private String task;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

}
