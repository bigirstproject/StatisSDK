package com.duowan.statis.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.duowan.statis.StatisHandler;
import com.duowan.statis.StatisManager;
import com.duowan.statis.interfaces.IStatisService;
import com.duowan.util.LogCat;

public class StatisServer extends Service {
	
	private StatisManager mStatisManager;
	@Override
	public void onCreate() {
		super.onCreate();
		mStatisManager = new StatisManager(LogCat.isDebug());
	}

	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mStatisManager.getHandler().removeMessages(StatisHandler.STATUS_START_CODE);
		mStatisManager.getHandler().removeMessages(StatisHandler.STATUS_STOP_CODE);
	};

	ServiceStub mBinder = new ServiceStub();

	public class ServiceStub extends Binder implements IStatisService {

		@Override
		public boolean startStatis(int id, String eventid, String packagename,
				long time) {
			return mStatisManager.startStatis(id, eventid, packagename, time);
		}

		@Override
		public boolean stopStatis(int id, String eventid, String packagename,
				long time) {
			return mStatisManager.stopStatis(id, eventid, packagename, time);
		}

		@Override
		public boolean stopAllStatis(long time) {
			return mStatisManager.stopAllStatis(time);
		}

	}

}
