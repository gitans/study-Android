package com.zhou.mobilesafe.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
/**
 * ��������ҳ�Ļ��࣬����Ҫ���嵥�ļ���ע�ᣬ��Ϊ����Ҫ����չʾ
 * @author zhouzuo
 *
 */
public abstract class BaseSetupActivity extends Activity {
	private GestureDetector mDectecteor;
	public SharedPreferences mPref;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mPref = getSharedPreferences("config", MODE_PRIVATE);
		
		mDectecteor = new GestureDetector(this, new SimpleOnGestureListener(){

			//�������ƻ����¼�
			/**
			 * e1��ʾ������ʼ�㣬e2��ʾ�����յ�
			 * velocityX��ʾˮƽ�ٶ�
			 * velocityY��ʾ��ֱ�ٶ� 
			 */
			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2,
					float velocityX, float velocityY) {
				//�ж����򻬶������Ƿ���󣬹��������л�����
				if(Math.abs(e2.getRawY()-e1.getRawY())>100){
					Toast.makeText(BaseSetupActivity.this, "�㻹���Ż������죿", 0).show();
					return true;
				}
				
				if(Math.abs(velocityX)<100){
					Toast.makeText(BaseSetupActivity.this, "ʳʺ�ˣ�����ô��", 0).show();
					return true;
				}
				
				//���һ�����һҳ
				if((e2.getRawX()-e1.getRawX())>100){
					showPreviousPage();
					return true;
				}
				
				if((e1.getRawX()-e2.getRawX())>100){
					showNextPage();
					return true;
				}
				
				return super.onFling(e1, e2, velocityX, velocityY);
			}
		});
	}
	
	//չʾ��һҳ
		public abstract void showPreviousPage();
		
		public abstract void showNextPage();
		
		//��һҳ
		public void next(View v){
			showNextPage();//������˳�����
		}
		//��һҳ
		public void previous(View v){
			showPreviousPage();
		}
		
		@Override
		public boolean onTouchEvent(MotionEvent event) {
			mDectecteor.onTouchEvent(event);//ί������ʶ�����������¼�
			
			return super.onTouchEvent(event);
			
		}
}
