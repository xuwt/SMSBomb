package com.xuwt.smsbomb;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

public class SettingFragment extends Fragment implements OnClickListener{
	
	public static final String SHARE_NAME="share_file";
	public static final String COUNT="count";
	public static final String INTERVAL="interval";
	public static final String AUTOSAVEINPUTNUM="autosaveinputnum";
	public static final String PHONENUM="phonenum";
	
	private View mBackLayout;
	private View mAboutLayout;
	private View mExitLayout;
	private View mIntervalLayout;
	private Button mBackBtn;
	private Button mSettingBtn;
	private TextView mBackview;
	private TextView mTitleView;
	
	private Context mContext;
	private EditDialog mDialog;
	private TextView mIntervalTimeView;
	private CheckBox mSavePhoneNumCheckBox;
	
	private SharedPreferences mSharePref;
	
	public static SettingFragment newInstance(){
		SettingFragment fragment=new SettingFragment();
		return fragment;
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mContext=getActivity();
		mSharePref=mContext.getSharedPreferences(SHARE_NAME, Activity.MODE_PRIVATE);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.setting, null);
		mBackLayout=(View)getActivity().findViewById(R.id.back_layout);
		mAboutLayout=(View)view.findViewById(R.id.about_layout);
		mExitLayout=(View)view.findViewById(R.id.exit_layout);
		mIntervalLayout=(View)view.findViewById(R.id.interval_layout);
		mBackBtn=(Button)getActivity().findViewById(R.id.back_btn);
		mSettingBtn=(Button)getActivity().findViewById(R.id.setting_btn);
		mBackview=(TextView)getActivity().findViewById(R.id.back_view);
		mTitleView=(TextView)getActivity().findViewById(R.id.title_view);
		mIntervalTimeView=(TextView)view.findViewById(R.id.interval_time_view);	
		mSavePhoneNumCheckBox=(CheckBox)view.findViewById(R.id.checkBox);	
		
		mSavePhoneNumCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				mSharePref.edit().putBoolean(AUTOSAVEINPUTNUM, buttonView.isChecked()).commit();
				
			}
		});
		
		mSavePhoneNumCheckBox.setChecked(mSharePref.getBoolean(AUTOSAVEINPUTNUM, true));
		mIntervalTimeView.setText(mSharePref.getString(INTERVAL, ""));
		
		mIntervalLayout.setOnClickListener(this);
		mBackLayout.setOnClickListener(this);
		mAboutLayout.setOnClickListener(this);
		mExitLayout.setOnClickListener(this);
		
		mDialog=new EditDialog(mContext,R.style.MyDialog);
		
		return view;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onPageStart("SettingScreen"); //统计页面
		
		mBackLayout.setVisibility(View.VISIBLE);
		mSettingBtn.setVisibility(View.GONE);
		mTitleView.setText(getString(R.string.setting_string));
	}
	@Override
	public void onPause() {
	    super.onPause();
	    MobclickAgent.onPageEnd("SettingScreen"); 
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v==mBackLayout){
			((MainActivity)getActivity()).replaceFragment(MainFragment.newInstance());
			
		}else if(v==mIntervalLayout){
			/*LayoutInflater inflater=(LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			final View view=inflater.inflate(R.layout.edit_dialog, null);
			Builder builder=new Builder(mContext);
			builder.setTitle(getString(R.string.setting_internval_string));
			builder.setView(view);
			builder.setPositiveButton(R.string.sure_string, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					EditText editview=(EditText) view.findViewById(R.id.interval_edit);
					
					
				}
			});
			builder.setNegativeButton(R.string.quit_string, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			});
			builder.create().show();*/
			
			mDialog.setCanceledOnTouchOutside(false);
			mDialog.show();
			mDialog.setListener(this);

		}else if(v==mDialog.getSureButton()){
			mDialog.cancel();
			String intervalTime=mDialog.getIntervalEdit().getText().toString();
			
			mIntervalTimeView.setText(intervalTime);
			mSharePref.edit().putString(INTERVAL,intervalTime).commit();
		}else if(v==mDialog.getQuitbButton()){
			mDialog.cancel();
		}else if(v==mAboutLayout){
			Dialog dialog=new Dialog(mContext, R.style.MyDialog);
			TextView textview=new TextView(mContext);
			textview.setText(getString(R.string.theory_string));
			textview.setPadding(20, 20, 20, 20);
			textview.setTextSize(16);
			textview.setTextColor(getResources().getColor(android.R.color.black));
			LayoutParams params=new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
			
			FrameLayout frameLayout=new FrameLayout(mContext);
			frameLayout.addView(textview);
			frameLayout.setBackgroundColor(getResources().getColor(android.R.color.white));
			dialog.setContentView(frameLayout, params);
			dialog.show();
			
		}else if(v==mExitLayout){
			((MainActivity)getActivity()).finish();
		}
	}
}
