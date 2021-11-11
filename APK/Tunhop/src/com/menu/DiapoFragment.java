package com.menu;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
// Permet d'afficher le diapositif dans la page d'acceuil
public class DiapoFragment  extends Fragment {
	int id;
	public DiapoFragment(int img){this.id=img;}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.acceuil_logo, container, false);
            ImageView image=(ImageView)rootView.findViewById(R.id.img);
            image.setBackgroundResource(id);
        return rootView;
    }
    
   
 
}
