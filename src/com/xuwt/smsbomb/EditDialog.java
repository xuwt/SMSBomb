package com.xuwt.smsbomb;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditDialog extends Dialog{
	
	private Button mSureBtn;
	private Button mQuitBtn;
	private EditText mIntervalView;

	public EditDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public EditDialog(Context context, int theme) {
		super(context, theme);
		// TODO Auto-generated constructor stub
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_dialog);
		mSureBtn=(Button)findViewById(R.id.sure_btn);
		mQuitBtn=(Button)findViewById(R.id.quit_btn);
		mIntervalView=(EditText)findViewById(R.id.interval_edit);
		
	}
	public Button getSureButton(){
		return mSureBtn;
	}
	public Button getQuitbButton(){
		return mQuitBtn;
	}
	public EditText getIntervalEdit(){
		return mIntervalView;
	}
	void setListener(View.OnClickListener onclick){
		mSureBtn.setOnClickListener(onclick);
		mQuitBtn.setOnClickListener(onclick);		
	}


}
