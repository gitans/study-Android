package com.zhou.mobilesafe.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.zhou.mobilesafe.R;
import com.zhou.mobilesafe.view.SettingItemView;

/*
 * ��������
 * 
 */

public class SettingActivity extends Activity {
	
	private SettingItemView sivUpdate;//��������
	private SharedPreferences mPref;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		
		mPref = getSharedPreferences("config", MODE_PRIVATE);
		
		sivUpdate = (SettingItemView) findViewById(R.id.siv_update);
//		sivUpdate.setTitle("�Զ�����");
		boolean autoUpdate = mPref.getBoolean("auto_update", true);
		if(autoUpdate){
//			sivUpdate.setDesc("�Զ������ѿ���");
			sivUpdate.setChecked(true);
		}else{
//			sivUpdate.setDesc("�Զ������ѹر�");
			sivUpdate.setChecked(false);
		}
		
		
		sivUpdate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//�жϵ�ǰ�Ĺ�ѡ״̬
				if(sivUpdate.isChecked()){
					//���ò���ѡ
					sivUpdate.setChecked(false);
//					sivUpdate.setDesc("�Զ������ѹر�");
					mPref.edit().putBoolean("auto_update", false).commit();
				}else{
					sivUpdate.setChecked(true);
//					sivUpdate.setDesc("�Զ������ѿ���");
					//����sp
					mPref.edit().putBoolean("auto_update", true).commit();
				}
			}
		});
		
	}
}
