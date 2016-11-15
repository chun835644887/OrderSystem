package com.feicui.order.ordersystem;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.feicui.model.FoodType;
import com.feicui.model.inten.IDataTable;
import com.feicui.order.adapter.FoodTypeAdapter;
import com.feicui.ordersystem.R;
import com.feicui.util.DBUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

public class DishesMenu extends Activity implements Runnable {

	Button submit;
	List<FoodType> list;
	List<List<FoodType>> allList;
	List<FoodType>listSelect;
	Spinner table;
	Spinner type;
	ExpandableListView expandableListView;
	FoodTypeAdapter typeAdapter;
	DBUtil dbUtil;
	ImageView fImage;
	TextView fName;
	TextView fPrice;
	TextView fDir;
	String select;
	String tableStr;
	String typeStr;
	String swiftNum;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dishes_menu);
		init();
		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getSelect();
				Bundle bundle=new Bundle();
				bundle.putSerializable("aa",(Serializable) listSelect);
				bundle.putString("table",tableStr);
				bundle.putString("swiftNum",swiftNum);
				Intent intent=new Intent(DishesMenu.this,BrowseOrderActivity.class);
				intent.putExtra("b",0);
				intent.putExtras(bundle);
				DishesMenu.this.startActivity(intent);
			}
		});
		/*
		 * expandableListView设置监听
		 */
		expandableListView.setOnChildClickListener(new OnChildClickListener() {
			
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				// TODO Auto-generated method stub
				fDir.setText(allList.get(groupPosition).get(childPosition).getfRemark());
				fName.setText(allList.get(groupPosition).get(childPosition).getfName());
				fPrice.setText(allList.get(groupPosition).get(childPosition).getfPrice());
				/*
				 * 用picasso缓存图片
				 */
				Picasso.with(DishesMenu.this).load(IDataTable.STR_URL_IMAGE+allList.get(groupPosition).get(childPosition).getfPic()).resize(200,300).into(fImage);
				boolean b=allList.get(groupPosition).get(childPosition).isCheck();
				allList.get(groupPosition).get(childPosition).setCheck(!b);
				typeAdapter.notifyDataSetChanged();
				return true;
			}
		});
		/**
		 * spinner设置监听
		 */
		table.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				tableStr = DishesMenu.this.getResources().getStringArray(R.array.table_num)[position];
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				tableStr = DishesMenu.this.getResources().getStringArray(R.array.table_num)[0];
			}
		});
		type.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				typeStr = DishesMenu.this.getResources().getStringArray(R.array.food_type)[position];
				if(position==0){
					for (int i = 0; i <position; i++) {
						expandableListView.expandGroup(i);
					}
				}else{
						for (int i = 0; i <expandableListView.getExpandableListAdapter().getGroupCount(); i++) {
							if(i!=(position-1)){
								expandableListView.collapseGroup(i);
							}
						}
					}
				typeAdapter.notifyDataSetChanged();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	public void init() {
		Intent intent = this.getIntent();
		swiftNum=intent.getStringExtra("swiftNum");
		fName=(TextView) this.findViewById(R.id.dishes_menu_food_name);
		fPrice=(TextView) this.findViewById(R.id.dishes_menu_food_prices);
		fImage=(ImageView) this.findViewById(R.id.dishes_menu_im);
		fDir=(TextView) this.findViewById(R.id.dishes_menu_dic);
		typeAdapter=new FoodTypeAdapter(this);
		submit = (Button) this.findViewById(R.id.dishes_menu_submit);
		expandableListView = (ExpandableListView) this
				.findViewById(R.id.dishes_menu_gridview);
		type = (Spinner) this.findViewById(R.id.dishes_menu_food_type);
		table=(Spinner) this.findViewById(R.id.dishes_menu_table_number);
		new Thread(this).start();
	}

	/**
	 * 提交数据
	 */
	public void setDishesData(String str) {
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(IDataTable.STR_SET_DATA);
		/*
		 * 添加提交的数据
		 */
		 List<NameValuePair>list=new ArrayList<NameValuePair>();
		 list.add(new BasicNameValuePair("str",str));
		try {
			 HttpEntity entity=new
			 UrlEncodedFormEntity(list,IDataTable.ENCODE);
			 post.setEntity(entity);
			HttpResponse respone = client.execute(post);
			/*
			 * 判断是否响应成功
			 */
			int code = respone.getStatusLine().getStatusCode();
			if (code == 200) {
				Toast.makeText(DishesMenu.this,"下单成功",Toast.LENGTH_SHORT).show();
//				Log.i("tag", "msg4");
//				HttpEntity reEntity = respone.getEntity();
//				String reEntityStr = EntityUtils.toString(reEntity, "GBK");
//				Gson gson = new Gson();
//				Log.i("tag", reEntityStr);
				// list=gson.fromJson(reEntityStr, new
				// TypeToken<List<FoodType>>() {
				// }.getType());
			}else{
				Toast.makeText(DishesMenu.this,"下单失败",Toast.LENGTH_SHORT).show();
			}
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
	
	/*
	 * 获取选中的菜单
	 */
	public void getSelect() {
		listSelect=new ArrayList<FoodType>();
		for (int i = 0; i <allList.size(); i++) {
			for (int j = 0; j <allList.get(i).size(); j++) {
				if(allList.get(i).get(j).isCheck()){
					listSelect.add(allList.get(i).get(j));
				}
			}
		}
	}

	public void getData() {
		list = new ArrayList<FoodType>();
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(IDataTable.STR_GET_DATA);
		try {
			HttpResponse response = client.execute(post);
			int code = response.getStatusLine().getStatusCode();
			if (code == 200) {
				HttpEntity entity = response.getEntity();
				String reStr = EntityUtils.toString(entity, "GBK");
				Gson gson = new Gson();
				list = gson.fromJson(reStr, new TypeToken<List<FoodType>>() {
				}.getType());
				getDataList();
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void getDataList() {
		allList = new ArrayList<List<FoodType>>();
		List<FoodType> mainList = new ArrayList<FoodType>();
		List<FoodType> coldList = new ArrayList<FoodType>();
		List<FoodType> soupList = new ArrayList<FoodType>();
		List<FoodType> hotList = new ArrayList<FoodType>();
		List<FoodType> tablewareList = new ArrayList<FoodType>();
		List<FoodType> drinkList = new ArrayList<FoodType>();
		for (int i = 0; i < list.size(); i++) {
			String fTypeStr = list.get(i).getfType();
			int picInt = Integer.parseInt(fTypeStr);
			switch (picInt) {
			case 1201:
				mainList.add(list.get(i));
				break;
			case 1202:
				coldList.add(list.get(i));
				break;
			case 1203:
				soupList.add(list.get(i));
				break;
			case 1204:
				drinkList.add(list.get(i));
				break;
			case 1205:
				hotList.add(list.get(i));
				break;
			case 1206:
				tablewareList.add(list.get(i));
				break;
			default:
				break;
			}
		}
		allList.add(mainList);
		allList.add(coldList);
		allList.add(soupList);
		allList.add(hotList);
		allList.add(tablewareList);
		allList.add(drinkList);
	}
	
	Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				break;
			case 1:
				typeAdapter.setList(allList);
				expandableListView.setAdapter(typeAdapter);
				break;
			}
		};
	};



	@Override
	public void run() {
		// TODO Auto-generated method stub
		getData();
		handler.sendEmptyMessage(1);
	}
}
