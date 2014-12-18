package com.duowan.statis;

import java.util.HashMap;

import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

public class StatisServiceUtil {

	private static IStatisService sService;

	private static HashMap<Context, ServiceBinder> sConnectionMap = new HashMap<Context, ServiceBinder>();

	public static class StatisServiceToken {
		ContextWrapper mWrappedContext;

		StatisServiceToken(ContextWrapper context) {
			mWrappedContext = context;
		}
	}

	private static class ServiceBinder implements ServiceConnection {

		public void onServiceConnected(ComponentName className, IBinder service) {
			sService = (IStatisService) service;
		}

		public void onServiceDisconnected(ComponentName className) {
			sService = null;
		}
	}

	public static StatisServiceToken bindToService(Context context) {
		ContextWrapper cw = new ContextWrapper(context);
		cw.startService(new Intent(cw, StatisServer.class));
		ServiceBinder sb = new ServiceBinder();
		if (cw.bindService((new Intent()).setClass(cw, StatisServer.class), sb,
				0)) {
			sConnectionMap.put(cw, sb);
			return new StatisServiceToken(cw);
		}
		return null;
	}

	public static void unbindFromService(StatisServiceToken token) {
		if (token == null) {
			return;
		}
		ContextWrapper cw = token.mWrappedContext;
		ServiceBinder sb = sConnectionMap.remove(cw);
		if (sb == null) {
			return;
		}
		cw.unbindService(sb);
		if (sConnectionMap.isEmpty()) {
			sService = null;
		}
	}

	private static boolean checkServiceBinded() {
		return sService != null;
	}

	/**
	 * start statis
	 * 
	 * @param id
	 * @param packagename
	 * @return
	 */
	public static boolean startStatis(int id, String packagename) {
		if (checkServiceBinded()) {
			return sService.startStatis(id, packagename);
		}
		return false;
	}
	/**
	 * stop statis
	 * 
	 * @param id
	 * @param packagename
	 * @return
	 */
	public static boolean StopStatis(int id, String packagename) {
		if (checkServiceBinded()) {
			return sService.stopStatis(id, packagename);
		}
		return false;
	}

}
