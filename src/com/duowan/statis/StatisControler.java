package com.duowan.statis;

import android.content.Intent;

import com.duowan.statis.StatisServiceUtil.StatisServiceToken;
import com.duowan.statis.ui.StatisApplication;

/**
 * 描述:应用启动/退出逻辑处理类
 * 
 */
public final class StatisControler {

	private static StatisApplication mApplication;

	private static StatisControler mAppControler;

	private StatisServiceToken mStatisServiceToken;

	public StatisControler() {
	}

	private StatisControler(StatisApplication application) {
		mApplication = application;
	}

	public static synchronized StatisControler getInstance() {
		if (mAppControler == null) {
			synchronized (StatisControler.class) {
				if (mAppControler == null) {
					StatisApplication baseApp = (StatisApplication) StatisApplication
							.getInstance();
					mAppControler = new StatisControler(baseApp);
				}
			}
		}
		return mAppControler;
	}

	/**
	 * 启动软件时做初始化工作
	 */
	public void init() {
		mStatisServiceToken = StatisServiceUtil.bindToService(StatisApplication
				.getInstance());
	}

	/**
	 * 退出服务
	 */
	public void exit() {
		StatisServiceUtil.unbindFromService(mStatisServiceToken);
		mApplication.stopService(new Intent(mApplication, StatisServer.class));
	}

}
