package com.zhou.mobilesafe.view;

import com.zhou.mobilesafe.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SettingItemView extends RelativeLayout {

	private static final String NAMESPACE = "http://schemas.android.com/apk/res/com.zhou.mobilesafe";
	private TextView tvTitle;
	private TextView tvDesc;
	private CheckBox cbStatus;
	private String mTitle;
	private String mDescOn;
	private String mDescOff;
	/**
	 * 设置自定义组合控件
	 * @param context	
	 * @param attrs
	 * @param defStyle
	 */
	public SettingItemView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView();
	}

	public SettingItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		int attributeCount = attrs.getAttributeCount();
		mTitle = attrs.getAttributeValue(NAMESPACE, "title");//根据属性名称获取属性的值
		mDescOn = attrs.getAttributeValue(NAMESPACE, "desc_on");
		mDescOff = attrs.getAttributeValue(NAMESPACE, "desc_off");
		initView();
		
	}

	public SettingItemView(Context context) {
		super(context);
		initView();
		
	}
	/**
	 * 初始化布局
	 */
	private void initView(){
		//将自定义好的布局文件设置给当前的SettingItemView
		View.inflate(getContext(), R.layout.view_setting_item, this);
		tvTitle = (TextView) findViewById(R.id.tv_title);
		tvDesc = (TextView) findViewById(R.id.tv_desc);
		cbStatus = (CheckBox) findViewById(R.id.cb_status);
		
		setTitle(mTitle);
	}
	
	public void setTitle(String title){
		tvTitle.setText(title);
	}
	
	public void setDesc(String desc){
		tvDesc.setText(desc);
	}
	
	/**
	 * 返回勾选状态
	 * @return
	 */
	public boolean isChecked(){
		return cbStatus.isChecked();
	}
	public void setChecked(boolean check){
		cbStatus.setChecked(check);
		//根据选择的状态，更新文本描述
		if(check){
			setDesc(mDescOn);
		}else{
			setDesc(mDescOff);
		}
	}
}
