package com.zhou.mobilesafe.activity;

import com.zhou.mobilesafe.R;
import com.zhou.mobilesafe.R.layout;
import com.zhou.mobilesafe.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.view.Menu;
import android.widget.TextView;

public class SplashActivity extends Activity {

	private TextView tvVersion;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tvVersion = (TextView) findViewById(R.id.tv_version);
		tvVersion.setText("�汾�ţ�"+getVersionName());
	}
	
	
	private String getVersionName(){
		PackageManager packageManager = getPackageManager();
		try {
			//��ȡ������Ϣ
			PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
			int versionCode=packageInfo.versionCode;
			String versionName=packageInfo.versionName;
			return versionName;
			
		} catch (NameNotFoundException e) {
			// û���ҵ�������ʱ���������쳣
			e.printStackTrace();
		}
		
		return "�汾�ų���";
		
	}
	
}
