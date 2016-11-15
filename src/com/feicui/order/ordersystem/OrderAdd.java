package com.feicui.order.ordersystem;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.feicui.model.StatusType;
import com.feicui.model.inten.IDataTable;
import com.feicui.order.adapter.OrderStatusAdapter;
import com.feicui.ordersystem.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class OrderAdd extends HorizontalActivity implements Runnable{
	List<StatusType> list;
	TextView num;
	Button back;
	ListView listView;
	OrderStatusAdapter orderStatusAdapter;
	String swiftNum;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_schedule_item);
		init();
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				swiftNum=list.get(position).getNum();
				Log.i("tag",swiftNum);
				for (int i = 0; i <list.size(); i++) {
					if(position==i){
						list.get(i).setCheck(!list.get(i).isCheck());
					}else{
						list.get(i).setCheck(false);
					}
				}
				orderStatusAdapter.notifyDataSetChanged();
				Intent intent=new Intent(OrderAdd.this,DishesMenu.class);
				intent.putExtra("swiftNum",swiftNum);
//				Bundle bundle=new Bundle();
//				bundle.putSerializable("list",(Serializable) list);
//				intent.putExtra("id",list.get(position).getId());
//				intent.putExtra("table",list.get(position).getNum());
				OrderAdd.this.startActivity(intent);
			}
		});
	}

	public void init() {
		SimpleDateFormat dateFormat=new  SimpleDateFormat("yyyyMMdd");
		listView=(ListView) this.findViewById(R.id.order_schedule_item_list);
		num = (TextView) this.findViewById(R.id.order_schedule_item_num);
		back = (Button) this.findViewById(R.id.order_schedule_item_back);
		num.setText(dateFormat.format(new Date()));
		new Thread(this).start();
	}

	public void doPost() {
		list = new ArrayList<StatusType>();
		// HttpURLConnection connection=new Default
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(IDataTable.STR_URL_IMAGE + "Status");
		try {
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
			orderStatusAdapter=new OrderStatusAdapter(OrderAdd.this, list);
			listView.setAdapter(orderStatusAdapter);
			break;

		default:
			break;
		}
	};
};
	@Override
	public void run() {
		// TODO Auto-generated method stub
		doPost();
		handler.sendEmptyMessage(0);
	}
}
