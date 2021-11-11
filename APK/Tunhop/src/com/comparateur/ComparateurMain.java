package com.comparateur;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import com.database.JSONParser;
import com.entite.Magasin;
import com.entite.Produit;
import com.entite.ProduitMagasin;
import com.menu.MainActivity;
import com.menu.R;
import com.scan.CallsScanner;
import com.util.Constants;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;


public class ComparateurMain extends CallsScanner {
	EditText code;
	int success;
    private ProgressDialog pDialog;
    JSONParser jParser ;
    ArrayList<ProduitMagasin> resultCompList;
    JSONArray compprix; 
    ListView listeComp;
    ComparateurAdapter adapter ;
    boolean erreur=false;
    ProduitMagasin pm; Produit p;Magasin m;
  
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.comparateur_mainlayout);
		code=(EditText)findViewById(R.id.res);		
		listeComp=(ListView)findViewById(R.id.list);
		TextView t=(TextView)findViewById(R.id.titre);
    	t.setText("Comparateur des prix");
	}
	//permet à retourner à la page précédente
	public void retourner(View v)
    {
		NavUtils.navigateUpTo(this, new Intent(this, MainActivity.class));
    }
	//clic sur le bouton de scan
	public void Scan (View v)
	{
		launchScanner(null);
	}
	//clic sur le bouton de recherche 
	public void chercherLib (View v)
	{	  
		if(adapter !=null){
			resultCompList.clear();
			adapter.notifyDataSetChanged();
			}
		
		if(!code.getText().toString().matches(""))
		{ 
			if(JSONParser.isConnected(this))	new LoadComparaison().execute();				
			else // Si aucune connexion n'est trouvée , un message d'erreur est affiché
			{
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
		            public void onClick(DialogInterface dialog, int id) {
		            }
		        })  ;
				    builder.setMessage(Constants.message_erreur);	                      
		            builder.create();
		            builder.show();
			}
		}
		else
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
	        builder.setMessage("Veuillez scanner le code barre ou le saisir")
	               .setPositiveButton("ok", new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) { }
	               })  ;   
	        builder.create();
	        builder.show();	
		}

	}
	//permet d'obtenir les prix du produit scanné
    class LoadComparaison extends AsyncTask<String, String, String>{
	  @Override
      protected void onPreExecute() {
          super.onPreExecute();
          Log.e("ASYNK","Pre execute");
          pDialog = new ProgressDialog(ComparateurMain.this);
          pDialog.setMessage("Veuillez patienter..");
          pDialog.setMax(5000);
          pDialog.setIndeterminate(false);
          pDialog.setCancelable(false);
          pDialog.show();
      }

	  protected String doInBackground(String... args)
	  {	 
		  Log.e("ASYNK","doInBackground");
		  try {
			  resultCompList= new ArrayList<ProduitMagasin>();
			  jParser= new JSONParser();
			  compprix=new JSONArray();
			  success=1;
			  erreur=false;
			  List<NameValuePair> params = new ArrayList<NameValuePair>();
			  params.add(new BasicNameValuePair(Constants.TAG_CODEP,code.getText().toString()));
			  JSONObject json = jParser.makeHttpRequest(Constants.adresse_script_comp, "GET", params);
			  if ( json!=null)
              {
				  Log.e("resultat",json.toString());
            	  if(json.getInt("success")==1)
				    {
            		  compprix = json.getJSONArray(Constants.TAG_COMP);                 
                  for (int i = 0; i < compprix.length(); i++) 
                  {  
                      	 JSONObject jsonPM = compprix.getJSONObject(i);
                         pm = new ProduitMagasin();
                         p=new Produit(jsonPM.getString(Constants.TAG_LIBP)
                        		 ,jsonPM.getString(Constants.TAG_IMG));
                         pm.setProduit(p) ;
                         pm.setPrix(jsonPM.getDouble(Constants.TAG_PRIX));
                         m=new Magasin (jsonPM.getString(Constants.TAG_LIBM),jsonPM.getString(Constants.TAG_LOGO) );
                         pm.setMagasin(m);
                         pm.setDescription(jsonPM.getString(Constants.TAG_DESC));                     
                      resultCompList.add(pm); 
                  }
              }
            	  else success=0;
              }
			  else erreur=true;
       } catch (Exception e)   { Log.e("JSON","erreur dans le chargement des données"); erreur=true;}	  
		  return null;
      }
	 
      /**
       * After completing background task Dismiss the progress dialog
       * **/
      protected void onPostExecute(String file_url) {
    	  Log.e("1 ASYNK","Post execute");
    	  ImageView v=(ImageView)findViewById(R.id.aucun);
    	  RelativeLayout l=(RelativeLayout)findViewById(R.id.re);
    	  Log.e("ints",erreur+" , "+success);
    	  pDialog.dismiss();
            	if (!erreur)
            	 {        	  
            	  v.setBackgroundResource(R.drawable.aucun_comparateur);
            	  if(success==0)// Si aucun résultat n'a été trouvé le layout va se changer
            	  {
            		  v.setVisibility(View.VISIBLE);
            		  l.setVisibility(View.GONE);
              		  listeComp.setVisibility(View.GONE);
            	  }
            	  else
            	  {
            		  //Sinon afficher la liste des prix
            		  
            		  listeComp.setVisibility(View.VISIBLE);
            		  v.setVisibility(View.GONE);
            		  l.setVisibility(View.VISIBLE);
            		  TextView prod=(TextView)findViewById(R.id.text_prod);
                      prod.setText(resultCompList.get(0).getProduit().getTitle());
                    
            	  }
            	  adapter= new ComparateurAdapter( ComparateurMain.this, R.layout.comp_item,resultCompList);                
                  listeComp.setAdapter(adapter); 
                  listeComp.setOnItemClickListener(new OnItemClickListener() {              
                  	  @Override
                	  public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
                  	  {
                	    //afficher les détails
                		  TextView mag=(TextView)view.findViewById(R.id.libp);
                		  ProduitMagasin m=(ProduitMagasin)mag.getTag();
                		  Intent intent = new Intent(ComparateurMain.this,CompDetailActivity.class);
            			  Bundle b = new Bundle();
            			  b.putString("libp",m.getProduit().getTitle()+"");
            			  b.putString("url",m.getProduit().getImg()+"");
            			  b.putString("description",m.getDescription()+"");
            			  intent.putExtras(b); 
            			  startActivity(intent);    
            			  finish();
                	  }
                	});              	  
                  
                 
            	 }            	
            	else { //S'il ya un problème au niveau du serveur
            		v.setBackgroundResource(R.drawable.probleme_serveur);
            		v.setVisibility(View.VISIBLE);
           		    listeComp.setVisibility(View.GONE);
            		
            		}
            	code.setText("");        
      }
  
    }
}
	

