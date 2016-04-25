package com.zhou.mobilesafe.activity.utils;

import android.content.Context;
import android.text.InputFilter.LengthFilter;
import android.widget.Toast;

public class ToastUtils {
	public static void showToast(Context ctx,String text){
		Toast.makeText(ctx, text,0).show();
	}
}
