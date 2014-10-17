package com.xuwt.alarm;

import java.util.Calendar;
import java.util.List;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
/**
 * 
* @Description: 定时闹钟管理类
* @author xuwt
* @date 2013年12月3日 下午6:23:45
* @version V1.0
 */
public class AlarmManagerSchedule {

	//闹钟响应事件列表
	private List<IAlarmEvent> mEntryArray;
	
	//闹钟是否正在运行标识
	private boolean mIsRunning = false;

	private AlarmManager mAlarmMgr;
	private Context mContext;
	private PendingIntent mPendingIntent;
	private BroadcastReceiver mAlarmReceiver;

	//广播接收action
	private static final String ACTION_RISING_ALARM = "cn.com.xuwt.alarm";
	private static final String ACTION_RISING_ALARM_DAEMON = "cn.com.xuwt.alarm.daemon";
	
	//标识是守护进程还是普通的定时进程
	public static final int DAEMON_ALARM=1;
	public static final int NORMAL_ALARM=0;
	
	//闹钟间隔时间
	public static final long NORMAL_INTEVAL=30*60*1000;//30分钟
	public static final long DAEMON_INTEVAL=1000;//0.8秒
	
	public AlarmManagerSchedule(Context context) {
		mContext = context;
	}

	public boolean isRunning() {
		return mIsRunning;
	}
	
	/**
	 * 
	* @Description: 开始进行定时，设置响应广播
	* @param inteval 时间间隔
	* @param wakeup 是否唤醒cpu，最好唤醒
	* @param tag 标识是守护线程还是普通定时
	* @param EntryArrays  要响应定时服务的事件列表
	* @return void
	 */
	public void start(long interval, boolean wakeup, int tag,
			List<IAlarmEvent> entryArrays) {
		mEntryArray=entryArrays;
		mAlarmMgr = (AlarmManager) mContext
				.getSystemService(Context.ALARM_SERVICE);
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(System.currentTimeMillis());
		Intent intent;
		
		if (tag == DAEMON_ALARM) {
			
			// 说明是守护进程
			intent = new Intent(ACTION_RISING_ALARM_DAEMON);
			mPendingIntent = PendingIntent.getBroadcast(mContext, DAEMON_ALARM,
					intent, PendingIntent.FLAG_UPDATE_CURRENT);
		} else {
			
			// 正常模式
			intent = new Intent(ACTION_RISING_ALARM);
			mPendingIntent = PendingIntent.getBroadcast(mContext, NORMAL_ALARM,
					intent, PendingIntent.FLAG_UPDATE_CURRENT);
		}
		register(tag);
		mAlarmMgr.setRepeating(wakeup ? AlarmManager.RTC_WAKEUP
				: AlarmManager.RTC, cal.getTimeInMillis(), interval,
				mPendingIntent);
		mIsRunning=true;
		
	}

	/**
	 * 
	* @Description: 释放资源
	* @param     
	* @return void
	 */
	public void destroy() {
		mContext.unregisterReceiver(mAlarmReceiver);
		mAlarmMgr.cancel(mPendingIntent);
		mEntryArray.clear();
		mIsRunning = false;
	}

	/**
	 * 
	* @Description: 注册广播接收器，响应闹钟事件
	* @param tag 标识是否是守护进行  
	* @return void
	 */
	private void register(int tag) {
		mAlarmReceiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				for (int i = 0; i < mEntryArray.size(); i++) {
					IAlarmEvent entry = (IAlarmEvent) mEntryArray
							.get(i);					
					try {
						entry.onTimeEvent();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		};
		
		IntentFilter intentFilter = new IntentFilter();
		
		if (tag == DAEMON_ALARM) {
			
			// 说明是守护进程
			intentFilter.addAction(ACTION_RISING_ALARM_DAEMON);
		} else {
			
			// 正常模式
			intentFilter.addAction(ACTION_RISING_ALARM);
		}
		
		mContext.registerReceiver(mAlarmReceiver, intentFilter);
	}
	
}
