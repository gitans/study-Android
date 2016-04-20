package com.zhou.mobilesafe.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.zhou.mobilesafe.R;
/**
 * �ڶ���������ҳ
 * @author zhouzuo
 *
 */
public class Setup2Activity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_setup2);
	}
	
	//��һҳ
	public void next(View v){
		startActivity(new Intent(this,Setup3Activity.class));
		finish();
		overridePendingTransition(R.anim.tran_in, R.anim.tran_out);//������˳�����
	}
	//��һҳ
	public void previous(View v){
		startActivity(new Intent(this,Setup1Activity.class));		
		finish();
		overridePendingTransition(R.anim.tran_previous_in, R.anim.tran_previous_out);
	}
}
