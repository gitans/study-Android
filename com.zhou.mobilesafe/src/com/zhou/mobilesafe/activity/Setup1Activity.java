package com.zhou.mobilesafe.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.zhou.mobilesafe.R;
/**
 * ��һ��������ҳ
 * @author zhouzuo
 *
 */
public class Setup1Activity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_setup1);
	}
	
	//��һҳ
	public void next(View v){
		startActivity(new Intent(this,Setup2Activity.class));
		finish();
		//����������л�����
		overridePendingTransition(R.anim.tran_in, R.anim.tran_out);//������˳�����
	}
}
