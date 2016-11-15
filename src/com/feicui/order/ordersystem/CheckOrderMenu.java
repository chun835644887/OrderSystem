package com.feicui.order.ordersystem;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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

import com.feicui.model.FoodType;
import com.feicui.model.inten.IDataTable;
import com.feicui.order.adapter.BrowseOrderAdapter;
import com.feicui.ordersystem.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class CheckOrderMenu extends HorizontalActivity implements Runnable {

	Bundle bundle;
	Intent intent;
	List<FoodType> list;
	Button certain;
	Button back;
	String tableStr;
	BrowseOrderAdapter adapter;
	ListView listView;
	TextView serialNumber;
	String swiftNum;
	int b=0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.browse_order);
		init();
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(CheckOrderMenu.this,CountAvtivity.class);
				intent.putExtra("b",1);
				CheckOrderMenu.this.startActivity(intent);
			}
		});
	}

	public void init() {
		serialNumber = (TextView) this.findViewById(R.id.browse_order_number);
		intent = this.getIntent();
		b=intent.getIntExtra("b",0);
		swiftNum = intent.getStringExtra("swiftNum");
		certain = (Button) this.findViewById(R.id.browse_order_certain);
		certain.setVisibility(View.INVISIBLE);
		back = (Button) this.findViewById(R.id.browse_order_back);
		listView = (ListView) this.findViewById(R.id.browse_order_lv);
		// adapter = new BrowseOrderAdapter(this, list);
		new Thread(this).start();
	}

	public void getOrderInfo() {
		HttpClient httpClient = (HttpClient) new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(IDataTable.STR_URL_IMAGE + "Check");
		List<BasicNameValuePair> listBasic = new ArrayList<BasicNameValuePair>();
		listBasic.add(new BasicNameValuePair("swiftNum", swiftNum));
		try {
			HttpEntity entity = new UrlEncodedFormEntity(listBasic,
					IDataTable.ENCODE);
			httpPost.setEntity(entity);
			HttpResponse response = httpClient.execute(httpPost);
			int code = response.getStatusLine().getStatusCode();
			if (code == 200) {
				HttpEntity reEntity = response.getEntity();
				String listStr = EntityUtils.toString(reEntity, "gbk");
				Gson gson = new Gson();
				list = gson.fromJson(listStr, new TypeToken<List<FoodType>>() {
				}.getType());
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				adapter=new BrowseOrderAdapter(CheckOrderMenu.this, list);
				Log.i("tag",list.size()+"msg3");
				adapter.setA(b);
				Log.i("tag","msg4");
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
		Log.i("tag","msg1");
		getOrderInfo();
		Log.i("tag","msg2");
		handler.sendEmptyMessage(0);
	}

}
