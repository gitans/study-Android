package com.zhou.mobilesafe.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhou.mobilesafe.R;

public class LostFindActivity extends Activity {
	private SharedPreferences mPrefs;
	private TextView tvsafePhone;
	private ImageView ivProtect;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mPrefs = getSharedPreferences("config", MODE_PRIVATE);		
		
		boolean configed = mPrefs.getBoolean("config", false);
		if(configed){
			setContentView(R.layout.activity_lostfind);
			//���°�ȫ����
			tvsafePhone = (TextView) findViewById(R.id.tv_safe_phone);
			String phone = mPrefs.getString("safe_phone", "");
			tvsafePhone.setText(phone);
			ivProtect=(ImageView) findViewById(R.id.iv_protect);
			boolean protect = mPrefs.getBoolean("protect", false);
			if(protect){
				ivProtect.setImageResource(R.drawable.lock);
			}else{
				ivProtect.setImageResource(R.drawable.unlock);
			}
			
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
