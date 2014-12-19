package com.duowan.statis.interfaces;

import android.os.Handler;

import com.duowan.statis.SingleFileStatis;

public interface IStatisManager<T, E> extends IStatisService {

	boolean addFromStatisingSet(String key, SingleFileStatis value);

	boolean removeFromStatisingSet(String key);

	T getFromStatisingSet(String key);

	T createSingleFileStatis(E e,long time);

	Handler getHandler();

}
