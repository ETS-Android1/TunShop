package com.carte;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import com.database.DBHelper;
import com.database.JSONParser;
import com.entite.Carte;
import com.menu.R;
import com.scan.*;
import com.util.Constants;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
/*
 * La classe qui permet d'afficher une carte
 */
public class AfficherCarte extends CallsScanner {
	String mag;
	Carte c;
	Dialog d ;
	public  DBHelper mydb;
	private ProgressDialog pDialog;
	 JSONParser jParser = new JSONParser();
	 JSONArray tab = null; 
	
	 public void onCreate(Bundle savedInstanceState) 
	 {
		int a = (int) System.currentTimeMillis();
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.affichercarte);
	        mydb = new DBHelper(this);
			initPage(); 
			int b = (int) System.currentTimeMillis();
			Log.i("Carte Affiche : temps de réponse",(b-a) +" ms" );//utilisé pour tester le temps de réponse
			}
	 //fonction qui permet de retourner à la page précedente
	   public void retourner(View v)
	    {
	    	Intent t=new Intent (this,CarteMain.class);
	    	startActivity(t);
	    }
	   //permet d'ouvrir le menu
	   public void openmenu(View v)
	   {
		   openOptionsMenu();
	   }
	 public void initPage()
	 {
		 	Bundle b = getIntent().getExtras();
		 	mag= b.getString("lib_mag");
		    d= new Dialog(this);
			if( mag.equals("Autres"))
			{
				Button s=(Button)findViewById(R.id.solde);
				s.setVisibility(View.GONE);
			}
			c=mydb.getCarte(b.getString("porteur"),mag);
			setTitle("Carte "+c.getLib());
			TextView port=(TextView)findViewById(R.id.port);
			TextView carte=(TextView)findViewById(R.id.carte);
			port.setText(c.getPorteur());
			carte.setText("Carte "+c.getLib());
			//générer le code barre
			LinearLayout l= (LinearLayout)findViewById(R.id.barcode);
			GenerateBarcode gen=new GenerateBarcode();
			gen.GetImageBarcode(getApplicationContext(),l,c.getCode());
			//afficher le logo 
			ImageView logo=(ImageView)findViewById(R.id.logo);
			if (mag.contains("Carrefour"))
			{logo.setImageResource(R.drawable.carrefour);}
			else
			if (mag.contains("Geant"))
			{logo.setImageResource(R.drawable.geant);}
			else if (mag.contains("Magasin General"))
			{logo.setImageResource(R.drawable.mg);}
			else if (mag.contains("Monoprix")){logo.setImageResource(R.drawable.monoprix);	}
			else logo.setImageResource(R.drawable.ic_launcher);
	}
	//initialiser le menu qui contient 2 éléments : modifier et supprimer
	 @Override
		public boolean onCreateOptionsMenu(Menu menu) {
			final MenuInflater inflater = this.getMenuInflater();
			inflater.inflate(R.menu.affichercarte_menu, menu);
			return true;
		}
	 
	/*
	 * Sert à définir quelle action à appliquer avec chaque élément du menu
	 */
	 @Override
	 public boolean onOptionsItemSelected(MenuItem item) 
{
			switch (item.getItemId()) 
	{
			//Si l'utilisateur veut supprimer sa carte
			case R.id.supp:
			{
				  final AlertDialog.Builder builder = new AlertDialog.Builder(this);
			        builder.setMessage("Voulez vous supprimer cette carte ?")
			               .setPositiveButton("oui", new DialogInterface.OnClickListener() {
			                   public void onClick(DialogInterface dialog, int id) {			                   
			                   mydb.deleteCarte(c.getPorteur(),mag);
			                   finish();
			                   Toast.makeText(getApplicationContext(),"Carte supprimée",Toast.LENGTH_LONG).show();
			                   Intent t=new Intent (AfficherCarte.this,CarteMain.class);
			       	    	   startActivity(t);
			                   }
			               })  ; 
			        builder.setMessage("Voulez vous supprimer cette carte ?")
			        .setNegativeButton("non", new DialogInterface.OnClickListener() {
			            public void onClick(DialogInterface dialog, int id) {}   })  ;   
			        builder.create();
			        builder.show();		
			        break;
			}
			//Si l'utilisateur veut modifier sa carte
			case R.id.modif:
			{				
				d.setContentView(R.layout.dialog_carte);
				d.setTitle("Modifier la carte");
				d.setCancelable(true);
				Button bs=(Button)d.findViewById(R.id.scan_btn);
				bs.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {launchScanner(d);}});
				final EditText port_nouv=(EditText)d.findViewById(R.id.port);
		    	final EditText code_nouv=(EditText)d.findViewById(R.id.res);
		    	port_nouv.setText(c.getPorteur());
		    	code_nouv.setText(c.getCode());
		    	Button b = (Button) d.findViewById(R.id.ajouter);
				b.setText("modifier");
				b.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						if (!port_nouv.getText().toString().matches("")&&(!code_nouv.getText().toString().matches("")))
				    	{
						TextView port_anc=(TextView)findViewById(R.id.port);
						LinearLayout l= (LinearLayout)findViewById(R.id.barcode);
						c.setPorteur(port_nouv.getText()+"");
						c.setCode(code_nouv.getText()+"");
						GenerateBarcode gen=new GenerateBarcode();
						gen.GetImageBarcode(getApplicationContext(),l,c.getCode());
						mydb.updateCarte(port_anc.getText()+"",port_nouv.getText()+"",mag,code_nouv.getText()+"");
						port_anc.setText(port_nouv.getText());
						d.dismiss();
						}
					else if (port_nouv.getText().toString().matches("")) 
						build("Veuillez saisir le nom du porteur");
			    	else 
			    		build("Veuillez scanner le code barre ou le saisir");
					}});
				Button c = (Button) d.findViewById(R.id.cancel);
				c.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						d.dismiss();
					}
				});
				d.show();
				break;
			}
			
	}
			return super.onOptionsItemSelected(item);
}
	/*
	 * sert à initialiser le MessageBuilder
	 */
	 public  void build(String msg)
	    {
	    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
	        builder.setMessage(msg)
	               .setPositiveButton("ok", new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) { }
	               })  ;   
	        builder.create();
	        builder.show();	
	    }
	/*
	 *  fonction qui permet de consulter le solde des points de fidélité 
	 */
	 public void consulterSolde(View v) {
		
		 new LoadPoints().execute(); 
		 }
	/*
	 * Classe LoadPoints permet d'obtenir le solde à partir de la base de façon asynchrone 
	 */
	 class LoadPoints extends AsyncTask<String, String, String>
	{
	  	 /*
	  	  * (non-Javadoc)
	  	  * @see android.os.AsyncTask#onPreExecute()
	  	  */
		 @Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	            Log.e("ASYNK","Pre execute");
	            pDialog = new ProgressDialog(AfficherCarte.this);
	            pDialog.setMessage("Veuillez patienter..");
	            pDialog.setIndeterminate(false);
	            pDialog.setCancelable(false);
	            pDialog.show();
	        }

	  	 /*
	  	  *  (non-Javadoc)
	  	  * @see android.os.AsyncTask#doInBackground(Params[])
	  	  */
		 protected String doInBackground(String... args)
	  	  {	  
	  		  Log.e("ASYNK","doInBackground");
	  		  try {
	  			  List<NameValuePair> params = new ArrayList<NameValuePair>();
	  			  params.add(new BasicNameValuePair(Constants.TAG_CODE,c.getCode().toString()));
	  			  Log.e("code",Constants.adresse_script_points+"");
	  			  JSONObject json = jParser.makeHttpRequest(Constants.adresse_script_points, "GET", params);	  
	  			  if (json!=null) 
	                {
	  				  		Log.e("resultat",json.toString());
	  				  		tab = json.getJSONArray(Constants.TAG_COMP);                   
	                        JSONObject jsonPM = tab.getJSONObject(0);
	                        c.setPoints(jsonPM.getInt(Constants.TAG_POINTS));   
	                        c.setValidite(jsonPM.getString(Constants.TAG_VAL));
	                 }
	                }         	 
	        catch (Exception e)   {Log.e("erreur in loading",e.getMessage()); }	  
	            return null;  
	        }	 
	     /*
	      *   (non-Javadoc)
	      * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	      */
		 protected void onPostExecute(String file_url) 
	       {
	      	  Log.e("1 ASYNK","Post execute");
	            pDialog.dismiss();
	            runOnUiThread(new Runnable() {
	                public void run() { 			  		
	                	AlertDialog.Builder builder = new AlertDialog.Builder(AfficherCarte.this);	                   
	                if (c.getValidite()==null)	
	                	builder.setMessage("Cette carte est introuvable");
	                else
	                	
	                	builder.setMessage("Vous avez "+c.getPoints()+" points , valables jusqu'à "+
	                    		c.getValidite());
	                
	                builder .setPositiveButton("ok", new DialogInterface.OnClickListener() {
	                               public void onClick(DialogInterface dialog, int id) { }
	                           })  ;
	                    builder.setTitle(mag);
	                    builder.create();
	                    builder.show();	    			  		  
	                }
	                });
	       
}}}
