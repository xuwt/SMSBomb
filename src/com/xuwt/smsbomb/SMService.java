package com.xuwt.smsbomb;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

import com.xuwt.alarm.AlarmManagerSchedule;
import com.xuwt.alarm.IAlarmEvent;
import com.xuwt.utils.VerifyCode;

public class SMService extends Service{

	private String mPhoneNumStr="";
	private AlarmManagerSchedule mAlarmManager;
	private List<IAlarmEvent> mEntryArray;
	private int mNum=0;
	
	private SharedPreferences mSharePref;
	private int interval;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		mAlarmManager=new AlarmManagerSchedule(SMService.this);
		mEntryArray=new ArrayList<IAlarmEvent>();
		
		mSharePref=getSharedPreferences(SettingFragment.SHARE_NAME, MODE_PRIVATE);
		mPhoneNumStr=mSharePref.getString(SettingFragment.PHONENUM, "13011199467");
		try {
			interval=Integer.parseInt(mSharePref.getString(SettingFragment.INTERVAL, "20"));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			interval=20;
		}
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		mEntryArray.add(new SMSEntry());
		mAlarmManager.start(interval*1000, true, 
				AlarmManagerSchedule.NORMAL_ALARM, mEntryArray);
		
		return super.onStartCommand(intent, flags, startId);
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mAlarmManager.destroy();
		  getSharedPreferences(SettingFragment.SHARE_NAME, MODE_PRIVATE)
		    .edit().putInt(SettingFragment.COUNT, 0).commit();
	}
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	private class SMSEntry implements IAlarmEvent {
		@Override
		public void onTimeEvent() {
			
			
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					int result=VerifyCode.sendVerifyCode(mPhoneNumStr, (new Random().nextInt(30)));
					Log.d("SMService", "第"+mNum+"次"+result);
					mNum++;
					
					Intent serviceIntent = new Intent();
				    serviceIntent.setAction(MainFragment.BROADCASTNAME);
				    serviceIntent.putExtra("count", mNum);
				    sendBroadcast(serviceIntent); 
				    
				    getSharedPreferences(SettingFragment.SHARE_NAME, MODE_PRIVATE)
				    .edit().putInt(SettingFragment.COUNT, mNum).commit();
					
				}
			}).start();
		}

	}

}
