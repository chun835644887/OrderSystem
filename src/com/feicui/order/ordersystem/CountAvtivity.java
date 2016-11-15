package com.feicui.order.ordersystem;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.feicui.model.CountType;
import com.feicui.model.inten.IDataTable;
import com.feicui.order.adapter.CountItemAdapter;
import com.feicui.ordersystem.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class CountAvtivity extends HorizontalActivity implements Runnable {

	TextView time;
	ListView listView;
	Button certain;
	Button back;
	TextView num;
	TextView table;
	List<CountType> list;
	CountItemAdapter itemAdapter;
	RadioButton radioButton;
	String numStr;
	TextView item;
	int a = 1;
	int b = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.checkout);
		init();
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				table.setText(list.get(position).getTable());
				numStr = list.get(position).getNum();
				for (int i = 0; i < list.size(); i++) {
					if (position == i) {
						list.get(i).setCheck(!list.get(position).isCheck());
					} else {
						list.get(i).setCheck(false);
					}
				}
				itemAdapter.notifyDataSetChanged();
			}
		});
		certain.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (a == b) {
					Intent intent1 = new Intent(CountAvtivity.this,
							CheckOrderMenu.class);
					intent1.putExtra("b",b);
					intent1.putExtra("swiftNum", numStr);
					startActivity(intent1);
				} else {
					new Thread(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							doPost();
							handler.sendEmptyMessage(1);
						}
					}) {
					}.start();
				}
			}
		});
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent1=null;
				if(b==0){
					intent1=new  Intent(CountAvtivity.this,MainActivity.class);
					startActivity(intent1);
				}else{
					intent1=new Intent(CountAvtivity.this,MuchActivity.class);
					startActivity(intent1);
				}
			}
		});
	}

	public void init() {
		Intent intent = this.getIntent();
		b = intent.getIntExtra("b", 0);
		listView = (ListView) this.findViewById(R.id.activity_count_lv);
		time = (TextView) this.findViewById(R.id.checkout_date);
		certain = (Button) this.findViewById(R.id.activity_count_btn_submit);
		back = (Button) this.findViewById(R.id.activity_count_btn_back);
		table = (TextView) this.findViewById(R.id.activity_count_tablenum);
		num = (TextView) this.findViewById(R.id.activity_count_tablenum);
		new Thread(CountAvtivity.this).start();
		getData();
		// itemAdapter=new
	}

	public void getDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月");
		String strDate = dateFormat.format(new Date());
		time.setText(strDate);
	}

	public void getData() {
		list = new ArrayList<CountType>();
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(IDataTable.STR_URL_IMAGE + "Count");
		try {
			HttpResponse response = client.execute(post);
			int code = response.getStatusLine().getStatusCode();
			if (code == 200) {
				HttpEntity entity = response.getEntity();
				String reStr = EntityUtils.toString(entity, "GBK");
				Gson gson = new Gson();
				list = gson.fromJson(reStr, new TypeToken<List<CountType>>() {
				}.getType());
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void doPost() {
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(IDataTable.STR_URL_IMAGE + "Count");
		/*
		 * 添加提交的数据
		 */
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		list.add(new BasicNameValuePair("count", "1"));
		list.add(new BasicNameValuePair("num", numStr));
		Log.i("tag", numStr);
		try {
			HttpEntity entity = new UrlEncodedFormEntity(list,
					IDataTable.ENCODE);
			post.setEntity(entity);
			HttpResponse respone = client.execute(post);
			/*
			 * 判断是否响应成功
			 */
			int code = respone.getStatusLine().getStatusCode();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			Log.i("tag", "msg7");
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			Log.i("tag", "msg8");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.i("tag", "msg9");
			e.printStackTrace();
		}

	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				itemAdapter = new CountItemAdapter(CountAvtivity.this, list);
				listView.setAdapter(itemAdapter);
				break;
			case 1:
				Toast.makeText(CountAvtivity.this, "结算成功", Toast.LENGTH_SHORT);
				Intent intent = new Intent(CountAvtivity.this,
						MainActivity.class);
				startActivity(intent);
				break;
			}
		};
	};

	@Override
	public void run() {
		// TODO Auto-generated method stub
		getData();
		handler.sendEmptyMessage(0);
	}
}
