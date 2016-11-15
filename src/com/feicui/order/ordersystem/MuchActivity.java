package com.feicui.order.ordersystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.feicui.ordersystem.R;

public class MuchActivity extends HorizontalActivity{

	Button checkOrder;
	Button changeTable;
	Button withTable;
	Button update;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_more);
		init();
		checkOrder.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(MuchActivity.this,CountAvtivity.class);
				intent.putExtra("b",1);
				MuchActivity.this.startActivity(intent);
			}
		});
		changeTable.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(MuchActivity.this,ChangeTable.class);
				MuchActivity.this.startActivity(intent);
			}
		});
		withTable.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(MuchActivity.this,WithTable.class);
				MuchActivity.this.startActivity(intent);
			}
		});
	}
	
	public void init() {
		checkOrder=(Button) this.findViewById(R.id.more_check);
		changeTable=(Button) this.findViewById(R.id.more_change);
		withTable=(Button) this.findViewById(R.id.more_with);
		update=(Button) this.findViewById(R.id.more_update);
	}
}
