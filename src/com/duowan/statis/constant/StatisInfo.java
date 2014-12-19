package com.duowan.statis.constant;

import java.io.Serializable;

public class StatisInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final int NORMAL_STATUS = 0;
	public static final int START_STATUS = 1;
	public static final int STOP_STATUS = 2;
	public static final int COMPLETE_STATUS = 3;

	private int id;

	private long startTime;

	private long stopTime;

	private long tatalTime;

	private int status;

	private String eventid;

	private String packagename;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getStopTime() {
		return stopTime;
	}

	public void setStopTime(long stopTime) {
		this.stopTime = stopTime;
	}

	public long getTatalTime() {
		return tatalTime;
	}

	public void setTatalTime(long tatalTime) {
		this.tatalTime = tatalTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getEventid() {
		return eventid;
	}

	public void setEventid(String eventid) {
		this.eventid = eventid;
	}

	public String getPackagename() {
		return packagename;
	}

	public void setPackagename(String packagename) {
		this.packagename = packagename;
	}

}
