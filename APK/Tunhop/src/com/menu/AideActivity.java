package com.menu;

import java.util.ArrayList;
import com.database.DBHelper;
import com.entite.Aide;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

//permet d'affiche la page d'aide
public class AideActivity extends Fragment {
	 private ExpandableListView listeaide;
	 private ArrayList<Aide> collection;
		 AideAdapter adapter;
		 DBHelper mydb;
	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		 final View view = inflater.inflate(R.layout.aide_mainlayout, null);
		 mydb=new DBHelper(getActivity());
		 listeaide = (ExpandableListView)view.findViewById(R.id.aide);
		 collection=mydb.getFAQ();
		 adapter = new AideAdapter(getActivity(),listeaide, collection);
		 listeaide.setAdapter(adapter);
		 return view;
	 }
}
