package com.feicui.order.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class SuperAdapter<T> extends BaseAdapter{
List<T> list;
Context context;
LayoutInflater layoutInflater;
public SuperAdapter(Context context,List<T>list) {
	// TODO Auto-generated constructor stub
	this.context=context;
	this.list=list;
	layoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
}
//public SuperAdapter() {
//	// TODO Auto-generated constructor stub
//}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list==null?0:list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return getListView(position, convertView, parent);
	}
	protected abstract View getListView(int position, View convertView, ViewGroup parent);

}
