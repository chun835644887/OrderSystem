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
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.feicui.model.StatusType;
import com.feicui.model.inten.IDataTable;
import com.feicui.order.adapter.OrderStatusItemAdapter;
import com.feicui.ordersystem.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class OrderStatusItemActivity extends HorizontalActivity implements Runnable{
	
	ListView listView;
	Button back;
	TextView num;
	String id;
	String table;
	List<StatusType>list;
	OrderStatusItemAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_schedule_item);
		init();
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(OrderStatusItemActivity.this,OrderStatusActivity.class);
				OrderStatusItemActivity.this.startActivity(intent);
				OrderStatusItemActivity.this.finish();
			}
		});
	}

	public void init() {
		Intent intent = this.getIntent();
		id=intent.getStringExtra("id");
		table=intent.getStringExtra("table");
		listView=(ListView) this.findViewById(R.id.order_schedule_item_list);
		back=(Button) this.findViewById(R.id.order_schedule_item_back);
		num=(TextView) this.findViewById(R.id.order_schedule_item_num);
		num.setText(table);
		new Thread(this).start();
	}
	
	public void getStatus() {
		list=new ArrayList<StatusType>();
		List<BasicNameValuePair>toList=new ArrayList<BasicNameValuePair>();
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(IDataTable.STR_URL_IMAGE + "StatusItem");
		try {
			toList.add(new BasicNameValuePair("id",id));
			HttpEntity toEntity=new UrlEncodedFormEntity(toList, IDataTable.ENCODE);
			post.setEntity(toEntity);
			HttpResponse response = client.execute(post);
			int code = response.getStatusLine().getStatusCode();
			if (code == 200) {
				HttpEntity entity = response.getEntity();
				String reStr = EntityUtils.toString(entity, "GBK");
				Gson gson = new Gson();
				list = gson.fromJson(reStr, new TypeToken<List<StatusType>>() {
				}.getType());
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			Log.i("tag", "msg10");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.i("tag", "msg11");
			e.printStackTrace();
		}
	}

	Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				adapter=new OrderStatusItemAdapter(OrderStatusItemActivity.this, list);
				listView.setAdapter(adapter);
				break;

			default:
				break;
			}
		};
	};
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		getStatus();
		handler.sendEmptyMessage(0);
	}
}
