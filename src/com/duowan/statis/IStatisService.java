package com.duowan.statis;

public interface IStatisService {

	boolean startStatis(int id, String packagename);

	boolean stopStatis(int id, String packagename);

}
