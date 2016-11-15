package com.feicui.order.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.feicui.model.FoodType;
import com.feicui.ordersystem.R;

public class FoodTypeAdapter extends BaseExpandableListAdapter{

String[] typeStr={"主食","凉菜","汤羹","热菜","餐具","饮品"};
List<List<FoodType>>list;
LayoutInflater layoutInflater;
Context context;

public FoodTypeAdapter(Context context) {
	// TODO Auto-generated constructor stub
	this.context=context;
	layoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	
}

public void setList(List<List<FoodType>>list) {
	this.list=list;
}

@Override
public int getGroupCount() {
	// TODO Auto-generated method stub
	return typeStr.length;
}
@Override
public int getChildrenCount(int groupPosition) {
	// TODO Auto-generated method stub
	return list==null?0:list.get(groupPosition).size();
}
@Override
public Object getGroup(int groupPosition) {
	// TODO Auto-generated method stub
	return null;
}
@Override
public Object getChild(int groupPosition, int childPosition) {
	// TODO Auto-generated method stub
	return null;
}
@Override
public long getGroupId(int groupPosition) {
	// TODO Auto-generated method stub
	return 0;
}
@Override
public long getChildId(int groupPosition, int childPosition) {
	// TODO Auto-generated method stub
	return 0;
}
@Override
public boolean hasStableIds() {
	// TODO Auto-generated method stub
	return false;
}
@Override
public View getGroupView(int groupPosition, boolean isExpanded,
		View convertView, ViewGroup parent) {
	// TODO Auto-generated method stub
	GridHolder gridHolder;
	if(convertView==null){
		convertView=this.layoutInflater.inflate(R.layout.dishes_grid, null);
		gridHolder=new GridHolder(convertView);
		convertView.setTag(gridHolder);
	}else{
		gridHolder=(GridHolder) convertView.getTag();
	}
	gridHolder.type.setText(typeStr[groupPosition]);
	return convertView;
}
@Override
public View getChildView(int groupPosition, int childPosition,
		boolean isLastChild, View convertView, ViewGroup parent) {
	// TODO Auto-generated method stub
	ChildHolder childHolder;
	if(convertView==null){
		convertView=layoutInflater.inflate(R.layout.dishes_grid_item,null);
		childHolder=new ChildHolder(convertView);
		convertView.setTag(childHolder);
	}else{
		childHolder=(ChildHolder) convertView.getTag();
	}
	childHolder.name.setText(list.get(groupPosition).get(childPosition).getfName());
	childHolder.isCheck.setChecked(list.get(groupPosition).get(childPosition).isCheck());
	return convertView;
}
@Override
public boolean isChildSelectable(int groupPosition, int childPosition) {
	// TODO Auto-generated method stub
	return true;
}
class GridHolder{
	TextView type;
	public GridHolder(View v) {
		super();
		this.type = (TextView) v.findViewById(R.id.dishes_grid_type);
	}	
}
class ChildHolder{
	TextView name;
	CheckBox isCheck;
	public ChildHolder(View v) {
		super();
		this.name =(TextView) v.findViewById(R.id.dishes_grid_name);
		this.isCheck = (CheckBox) v.findViewById(R.id.dishes_grid_ischeck);
	}
	
}
}
