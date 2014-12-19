package com.duowan.statis.interfaces;

import android.os.Handler;

public interface ISingleFileStatis<T> {

	T getStatisInfo();

	boolean startStatistics(Handler mHandler, T t);
	
	boolean stopStatistics(Handler mHandler, T t);

}
