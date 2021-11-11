package com.menu;

import java.util.ArrayList;
import com.entite.Aide;
import com.menu.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;

public class AideAdapter extends BaseExpandableListAdapter {
	
	private Context Context;
	private ExpandableListView ListView;
	public ArrayList<Aide> collection;
	private int[] groupStatus;
	
	public AideAdapter(Context Context,ExpandableListView pExpandableListView,ArrayList<Aide> pGroupCollection) 
	{
		this.Context = Context;
		collection = pGroupCollection;
		ListView = pExpandableListView;
		groupStatus = new int[collection.size()];
		setListEvent();
	}

	private void setListEvent() {
		ListView.setOnGroupExpandListener(new OnGroupExpandListener() 
		{
					@Override
					public void onGroupExpand(int arg0) {groupStatus[arg0] = 1;}
				});

		ListView.setOnGroupCollapseListener(new OnGroupCollapseListener() 
		{
					@Override
					public void onGroupCollapse(int arg0) {groupStatus[arg0] = 0;}
				});
		}
	class GroupHolder 
	{
		TextView question;
		ImageView img;
	}

	class ChildHolder 
	{
		TextView reponse;
	}
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,View convertView, ViewGroup parent) {
		GroupHolder groupHolder;
		if (convertView == null) 
		{
			convertView = LayoutInflater.from(Context).inflate(R.layout.aide_group,null);
			groupHolder = new GroupHolder();		
			groupHolder.question = (TextView) convertView.findViewById(R.id.question);
			groupHolder.img = (ImageView) convertView.findViewById(R.id.tag_img);
			convertView.setTag(groupHolder);
		} 
		else groupHolder = (GroupHolder) convertView.getTag();
		if (groupStatus[groupPosition] == 0) {groupHolder.img.setImageResource(R.drawable.group_down);}
		else {groupHolder.img.setImageResource(R.drawable.group_up);}
		groupHolder.question.setText(collection.get(groupPosition).getQuestion());
		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,boolean isLastChild, View convertView, ViewGroup parent) {
		ChildHolder childHolder;
		if (convertView == null) {
			convertView = LayoutInflater.from(Context).inflate(R.layout.aide_item, null);
			childHolder = new ChildHolder();
			childHolder.reponse = (TextView) convertView.findViewById(R.id.reponse);
			convertView.setTag(childHolder);
		}else 
		childHolder = (ChildHolder) convertView.getTag();
		childHolder.reponse.setText(collection.get(groupPosition).getReponse());	
		return convertView;
	}

	@Override
	public int getGroupCount() {
		return collection.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return 1;
	}

	@Override
	public Aide getGroup(int groupPosition) {
		return collection.get(groupPosition);
	}

	@Override
	public String getChild(int groupPosition, int childPosition) {
		return collection.get(groupPosition).getQuestion();
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
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
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return false;
	}
}
