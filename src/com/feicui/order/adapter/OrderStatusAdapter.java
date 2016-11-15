package com.feicui.order.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.feicui.model.StatusType;
import com.feicui.ordersystem.R;

public class OrderStatusAdapter extends SuperAdapter<StatusType> {

	public OrderStatusAdapter(Context context, List<StatusType> list) {
		super(context, list);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected View getListView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Holder holder;
		if(convertView==null){
			convertView=layoutInflater.inflate(R.layout.count_lv_item,null);
			holder=new Holder(convertView);
			convertView.setTag(holder);
		}else{
			holder=(Holder) convertView.getTag();
		}
		holder.num.setText(list.get(position).getNum());
		holder.radioButton.setChecked(list.get(position).isCheck());
		return convertView;
	}
class Holder{
	RadioButton radioButton;
	TextView num;
	public Holder(View v) {
		// TODO Auto-generated constructor stub
		radioButton=(RadioButton) v.findViewById(R.id.count_lv_item_radio);
		num=(TextView) v.findViewById(R.id.count_lv_item_num);
	}
}
}
