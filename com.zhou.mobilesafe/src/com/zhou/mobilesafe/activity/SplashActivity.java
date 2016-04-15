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
		tvVersion.setText("版本号："+getVersionName());
	}
	
	
	private String getVersionName(){
		PackageManager packageManager = getPackageManager();
		try {
			//获取包的信息
			PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
			int versionCode=packageInfo.versionCode;
			String versionName=packageInfo.versionName;
			return versionName;
			
		} catch (NameNotFoundException e) {
			// 没有找到包名的时候会走这个异常
			e.printStackTrace();
		}
		
		return "版本号出错";
		
	}
	
}
