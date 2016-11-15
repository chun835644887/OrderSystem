package com.feicui.order.ordersystem;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.feicui.model.inten.IDataTable;
import com.feicui.ordersystem.R;

@SuppressLint("SetJavaScriptEnabled") 
public class LaunchActivity extends HorizontalActivity {

	WebView launchWebView;
	String userName;
	String userPassword;
	String url="file:///android_asset/landing.html";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview_launch);
		init();
		
		launchWebView.loadUrl(url);
		launchWebView.setWebViewClient(new WebViewClient(){
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				launchWebView.loadUrl(url);
				return true;
			}
		});
		WebSettings webSettings=launchWebView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		launchWebView.addJavascriptInterface(this,"main");
	}
	/*
	 * 初始化控件
	 */
	public void init() {
		launchWebView=(WebView) this.findViewById(R.id.launch_webview);
	}
	
	/*
	 * 获取账号信息
	 */
	@JavascriptInterface
	public void getUserInfo(String name,String password) {
		this.userName=name;
		this.userPassword=password;
		Log.i("tag",userName+"   "+userPassword);
	}
	
	private void doPost() {
		HttpClient client=new DefaultHttpClient();
		HttpPost post=new HttpPost(IDataTable.STR_URL);
		/*
		 * 添加提交的数据
		 */
		List<NameValuePair>list=new ArrayList<NameValuePair>();
		list.add(new BasicNameValuePair("name",userName));
		list.add(new BasicNameValuePair("password",userPassword));
		try {
			HttpEntity entity=new UrlEncodedFormEntity(list,IDataTable.ENCODE);
			post.setEntity(entity);
			HttpResponse respone = client.execute(post);
			/*
			 * 判断是否响应成功
			 */
			int code=respone.getStatusLine().getStatusCode();
			if(code==200){
				HttpEntity reEntity = respone.getEntity();
				String reEntityStr=EntityUtils.toString(reEntity);
				int recode=Integer.parseInt(reEntityStr);
				switch (recode) {
				case 0:
					Toast.makeText(this,"账号或者密码错误",Toast.LENGTH_SHORT).show();
					break;
				case 1:
					Intent intent=new Intent(LaunchActivity.this,MainActivity.class);
					startActivity(intent);
					break;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				doPost();
				break;

			case 1:
				
				break;
			}
		};
	};
}
