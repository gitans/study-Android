package com.zhou.mobilesafe.service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
/**
 * ��ȡ��γ�������service
 * @author zhouzuo
 *
 */
public class LocationService extends Service {

	private LocationManager lm;
	private MyLocationListener listener;
	private SharedPreferences mPref;
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		mPref = getSharedPreferences("config", MODE_PRIVATE);

		lm = (LocationManager) getSystemService(LOCATION_SERVICE);
		// List<String> allProviders = lm.getAllProviders();// ��ȡ����λ���ṩ��
		// System.out.println(allProviders);

		Criteria criteria = new Criteria();
		criteria.setCostAllowed(true);// �Ƿ�������,����ʹ��3g���綨λ
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		String bestProvider = lm.getBestProvider(criteria, true);// ��ȡ���λ���ṩ��

		listener = new MyLocationListener();
		lm.requestLocationUpdates(bestProvider, 0, 0, listener);// ��1��ʾλ���ṩ��,��2��ʾ���
		
	}
	class MyLocationListener implements LocationListener {

		// λ�÷����仯
		@Override
		public void onLocationChanged(Location location) {
			System.out.println("get location!");
			
			// ����ȡ�ľ�γ�ȱ�����sp��
			mPref.edit()
					.putString(
							"location",
							"j:" + location.getLongitude() + "; w:"
									+ location.getLatitude()).commit();
			
			stopSelf();//ͣ��service
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			
		}

		@Override
		public void onProviderEnabled(String provider) {
			
		}

		@Override
		public void onProviderDisabled(String provider) {
			
		}
		
		
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		lm.removeUpdates(listener);// ��activity����ʱ,ֹͣ����λ��, ��ʡ����
	}

}
