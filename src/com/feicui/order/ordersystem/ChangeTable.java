package com.feicui.order.ordersystem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.feicui.model.inten.IDataTable;
import com.feicui.ordersystem.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ChangeTable extends HorizontalActivity implements Runnable {

	Spinner nowTable;
	Spinner freeTable;
	Button back;
	Button certain;
	List<Integer> freeList;
	ArrayAdapter<Integer> adapter;
	int tableId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_more_change);
		init();
		certain.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						updateTable();
					}
				}).start();
			}
		});
		freeTable.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				tableId = freeList.get(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				tableId = freeList.get(0);
			}
		});
	}

	public void init() {
		nowTable = (Spinner) this.findViewById(R.id.more_change_now);
		freeTable = (Spinner) this.findViewById(R.id.more_change_free);
		back = (Button) this.findViewById(R.id.more_change_back);
		certain = (Button) this.findViewById(R.id.more_change_certain);
		new Thread(this).start();
	}

	public void getFreeTable() {
		freeList = new ArrayList<Integer>();
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(IDataTable.STR_URL_IMAGE + "FreeTable");
		try {
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity reEntity = response.getEntity();
			String strList = EntityUtils.toString(reEntity, "GBK");
			Gson gson = new Gson();
			freeList = gson.fromJson(strList, new TypeToken<List<Integer>>() {
			}.getType());
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void updateTable() {
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(IDataTable.STR_URL_IMAGE + "Change");
		List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
		try {
			list.add(new BasicNameValuePair("tableNum", tableId + ""));
			HttpEntity entity = new UrlEncodedFormEntity(list, "gbk");
			httpPost.setEntity(entity);
			HttpResponse response = httpClient.execute(httpPost);
			int code = response.getStatusLine().getStatusCode();
			if (code == 200) {
				handler.sendEmptyMessage(1);
			} else {
				handler.sendEmptyMessage(2);
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				adapter = new ArrayAdapter<Integer>(ChangeTable.this,
						android.R.layout.simple_spinner_dropdown_item, freeList);
				adapter.setDropDownViewResource(android.R.layout.preference_category);
				freeTable.setAdapter(adapter);
				break;
			case 1:
				Toast.makeText(ChangeTable.this, "»»×À³É¹¦", Toast.LENGTH_SHORT)
						.show();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Intent intent = new Intent(ChangeTable.this, MuchActivity.class);
				ChangeTable.this.startActivity(intent);
				break;
			case 2:
				Toast.makeText(ChangeTable.this, "»»×ÀÊ§°Ü", Toast.LENGTH_SHORT)
						.show();
				break;
			default:
				break;
			}
		};
	};

	@Override
	public void run() {
		// TODO Auto-generated method stub
		getFreeTable();
		handler.sendEmptyMessage(0);
	}
}
