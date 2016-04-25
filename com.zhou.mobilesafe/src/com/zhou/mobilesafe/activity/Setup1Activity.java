package com.zhou.mobilesafe.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.zhou.mobilesafe.R;
/**
 * 第一个设置向导页
 * @author zhouzuo
 *
 */
public class Setup1Activity extends BaseSetupActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_setup1);
	}
	
	public void showNextPage(){
		startActivity(new Intent(this,Setup2Activity.class));
		finish();
		overridePendingTransition(R.anim.tran_in, R.anim.tran_out);//进入和退出动画
	}

	@Override
	public void showPreviousPage() {	
	}
}
