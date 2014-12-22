package com.duowan.statis.service;

import java.util.HashMap;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;

import com.duowan.sso.StatisRemote;
import com.duowan.statis.StatisHandler;
import com.duowan.statis.StatisManager;
import com.duowan.util.LogCat;

public class StatisServer extends Service {

	private StatisManager mStatisManager;

	private HashMap<String, String> mRemoteEvent;

	@Override
	public void onCreate() {
		super.onCreate();
		mRemoteEvent = new HashMap<String, String>();
		mStatisManager = new StatisManager(LogCat.isDebug());
	}

	@Override
	public IBinder onBind(Intent intent) {
		return new ServiceStub();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mStatisManager.getHandler().removeMessages(
				StatisHandler.STATUS_START_CODE);
		mStatisManager.getHandler().removeMessages(
				StatisHandler.STATUS_STOP_CODE);
	};

	public class ServiceStub extends StatisRemote.Stub {

		@Override
		public boolean startStatis(int id, String eventid, String packagename,
				long time) throws RemoteException {
			boolean status = mStatisManager.startStatis(id, eventid,
					packagename, time);
			if (status) {
				mRemoteEvent.put(eventid, eventid);
			}
			return status;
		}

		@Override
		public boolean stopStatis(int id, String eventid, String packagename,
				long time) throws RemoteException {
			if (!TextUtils.isEmpty(mRemoteEvent.remove(eventid))) {
				return mStatisManager
						.stopStatis(id, eventid, packagename, time);
			}
			return false;
		}

		@Override
		public boolean stopAllStatis(long time) throws RemoteException {
			if (mRemoteEvent != null && mRemoteEvent.size() > 0) {
				return mStatisManager.stopAllStatis(time);
			}
			return false;
		}

	}

}
