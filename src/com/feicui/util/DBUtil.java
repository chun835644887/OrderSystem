package com.feicui.util;

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

import android.content.Context;

import com.feicui.model.FoodType;
import com.feicui.model.inten.IDataTable;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class DBUtil implements Runnable {
	List<List<FoodType>> allList;
	List<FoodType> list;
	List<FoodType> mainList;
	List<FoodType> coldList;
	List<FoodType> soupList;
	List<FoodType> hotList;
	List<FoodType> tablewareList;
	List<FoodType> drinkList;
	Context context;

	public DBUtil(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
		new Thread(DBUtil.this).start();
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
		// new Thread(DBUtil.this).start();
		// getData();
		List<List<FoodType>> allList = new ArrayList<List<FoodType>>();
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
		// allList.add(list);
		allList.add(mainList);
		allList.add(coldList);
		allList.add(soupList);
		allList.add(hotList);
		allList.add(tablewareList);
		allList.add(drinkList);
	}

	public List<List<FoodType>> getAllList() {
		return allList;
	}

	@Override
	public void run() {
		getData();
	}

}
