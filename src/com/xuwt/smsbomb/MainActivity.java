package com.xuwt.smsbomb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengUpdateAgent;
import com.xuwt.utils.HttpNetUtil;

public class MainActivity extends BaseActivity implements OnClickListener {
	/**
	 * 用于对Fragment进行管理
	 */
	private FragmentManager mFragmentManager;
	private FragmentTransaction mTransaction;

	private Button mSettingBtn;
	private HttpNetUtil mUtil;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_main);
		mUtil = new HttpNetUtil();
		
		UmengUpdateAgent.setUpdateOnlyWifi(false);
		UmengUpdateAgent.update(this);
		MobclickAgent.openActivityDurationTrack(false);

		mSettingBtn = (Button) findViewById(R.id.setting_btn);
		mSettingBtn.setOnClickListener(this);
		mFragmentManager = getSupportFragmentManager();

		mTransaction = mFragmentManager.beginTransaction();
		mTransaction.replace(R.id.content, MainFragment.newInstance());
		mTransaction.commit();
	}

	public void replaceFragment(Fragment fragment) {
		mTransaction = mFragmentManager.beginTransaction();

		mTransaction.replace(R.id.content, fragment);

		mTransaction.addToBackStack(null);
		mTransaction.commit();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		replaceFragment(SettingFragment.newInstance());
		MobclickAgent.onEvent(MainActivity.this, "setting_click");
	}
	
	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
		if (mUtil.isServiceRunning(MainActivity.this, MainFragment.SERVICENAME)) {
			stopService(new Intent(MainActivity.this, SMService.class));
		}
	}
}
