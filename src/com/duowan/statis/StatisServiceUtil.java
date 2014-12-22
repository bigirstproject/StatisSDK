package com.duowan.statis;

import java.util.HashMap;

import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import com.duowan.sso.StatisRemote;
import com.duowan.statis.service.StatisServer;

public class StatisServiceUtil {

	private static StatisRemote sService;

	private static HashMap<Context, ServiceBinder> sConnectionMap = new HashMap<Context, ServiceBinder>();

	public static class StatisServiceToken {
		ContextWrapper mWrappedContext;

		StatisServiceToken(ContextWrapper context) {
			mWrappedContext = context;
			
		}
	}

	private static class ServiceBinder implements ServiceConnection {

		public void onServiceConnected(ComponentName className, IBinder service) {
			sService = StatisRemote.Stub.asInterface(service);
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
			token = null;
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
			try {
				return sService.startStatis(id, eventid, packagename, time);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
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
			String packagename, long time)  {
		if (checkServiceBinded()) {
			try {
				return sService.stopStatis(id, eventid, packagename, time);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * stop all statis
	 * 
	 * @param time
	 * @return
	 */
	public static boolean StopAllStatis(long time) {
		if (checkServiceBinded()) {
			try {
				return sService.stopAllStatis(time);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

}
