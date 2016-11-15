package com.feicui.order.ordersystem;

import android.os.Bundle;
import android.widget.EditText;

import com.feicui.ordersystem.R;

public class SetPassword extends HorizontalActivity{
	
	EditText originalPW;
	EditText newPW;
	EditText confirmPW;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.change_password);
	}

}
