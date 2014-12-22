package com.duowan.sso;

interface StatisRemote{
boolean startStatis(int id, String eventid, String packagename,
				long time);	
boolean stopStatis(int id, String eventid, String packagename,
				long time);				
boolean	stopAllStatis(long time);
}