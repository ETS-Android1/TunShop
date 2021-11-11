package com.promotion;

import java.net.URL;
import java.util.List;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import com.entite.Promotion;
import com.menu.R;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * 
 * Classe qui permet de parser le document xml
 *
 */
public class RssService extends AsyncTask<String, Void, List<Promotion>> {

	private ProgressDialog progress;
	boolean erreur;
	private Context context;
	private PromoListFragment PromoListFrag;
	private String tri;
	PromotionAdapter adapter;
	SectionPromoAdapter sectionAdapter;

	public RssService(PromoListFragment ListFragment,String tri) {
		context = ListFragment.getActivity();
		PromoListFrag = ListFragment;
		progress = new ProgressDialog(context);
		progress.setMessage("Chargement...");
		this.tri=tri;
	}
	protected void onPreExecute() {Log.e("ASYNC", "PRE EXECUTE");	}

	@Override
	protected List<Promotion> doInBackground(String... urls) {
		String feed = urls[0];
		Log.e("ASYNC", "Do In background");
		erreur=true;
		try {
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();
			XMLReader xr = sp.getXMLReader();
			URL url  = new URL(feed);
			RssPromoProvider rh = new RssPromoProvider();
			xr.setContentHandler(rh);
			xr.parse(new InputSource(url.openStream()));		
			Log.e("ASYNC", "PARSING FINISHED");
			erreur=false;
			return rh.getPromoList();
		} catch (Exception e) {
			Log.e("erreur in parsing","Erreur dans RSS");
			erreur=true; 
			return null;
			}
	}	
	protected  void onPostExecute(final List<Promotion>  promotions) {
		Log.e("ASYNC", "POST EXECUTE");
		progress.dismiss();		
				if( !erreur)
	{
		adapter = new PromotionAdapter(PromoListFrag.getActivity(),R.layout.promotion_item, promotions,tri);					
		sectionAdapter=new SectionPromoAdapter(PromoListFrag.getActivity().getLayoutInflater(),adapter,tri);
				    PromoListFrag.setListAdapter(sectionAdapter);					
	}		
			else
			{
			LinearLayout l=(LinearLayout)PromoListFrag.getActivity().findViewById(R.id.content);
   	        ImageView v=new ImageView(PromoListFrag.getActivity());
   	           v.setBackgroundResource(R.drawable.probleme_serveur);
   	           Button b=new Button(PromoListFrag.getActivity());
   	           b.setText("Réssayer");
   	           l.addView(b); 
   	           l.addView(v);           
   	           b.setOnClickListener(new View.OnClickListener() {			  
   			       public void onClick(View v) {			  	          
   			    	PromoListFrag.getActivity().finish();
   			    	PromoListFrag.getActivity().startActivity(PromoListFrag.getActivity().getIntent());	  
   				      }			 
   				   });				
			}			
	}
}