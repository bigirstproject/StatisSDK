package com.duowan.statis;

import java.util.Calendar;
import java.util.HashMap;

import android.os.Handler;
import android.text.TextUtils;

import com.duowan.statis.constant.StatisInfo;
import com.duowan.statis.interfaces.IStatisManager;
import com.duowan.util.LogCat;

public class StatisManager implements
		IStatisManager<SingleFileStatis, StatisInfo> {

	private StatisHandler mHandler;
	private HashMap<String, SingleFileStatis> mStatisSet;

	public StatisManager(boolean isDebug) {
		mStatisSet = new HashMap<String, SingleFileStatis>();
		mHandler = new StatisHandler(this);
		LogCat.setDebug(isDebug);
	}

	@Override
	public boolean startStatis(int id, String eventid, String packagename,
			long time) {
		StatisInfo statisInfo = new StatisInfo();
		statisInfo.setId(id);
		statisInfo.setStartTime(Calendar.getInstance().getTimeInMillis());
		statisInfo.setStatus(StatisInfo.START_STATUS);
		statisInfo.setEventid(eventid);
		statisInfo.setPackagename(packagename);
		SingleFileStatis mSingleFileStatis = (SingleFileStatis) createSingleFileStatis(
				statisInfo, time);
		if (mSingleFileStatis != null) {
			mStatisSet.put(eventid, mSingleFileStatis);
		}
		return mSingleFileStatis.startStatistics(mHandler, statisInfo);
	}

	@Override
	public boolean stopStatis(int id, String eventid, String packagename,
			long time) {
		SingleFileStatis singleFileStatis = mStatisSet.get(eventid);
		if (singleFileStatis.getStatisInfo().getEventid().equals(eventid)) {
			if (!TextUtils.isEmpty(packagename)
					&& singleFileStatis.getStatisInfo().getPackagename()
							.equals(packagename)) {
				singleFileStatis.getStatisInfo().setStatus(
						StatisInfo.STOP_STATUS);
				singleFileStatis.getStatisInfo().setStopTime(
						Calendar.getInstance().getTimeInMillis());
			} else {
				return false;
			}
			singleFileStatis.getStatisInfo().setStatus(StatisInfo.STOP_STATUS);
			singleFileStatis.getStatisInfo().setStopTime(
					Calendar.getInstance().getTimeInMillis());
		} else {
			return false;
		}
		if (singleFileStatis != null) {
			mStatisSet.remove(eventid);
		}
		return singleFileStatis.stopStatistics(mHandler,
				singleFileStatis.getStatisInfo());
	}

	@Override
	public boolean stopAllStatis(long time) {
		if (mStatisSet != null) {
			mStatisSet.clear();
			mHandler.sendEmptyMessage(StatisHandler.STATUS_STOP_ALL_CODE);
		}
		return false;
	}

	@Override
	public boolean addFromStatisingSet(String key, SingleFileStatis value) {
		mStatisSet.put(key, value);
		return true;
	}

	@Override
	public boolean removeFromStatisingSet(String key) {
		if(mStatisSet.containsKey(key)){
			mStatisSet.remove(key);
			return true;
		}
		return false;
	}

	@Override
	public SingleFileStatis getFromStatisingSet(String key) {
		if (mStatisSet != null) {
			return mStatisSet.get(key);
		}
		return null;
	}

	@Override
	public SingleFileStatis createSingleFileStatis(StatisInfo e, long time) {
		SingleFileStatis mSingleFileStatis = new SingleFileStatis(e, time);
		return mSingleFileStatis;
	}

	@Override
	public Handler getHandler() {

		return mHandler;
	}

}
