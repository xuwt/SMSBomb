package com.xuwt.smsbomb;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;
import com.xuwt.utils.HttpNetUtil;
import com.xuwt.utils.RegexUtility;

public class MainFragment extends Fragment implements OnClickListener,
		TextWatcher {

	private View mBackLayout;
	private Button mBackBtn;
	private Button mSettingBtn;
	private TextView mBackview;
	private TextView mTitleView;

	private EditText mPhoneNumView;
	private Button mStartBtn;

	private String mPhoneNumStr = "";
	private TextView mNumberView;

	private HttpNetUtil mUtil;
	private int mPosition;

	public static final String SERVICENAME = "com.xuwt.smsbomb.SMService";
	public static final String BROADCASTNAME = "com.xuwt.smsbomb.MyBroadcast";
	private MyBroadcast mybroadcast;
	private Context mContext;

	private SharedPreferences mSharePref;
	
	public static MainFragment newInstance() {
		MainFragment fragment = new MainFragment();
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mContext = getActivity();
		mUtil = new HttpNetUtil();
		
		mSharePref=mContext.getSharedPreferences(SettingFragment.SHARE_NAME, Activity.MODE_PRIVATE);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_main, null);
		mPhoneNumView = (EditText) view.findViewById(R.id.phone_num_edit);
		mStartBtn = (Button) view.findViewById(R.id.start_btn);

		mNumberView = (TextView) view.findViewById(R.id.number_text);

		mPhoneNumView.addTextChangedListener(this);
		mStartBtn.setOnClickListener(this);

		mBackLayout = (View) getActivity().findViewById(R.id.back_layout);
		mBackBtn = (Button) getActivity().findViewById(R.id.back_btn);
		mSettingBtn = (Button) getActivity().findViewById(R.id.setting_btn);
		mBackview = (TextView) getActivity().findViewById(R.id.back_view);
		mTitleView = (TextView) getActivity().findViewById(R.id.title_view);
		
		if(mSharePref.getBoolean(SettingFragment.AUTOSAVEINPUTNUM, false)){
			mPhoneNumView.setText(mSharePref.getString(SettingFragment.PHONENUM, ""));
		}
		return view;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onPageStart("MainScreen"); // 统计页面

		mBackLayout.setVisibility(View.GONE);
		mSettingBtn.setVisibility(View.VISIBLE);
		mTitleView.setText(getString(R.string.sms_bomb_free_string));
	}

	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("MainScreen");
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		if (!mUtil.isServiceRunning(mContext, SERVICENAME)) {
			mStartBtn.setText(getResources().getString(
					R.string.start_btn_string));

		} else {
			mStartBtn.setText(getResources()
					.getString(R.string.stop_btn_string));
		}
		mNumberView.setText(String
				.format(getResources().getString(R.string.number_string),
						mContext.getSharedPreferences(
								SettingFragment.SHARE_NAME,
								Activity.MODE_PRIVATE).getInt(
								SettingFragment.COUNT, 0)));

		mybroadcast = new MyBroadcast();
		IntentFilter filter = new IntentFilter();
		filter.addAction(BROADCASTNAME);
		mContext.registerReceiver(mybroadcast, filter); // 注册Broadcast Receiver
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		mContext.unregisterReceiver(mybroadcast);// 取消注册
	}

	@Override
	public void onClick(View v) {
		if (v == mStartBtn) {
			MobclickAgent.onEvent(mContext, "execute_click");

			if (!RegexUtility.matchCNMobileNumber(mPhoneNumStr)) {
				Toast.makeText(
						mContext,
						getResources().getString(
								R.string.error_phone_format_string),
						Toast.LENGTH_SHORT).show();
				return;
			}

			mSharePref.edit().putString(SettingFragment.PHONENUM, mPhoneNumStr).commit();
			
			Intent intent = new Intent(mContext, SMService.class);

			if (mStartBtn.getText().toString()
					.equals(getResources().getString(R.string.stop_btn_string))) {

				if (mUtil.isServiceRunning(mContext, SERVICENAME)) {
					mContext.stopService(intent);
				}
				Toast.makeText(mContext,
						getResources().getString(R.string.stop_smsbomb_string),
						Toast.LENGTH_SHORT).show();
				mStartBtn.setText(getResources().getString(
						R.string.start_btn_string));

			} else {
				if (!mUtil.isServiceRunning(mContext, SERVICENAME)) {
					mContext.startService(intent);
				}
				Toast.makeText(
						mContext,
						getResources().getString(R.string.start_smsbomb_string),
						Toast.LENGTH_SHORT).show();
				mStartBtn.setText(getResources().getString(
						R.string.stop_btn_string));
			}
		}

	}

	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		mPhoneNumStr = mPhoneNumView.getText().toString();
	}

	public class MyBroadcast extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			int count = intent.getIntExtra("count", 0);
			mNumberView.setText(String.format(
					getResources().getString(R.string.number_string), count));
		}
	}

}
