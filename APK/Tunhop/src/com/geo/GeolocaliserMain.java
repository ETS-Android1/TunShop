package com.geo;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import com.database.JSONParser;
import com.entite.GeoPoint;
import com.entite.Magasin;
import com.entite.PointVente;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.menu.MainActivity;
import com.menu.R;
import com.util.Constants;

public class GeolocaliserMain extends Activity  {
    int a,b;
    boolean visible=true;
    boolean erreur=false;
    double distance;
    private GoogleMap googleMap;
    ArrayList<Magasin> listMag=new ArrayList<Magasin>();
    String spin1Ville; String spin2Mag;
    Spinner spinner1, spinner2;
    JSONParser jParser = new JSONParser(); 
	AlertDialog.Builder builder;
	private ProgressDialog pDialog;
@Override
public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.geolocaliser_main);
	builder = new AlertDialog.Builder(GeolocaliserMain.this);	
	if( JSONParser.isConnected(GeolocaliserMain.this))
		new LoadGeo().execute();
	else
	{
		builder.setMessage(Constants.message_erreur)
        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            	finish();
            }
        })  ;   builder.create();builder.show();
	}
}
/**
 Classe qui permet de charger les données nécéssaires pour la géolocalisation à partir de la base
 */
class LoadGeo extends AsyncTask<String, String, String>{
	
 @Override
protected void onPreExecute() {
	super.onPreExecute();
	 Log.e("ASYNK","Pre execute");
     pDialog = new ProgressDialog(GeolocaliserMain.this);
     pDialog.setMessage("Chargement..");
     pDialog.setIndeterminate(false);
     pDialog.setCancelable(false);
     pDialog.show();
    
}
	@Override
	protected String doInBackground(String... args)
	  {		
		Log.e("ASYNK","doInBackground");
		
            try
          { 
            	  List<NameValuePair> params = new ArrayList<NameValuePair>();           	  
            	  JSONObject json = jParser.makeHttpRequest(Constants.adresse_script_mag, "GET", params);
     			  
     			  if ( json!=null) 
                   {  				  
     				 Log.e("resultat magasins",json.toString());
     				 JSONArray tab_mag  = json.getJSONArray(Constants.TAG_COMP);                 
                       for (int i = 0; i < tab_mag.length(); i++) 
                       {  
                           JSONObject jsonPM = tab_mag.getJSONObject(i);
                             Magasin  m=new Magasin();
                              m.setLib(jsonPM.getString(Constants.TAG_LIBM));
                              m.setUrl_logo(jsonPM.getString(Constants.TAG_LOGO));                               
                              listMag.add(m); 
                       }                      
                       for (int i=0;i<listMag.size();i++)
                       {
                    	   params.clear();
                    	   params.add(new BasicNameValuePair(Constants.TAG_LIBM,listMag.get(i).getLib()));
              			   json = jParser.makeHttpRequest(Constants.adresse_script_geo, "GET", params);  
              			  if(json!=null)
              			  {         				
              				  Log.e("resultat "+(i+1),json.toString());
              				  //mettre le résultat dans un tableau
              				  JSONArray goeArray = json.getJSONArray(Constants.TAG_COMP);  
                              for (int j = 0; j< goeArray.length(); j++) {
                                  JSONObject jsonGeo = goeArray.getJSONObject(j);                                  
                                  GeoPoint gp=new GeoPoint();
                                  PointVente c=new PointVente();
                                  gp.setLat(Double.parseDouble(jsonGeo.getString(Constants.TAG_LAT)));
                                  gp.setLng(Double.parseDouble(jsonGeo.getString(Constants.TAG_LNG)));
                                  c.setOuverture(jsonGeo.getString(Constants.TAG_OUVERTURE));
                                  c.setFermeture(jsonGeo.getString(Constants.TAG_FERMETURE));
                                  c.setAdresse(jsonGeo.getString(Constants.TAG_ADRESSE));
                                  c.setVille(jsonGeo.getString(Constants.TAG_VILLE));
                                  c.setPoint(gp);
                                  listMag.get(i).ajouter(c);
                                  Log.e("lib",c.getVille());
                              }

              			  } else {erreur=true;}
                       }
                       
                   } else {erreur=true;}
          } catch (Exception e) {Log.e("JSON","erreur dans le chargement des données");erreur=true;}
                  
		    return null;
      }
	protected void onPostExecute (String ar)
	{
		
		Log.e("ASYNK","onPostExecute");
		super.onPostExecute(ar);
			 if (erreur) 
		{ 
			   LinearLayout l=(LinearLayout)findViewById(R.id.content);
	           l.setVisibility(View.VISIBLE);
	           l.removeAllViews();
	           ImageView v=new ImageView(GeolocaliserMain.this);
	           v.setBackgroundResource(R.drawable.probleme_serveur);
	           Button b=new Button(GeolocaliserMain.this);
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
		 else initPage();
		 pDialog.dismiss();			
		
	}
      }
public void initPage()
{	
	LinearLayout l=(LinearLayout)findViewById(R.id.content);
    l.setVisibility(View.VISIBLE);
	spinner2 = (Spinner) findViewById(R.id.spinner2);
	spinner1 = (Spinner) findViewById(R.id.spinner1);
	addItemsOnSpinner1();	
	initilizeMap();
   addItemsOnSpinner2();
}
public void retourner(View v)
{
	Intent t=new Intent (this,MainActivity.class);
	startActivity(t);
}

//ajouter les magasins à partir de la base
public void addItemsOnSpinner2() {
	List<String> list = new ArrayList<String>();
	for (int i=0;i<listMag.size();i++)
	{
	list.add(listMag.get(i).getLib());
	}
	ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,list);
	dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	spinner2.setAdapter(dataAdapter);
}
//Initialiser les villes (il manque certaines 16 autres villes )
public void addItemsOnSpinner1() {
	List<String> list = new ArrayList<String>();
	list.add("Ariana");list.add("Bizerte");list.add("Mahdia");list.add("Mounastir");
	list.add("Nabeul");list.add("Sfax");list.add("Sousse");list.add("Tunis");
	ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,list);
	dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	spinner1.setAdapter(dataAdapter);
}
public void onClick(View v) {
	googleMap.clear(); 
	afficherMap();
}
//Masquer/Afficher la zone de filtre 
public void glisser(View v)
{
	LinearLayout l=(LinearLayout)findViewById(R.id.sp);
	ImageView img=(ImageView)v;
	if(visible)
	{
		visible=false;
		img.setImageResource(R.drawable.group_down);
		l.setVisibility(View.GONE);
	}
	else
	{
		visible=true;
		img.setImageResource(R.drawable.group_up);
		l.setVisibility(View.VISIBLE);
	}
}

 private void initilizeMap() {
		if (googleMap == null) {
			googleMap = ((MapFragment) getFragmentManager().findFragmentById(
					R.id.map)).getMap();
			googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);	
			// Showing / hiding your current location
			googleMap.setMyLocationEnabled(true);
			// Enable / Disable zooming controls
			googleMap.getUiSettings().setZoomControlsEnabled(true);
			// Enable / Disable my location button
			googleMap.getUiSettings().setMyLocationButtonEnabled(true);
			// Enable / Disable Compass icon
			googleMap.getUiSettings().setCompassEnabled(true);
			// Enable / Disable Rotate gesture
			googleMap.getUiSettings().setRotateGesturesEnabled(true);
			// Enable / Disable zooming functionality
			googleMap.getUiSettings().setZoomGesturesEnabled(true);
			
			if (googleMap == null) {
				Toast.makeText(getApplicationContext(),
						"Désolé ! Impossible de créer la carte ", Toast.LENGTH_SHORT)
						.show();
			}
		}	
			
	}
 //Afficher le géo point du magasin et ville concernés
 public void afficherMap(){ 
	 boolean exist=false;	
	 for(int i=0;i<listMag.size();i++){				 
 		    spin1Ville=String.valueOf(spinner1.getSelectedItem());
     		spin2Mag=String.valueOf(spinner2.getSelectedItem());   
 		     if(listMag.get(i).getLib().equals(spin2Mag))
 		     {	   	 	       			
 		    	 		Magasin m=listMag.get(i);
 		    	 	    for (int j=0;j<m.getPointsvente().size();j++)
 		    	 	    if(m.getPointsvente().get(j).getVille().equals(spin1Ville))
 		    	 	    { 		    	 	    	
 		    	 	    	exist=true; 		    	 	    	
 		    	 	    	PointVente p=m.getPointsvente().get(j);
 		    	 	    	GeoPoint gp=p.getPoint();
 		    	 	    	double lat=gp.getLat();
 		    	 	        double lng=gp.getLng();
 		    	 	        double[] randomLocation = createRandLocation(lat,lng);
 		    	 	         // Adding a marker
 		    	 	        MarkerOptions   marker = new MarkerOptions().position(
 		    	 	        new LatLng(randomLocation[0], randomLocation[1]))
 		    	 	        .title("De "+p.getOuverture()+" jusqu'à "+p.getFermeture());
 		    	 	        marker.snippet(p.getAdresse());
 	    	 	            CameraPosition cameraPosition = new CameraPosition.Builder()
 						         .target(new LatLng(randomLocation[0],randomLocation[1])).zoom(9).build();
 	    	 	            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
 	    	 	          
 	    	 	           if (listMag.get(i).getLib().equals("Monoprix"))
 	    	 	        	   marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.mono_mark));
 	    	 	           else if (listMag.get(i).getLib().equals("Carrefour"))
 	    	 	        	   marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.carr_mark));
 	    	 	           else if (listMag.get(i).getLib().equals("Magasin General"))
 	    	 	        	   marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.mg_mark));
 	    	 	           else if (listMag.get(i).getLib().equals("Geant"))
 	    	 	        	   marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.geant_mark));
		    	 	        
 	    	 	           googleMap.addMarker(marker);

 	    	 	           
 		    	 	  }		   }}
	
if( !exist)
{
	builder.setMessage("Aucun magasin "+spin2Mag+" n'est associé à "+spin1Ville);	 
    builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int id) {}  })  ; 
    builder.create(); builder.show();
}
 } 
 private double[] createRandLocation(double latitude, double longitude) {

		return new double[] { latitude + ((Math.random() - 0.5) / 500),
				longitude + ((Math.random() - 0.5) / 500),
				150 + ((Math.random() - 0.5) * 10) };
	}
 
	}
