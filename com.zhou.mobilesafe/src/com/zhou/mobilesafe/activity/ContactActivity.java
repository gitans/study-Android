package com.zhou.mobilesafe.activity;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.zhou.mobilesafe.R;

public class ContactActivity extends Activity {
	
	
	private ListView lvList;
	private ArrayList<HashMap<String, String>> readContact;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact);
		lvList = (ListView) findViewById(R.id.lv_list);
		
		readContact = readContact();
		// System.out.println(readContact);
		lvList.setAdapter(new SimpleAdapter(this, readContact,
				R.layout.contact_list_item, new String[] { "name", "phone" },
				new int[] { R.id.tv_name, R.id.tv_phone }));
		lvList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//读取当前item的电话号码
				String phone = readContact.get(position).get("phone");
				Intent intent=new Intent();
				intent.putExtra("phone", phone);
				setResult(Activity.RESULT_OK,intent);//将数据放在intent中返回给上一个页面
				System.out.println("phone"+phone);
				finish();
			}
			
		});
		
	}
	private ArrayList<HashMap<String, String>> readContact() {
		// 首先，从raw_contact中读取联系人的id("contact_id")
		// 其次，根据contact_id从data表中查询出相应的电话号码和联系人名称
		// 然后,根据mimetype来区分哪个是联系人，哪个是号码
		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		ContentResolver cr = getContentResolver();
		Cursor cursorContactid = cr.query(
				Uri.parse("content://com.android.contacts/raw_contacts"),
				new String[] { "contact_id" }, null, null, null);
		if(cursorContactid!=null){
			while (cursorContactid.moveToNext()) {
				// 获取联系人Id
				String contactId = cursorContactid.getString(cursorContactid
						.getColumnIndex("contact_id"));
				Cursor cursorData = cr.query(
						Uri.parse("content://com.android.contacts/data"),
						new String[] { "data1", "mimetype" }, "raw_contact_id=?",
						new String[] { contactId }, null);
				if(cursorData!=null){
					HashMap<String, String> map = new HashMap<String, String>();
					while (cursorData.moveToNext()) {
						String data1 = cursorData.getString(cursorData
								.getColumnIndex("data1"));
						String mimetype = cursorData.getString(cursorData
								.getColumnIndex("mimetype"));
//						System.out.println(data1 + "," + mimetype);
						
						if ("vnd.android.cursor.item/phone_v2".equals(mimetype)) {
							map.put("phone", data1);
						} else if ("vnd.android.cursor.item/name"
								.equals(mimetype)) {
							map.put("name", data1);
						}

					}
					list.add(map);
					cursorData.close();
				}
			}
			cursorContactid.close();
		}
		
		return list;
	}
}
