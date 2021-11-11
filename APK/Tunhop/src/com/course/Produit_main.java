package com.course;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.scan.CallsScanner;
import com.util.Constants;
import com.util.DoubleFormat;
import com.carte.CarteMain;
import com.database.DBHelper;
import com.database.JSONParser;
import com.entite.Course;
import com.entite.Produit;
import com.menu.R;

public class Produit_main extends CallsScanner {
	private ProduitAdapter arrayAdapter1, arrayAdapter2;
	private SectionListAdapter sectionAdapter1, sectionAdapter2;
	private ListView listView1, listView2;
	ArrayList<Produit> l1 ,l2 ;
	DBHelper mydb ;
	AlertDialog.Builder builder;
	Produit p;
	Dialog d ;
	Course currentcourse;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.produit_main); 
		init();
	}
	public void init()
	{
		mydb= new DBHelper(this);
		Bundle b = getIntent().getExtras();
		TextView bud = (TextView) findViewById(R.id.budget_text);
		bud.setText(b.getString("budget"));
		currentcourse=new Course(b.getString("lib_course"),b.getDouble("budget"));		
		l1=new ArrayList<Produit>();l2=new ArrayList<Produit>();
		arrayAdapter1 = new ProduitAdapter(this,R.layout.produit_item, l1);
		sectionAdapter1 = new SectionListAdapter(getLayoutInflater(), arrayAdapter1);
		listView1 = (ListView) findViewById(R.id.section_list_view);
		listView1.setAdapter(sectionAdapter1);
		arrayAdapter2 = new ProduitAdapter(this, R.layout.produit_item, l2);
		sectionAdapter2 = new SectionListAdapter(getLayoutInflater(), arrayAdapter2);
		listView2 = (ListView) findViewById(R.id.section_list_view_panier);
		listView2.setAdapter(sectionAdapter2);
		arrayAdapter1.notifyDataSetChanged();arrayAdapter2.notifyDataSetChanged();
		try{
		currentcourse.setListProd(mydb.getProduits(currentcourse));
		if(!currentcourse.getListProd().matches(""))
			{
			 AfficherProduits(currentcourse.getListProd());		
			}    
		}
		catch (Exception e){Log.e("course","vide");}
		initListView();
		TextView t=(TextView)findViewById(R.id.titre);t.setText(currentcourse.getTitle());
    	d= new Dialog(this);d.setCancelable(true);
    	 builder = new AlertDialog.Builder(Produit_main.this);
    	 builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
             public void onClick(DialogInterface dialog, int id) {
             }
         }) ;
	}
	private void initListView() 
	{	
		ImageView v=(ImageView)findViewById(R.id.aucune);
		RelativeLayout l=(RelativeLayout)findViewById(R.id.header);
		if(l1.size()==0&&l2.size()==0)
    	{
    		v.setVisibility(View.VISIBLE);
    		l.setVisibility(View.GONE);
    		
    	}
		else
		{	
		v.setVisibility(View.GONE);
    	l.setVisibility(View.VISIBLE);
    	
   }	    
	}
	public void Scan (View v)
	{launchScanner(d);}
	public void retourner(View v) 
	{sauvegarderListe();NavUtils.navigateUpTo(this, new Intent(this, Course_main.class));}
	
//modifier ou supprimer un produit
	public void UpdateProduit(final View vl) {
		d.setContentView(R.layout.menu_prod);
		d.setTitle("Action");	
		// Si action= suppression
		Button b1 = (Button) d.findViewById(R.id.supp);
		b1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				RelativeLayout l = (RelativeLayout) vl;
				TextView txt=(TextView)l.findViewById(R.id.produit);
				Produit p = (Produit) txt.getTag();
				Toast.makeText(getApplicationContext(),"produit supprimé",Toast.LENGTH_LONG).show();
				if (p.getSection().contains(Constants.en_cours))
				{l1.remove(p);arrayAdapter1.notifyDataSetChanged();}
				else{l2.remove(p);arrayAdapter2.notifyDataSetChanged();}				
				d.dismiss();
				MAJTotal();
				initListView();
			}
		});
		//Action =modification 
		Button b2 = (Button) d.findViewById(R.id.modifier);
		b2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				open_dialog_modifier(vl);	
				d.dismiss();
				MAJTotal();
			}});
		d.show();}
	public void  sauvegarderListe() {
				String s="";
        		if (l1!=null){s+=AjouterProduit(l1);}
        		if (l2!=null){s+=AjouterProduit(l2);}
        		currentcourse.setListProd(s);
				try{mydb.updateCourse(currentcourse);}catch (Exception e)	{} }
	@Override
	protected void onStop() {sauvegarderListe();super.onStop();}
	@Override
	protected void onDestroy() {sauvegarderListe();super.onDestroy();}
	public void AfficherProduits(String s) {
			String[] tab_prod=s.split(";");
	       for (int i=0;i<tab_prod.length;i++)
	       {String[]tab_details_prod=tab_prod[i].split(","); 
	       addProduit(tab_details_prod[0],tab_details_prod[1],
	      			 			Double.parseDouble(tab_details_prod[3])
	      			 			,Integer.parseInt(tab_details_prod[2]));}
	       MAJTotal();
	}
	//Convertir la liste des produits en une chaine de caractère  
	public   String AjouterProduit(ArrayList<Produit> list)  
	  {String s=""; for (int i=0;i<list.size();i++) s+=list.get(i)+";";return s; }

	//Ajouter un nouveau produit
	public void NouveauProduit(View v)
	{
		d.setContentView(R.layout.dialog_ajouter_produit);
		cancel(d);
		d.setTitle("ajouter un nouveau produit");
		final EditText lib = (EditText) d.findViewById(R.id.libP);
		Button b = (Button) d.findViewById(R.id.ajouterP);
		b.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {					
			if (!lib.getText().toString().matches("")){
				String Libprod = lib.getText().toString();
				addProduit(Libprod, Constants.en_cours, 0.0, 1);d.dismiss();}
			else
			{				
	            builder.setMessage("Veuillez saisir le libelle du produit ");	                   
	            builder.create(); builder.show();	
			}
				MAJTotal();	
			}
		});
		d.show();
	}
	//Ajouter un produit dans la listView
	public void addProduit(String lib, String section, Double pr, int q) {
		Produit p = new Produit(lib, section, q, pr);
		if (section.contains(Constants.en_cours)) {l1.add(p);arrayAdapter1.notifyDataSetChanged();} 
		else if (section.contains(Constants.dans_panier)){l2.add(p);arrayAdapter2.notifyDataSetChanged();}
		initListView() ;	
	}
// Lorsqu'un produit est coché 
	public void CheckBoxEvent(View v) {
		CheckBox cb = (CheckBox) v;
		p = (Produit) cb.getTag();
		// Si le produit est coché alors mettre le dans le panier
		if (cb.isChecked()) {
			cb.setChecked(false);arrayAdapter1.remove(p);addProduit(p.getTitle(),
									Constants.dans_panier, p.getPrix(), p.getQuantite());
			
			if (p.getPrix() == 0) //vérifier si le prix est mentionné
		{
				p=l2.get(l2.size()-1);
			if (JSONParser.isConnected(Produit_main.this))// Mode connecté
			{
				this.d.setContentView(R.layout.marquer_connecte);
				this.d.setTitle("Scanner et remplacer");
				cancel(d);
				Button bs=(Button)d.findViewById(R.id.scan_btn);
				bs.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {launchScanner(d);}});
				Button b = (Button) d.findViewById(R.id.scanner);
				b.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {		
						final EditText code=(EditText) d.findViewById(R.id.res);
						final Spinner magasin = (Spinner) d.findViewById(R.id.magasin);
						new LoadPrice(code.getText()+"",magasin.getSelectedItem().toString(),p).execute();  	                 						
					}
				});
				d.show();
			}
			else 
			{//mode non connecté
				d.setContentView(R.layout.marquer_non_connecte);
				cancel(d);
				d.setTitle("Ajouter les details");
				d.setCancelable(true);
				Button b = (Button) d.findViewById(R.id.ajouter);
				b.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						EditText prix=(EditText)d.findViewById(R.id.prix);
						EditText quantite=(EditText)d.findViewById(R.id.quantite);						
						try {
						Double pr=Double.parseDouble(prix.getText()+"");
						int qu=Integer.parseInt(quantite.getText()+"");
						p.setQuantite(qu);p.setPrix(pr);
						}
						catch(Exception e){p.setQuantite(1);p.setPrix(0.0);}
						MAJTotal();
						d.dismiss();
						}
				});
				d.show();
			}
		}
			if (l1.size()==0)//vérifier si la course est términée
			{
				d.setContentView(R.layout.terminer_course);
				cancel(d);
				d.setTitle("Avant de quitter");
				Button b = (Button) d.findViewById(R.id.fermer);
				b.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						CheckBox c=(CheckBox)d.findViewById(R.id.ch2);
						if (c.isChecked())
						{
							tous_encours();
							sauvegarderListe();	
							Toast.makeText(getApplicationContext(),"Votre liste a été sauvegardée et rénitialisée"
									,Toast.LENGTH_LONG).show();
					   }
						else mydb.deleteCourse(currentcourse.getTitle());					
						CheckBox c1=(CheckBox)d.findViewById(R.id.ch1);
						Intent intent;
						if (!c1.isChecked()) intent= new Intent(Produit_main.this, Course_main.class);
						else intent = new Intent(Produit_main.this, CarteMain.class);
						startActivity(intent);
						d.dismiss();finish();}});d.show();	}}
		else {cb.setChecked(true);arrayAdapter2.remove(p);addProduit(p.getTitle(),
												Constants.en_cours, p.getPrix(), p.getQuantite());}	
	}
	//inialiser la course
	public void tous_encours()
	{int i=0;while (i<l2.size())
	{Produit p=(Produit)l2.get(i);l2.remove(l2.get(i));p.setSection
												(Constants.en_cours);l1.add(p);}
		MAJTotal();	
	}
	public void open_dialog_modifier(View v)
	{		
		RelativeLayout l = (RelativeLayout)v;
		final TextView txt=(TextView)l.findViewById(R.id.produit);
		final TextView qua=(TextView)l.findViewById(R.id.quantite);
		final TextView pr=(TextView)l.findViewById(R.id.prix);
		final TextView tot=(TextView)l.findViewById(R.id.total);	
		final Dialog d = new Dialog(this);
		d.setContentView(R.layout.dialog_modifier);
	    d.setTitle("Modifier un produit");	    
		d.setCancelable(true);
		  final EditText lib = (EditText) d.findViewById(R.id.libP);
		  final EditText qu = (EditText) d.findViewById(R.id.quantite);
		  final EditText prix = (EditText) d.findViewById(R.id.prix);
		  lib.setText(txt.getText()+"");
		  qu.setText(qua.getText()+"");
		  prix.setText(pr.getText()+"");
		Button b = (Button) d.findViewById(R.id.ajouterP);
		b.setText("modifier");
		b.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				boolean dismiss=true;
				p=(Produit)txt.getTag();
				if (!lib.getText().toString().matches(""))p.setTitle(lib.getText()+"");
				else {
					builder.setMessage("Veuillez saisir le libéllé  ");	                   
		            builder.create(); builder.show();
		            dismiss=false;}
			    {
				try{p.setPrix(Double.parseDouble(prix.getText().toString()));}
				catch (Exception e){
					dismiss=false;
					builder.setMessage("Prix invalide");	                   
		            builder.create(); builder.show();
					}
				try
				{p.setQuantite(Integer.parseInt(qu.getText()+""));}
				catch (Exception e)
				{
					dismiss=false;
				builder.setMessage("Quantité invalide ");	                   
	            builder.create(); builder.show();
	            }
				tot.setText(p.getTotal()+"");
				MAJTotal();
				if (dismiss)d.dismiss();
			    }
				
			}
		});
		d.show();
		cancel(d);
	}
	// masquer un dialog
	public void cancel (final Dialog d)
	{
		Button c = (Button) d.findViewById(R.id.cancel);
		c.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {d.dismiss();}});
	}
	/*
	 * Fonction qui permet de mettre à jour textview somme 
	 */
	public void MAJTotal()
	{
		try{
		Double s1=0.0,s2=0.0;
	for (int i=0;i<l1.size();i++)
		s1+=l1.get(i).getTotal();
	for (int i=0;i<l2.size();i++)
		s2+=l2.get(i).getTotal();
	TextView s = (TextView) findViewById(R.id.somme_TOT);	
	s.setText( DoubleFormat.round(s1+s2, 2)+"");	
	Double somme=s1+s2;
	TextView warning = (TextView) findViewById(R.id.limite);
	TextView bud = (TextView) findViewById(R.id.budget_text);
	Double b = Double.parseDouble(bud.getText().toString());
	if (somme > b) {
		warning.setText("Vous avez dépassé le budget");
		warning.setVisibility(1);
	} else warning.setText("Il vous reste " + (b - somme));}
	catch (Exception e){Log.e("MAJTotal",e.getMessage());}
	arrayAdapter1.notifyDataSetChanged();arrayAdapter2.notifyDataSetChanged();}
	
	
	/*
	 * Classe qui permet de charger le prix d'un produit
	 * 
	 */
	class LoadPrice extends AsyncTask<String, String, String>{
		String code,mag;JSONArray compprix = null; Produit p;Double prix=0.0;String Libp="";
		public LoadPrice (String code,String mag ,Produit p)
		{this.code=code;this.mag=mag;	this.p=p;}
		JSONParser jParser = new JSONParser();
		  protected String doInBackground(String... args)
		  {	  
			  Log.e("ASYNK","doInBackground");
			  try {
				  List<NameValuePair> params = new ArrayList<NameValuePair>();
				  params.add(new BasicNameValuePair(Constants.TAG_CODEP,code));
				  params.add(new BasicNameValuePair(Constants.TAG_LIBM,mag));
				  JSONObject json = jParser.makeHttpRequest(Constants.adresse_script_prix, "GET", params);
				  if (json!=null) 
	            {
					  		 Log.e("resultat",json.toString());
					  		 compprix = json.getJSONArray(Constants.TAG_COMP);                 
	                    	 JSONObject jsonPM = compprix.getJSONObject(0);
	                    	 prix=jsonPM.getDouble(Constants.TAG_PRIX);
	                    	 Libp=jsonPM.getString(Constants.TAG_LIBP);
	                    	 
	            }
	     } catch (Exception e)   { Log.e("erreur json",e.getMessage()); }	  
	        return null;
	    }
		  protected void onPostExecute(String file_url) 
		  {
			  Log.e("ASYNK","onPostExecute");			 
			  d.dismiss();
			  try{  p.setPrix(prix); }
			  catch (Exception e){p.setPrix(0.0);}
			  MAJTotal();
			  if(p.getPrix()>0)p.setTitle(Libp);
			  else Toast.makeText(getApplicationContext(),"Impossible de trouver le prix de ce produit",Toast.LENGTH_LONG).show();
		  }
	}
}