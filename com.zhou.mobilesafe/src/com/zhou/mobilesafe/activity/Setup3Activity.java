package com.zhou.mobilesafe.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.zhou.mobilesafe.R;
/**
 * ��3��������ҳ
 * @author zhouzuo
 *
 */
public class Setup3Activity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_setup3);
	}
	
	//��һҳ
	public void next(View v){
		startActivity(new Intent(this,Setup4Activity.class));
		finish();
		overridePendingTransition(R.anim.tran_in, R.anim.tran_out);//������˳�����
	}
	//��һҳ
	public void previous(View v){
		startActivity(new Intent(this,Setup2Activity.class));
		finish();
		overridePendingTransition(R.anim.tran_previous_in, R.anim.tran_previous_out);
	}
}
