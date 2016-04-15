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
	
	//���������ص���Ϣ
	private String mVersionName;//��ȡ�汾����
	private int mVersionCode;//��ȡ�汾��
	private String mDesc;//��ȡ�汾����
	private String mDownLoadUrl;//��ȡ�汾���ص�ַ
	
	
	
	private Handler mHandler =new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case CODE_UPDATE_DAILOG:
				showUpdateDailog();
				break;

			case CODE_URL_ERROR:
				Toast.makeText(SplashActivity.this, "url����", 0).show();
				enterHome();
				break;
			case CODE_NET_ERROR:
				Toast.makeText(SplashActivity.this, "�������", 0).show();
				enterHome();
				break;
			case CODE_JSON_ERROR:
				Toast.makeText(SplashActivity.this, "���ݽ�������", 0).show();
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
		tvVersion.setText("�汾�ţ�" + getVersionName());
		tvprogress=(TextView) findViewById(R.id.tv_progress);//Ĭ������
		checkVersion();
	}

	/**
	 * ��ȡ�汾����
	 * 
	 * @return
	 */
	private String getVersionName() {
		PackageManager packageManager = getPackageManager();
		try {
			// ��ȡ������Ϣ
			PackageInfo packageInfo = packageManager.getPackageInfo(
					getPackageName(), 0);
			// int versionCode=packageInfo.versionCode;
			String versionName = packageInfo.versionName;
			return versionName;

		} catch (NameNotFoundException e) {
			// û���ҵ�������ʱ���������쳣
			e.printStackTrace();
		}

		return "�汾�ų���";

	}

	/**
	 * ��ȡ�汾��
	 * @return
	 */
	private int  getVersionCode() {
		PackageManager packageManager = getPackageManager();
		try {
			// ��ȡ������Ϣ
			PackageInfo packageInfo = packageManager.getPackageInfo(
					getPackageName(), 0);
			int versionCode=packageInfo.versionCode;
//			String versionName = packageInfo.versionName;
			return versionCode;

		} catch (NameNotFoundException e) {
			// û���ҵ�������ʱ���������쳣
			e.printStackTrace();
		}

		return -1;

	}
	/**
	 * �ӷ�������ȡ�汾��Ϣ����У��
	 */
	private void checkVersion() {
		
		final long startTime=System.currentTimeMillis();
		//�������߳��첽��������
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
//						System.out.println("����"+result);
						//����json
						JSONObject jo=new JSONObject(result);
						
						mVersionName = jo.getString("versionName");
						mVersionCode = jo.getInt("versionCode");
						mDesc = jo.getString("description");
						mDownLoadUrl = jo.getString("downloadUri");
						
//						System.out.println(mDesc);
						if(mVersionCode>getVersionCode()){//�ж��Ƿ��и���
						
							//˵���и��У����������Ի���
							msg.what=CODE_UPDATE_DAILOG;
							
						}else{
							//û�а汾����
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
					//ǿ������һ��ʱ�䣬��֤����ҳչʾ2��
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
	 * ��ʾ������Ϣ
	 */
	private void showUpdateDailog() {
		AlertDialog.Builder builder=new AlertDialog.Builder(this);
		builder.setTitle("���°汾:"+mVersionName);
		builder.setMessage(mDesc);
//		builder.setCancelable(false);//�����û�ȡ���Ի����û�����̫���������
		builder.setPositiveButton("��������", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
//				System.out.println("��������");
				download();
				
			}
		});
		builder.setNegativeButton("�Ժ���˵", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				enterHome();
				
			}
		});
		
		//����ȡ���������û�������ؼ�ʱ����
		builder.setOnCancelListener(new OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface dialog) {
				enterHome();
				
			}
		});
		
		builder.show();
		
	}

	
	/**
	 * ����apk�ļ�
	 */
	protected void download() {
		if(Environment.getExternalStorageState()
				.equals(Environment.MEDIA_MOUNTED)){
			tvprogress.setVisibility(View.VISIBLE);
			String target=Environment.getExternalStorageDirectory()+"/update.apk";
			//XUtils
			HttpUtils utils=new HttpUtils();
			utils.download(mDownLoadUrl, target, new RequestCallBack<File>() {
				//�����ļ��Ľ���
				@Override
				public void onLoading(long total, long current, boolean isUploading) {
					
					super.onLoading(total, current, isUploading);
//					System.out.println("���ؽ��ȣ�"+current+"/"+total);
					tvprogress.setText("���ؽ��ȣ�"+current*100/total+"%");
				}
				//���سɹ�
				@Override
				public void onSuccess(ResponseInfo<File> arg0) {
//					System.out.println("���سɹ�");
					tvprogress.setText("���سɹ�");
					Intent intent=new Intent(Intent.ACTION_VIEW);
					intent.addCategory(Intent.CATEGORY_DEFAULT);
					intent.setDataAndType(Uri.fromFile(arg0.result),
							"application/vnd.android.package-archive");
					
					startActivityForResult(intent, 0);//�û�ȡ����װ�Ļ�,�᷵�ؽ��										
				}
				//����ʧ��
				@Override
				public void onFailure(HttpException arg0, String arg1) {
					Toast.makeText(SplashActivity.this, "����ʧ��", 0).show();
					
				}
			});
		}else{
			Toast.makeText(SplashActivity.this, "��û��sd��", 0).show();
		}
	}

	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		enterHome();
		super.onActivityResult(requestCode, resultCode, data);
		
		
	}
	/**
	 * ������ҳ��
	 */
	private void enterHome(){
		Intent intent=new Intent(this,HomeActivity.class);
		startActivity(intent);
		finish();
	}

}
