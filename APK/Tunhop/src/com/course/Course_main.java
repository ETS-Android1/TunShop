package com.course;

import java.util.ArrayList;
import com.database.DBHelper;
import com.entite.Course;
import com.menu.MainActivity;
import com.menu.R;
import com.util.Constants;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
/*
 * Page principale de la gestion des courses
 */
public class Course_main extends Activity {

	CourseAdapter dataAdapter;
	ListView listView ;
	ArrayList<Course> courseList = new ArrayList<Course>();
	DBHelper mydb;
	int a,b;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		 a = (int) System.currentTimeMillis();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.course_main_layout);
		init_listview();
		mydb= new DBHelper(this);
		afficher_listeCourse();
		TextView t=(TextView)findViewById(R.id.titre);
    	t.setText("Liste des courses");
    	 b = (int) System.currentTimeMillis();
    	 Log.e("Course : temps de réponse",(b-a) +" ms" );
	}
	 public void init_listview()
     {
		 listView = (ListView) findViewById(R.id.listshop);
	    listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
	            public boolean onItemLongClick(AdapterView<?> arg0, View v, int index, long arg3) 
	            {
	            	try{
	            		LongClickOnListViewItem(v);
	            		}catch (OutOfMemoryError e){Log.e("OutOfMemory ",e.getMessage());}
	            	return true;
	            }
	}); 
	      //capture normal listview click
	      listView.setOnItemClickListener(new OnItemClickListener() {

				public void onItemClick(final AdapterView<?> arg0, final View v, final int position,
						final long id) {
					//getting course id
					Course selecteditem = dataAdapter.getItem(position);
					//open listView item Intent 
					Intent intent = new Intent(Course_main.this, Produit_main.class);
					Bundle b = new Bundle();
					b.putString("lib_course",selecteditem.getTitle() ); //course id
					Double budget = selecteditem.getBudget();
					b.putString("budget",budget+"");
					intent.putExtras(b); //Put course id to  product activity
					startActivity(intent);
				}

			});
     }
	 public void retourner(View v)
	    {
		 NavUtils.navigateUpTo(this, new Intent(this, MainActivity.class));
	    }
	
	private void afficher_listeCourse() {
		ImageView v=(ImageView)findViewById(R.id.aucune);
        if(mydb.getCount(Constants.TAB_COURSE)>0)
		 {courseList= mydb.getAllCourses();
		  //create an ArrayAdaptar from the String Array
		  dataAdapter = new CourseAdapter(this, R.layout.course_item, courseList);
		  // Assign adapter to ListView
		  listView.setAdapter(dataAdapter);
		  v.setVisibility(View.GONE);
  		  listView.setVisibility(View.VISIBLE);
		 }
        else
        {
        	v.setVisibility(View.VISIBLE);
    		listView.setVisibility(View.GONE);
        }
	}
	//cliquer sur "ajouter une nouvelle liste de courses"
	public void Nouvellecourse(View v)
	{
        if (mydb.getCount(Constants.TAB_COURSE)<mydb.ValeurConstante(Constants.Constantecourse))
        {	
         Intent t=new Intent(Course_main.this,AjouterCourse.class);
         startActivity(t);
        }
        else
        {
        	AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Vous avez atteint le nombre limité de listes")
                   .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                       public void onClick(DialogInterface dialog, int id) {
                       }
                   })  ;   
            builder.create();
            builder.show();		}	
	}
	// Ouvrir le menu qui permet la suppression et modification d'une liste de courses
public void	LongClickOnListViewItem(final View vc)
	{
	final Dialog d = new Dialog(this);
	d.setContentView(R.layout.menu_prod);
	d.setTitle("Action");
	d.setCancelable(true);
	// Action = suppression
	Button b1 = (Button) d.findViewById(R.id.supp);
	b1.setOnClickListener(new View.OnClickListener() {
		public void onClick(View v) {
			// do here
			TextView txt=(TextView)vc.findViewById(R.id.title);
			Course c=(Course)txt.getTag();
			dataAdapter.remove(c);
			d.dismiss();
			mydb.deleteCourse(txt.getText()+"");
			Toast.makeText(getApplicationContext(),"Liste supprimée",Toast.LENGTH_LONG).show();
			afficher_listeCourse();
		}
	});
	//Action =Modification
	Button b2 = (Button) d.findViewById(R.id.modifier);
	b2.setOnClickListener(new View.OnClickListener() {
		public void onClick(View v) {			
			open_dialog_modifier(vc);
			d.dismiss();
			
		}
	});
		
	d.show();
	}
public void open_dialog_modifier(final View vc)
{
	final Dialog d = new Dialog(this);			  
	   d.setContentView(R.layout.dialog_ajout_liste);			  
	   d.setTitle("Modifier une liste");			  
	   d.setCancelable(true);
	   final EditText lib = (EditText) d.findViewById(R.id.libC); 
	   final EditText budget = (EditText) d.findViewById(R.id.budget);
	   final TextView txt=(TextView)vc.findViewById(R.id.title);
	   final TextView bud=(TextView)vc.findViewById(R.id.bud);
	   lib.setText(txt.getText());budget.setText((bud.getText()).toString()
			   .substring(0,(bud.getText()).toString().length()-3));
	   Button b = (Button) d.findViewById(R.id.ajouterC);			  
	   b.setOnClickListener(new View.OnClickListener() {			  
	       public void onClick(View v) {	    	   
				Course c=(Course)txt.getTag();
				c.setTitle(lib.getText()+"");
				c.setBudget(Double.parseDouble(budget.getText()+""));
				mydb.updateCourseDetails(txt.getText()+"",c.getTitle(),c.getBudget());
				dataAdapter.notifyDataSetChanged();	
				d.dismiss();	
	      }			 
	   });	
	   Button c = (Button) d.findViewById(R.id.cancel);			  
	   c.setOnClickListener(new View.OnClickListener() {			  
	       public void onClick(View v) {			  	          
	        d.dismiss();			  
	      }			 
	   });			  
	   d.show();
	
}

}
