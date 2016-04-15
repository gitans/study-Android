package com.zhou.mobilesafe.activity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.zhou.mobilesafe.R;
import com.zhou.mobilesafe.activity.utils.StreamUtils;

public class SplashActivity extends Activity {

	protected static final int CODE_UPDATE_DAILOG = 0;
	protected static final int CODE_URL_ERROR = 1;
	protected static final int CODE_NET_ERROR = 2;
	protected static final int CODE_JSON_ERROR =3;
	protected static final int CODE_ENTER_HOME =4;
	
	
	
	private TextView tvVersion;
	private TextView tvprogress;
	
	//服务器返回的信息
	private String mVersionName;//获取版本名称
	private int mVersionCode;//获取版本号
	private String mDesc;//获取版本描述
	private String mDownLoadUrl;//获取版本下载地址
	
	
	
	private Handler mHandler =new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case CODE_UPDATE_DAILOG:
				showUpdateDailog();
				break;

			case CODE_URL_ERROR:
				Toast.makeText(SplashActivity.this, "url错误", 0).show();
				enterHome();
				break;
			case CODE_NET_ERROR:
				Toast.makeText(SplashActivity.this, "网络错误", 0).show();
				enterHome();
				break;
			case CODE_JSON_ERROR:
				Toast.makeText(SplashActivity.this, "数据解析错误", 0).show();
				enterHome();
				break;
			case CODE_ENTER_HOME:
				
				enterHome();
				break;
			
			}
		};
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		tvVersion = (TextView) findViewById(R.id.tv_version);
		tvVersion.setText("版本号：" + getVersionName());
		tvprogress=(TextView) findViewById(R.id.tv_progress);//默认隐藏
		checkVersion();
	}

	/**
	 * 获取版本名称
	 * 
	 * @return
	 */
	private String getVersionName() {
		PackageManager packageManager = getPackageManager();
		try {
			// 获取包的信息
			PackageInfo packageInfo = packageManager.getPackageInfo(
					getPackageName(), 0);
			// int versionCode=packageInfo.versionCode;
			String versionName = packageInfo.versionName;
			return versionName;

		} catch (NameNotFoundException e) {
			// 没有找到包名的时候会走这个异常
			e.printStackTrace();
		}

		return "版本号出错";

	}

	/**
	 * 获取版本号
	 * @return
	 */
	private int  getVersionCode() {
		PackageManager packageManager = getPackageManager();
		try {
			// 获取包的信息
			PackageInfo packageInfo = packageManager.getPackageInfo(
					getPackageName(), 0);
			int versionCode=packageInfo.versionCode;
//			String versionName = packageInfo.versionName;
			return versionCode;

		} catch (NameNotFoundException e) {
			// 没有找到包名的时候会走这个异常
			e.printStackTrace();
		}

		return -1;

	}
	/**
	 * 从服务器获取版本信息进行校验
	 */
	private void checkVersion() {
		
		final long startTime=System.currentTimeMillis();
		//启动子线程异步加载数据
		new Thread() {
			
			private HttpURLConnection conn;

			@Override
			public void run() {
				super.run();
				
				Message msg=mHandler.obtainMessage();

				try {
					URL url = new URL("http://192.168.1.104:8080/update.json");
					conn = (HttpURLConnection) url
							.openConnection();
					
					conn.setRequestMethod("GET");
					conn.setConnectTimeout(5000);
					conn.setReadTimeout(5000);
					conn.connect();
					
					if(conn.getResponseCode()==200){
						InputStream inputStream = conn.getInputStream();
						String result=StreamUtils.readFromStream(inputStream);
//						System.out.println("返回"+result);
						//解析json
						JSONObject jo=new JSONObject(result);
						
						mVersionName = jo.getString("versionName");
						mVersionCode = jo.getInt("versionCode");
						mDesc = jo.getString("description");
						mDownLoadUrl = jo.getString("downloadUri");
						
//						System.out.println(mDesc);
						if(mVersionCode>getVersionCode()){//判断是否有更新
						
							//说明有更行，弹出升级对话框
							msg.what=CODE_UPDATE_DAILOG;
							
						}else{
							//没有版本更新
							msg.what=CODE_ENTER_HOME;
						}
						
					}

				} catch (MalformedURLException e) {
					msg.what=CODE_URL_ERROR;
					e.printStackTrace();
				} catch (IOException e) {
					msg.what=CODE_NET_ERROR;
					e.printStackTrace();
				} catch (JSONException e) {
					msg.what=CODE_JSON_ERROR;
					e.printStackTrace();
				}finally{
					
					long endTime=System.currentTimeMillis();
					
					long timeUsed=endTime-startTime;
					//强制休眠一段时间，保证闪屏页展示2秒
					if(timeUsed<2000){
						try {
							Thread.sleep(2000-timeUsed);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					mHandler.sendMessage(msg);
					
					if(conn!=null){
						conn.disconnect();
					}
				}

			}

		}.start();

	}

	/**
	 * 显示更新信息
	 */
	private void showUpdateDailog() {
		AlertDialog.Builder builder=new AlertDialog.Builder(this);
		builder.setTitle("最新版本:"+mVersionName);
		builder.setMessage(mDesc);
//		builder.setCancelable(false);//不让用户取消对话框，用户体验太差，尽量不用
		builder.setPositiveButton("立即更行", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
//				System.out.println("立即更新");
				download();
				
			}
		});
		builder.setNegativeButton("以后再说", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				enterHome();
				
			}
		});
		
		//设置取消监听，用户点击返回键时触发
		builder.setOnCancelListener(new OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface dialog) {
				enterHome();
				
			}
		});
		
		builder.show();
		
	}

	
	/**
	 * 下载apk文件
	 */
	protected void download() {
		if(Environment.getExternalStorageState()
				.equals(Environment.MEDIA_MOUNTED)){
			tvprogress.setVisibility(View.VISIBLE);
			String target=Environment.getExternalStorageDirectory()+"/update.apk";
			//XUtils
			HttpUtils utils=new HttpUtils();
			utils.download(mDownLoadUrl, target, new RequestCallBack<File>() {
				//下载文件的进度
				@Override
				public void onLoading(long total, long current, boolean isUploading) {
					
					super.onLoading(total, current, isUploading);
//					System.out.println("下载进度："+current+"/"+total);
					tvprogress.setText("下载进度："+current*100/total+"%");
				}
				//下载成功
				@Override
				public void onSuccess(ResponseInfo<File> arg0) {
//					System.out.println("下载成功");
					tvprogress.setText("下载成功");
					Intent intent=new Intent(Intent.ACTION_VIEW);
					intent.addCategory(Intent.CATEGORY_DEFAULT);
					intent.setDataAndType(Uri.fromFile(arg0.result),
							"application/vnd.android.package-archive");
					
					startActivityForResult(intent, 0);//用户取消安装的话,会返回结果										
				}
				//下载失败
				@Override
				public void onFailure(HttpException arg0, String arg1) {
					Toast.makeText(SplashActivity.this, "下载失败", 0).show();
					
				}
			});
		}else{
			Toast.makeText(SplashActivity.this, "你没有sd卡", 0).show();
		}
	}

	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		enterHome();
		super.onActivityResult(requestCode, resultCode, data);
		
		
	}
	/**
	 * 进入主页面
	 */
	private void enterHome(){
		Intent intent=new Intent(this,HomeActivity.class);
		startActivity(intent);
		finish();
	}

}
