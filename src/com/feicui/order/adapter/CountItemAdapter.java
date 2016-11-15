package com.feicui.order.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.feicui.model.CountType;
import com.feicui.ordersystem.R;

public class CountItemAdapter extends SuperAdapter<CountType> {

	public CountItemAdapter(Context context, List<CountType> list) {
		super(context, list);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected View getListView(final int position, View convertView, ViewGroup parent) {
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
		holder.radio.setChecked(list.get(position).isCheck());
		return convertView;
	}

	class Holder {
		TextView num;
		RadioButton radio;

		public Holder(View v) {
			num = (TextView) v.findViewById(R.id.count_lv_item_num);
			radio = (RadioButton) v.findViewById(R.id.count_lv_item_radio);
		}

	}
}
