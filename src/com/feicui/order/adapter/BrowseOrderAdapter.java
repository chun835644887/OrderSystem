package com.feicui.order.adapter;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.feicui.model.FoodType;
import com.feicui.ordersystem.R;

public class BrowseOrderAdapter extends SuperAdapter<FoodType> {

	int b = 0;

	public BrowseOrderAdapter(Context context, List<FoodType> list) {
		super(context, list);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 判断是结算还是查看跳转
	 * 
	 * @param a
	 */
	public void setA(int a) {
		this.b = a;
	}

	@Override
	protected View getListView(final int position, View convertView,
			ViewGroup parent) {
		// TODO Auto-generated method stub
		BrowseHolder holder;
		if (convertView == null) {
			convertView = layoutInflater.inflate(
					R.layout.borowse_order_listview, null);
			holder = new BrowseHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (BrowseHolder) convertView.getTag();
		}
		holder.num.setText(position + 1 + "");
		holder.fName.setText(list.get(position).getfName());
		holder.fPrice.setText(list.get(position).getfPrice());
		holder.fCount.setText(list.get(position).getCount() + "");
		String type = list.get(position).getfType();
		/*
		 * 获取总价
		 */
		int picInt = Integer.parseInt(type);
		int a = Integer.parseInt(list.get(position).getfPrice());
		float p = (float) a * (list.get(position).getCount());
		list.get(position).setAllPrice(p);
		holder.allPrice.setText(p + "");
		switch (picInt) {
		case 1201:
			holder.type.setText("主食");
			break;
		case 1202:
			holder.type.setText("凉菜");
			break;
		case 1203:
			holder.type.setText("汤羹");
			break;
		case 1204:
			holder.type.setText("饮料");
			break;
		case 1205:
			holder.type.setText("热食");
			break;
		case 1206:
			holder.type.setText("餐具");
			break;
		default:
			break;
		}
		Log.i("tag",b+"=++++++++++");
		if (b ==1) {
			holder.add.setVisibility(View.INVISIBLE);
			holder.sub.setVisibility(View.INVISIBLE);
			holder.del.setVisibility(View.INVISIBLE);
		}
		holder.add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				list.get(position).setCount(list.get(position).getCount() + 1);

				notifyDataSetChanged();
			}
		});
		holder.sub.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (list.get(position).getCount() > 1) {
					list.get(position).setCount(
							list.get(position).getCount() - 1);
				} else {
					list.remove(position);
				}
				notifyDataSetChanged();
			}
		});
		holder.del.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				list.remove(position);
				notifyDataSetChanged();
			}
		});

		holder.reMark.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
		return convertView;
	}

	class BrowseHolder {
		TextView num;
		TextView fName;
		TextView fPrice;
		TextView fCount;
		ImageButton add;
		ImageButton sub;
		TextView allPrice;
		TextView reMark;
		TextView type;
		ImageButton del;

		public BrowseHolder(View v) {
			// TODO Auto-generated constructor stub
			num = (TextView) v.findViewById(R.id.bor_order_list_sequence);
			fName = (TextView) v.findViewById(R.id.bor_order_list_name);
			fPrice = (TextView) v.findViewById(R.id.bor_order_list_unilt_price);
			fCount = (TextView) v.findViewById(R.id.bor_order_list_count);
			add = (ImageButton) v.findViewById(R.id.bor_order_list_add);
			sub = (ImageButton) v.findViewById(R.id.bor_order_list_sub);
			allPrice = (TextView) v.findViewById(R.id.bor_order_list_price);
			reMark = (TextView) v.findViewById(R.id.bor_order_list_remarks);
			type = (TextView) v.findViewById(R.id.bor_order_list_type);
			del = (ImageButton) v.findViewById(R.id.bor_order_list_del);
		}
	}

}
