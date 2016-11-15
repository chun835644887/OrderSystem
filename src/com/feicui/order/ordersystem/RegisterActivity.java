package com.feicui.order.ordersystem;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.feicui.model.inten.IDataTable;
import com.feicui.ordersystem.R;

public class RegisterActivity extends HorizontalActivity{
	
	EditText nameEdit;
	EditText passwordEdit1;
	EditText passwordEdit2;
	Button btnSubmit;
	String userName;
	String password1;
	String password2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_activity);
		init();
		btnSubmit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getRegisterInfo();
				getRegister();
			}
		});
	}
	
	public void init() {
		nameEdit=(EditText) this.findViewById(R.id.regiseter_user);
		passwordEdit1=(EditText) this.findViewById(R.id.regiseter_edit2);
		passwordEdit2=(EditText) this.findViewById(R.id.regiseter_edit3);
		btnSubmit=(Button) this.findViewById(R.id.regiseter_btn);
	}
	
	public void getRegisterInfo() {
		userName=nameEdit.getText().toString();
		password1=passwordEdit1.getText().toString();
		password2=passwordEdit2.getText().toString();
	}
	
	public void getRegister() {
		if(password1.equals(password2)){
			try {
				URL url = new URL(IDataTable.STR_URL+"?name="+userName+"&password="+password1);
				HttpURLConnection connection = (HttpURLConnection) url
						.openConnection();
				connection.setRequestMethod("GET");
				connection.setConnectTimeout(5000);
				int code = connection.getResponseCode();
				Toast.makeText(this, "---" + code, 0).show();
				if (code == 200) {
//					Intent intent=new Intent(RegisterActivity.this,LaunchActivity.class);
//					startActivity(intent);
					InputStream is = connection.getInputStream();
					byte b[] = new byte[10];
					int len = 0;
					while ((len = is.read(b)) != -1) {
//						 System.out.println(new String(b,0,len));
						String answerStr=new String(b,0,len);
						Log.i("tag",answerStr+"-----");
						int answer=Integer.parseInt(answerStr);
						switch (answer) {
						case 0:
							Toast.makeText(RegisterActivity.this,"用户名已存在",Toast.LENGTH_SHORT).show();
							break;
						case 1:
							Intent intent=new Intent(RegisterActivity.this,LaunchActivity.class);
							startActivity(intent);
							break;
						}
					}
				} else {
					System.out.println("defeat");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}}else{
			Toast.makeText(RegisterActivity.this,"服务器繁忙",Toast.LENGTH_SHORT).show();
			return;
		}
	}

}
