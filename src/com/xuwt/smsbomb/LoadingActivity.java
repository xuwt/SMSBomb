package com.xuwt.smsbomb;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.umeng.analytics.MobclickAgent;

public class LoadingActivity extends BaseActivity implements Callable<Integer> {

	private Handler mHandler;
	ExecutorService mExecPool;

	/** 线程状态 */
	Future<Integer> mFunture;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loading);
		
		MobclickAgent.setDebugMode( true );
		MobclickAgent.updateOnlineConfig( LoadingActivity.this );
		
		mHandler = new InitializeHandler();
		mExecPool = Executors.newSingleThreadScheduledExecutor();
		mFunture = mExecPool.submit(this);
	}

	class InitializeHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			startActivity(new Intent(LoadingActivity.this, MainActivity.class));
			finish();
		}
	}

	@Override
	public Integer call() throws Exception {
		Thread.sleep(2000);
		mHandler.sendEmptyMessage(0);
		return null;
	}

	

}
