package com.menu;

import com.database.DBHelper;
import com.menu.R;
import com.util.Constants;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
/*
 * Page de réglages
 */
public class OptionMain extends Fragment {
	DBHelper mydb;TextView co;TextView ca;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
       final View view = inflater.inflate(R.layout.optionlayout, null);
        SeekBar carte = (SeekBar)view.findViewById(R.id.carte);
        SeekBar course = (SeekBar)view.findViewById(R.id.course);
       
         co=(TextView)view.findViewById(R.id.co);
         ca=(TextView)view.findViewById(R.id.ca);
        
         mydb=new DBHelper(getActivity());
        
        carte.setMax(Constants.max_carte);
        course.setMax(Constants.max_course);
        
        course.setProgress(mydb.ValeurConstante(Constants.Constantecourse));
        carte.setProgress(mydb.ValeurConstante(Constants.Constantecarte));
        
        ca.setText(carte.getProgress()+"");
        co.setText(course.getProgress()+"");
        carte.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			int progressChanged = 0;
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
				progressChanged = progress;
			}
			public void onStartTrackingTouch(SeekBar seekBar) {}
			
			public void onStopTrackingTouch(SeekBar seekBar) 
			{
				
				ca.setText(progressChanged+"");
				mydb.updateMaxConstants(Constants.Constantecarte, progressChanged);
			}
		});  
        course.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			int progressChanged = 0;
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
				progressChanged = progress;
			}
			public void onStartTrackingTouch(SeekBar seekBar) {}
			
			public void onStopTrackingTouch(SeekBar seekBar) 
			{
				
				co.setText(progressChanged+"");
				mydb.updateMaxConstants(Constants.Constantecourse, progressChanged);
			}
		});
        
        //Chargement des images : actif ou non
        int n=mydb.ValeurConstante(Constants.CONSTANTS_RESAUX);
        if(n==1)
        {
        	CheckBox c=(CheckBox)view.findViewById(R.id.reseau);
        	c.setChecked(true);
        }
        return view;
    }
}