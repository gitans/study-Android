package com.zhou.mobilesafe.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewDebug.ExportedProperty;
import android.widget.TextView;

public class FocusedTextView extends TextView {

	//��style��ʽ�Ļ��ߴ˷���
	public FocusedTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
	}

	
	public FocusedTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
	}

	public FocusedTextView(Context context) {
		super(context);
		
	}
	
	/**
	 * ��ʾ��û�л�ȡ����
	 * 
	 * �����Ҫ����,���ȵ��ô˺����ж��Ƿ��н���,��true�Ļ�,����ƲŻ���Ч�� �������ǲ���ʵ����textview��û�н���,
	 * ���Ƕ�ǿ�Ʒ���true, ���������Ϊ�н���
	 */
	@Override
	@ExportedProperty(category = "focus")
	public boolean isFocused() {
		// TODO Auto-generated method stub
		return true;
	}

}
