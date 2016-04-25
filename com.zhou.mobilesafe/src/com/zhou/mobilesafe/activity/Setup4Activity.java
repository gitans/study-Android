package com.zhou.mobilesafe.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.zhou.mobilesafe.R;
/**
 * 第4个设置向导页
 * @author zhouzuo
 *
 */
public class Setup4Activity extends BaseSetupActivity {
	private  CheckBox cbprotext;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_setup4);
		
		cbprotext=(CheckBox) findViewById(R.id.cb_protect);
		
		boolean protect = mPref.getBoolean("protect", false);
		//根据保存状态
		if(protect){
			cbprotext.setText("防盗保护已经开启");
			cbprotext.setChecked(true);
		}else{
			cbprotext.setText("防盗保护没有开启");
			cbprotext.setChecked(false);
		}
		//当checkBOX发生变化时，回调此方法
		cbprotext.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					cbprotext.setText("防盗保护已经开启");
					mPref.edit().putBoolean("protect", true).commit();
				}else{
					cbprotext.setText("防盗保护没有开启");
					mPref.edit().putBoolean("protect", false).commit();
				}					
			}
		});
	}
	
	public void showPreviousPage(){
		startActivity(new Intent(this,Setup3Activity.class));		
		finish();
		overridePendingTransition(R.anim.tran_previous_in, R.anim.tran_previous_out);
	}
	
	public void showNextPage(){
		startActivity(new Intent(this,LostFindActivity.class));
		finish();
		overridePendingTransition(R.anim.tran_in, R.anim.tran_out);//进入和退出动画
	}
}
