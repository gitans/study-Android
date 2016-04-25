package com.zhou.mobilesafe.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.zhou.mobilesafe.R;

/**
 * 第3个设置向导页
 * 
 * @author zhouzuo
 * 
 */
public class Setup3Activity extends BaseSetupActivity {
	private EditText etPhone;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_setup3);

		etPhone = (EditText) findViewById(R.id.et_phone);
		String phone = mPref.getString("safe_phone", "");
		etPhone.setText(phone);
	}

	public void showPreviousPage() {
		startActivity(new Intent(this, Setup2Activity.class));
		finish();
		overridePendingTransition(R.anim.tran_previous_in,
				R.anim.tran_previous_out);
	}

	public void showNextPage() {
		String phone=etPhone.getText().toString().trim();
		if(TextUtils.isEmpty(phone)){
			Toast.makeText(this, "安全号码不能为空", 0).show();
			return;
		}
		
		mPref.edit().putString("safe_phone", phone).commit();//保存安全号码
		startActivity(new Intent(this, Setup4Activity.class));
		finish();
		overridePendingTransition(R.anim.tran_in, R.anim.tran_out);// 进入和退出动画
	}

	/**
	 * 选择联系人
	 * 
	 * @param v
	 */
	public void selectContact(View v) {
		Intent intent = new Intent(this, ContactActivity.class);
		startActivityForResult(intent, 0);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			String phone = data.getStringExtra("phone");
			phone = phone.replaceAll("-", " ").replaceAll(" ", "");
			System.out.println(phone);
			etPhone.setText(phone);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}
