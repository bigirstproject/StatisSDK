package com.duowan.statis.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.duowan.statis.R;
import com.duowan.statis.StatisControler;
import com.duowan.statis.StatisServiceUtil;
import com.duowan.statis.constant.IntervalStatis;
import com.duowan.statis.constant.StatisEvent;
import com.duowan.util.ToastShowUtil;

public class MainActivity extends Activity implements OnClickListener {
	private Button mStart;
	private Button mStop;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		mStart = (Button) findViewById(R.id.start);
		mStop = (Button) findViewById(R.id.stop);
		mStart.setOnClickListener(this);
		mStop.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.start:
			ToastShowUtil.showMsgShort(this,
					getResources().getString(R.string.statis_start));
			StatisServiceUtil.startStatis(1, StatisEvent.ACTIVITY_TIME,
					"com.duowan.statis", IntervalStatis.TIME_INTERVAL_TEST);
			break;
		case R.id.stop:
//			ToastShowUtil.showMsgShort(this,
//					getResources().getString(R.string.statis_stop));
			StatisServiceUtil.StopStatis(1, StatisEvent.ACTIVITY_TIME,
					"com.duowan.statis", IntervalStatis.TIME_INTERVAL_TEST);
			break;
		default:
			break;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		StatisControler.getInstance().stopStatis();
	}

}
