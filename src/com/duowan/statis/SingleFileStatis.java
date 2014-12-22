package com.duowan.statis;

import android.os.Handler;
import android.os.Message;

import com.duowan.statis.constant.StatisInfo;
import com.duowan.statis.interfaces.ISingleFileStatis;

public class SingleFileStatis implements ISingleFileStatis<StatisInfo> {

	public static final int NORMAL_STATUS = 0;
	public static final int START_STATUS = 1;
	public static final int STOP_STATUS = 2;
	public static final int COMPLETE_STATUS = 3;

	private int id;
	private String eventid;
	private String packagename;
	private long time;
	private StatisInfo mStatisInfo;

	public SingleFileStatis(StatisInfo mStatisInfo, long time) {
		this.mStatisInfo = mStatisInfo;
		this.time = time;
		if (mStatisInfo != null) {
			this.id = mStatisInfo.getId();
			this.eventid = mStatisInfo.getEventid();
			this.packagename = mStatisInfo.getPackagename();
		}
	}

	@Override
	public StatisInfo getStatisInfo() {
		if (mStatisInfo != null) {
			return mStatisInfo;
		}
		return null;
	}

	@Override
	public boolean startStatistics(Handler mHandler, StatisInfo statisInfo) {
		Message msg = new Message();
		msg.what = StatisHandler.STATUS_START_CODE;
		msg.obj = statisInfo;
		return mHandler.sendMessageDelayed(msg, time);
	}

	@Override
	public boolean stopStatistics(Handler mHandler, StatisInfo statisInfo) {
		Message msg = new Message();
		msg.what = StatisHandler.STATUS_STOP_CODE;
		msg.obj = statisInfo;
		return mHandler.sendMessage(msg);
	}

}
