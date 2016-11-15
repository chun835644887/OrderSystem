package com.feicui.order.ordersystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Gallery;

import com.feicui.order.adapter.GalleryAdapter;
import com.feicui.ordersystem.R;
@SuppressWarnings("deprecation")
public class MainActivity extends HorizontalActivity {

	Button order;
	Button count;
	Button orderStatus;
	Button addOrder;
	Button much;
	Gallery gallery;
	GalleryAdapter adapter;
	List<Integer>list;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
		gallery.setAdapter(adapter);
		order.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(MainActivity.this,DishesMenu.class);
				intent.putExtra("swiftNum","1");
				startActivity(intent);
			}
		});
		count.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(MainActivity.this,CountAvtivity.class);
				MainActivity.this.startActivity(intent);
			}
		});
		orderStatus.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(MainActivity.this,OrderStatusActivity.class);
				MainActivity.this.startActivity(intent);
			}
		});
		addOrder.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(MainActivity.this,OrderAdd.class);
				startActivity(intent);
			}
		});
		much.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(MainActivity.this,MuchActivity.class);
				MainActivity.this.startActivity(intent);
			}
		});
	}
	
	public void init() {
		order=(Button) this.findViewById(R.id.menu_btn_order);
		count=(Button) this.findViewById(R.id.menu_btn_count);
		orderStatus=(Button) this.findViewById(R.id.menu_btn_orderstatus);
		addOrder=(Button) this.findViewById(R.id.menu_btn_addorder);
		much=(Button) this.findViewById(R.id.menu_btn_much);
		gallery=(Gallery) this.findViewById(R.id.menu_gallery);
		list=new ArrayList<Integer>();
		for (int i = 1; i <7; i++) {
			this.list.add(getResources().getIdentifier("gallery"+i, "drawable",getPackageName()));
		}
		adapter=new GalleryAdapter(this, list);
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			click();
		}
		return false;
	}

	boolean exit = false;

	public void click() {
		if (exit == false) {
			exit = true;
			Timer timeExit = new Timer();
			timeExit.schedule(new TimerTask() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					exit = false;
				}
			}, 3000);
		} else {
			AlertDialog ad = new AlertDialog.Builder(this).create();
			ad.setTitle("提示");
			ad.setMessage("确定退出");
			ad.setIcon(R.drawable.ic_launcher);
			ad.setButton(DialogInterface.BUTTON_POSITIVE, "是",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							System.exit(0);
						}
					});
			ad.setButton(DialogInterface.BUTTON_NEGATIVE, "否",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
						}
					});
			ad.show();
		}
	}
  
}
