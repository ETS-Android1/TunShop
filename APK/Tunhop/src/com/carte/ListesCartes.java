package com.carte;

import java.util.List;
import com.database.JSONParser;
import com.entite.Magasin;
import com.menu.R;
import com.util.Constants;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.util.Log;
import android.view.View;
import java.util.ArrayList;
import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

/*
 * Classe qui permet d'afficher les magasins pour que l'utilisateur en choisit un 
 */
public class ListesCartes extends Activity 
{ 	
	boolean erreur=false;
	 ListView maliste;
	private ProgressDialog pDialog;
	MagasinAdapter adapter;
	 JSONParser jParser = new JSONParser();
	 JSONArray tab_mag = null; 
	 ArrayList<Magasin> listeDesMagasins;
	 Magasin m=new Magasin();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listecarte_mainlayout); 
        maliste=(ListView)findViewById(R.id.cartes);
        listeDesMagasins= new ArrayList<Magasin>();
        new LoadListe().execute();
    }
    // Si l'utilisateur a choisi de creer une carte personnalisée
    public void AjouterAutres (View v)
    {
    	 Intent intent = new Intent(ListesCartes.this,NouvelleCarte.class);
		  Bundle b = new Bundle();
		  b.putString("maglib","Autres");
		  b.putString("cartelib","");
		  intent.putExtras(b); 
		  startActivity(intent);    
		  finish();	
    }
    //permet à retourner à la page précédente
    public void retourner(View v)
    {
    	Intent t=new Intent (this,CarteMain.class);
    	startActivity(t);
    }
   
    // classe qui permet d'obetnir les magasins à partir de la base
    class LoadListe extends AsyncTask<String, String, String>{
  	  @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.e("ASYNK","Pre execute");
            pDialog = new ProgressDialog(ListesCartes.this);
            pDialog.setMessage("Veuillez patienter..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

  	  protected String doInBackground(String... args)
  	  {	  
  		  Log.e("ASYNK","doInBackground");
  		  try {
  			  List<NameValuePair> params = new ArrayList<NameValuePair>();
  			  JSONObject json = jParser.makeHttpRequest(Constants.adresse_script_mag, "GET", params);	  			  
  			  if (json!=null) 
                { 	
  				Log.e("resultat",json.toString()); 
  				tab_mag = json.getJSONArray(Constants.TAG_COMP);                 
                    for (int i = 0; i < tab_mag.length(); i++) 
                    {  
                        JSONObject jsonPM = tab_mag.getJSONObject(i);
                           m=new Magasin();
                           m.setLib(jsonPM.getString(Constants.TAG_LIBM));
                           m.setUrl_logo(jsonPM.getString(Constants.TAG_LOGO));
                           m.setLibcarte(jsonPM.getString(Constants.TAG_LIBF));                               
                        listeDesMagasins.add(m); 
                    }
                } 
  			  else erreur=true;
         } catch (Exception e)   {Log.e("ListesCartes","erreur dans le chargement des données");erreur=true; }	  
            return null;
        }
  	 
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) 
       {
      	  			Log.e("1 ASYNK","Post execute");
      	  			pDialog.dismiss(); 
      	  		if (!erreur)
      	  		{	
                	maliste.setOnItemClickListener(new OnItemClickListener() {              
              	  @Override
            	  public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
              	  {
            	    //nouvelle carte
            		  TextView mag=(TextView)view.findViewById(R.id.mag);
            		  Magasin m=(Magasin)mag.getTag();
            		  Intent intent = new Intent(ListesCartes.this,NouvelleCarte.class);
        			  Bundle b = new Bundle();
        			  b.putString("maglib",m.getLib()+"");
        			  b.putString("cartelib",m.getLibcarte()+"");
        			  intent.putExtras(b); 
        			  startActivity(intent);    
        			  finish();
            	  }
            	});  
                	adapter= new MagasinAdapter( ListesCartes.this,R.layout.listecarte_item,listeDesMagasins);                
                    maliste.setAdapter(adapter);
                }
      	  		
      	  		   else
        {
      	  			   //S'il ya une erreur au niveau du serveur , le contenu de la page va se changer
         	  	   LinearLayout l=(LinearLayout)findViewById(R.id.content);
     	           ImageView v=new ImageView(ListesCartes.this);
     	           v.setBackgroundResource(R.drawable.probleme_serveur);
     	           Button b=new Button(ListesCartes.this);
     	           b.setText("Réssayer");
     	           l.addView(b); 
     	           l.addView(v);           
     	           b.setOnClickListener(new View.OnClickListener() {			  
     			       public void onClick(View v) {			  	          
     			    	   finish();
     			    	   startActivity(getIntent());	  
     				      }			 
     				   });	
         }
        }
        }
    }
                