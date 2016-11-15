package com.feicui.order.ordersystem;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
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
import android.widget.Toast;

import com.feicui.model.FoodType;
import com.feicui.model.inten.IDataTable;
import com.feicui.order.adapter.BrowseOrderAdapter;
import com.feicui.ordersystem.R;
import com.google.gson.Gson;

public class BrowseOrderActivity extends HorizontalActivity implements Runnable {

	Bundle bundle;
	Intent intent;
	List<FoodType> list;
	Button certain;
	Button back;
	String tableStr;
	BrowseOrderAdapter adapter;
	ListView listView;
	TextView serialNumber;
	String numStr;
	String strList;
	String swiftNum;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.browse_order);
		init();
		listView.setAdapter(adapter);
		certain.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i("tag",swiftNum+"-------");
				Gson gson = new Gson();
				strList = gson.toJson(list);
				new Thread(BrowseOrderActivity.this).start();
			}
		});
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(BrowseOrderActivity.this,
						DishesMenu.class);
				startActivity(intent);
			}
		});
	}

	public void init() {
		serialNumber = (TextView) this.findViewById(R.id.browse_order_number);
		intent = this.getIntent();
		bundle = intent.getExtras();
		swiftNum=bundle.getString("swiftNum");
		tableStr = bundle.getString("table");
		getSerialNumber();
		serialNumber.setText(numStr);
		list = (List<FoodType>) bundle.getSerializable("aa");
		certain = (Button) this.findViewById(R.id.browse_order_certain);
		back = (Button) this.findViewById(R.id.browse_order_back);
		listView = (ListView) this.findViewById(R.id.browse_order_lv);
		adapter = new BrowseOrderAdapter(this, list);
	}

	public void getSerialNumber() {
		Random random=new Random();
		int a=random.nextInt(900)+100;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		numStr = formatter.format(new Date()) + "0000" + tableStr+a;
	}

	public void postOrder() {
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(IDataTable.STR_URL_IMAGE + "Order");
		/*
		 * 设置是加单还是第一次点菜
		 */
		String add="0";
		/*
		 * 添加提交的数据
		 */
		List<NameValuePair> listPost = new ArrayList<NameValuePair>();
		/**
		 * 流水号
		 */
		if(!"1".equals(swiftNum)){
			numStr=swiftNum;
			add="1";
		}
		listPost.add(new BasicNameValuePair("num", numStr));
		/*
		 * 设置是加单还是第一次点菜
		 */
		listPost.add(new BasicNameValuePair("add",add));
		/**
		 * 点菜的集合
		 */
		listPost.add(new BasicNameValuePair("list", strList));
		/**
		 * 餐桌号
		 */
		listPost.add(new BasicNameValuePair("table", tableStr));
//		HttpEntity entity;
Log.i("tag",add);
		try {
			HttpEntity entity = new UrlEncodedFormEntity(listPost, IDataTable.ENCODE);
			post.setEntity(entity);
			HttpResponse respone = client.execute(post);
			int code = respone.getStatusLine().getStatusCode();
			Log.i("tag",code+"");
			if (code == 200) {
				HttpEntity reEntity = respone.getEntity();
				String reEntityStr = EntityUtils.toString(reEntity, "GBK");
				Log.i("tag",reEntityStr+"------");
				// Gson gson = new Gson();
//				int reCode = Integer.parseInt(reEntityStr);
				switch (1) {
				case 0:
					handler.sendEmptyMessage(0);
					break;
				case 1:
					handler.sendEmptyMessage(1);
					break;
				}
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			Log.i("tag","msg10");
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			Log.i("tag","msg11");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.i("tag","msg12");
			e.printStackTrace();
		}
	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				Toast.makeText(BrowseOrderActivity.this, "下单失败",
						Toast.LENGTH_SHORT).show();
				break;

			case 1:
				Log.i("tag","下单成功");
				Toast.makeText(BrowseOrderActivity.this, "下单成功",
						Toast.LENGTH_SHORT).show();
				try {
					new Thread(BrowseOrderActivity.this).sleep(2000);
					Intent intent=new Intent(BrowseOrderActivity.this,MainActivity.class);
					BrowseOrderActivity.this.startActivity(intent);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
		};
	};

	@Override
	public void run() {
		// TODO Auto-generated method stub
		postOrder();
	}
}
