package com.duowan.statis.interfaces;

public interface IStatisService {

	boolean startStatis(int id, String eventid, String packagename, long time);

	boolean stopStatis(int id, String eventid, String packagename, long time);
	
	boolean stopAllStatis(long time);

}
