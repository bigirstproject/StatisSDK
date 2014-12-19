package com.duowan.statis.ui;

import android.app.Application;

import com.duowan.statis.StatisControler;

public class StatisApplication extends Application {

	private static StatisApplication mStatisApplication;

	@Override
	public void onCreate() {
		super.onCreate();
		mStatisApplication = this;
		StatisControler.getInstance().init(this);
		StatisControler.getInstance().startStatis();
	}

	public static StatisApplication getInstance() {
		if (mStatisApplication == null) {
			throw new IllegalStateException("Application is not created.");
		}
		return mStatisApplication;
	}

}
