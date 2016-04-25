package com.zhou.mobilesafe.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.TextureView;
import android.view.View;
import android.view.View.OnClickListener;

import com.zhou.mobilesafe.R;
import com.zhou.mobilesafe.activity.utils.ToastUtils;
import com.zhou.mobilesafe.view.SettingItemView;
/**
 * �ڶ���������ҳ
 * @author zhouzuo
 *
 */
public class Setup2Activity extends BaseSetupActivity{

	private SettingItemView sivSim;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_setup2);
		
		sivSim = (SettingItemView) findViewById(R.id.siv_sim);
		
		String sim = mPref.getString("sim", null);
		if(!TextUtils.isEmpty(sim)){
			sivSim.setChecked(true);
		}else{
			sivSim.setChecked(false);
		}
		
		sivSim.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(sivSim.isChecked()){
					sivSim.setChecked(false);
					//ɾ���Ѱ󶨵�sim��
					mPref.edit().remove("sim").commit();
				}else{
					sivSim.setChecked(true);
					//����sim������Ϣ
					TelephonyManager tm=(TelephonyManager) getSystemService(TELEPHONY_SERVICE);
					String simSerialNumber = tm.getSimSerialNumber();
					
					mPref.edit().putString("sim", simSerialNumber).commit();
				}
			}
		});
	}
	
	public void showPreviousPage(){
		startActivity(new Intent(this,Setup1Activity.class));		
		finish();
		overridePendingTransition(R.anim.tran_previous_in, R.anim.tran_previous_out);
	}
	
	public void showNextPage(){
		//���sim��û�а󶨡��Ͳ�����ȥ��һ��ҳ��
		String sim = mPref.getString("sim", null);
		if(TextUtils.isEmpty(sim)){
			ToastUtils.showToast(this, "�����sim��");
			return;
		}
		startActivity(new Intent(this,Setup3Activity.class));
		finish();
		overridePendingTransition(R.anim.tran_in, R.anim.tran_out);//������˳�����
	}
}
