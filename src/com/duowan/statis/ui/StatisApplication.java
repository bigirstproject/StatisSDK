package com.duowan.statis.ui;

import com.duowan.statis.StatisControler;

import android.app.Application;

public class StatisApplication extends Application {
	private static StatisApplication mStatisApplication;

	@Override
	public void onCreate() {
		super.onCreate();
		mStatisApplication = this;
		StatisControler.getInstance().init();
	}

	public static StatisApplication getInstance() {
		if (mStatisApplication == null) {
			throw new IllegalStateException("Application is not created.");
		}
		return mStatisApplication;
	}

}
