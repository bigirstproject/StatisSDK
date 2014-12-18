package com.duowan.statis;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;

public class StatisServer extends Service {
	
	private Handler mHandler;
	
	@Override
	public void onCreate() {
		super.onCreate();
		mHandler = new Handler();
	}

	@Override
	public IBinder onBind(Intent intent) {
		
		return mBinder;
	}

	ServiceStub mBinder =new ServiceStub();
	
	class ServiceStub extends Binder implements IStatisService {

		@Override
		public boolean startStatis(int id, String packagename) {
			
			return false;
		}

		@Override
		public boolean stopStatis(int id, String packagename) {
			
			return false;
		}
		
	}
	
}
