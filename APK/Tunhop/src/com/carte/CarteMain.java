package com.carte;

import java.util.ArrayList;
import com.database.DBHelper;
import com.database.JSONParser;
import com.entite.Carte;
import com.menu.MainActivity;
import com.menu.R;
import com.util.Constants;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

/*
 * Acitivité principale de la gestion des cartes
 */
public class CarteMain extends Activity{
	 private ExpandableListView mExpandableListView;
	 private ArrayList<MesCartes> mGroupCollection= new ArrayList<MesCartes>();;
		DBHelper mydb;
		CarteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.cartemain_layout);
    	TextView t=(TextView)findViewById(R.id.titre);
    	t.setText("Liste des Cartes");
		mExpandableListView = (ExpandableListView)findViewById(R.id.expandableListView); 
    	mydb=new DBHelper(this);
        initListView();
        
    }
	private void initListView() 
	{	
		ImageView v=(ImageView)findViewById(R.id.aucune);
		if(mydb.getCount(Constants.TAB_MESCARTES)==0)
    	{
    		v.setVisibility(View.VISIBLE);
    		mExpandableListView.setVisibility(View.GONE);
    	}
		else
		{	
		v.setVisibility(View.GONE);
    	mExpandableListView.setVisibility(View.VISIBLE);
	    ArrayList<String> mag=mydb.getMagasins();
		for (int i=0;i<mag.size();i++)			
	      mGroupCollection.add(getGroup(mag.get(i).toString()));	
		  adapter = new CarteAdapter(this,mExpandableListView, mGroupCollection);
		  mExpandableListView.setAdapter(adapter);
		}
	}
	// Obtenir les cartes d'un magasin
	public MesCartes getGroup(String lib)
	{
		MesCartes ge = new MesCartes();
		ge.setMagasin(lib) ;
		ArrayList<Carte> cartes=mydb.getCartes(lib);
		for (int j=0; j < cartes.size(); j++) {ge.Ajouter(cartes.get(j));}
		return ge;
	}
	//  clic sur le bouton "ajouter une nouvelle carte"
	public void nouvellecarte(View v) 
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        })  ;
		//vérifier si l'utilisateur a atteint le nombre maximum de cartes
		if (mydb.getCount(Constants.TAB_MESCARTES)< mydb.ValeurConstante(Constants.Constantecarte))	
		{		
			if(JSONParser.isConnected(this))
			{
			Intent intent = new Intent(CarteMain.this, ListesCartes.class);
			startActivity(intent);
			}			
			else
			{				
	            builder.setMessage(Constants.message_erreur);	                      
	            builder.create();
	            builder.show();	
			}
	    }
		else
		{
            builder.setMessage("Vous avez atteint le nombre limité de cartes");  
            builder.create();
            builder.show();	
		}
	}
	// Permet d'ouvrir la carte séléctionneé depuis la liste
		public void ouvrirCarte(View v)
		{
		
			TextView port=(TextView)v.findViewById(R.id.item_title);
			TextView id=(TextView)v.findViewById(R.id.id_c);
			String mag=mydb.getMagasin(id.getText().toString());
			Intent intent = new Intent(CarteMain.this, AfficherCarte.class);
			Bundle b = new Bundle();
			b.putString("porteur",port.getText()+"" ); 
			b.putString("lib_mag",mag);
			b.putString("id",id.getText().toString());
			intent.putExtras(b); 
		    startActivity(intent);
		  }
		//permet de retourner à la page précédente
		  public void retourner(View v)
		    {
		    	NavUtils.navigateUpTo(this, new Intent(this, MainActivity.class));
		    }
}