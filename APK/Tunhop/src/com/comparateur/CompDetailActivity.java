package com.comparateur;

import com.database.DBHelper;
import com.database.JSONParser;
import com.menu.R;
import com.util.Constants;
import com.util.DownloadImageTask;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
/*
 * permet d'afficher les détails d'un produit séléctionné
 */
public class CompDetailActivity extends Activity
{
	String titre,url,descrip;
private ProgressDialog pDialog;

@Override
public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.comp_detail_layout); 
    Bundle b =  getIntent().getExtras();
	 titre=b.getString("libp");
	 url=b.getString("url");
	 descrip=b.getString("description");
	 int on= new DBHelper(this).ValeurConstante(Constants.CONSTANTS_RESAUX);
	 	if( ( on==1&& JSONParser.HeightConnection(this))||on==0)
     {
	new DownloadImageTask((ImageView)findViewById(R.id.img)).execute(Constants.url_prod+url);
     }
    new LoadProduit().execute();
}
public void retourner(View v)
{
	finish();
}

//permet d'afficher l'image du produit
class LoadProduit extends AsyncTask<String, String, String>{
	  @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.e("ASYNK","Pre execute");
        pDialog = new ProgressDialog(CompDetailActivity.this);
        pDialog.setMessage("Veuillez patienter..");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

	  protected String doInBackground(String... args)
	  {	 
		  Log.e("ASYNK","doInBackground");	 
        return null;
    }
    protected void onPostExecute(String file_url) 
   {
  	  Log.e("1 ASYNK","Post execute");
        pDialog.dismiss();
        // updating UI from Background Thread
        runOnUiThread(new Runnable() {
            public void run() {
            	TextView t=(TextView)findViewById(R.id.title);
            	TextView d=(TextView)findViewById(R.id.details);
            	t.setText(titre);
            	d.setText(descrip);
            }
            });
       }
    }
}
            