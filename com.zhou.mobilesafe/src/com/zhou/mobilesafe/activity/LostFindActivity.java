package com.zhou.mobilesafe.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.zhou.mobilesafe.R;

public class LostFindActivity extends Activity {
	private SharedPreferences mPref;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mPref = getSharedPreferences("config", MODE_PRIVATE);
		
		boolean configed = mPref.getBoolean("config", false);
		if(configed){
			setContentView(R.layout.activity_lostfind);
			
		}else{
			//��ת������ҳ
			startActivity(new Intent(this,Setup1Activity.class));
			finish();
			
		}
	}
	
	/**
	 * ���½���������
	 * @param v
	 */
	public void reEnter(View v){
		startActivity(new Intent(this,Setup1Activity.class));
		finish();
		overridePendingTransition(R.anim.tran_in, R.anim.tran_out);//������˳�����
	}
	
}
