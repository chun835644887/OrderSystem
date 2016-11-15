package com.feicui.order.ordersystem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.feicui.model.inten.IDataTable;
import com.feicui.ordersystem.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class WithTable extends HorizontalActivity implements Runnable{

	TextView tableA;
	TextView tableB;
	Spinner spinnerTableA;
	Spinner spinnerTableB;
	Button back;
	Button certain;
	List<Integer>list;
	ArrayAdapter<Integer>adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_more_change);
		init();
	}

	public void init() {
		tableA = (TextView) this.findViewById(R.id.more_change_now);
		tableB = (TextView) this.findViewById(R.id.more_change_free);
		tableA.setText("×ÀºÅA");
		tableB.setText("×ÀºÅB");
		back=(Button) this.findViewById(R.id.more_change_back);
		certain=(Button) this.findViewById(R.id.more_change_certain);
		spinnerTableA=(Spinner) this.findViewById(R.id.more_change_now);
		spinnerTableB=(Spinner) this.findViewById(R.id.more_change_free);
		new Thread(this).start();
	}
	
	public void getUseTable() {
		list=new ArrayList<Integer>();
		HttpClient httpClient=new DefaultHttpClient();
		HttpPost httpPost=new HttpPost(IDataTable.STR_URL_IMAGE+"WithTable");
		try {
			HttpResponse response = httpClient.execute(httpPost);
			int code = response.getStatusLine().getStatusCode();
			if(code==200){
				HttpEntity entity = response.getEntity();
				String str = EntityUtils.toString(entity);
				Gson gson=new Gson();
				list=gson.fromJson(str,new TypeToken<List<Integer>>() {
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

	Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				adapter=new ArrayAdapter<Integer>(WithTable.this,
						android.R.layout.simple_spinner_dropdown_item, list);
				adapter.setDropDownViewResource(android.R.layout.preference_category);
				spinnerTableA.setAdapter(adapter);
				spinnerTableB.setAdapter(adapter);
				break;

			default:
				break;
			}
			
		};
	};
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		getTaskId();
		handler.sendEmptyMessage(0);
	}
}
