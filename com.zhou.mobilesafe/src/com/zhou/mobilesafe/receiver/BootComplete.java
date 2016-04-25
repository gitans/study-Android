package com.zhou.mobilesafe.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;


public class BootComplete extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
				
		boolean protect = sp.getBoolean("protect", false);
		//ֻ���ڷ���������ǰ���²�ִ��sim���ж�
		if(protect){
			String sim = sp.getString("sim", null);//��ȡ�󶨵�sim��
			if(!TextUtils.isEmpty(sim)){
				//��ȡ��ǰ�ֻ���sim��
				TelephonyManager tm=(TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
				String currentSim = tm.getSimSerialNumber();
				
				if(sim.equals(currentSim)){
//					System.out.println("�ֻ���ȫ");
				}else{
//					System.out.println("sim���Ѿ��任�����ͱ�������");
					String phone=sp.getString("safe_phone", "");//��ȡ��ȫ����
					SmsManager smsManager = SmsManager.getDefault();
					smsManager.sendTextMessage(phone, null, "sim card changer", null, null);
					
				}
			}
		}
	}

}
