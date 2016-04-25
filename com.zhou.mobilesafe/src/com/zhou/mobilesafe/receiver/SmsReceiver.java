package com.zhou.mobilesafe.receiver;


import com.zhou.mobilesafe.R;
import com.zhou.mobilesafe.service.LocationService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
/**
 * ���ض���
 * @author zhouzuo
 *
 */
public class SmsReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Object[] objects = (Object[]) intent.getExtras().get("pdus");
		for(Object object:objects){//���������140�ֽڣ������Ļ�����ֶ������ͣ�������һ������
			SmsMessage message = SmsMessage.createFromPdu((byte[]) object);
			String originatingAddress = message.getOriginatingAddress();//������Դ����
			String messageBody = message.getMessageBody();//��������
			
			if("#*alarm*#".equals(messageBody)){
				//�������֣���ʹ�ֻ�������Ҳ�ܲ������֣���Ϊʹ�õ���ý��ͨ��
				MediaPlayer player = MediaPlayer.create(context,R.raw.alarm);
				player.setVolume(1f, 1f);
				player.setLooping(true);
				player.start();				
				abortBroadcast();//�ж϶��ŵĴ��ݣ��Ӷ�ϵͳ����app�ͽ��ղ���������
			}else if("#*location*#".equals(messageBody)){
				//��ȡ��γ��
				context.startService(new Intent(context,LocationService.class));
				SharedPreferences sp=context.getSharedPreferences("config", Context.MODE_PRIVATE);
				String location = sp.getString("location", "getting location������");
				
				
				abortBroadcast();
			}else if("#*wipedata*#".equals(messageBody)){
				
				abortBroadcast();
			}else if("#*lockscreen*#".equals(messageBody)){
				
				abortBroadcast();
			}
		}
	}

}
