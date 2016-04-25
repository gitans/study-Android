package com.zhou.mobilesafe.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.zhou.mobilesafe.R;
/**
 * ��4��������ҳ
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
		//���ݱ���״̬
		if(protect){
			cbprotext.setText("���������Ѿ�����");
			cbprotext.setChecked(true);
		}else{
			cbprotext.setText("��������û�п���");
			cbprotext.setChecked(false);
		}
		//��checkBOX�����仯ʱ���ص��˷���
		cbprotext.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					cbprotext.setText("���������Ѿ�����");
					mPref.edit().putBoolean("protect", true).commit();
				}else{
					cbprotext.setText("��������û�п���");
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
		overridePendingTransition(R.anim.tran_in, R.anim.tran_out);//������˳�����
	}
}
