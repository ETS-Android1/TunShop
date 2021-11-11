package com.carte;

import java.util.ArrayList;
import com.database.DBHelper;
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

/*
 * Classe adapter qui permet de gérer la listView des cartes
 */
public class CarteAdapter extends BaseExpandableListAdapter
{
	private Context Context;
	private ExpandableListView ListView;
	public  ArrayList<MesCartes> CarteCollection;
	private int[] groupStatus;
	private DBHelper mydb;
	
	public CarteAdapter(Context Context,ExpandableListView pExpandableListView,
			ArrayList<MesCartes> pGroupCollection) 
	{
		this.Context = Context;
		mydb=new DBHelper(Context);
		CarteCollection = pGroupCollection;
		ListView = pExpandableListView;
		groupStatus = new int[CarteCollection.size()];
		setListEvent();
	}
	private void setListEvent() {

		ListView.setOnGroupExpandListener(new OnGroupExpandListener() 
		{
					@Override
					public void onGroupExpand(int arg0) {
						// TODO Auto-generated method stub
						groupStatus[arg0] = 1;
					}
				});

		ListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

					@Override
					public void onGroupCollapse(int arg0) {
						// TODO Auto-generated method stub
						groupStatus[arg0] = 0;
					}
				});
	}

	@Override
	public String getChild(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return CarteCollection.get(arg0).getCarte(arg1).getLib();
	}

	@Override
	public long getChildId(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return 0;
	}
	class GroupHolder 
	{
		ImageView img;
		TextView title;
		ImageView logo;
	}

	class ChildHolder 
	{
		TextView title;
		int imageId;
		TextView id;
	}

	@Override
	public View getGroupView(int arg0, boolean arg1, View arg2, ViewGroup arg3) 
	{
		// TODO Auto-generated method stub
		GroupHolder groupHolder;
		if (arg2 == null) 
		{
			arg2 = LayoutInflater.from(Context).inflate(R.layout.carte_group,null);
			groupHolder = new GroupHolder();
			groupHolder.img = (ImageView) arg2.findViewById(R.id.tag_img);
			groupHolder.title = (TextView) arg2.findViewById(R.id.group_title);
			groupHolder.logo =(ImageView)arg2.findViewById(R.id.logo);
			arg2.setTag(groupHolder);
		} 
		else {groupHolder = (GroupHolder) arg2.getTag();}
		if (groupStatus[arg0] == 0) {groupHolder.img.setImageResource(R.drawable.group_down);}
		else {groupHolder.img.setImageResource(R.drawable.group_up);}
		
		if (CarteCollection.get(arg0).getMagasin().contains("Carrefour"))
		{groupHolder.logo.setImageResource(R.drawable.carrefour);}
		else
		if (CarteCollection.get(arg0).getMagasin().contains("Geant"))
		{groupHolder.logo.setImageResource(R.drawable.geant);}
		else if (CarteCollection.get(arg0).getMagasin().contains("Magasin General"))
		{groupHolder.logo.setImageResource(R.drawable.mg);}
		else if (CarteCollection.get(arg0).getMagasin().contains("Monoprix")) {groupHolder.logo.setImageResource(R.drawable.monoprix);	}
		else groupHolder.logo.setImageResource(R.drawable.ic_launcher);
		
		groupHolder.title.setText(CarteCollection.get(arg0).getMagasin());		
		return arg2;
	}


	@Override
	public View getChildView(int arg0, int arg1, boolean arg2, View arg3,ViewGroup arg4) {
		// TODO Auto-generated method stub

		ChildHolder childHolder;
		if (arg3 == null) {
			arg3 = LayoutInflater.from(Context).inflate(R.layout.carte_item, null);
			childHolder = new ChildHolder();
			childHolder.title = (TextView) arg3.findViewById(R.id.item_title);
			childHolder.id = (TextView) arg3.findViewById(R.id.id_c);
			arg3.setTag(childHolder);
		}else 
		{childHolder = (ChildHolder) arg3.getTag();}
		
		childHolder.title.setText(CarteCollection.get(arg0).getCarte(arg1).getPorteur());	
		childHolder.id.setText(mydb.id_carte(childHolder.title.getText()+"",getGroup(arg0).getMagasin()));
		CarteCollection.get(arg0).getCarte(arg1).setId(childHolder.id.getText()+"");
		
		return arg3;
	}

	@Override
	public int getChildrenCount(int arg0) {
		// TODO Auto-generated method stub
		return CarteCollection.get(arg0).getCartes().size();
	}

	@Override
	public MesCartes getGroup(int arg0) {
		// TODO Auto-generated method stub
		return CarteCollection.get(arg0);
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return CarteCollection.size();
	}

	@Override
	public long getGroupId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
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
