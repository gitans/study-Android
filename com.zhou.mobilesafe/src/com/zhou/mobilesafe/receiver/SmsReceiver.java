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
 * 拦截短信
 * @author zhouzuo
 *
 */
public class SmsReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Object[] objects = (Object[]) intent.getExtras().get("pdus");
		for(Object object:objects){//短信最多是140字节，超出的话，会分多条发送，所以用一个数组
			SmsMessage message = SmsMessage.createFromPdu((byte[]) object);
			String originatingAddress = message.getOriginatingAddress();//短信来源号码
			String messageBody = message.getMessageBody();//短信内容
			
			if("#*alarm*#".equals(messageBody)){
				//播放音乐，即使手机静音，也能播放音乐，因为使用的是媒体通道
				MediaPlayer player = MediaPlayer.create(context,R.raw.alarm);
				player.setVolume(1f, 1f);
				player.setLooping(true);
				player.start();				
				abortBroadcast();//中断短信的传递，从而系统短信app就接收不到内容了
			}else if("#*location*#".equals(messageBody)){
				//获取经纬度
				context.startService(new Intent(context,LocationService.class));
				SharedPreferences sp=context.getSharedPreferences("config", Context.MODE_PRIVATE);
				String location = sp.getString("location", "getting location。。。");
				
				
				abortBroadcast();
			}else if("#*wipedata*#".equals(messageBody)){
				
				abortBroadcast();
			}else if("#*lockscreen*#".equals(messageBody)){
				
				abortBroadcast();
			}
		}
	}

}
