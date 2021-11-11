package com.carte;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.scan.*;
import com.database.DBHelper;
import com.entite.Carte;
import com.menu.*;

/*
 * Classe qui permet de creer une nouvelle carte
 */
public class NouvelleCarte extends CallsScanner 
/*remarque : chaque classe qui utilise le scan etend de la 
 * classe CallsScanner pour en hériter les méthodes nécéssaires
 * pour le scan
 */
{

    DBHelper mydb;
    String mag,carte,id;
    EditText port,code;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nouvellecarte);
        port=(EditText)findViewById(R.id.port);
    	code=(EditText)findViewById(R.id.res);
        mydb= new DBHelper(this);
        Bundle b = getIntent().getExtras();
		mag = b.getString("maglib");
		carte=b.getString("cartelib");
		id=b.getString("id");
		Button bs=(Button)findViewById(R.id.scan_btn);
		bs.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				launchScanner(null);
			}
		});
		Button ann=(Button)findViewById(R.id.cancel);
		ann.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
		TextView titre=(TextView)findViewById(R.id.titre);
		titre.setText("| "+mag);
    }

    public void retourner(View v)
    {
    	Intent t=new Intent (this,ListesCartes.class);
    	startActivity(t);
    }
    //sauvegarder la carte dans la base SQLite
    public void saveCarte(View v)
    {   	
    	if (!port.getText().toString().matches("")&&(!code.getText().toString().matches("")))
    	{
    	   String po=	port.getText().toString();
    		while(mydb.existCarte(po,mag))
    	 	{
    	 		if( po.charAt((po.length()-1))==')')
    	 		{
    	 			int n=Integer.parseInt(po.charAt((po.length()-2))+"");
    	 			po= po.substring(0,po.length()-3);
    	 			po=po+"("+n+")";
    	 		}
    	 		else po=po+"(1)";
    	 	}
    	Carte ca=new Carte(id,carte,code.getText()+"",po);
    	mydb.insertCarte(ca,mag);
    	Toast.makeText(getApplicationContext(),"Votre carte a été bien engregistrée",Toast.LENGTH_SHORT).show();
    	finish();
    	Intent t=new Intent (NouvelleCarte.this,CarteMain.class);
    	startActivity(t);
    	}
    	else if (port.getText().toString().matches("")) build("Veuillez saisir le nom du porteur");
    	else build("Veuillez saisir le code barre ou le scanner en appuiyant sur 'Scan'");
    	
    }
    //initialiser le pop pup 
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

  

}
