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
 * 设置引道页的基类，不需要再清单文件中注册，因为不需要界面展示
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

			//监听手势滑动事件
			/**
			 * e1表示滑动起始点，e2表示滑动终点
			 * velocityX表示水平速度
			 * velocityY表示垂直速度 
			 */
			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2,
					float velocityX, float velocityY) {
				//判断纵向滑动幅度是否过大，过大不允许切换界面
				if(Math.abs(e2.getRawY()-e1.getRawY())>100){
					Toast.makeText(BaseSetupActivity.this, "你还竖着划？逆天？", 0).show();
					return true;
				}
				
				if(Math.abs(velocityX)<100){
					Toast.makeText(BaseSetupActivity.this, "食屎了，划这么慢", 0).show();
					return true;
				}
				
				//向右划，上一页
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
	
	//展示上一页
		public abstract void showPreviousPage();
		
		public abstract void showNextPage();
		
		//下一页
		public void next(View v){
			showNextPage();//进入和退出动画
		}
		//上一页
		public void previous(View v){
			showPreviousPage();
		}
		
		@Override
		public boolean onTouchEvent(MotionEvent event) {
			mDectecteor.onTouchEvent(event);//委托手势识别器处理触摸事件
			
			return super.onTouchEvent(event);
			
		}
}
