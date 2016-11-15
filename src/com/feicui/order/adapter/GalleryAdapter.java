package com.feicui.order.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.feicui.ordersystem.R;

public class GalleryAdapter extends SuperAdapter<Integer>{

	public GalleryAdapter(Context context, List<Integer> list) {
		super(context, list);
		
	}

	@Override
	protected View getListView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		GalleryHolder holder;
		if(convertView==null){
			convertView=layoutInflater.inflate(R.layout.main_gallery,null);
			holder=new GalleryHolder(convertView);
			convertView.setTag(holder);
		}else{
			holder=(GalleryHolder) convertView.getTag();
		}
		holder.imageView.setImageResource(list.get(position));
		return convertView;
	}
	class GalleryHolder{
		ImageView imageView;
		public GalleryHolder(View view) {
			imageView=(ImageView) view.findViewById(R.id.gallery_item);
		}
	}

}
