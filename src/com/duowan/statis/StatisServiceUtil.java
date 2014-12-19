package com.duowan.statis;

import java.util.HashMap;

import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.duowan.statis.service.StatisServer;
import com.duowan.statis.service.StatisServer.ServiceStub;

public class StatisServiceUtil {

	private static ServiceStub sService;

	private static HashMap<Context, ServiceBinder> sConnectionMap = new HashMap<Context, ServiceBinder>();

	public static class StatisServiceToken {
		ContextWrapper mWrappedContext;

		StatisServiceToken(ContextWrapper context) {
			mWrappedContext = context;
			
		}
	}

	private static class ServiceBinder implements ServiceConnection {

		public void onServiceConnected(ComponentName className, IBinder service) {
			sService = (ServiceStub) service;
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
				Context.BIND_AUTO_CREATE)) {
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
	 * @param eventid
	 * @param packagename
	 * @param time
	 * @return
	 */
	public static boolean startStatis(int id, String eventid,
			String packagename, long time) {
		if (checkServiceBinded()) {
			return sService.startStatis(id, eventid, packagename, time);
		}
		return false;
	}

	/**
	 * stop statis
	 * 
	 * @param id
	 * @param eventid
	 * @param packagename
	 * @param time
	 * @return
	 */
	public static boolean StopStatis(int id, String eventid,
			String packagename, long time) {
		if (checkServiceBinded()) {
			return sService.stopStatis(id, eventid, packagename, time);
		}
		return false;
	}

	/**
	 * stop all statis
	 * 
	 * @param time
	 * @return
	 */
	public static boolean StopStatis(long time) {
		if (checkServiceBinded()) {
			return sService.stopAllStatis(time);
		}
		return false;
	}

}
