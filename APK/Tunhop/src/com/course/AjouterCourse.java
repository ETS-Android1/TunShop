package com.course;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import com.database.DBHelper;
import com.menu.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
/*
 * Permet d'ajouter une nouvelle course
 */
public class AjouterCourse extends Activity 
{
	DBHelper mydb;
	EditText lib;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ajout_liste);
		setTitle("ajouter une nouvelle liste");	
		mydb=new DBHelper(this);
		Button c = (Button)findViewById(R.id.cancel);			  
		   c.setOnClickListener(new View.OnClickListener() {			  
		       public void onClick(View v) {			  	          
		        finish();			  
		      }			 
		   });
		    lib = (EditText)findViewById(R.id.libC);
			Calendar ca = Calendar.getInstance(); 
			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			lib.setHint("Liste de "+df.format(ca.getTime()));	
	}
	 public void retourner(View v)
	    {
		 NavUtils.navigateUpTo(this, new Intent(this, Course_main.class));
	    }
	
	 public void creerListe(View v)
	{
		 final EditText budget = (EditText)findViewById(R.id.budget);			 
		  String  LibCourse= lib.getText().toString();
	         if (LibCourse.matches(""))LibCourse=lib.getHint()+"";
	     	 Double bud;			        	 	
	 	//renommer la course si son libéllé existe déjà
	     	 while(mydb.exist(LibCourse))
	 	{
	 		if( LibCourse.charAt((LibCourse.length()-1))==')')
	 		{
	 			int n=Integer.parseInt(LibCourse.charAt((LibCourse.length()-2))+"");
	 			LibCourse= LibCourse.substring(0,LibCourse.length()-3);
	 		    n=n+1;
	 			LibCourse=LibCourse+"("+n+")";
	 		}
	 		else LibCourse=LibCourse+"(1)";
	 	}
			try{bud=Double.parseDouble(budget.getText().toString()); }
	         catch (Exception e){bud=0.0; }			                	 
	 		 //add to database
	        mydb.insertCourse(LibCourse,bud);
	        //retourner la page "course_main"
	        Intent t=new Intent(AjouterCourse.this,Course_main.class);
	        startActivity(t);
		
	}

}
