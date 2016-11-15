package com.feicui.order.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.feicui.model.StatusType;
import com.feicui.model.inten.IDataTable;
import com.feicui.ordersystem.R;
import com.squareup.picasso.Picasso;

public class OrderStatusItemAdapter extends SuperAdapter<StatusType> {

	public OrderStatusItemAdapter(Context context, List<StatusType> list) {
		super(context, list);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected View getListView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Holder holder;
		if (convertView == null) {
			convertView = layoutInflater.inflate(
					R.layout.order_schedule_item_listview, null);
			holder = new Holder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		/*
		 * 用picasso缓存图片
		 * 
		 * Picasso.with(DishesMenu.this).load(IDataTable.STR_URL_IMAGE+allList.get
		 * (
		 * groupPosition).get(childPosition).getfPic()).resize(200,300).into(fImage
		 * );
		 */
		Picasso.with(context)
				.load(IDataTable.STR_URL_IMAGE
						+ list.get(position).getImageUrl()).resize(200, 200)
				.into(holder.imageView);
		holder.fName.setText(list.get(position).getfName());
		int a = Integer.parseInt(list.get(position).getfStatus());
		switch (a) {
		case 0:
holder.status.setText("已提交");
holder.statusBar.setProgress(0);
			break;
		case 1:
			holder.status.setText("正在配菜");
			holder.statusBar.setProgress(25);
			break;
		case 2:
			holder.status.setText("正在烹饪");
			holder.statusBar.setProgress(50);
			break;
		case 3:
			holder.status.setText("准备上菜");
			holder.statusBar.setProgress(75);
			break;
		case 4:
			holder.status.setText("已上菜");
			holder.statusBar.setProgress(100);
			break;
		default:
			break;
		}
		return convertView;
	}

	class Holder {
		ImageView imageView;
		ProgressBar statusBar;
		TextView fName;
		TextView status;

		public Holder(View v) {
			imageView = (ImageView) v
					.findViewById(R.id.order_schedule_item_listview_im);
			fName = (TextView) v
					.findViewById(R.id.order_schedule_item_listview_name);
			statusBar = (ProgressBar) v
					.findViewById(R.id.order_schedule_item_listview_progress);
			status = (TextView) v
					.findViewById(R.id.order_schedule_item_listview_isdo);
		}
	}

}
