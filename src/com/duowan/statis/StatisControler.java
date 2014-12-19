package com.duowan.statis;

import android.content.Context;
import android.content.Intent;

import com.duowan.statis.StatisServiceUtil.StatisServiceToken;
import com.duowan.statis.service.StatisServer;

public final class StatisControler {

	private Context mContext;

	private static StatisControler mAppControler;

	private StatisServiceToken mStatisServiceToken;

	public StatisControler() {
	}

	public StatisControler init(Context context) {
		this.mContext = context;
		return mAppControler;
	}

	public static synchronized StatisControler getInstance() {
		if (mAppControler == null) {
			synchronized (StatisControler.class) {
				if (mAppControler == null) {
					mAppControler = new StatisControler();
				}
			}
		}
		return mAppControler;
	}

	/**
	 * 启动软件时做初始化工作
	 */
	public void startStatis() {
		mStatisServiceToken = StatisServiceUtil.bindToService(mContext);
	}

	/**
	 * 退出服务
	 */
	public void stopStatis() {
		StatisServiceUtil.unbindFromService(mStatisServiceToken);
		mContext.stopService(new Intent(mContext, StatisServer.class));
	}

}
