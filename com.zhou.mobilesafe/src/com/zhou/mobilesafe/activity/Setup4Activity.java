package com.zhou.mobilesafe.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.zhou.mobilesafe.R;
/**
 * ��4��������ҳ
 * @author zhouzuo
 *
 */
public class Setup4Activity extends Activity {
	private SharedPreferences mPref;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_setup4);
		
		mPref = getSharedPreferences("config", MODE_PRIVATE);
	}
	
	//��һҳ
	public void next(View v){
		startActivity(new Intent(this,LostFindActivity.class));
		finish();
		overridePendingTransition(R.anim.tran_in, R.anim.tran_out);//������˳�����
		//����sp����ʾ�Ѿ�չʾ������
		mPref.edit().putBoolean("config", true).commit();
		
	}
	//��һҳ
	public void previous(View v){
		startActivity(new Intent(this,Setup3Activity.class));
		finish();
		overridePendingTransition(R.anim.tran_previous_in, R.anim.tran_previous_out);
	}
}
