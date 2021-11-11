package com.menu;

import java.util.ArrayList;
import com.carte.CarteMain;
import com.comparateur.ComparateurMain;
import com.course.Course_main;
import com.database.DBHelper;
import com.geo.GeolocaliserMain;
import com.promotion.PromoMain;
import com.util.Constants;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;

// Page principale de l'application
public class MainActivity extends FragmentActivity {

	 float x1,x2;
     float y1, y2;
     int pos=0;
	int id_img[] = new int[] {R.drawable.acceuil,R.drawable.geo,
			R.drawable.course,R.drawable.promo,R.drawable.fid,R.drawable.comp};
	 TableLayout tl ;
	 ArrayList <DiapoFragment> frags=new ArrayList<DiapoFragment>();
	MainLayout mLayout;
	private ListView lvMenu;
	private String[] lvMenuItems;
	//private String[] navMenuIcons;
	Button btMenu;
	TextView tvTitle;
	public static Context c;
	DBHelper mydb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mLayout = (MainLayout) this.getLayoutInflater().inflate(
				R.layout.activity_main, null);
		setContentView(mLayout);
		c=getApplicationContext();
		lvMenuItems = getResources().getStringArray(R.array.menu_items);
		lvMenu = (ListView) findViewById(R.id.menu_listview);
		lvMenu.setAdapter(new ArrayAdapter<String>(this,R.layout.simplelistitem, lvMenuItems));
		lvMenu.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				onMenuItemClick(parent, view, position, id);
			}

		});

		btMenu = (Button) findViewById(R.id.button_menu);
		btMenu.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Show/hide the menu
				toggleMenu(v);
			}
		});

		tvTitle = (TextView) findViewById(R.id.activity_main_content_title);
		tl= (TableLayout) findViewById(R.id.tab);
        for (int i=0;i<id_img.length;i++)
        {
        	TableRow tr = new TableRow(this);
        	TextView tv = new TextView(this);
        	tv.setText(i+1+"");
        	tr.addView(tv);
        	tl.addView(tr);
        	DiapoFragment f = new DiapoFragment(id_img[i]);
        	frags.add(f);
        }
        //Ouvrir la page d'acceuil par défaut
		FragmentManager fm = MainActivity.this.getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		DiapoFragment fragment = new DiapoFragment(id_img[pos]);
		ft.add(R.id.activity_main_content_fragment, fragment);
		ft.commit();
		mydb=new DBHelper(this);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
//glisser le menu
	public void toggleMenu(View v) {
		mLayout.toggleMenu();
	}

	private void onMenuItemClick(AdapterView<?> parent, View view,int position, long id) {
		String selectedItem = lvMenuItems[position];
		String currentItem = tvTitle.getText().toString();
		if (selectedItem.compareTo(currentItem) == 0) {
			mLayout.toggleMenu();
			return;
		}

		FragmentManager fm = MainActivity.this.getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		Fragment fragment = null;               
		if (selectedItem.contains("Réglages")) {
			fragment = new OptionMain();
		} else if (selectedItem.contains("Listes des courses")) {
			
			//fragment = new CourseMain();
			Intent intent=new Intent(this,Course_main.class);
			startActivity(intent);

		}
		else if (selectedItem.contains("Cartes de Fidélité")) {
			//fragment = new CarteMain().getFragment();
			Intent intent = new Intent(MainActivity.this, CarteMain.class);
			startActivity(intent);
		}
		else if (selectedItem.contains("Géolocalisation")) {
			Intent intent = new Intent(MainActivity.this, GeolocaliserMain.class);
			startActivity(intent);
		}
		else if (selectedItem.contains("Promotions")) {
			Intent intent = new Intent(MainActivity.this, PromoMain.class);
			startActivity(intent);
		}
		else if (selectedItem.contains("Comparateur des prix")) {
			//fragment = new Comparateur();
			Intent intent = new Intent(MainActivity.this, ComparateurMain.class);
			startActivity(intent);
		}
		else if (selectedItem.compareTo("Acceuil") == 0) {
			fragment = new DiapoFragment(id_img[0]);
		}
		else if (selectedItem.compareTo("Aide") == 0) {
			fragment = new AideActivity();
		}
		if (fragment != null) {
			ft.replace(R.id.activity_main_content_fragment, fragment);
			ft.commit();
			tvTitle.setText(selectedItem);
		}
		mLayout.toggleMenu();
	}

	@Override
	public void onBackPressed() {
		if (mLayout.isMenuShown()) {
			mLayout.toggleMenu();
		} else {
			super.onBackPressed();
		}
		
	}
	//gérer l'affichage du diapo
	 public  boolean onTouchEvent(MotionEvent touchevent) 
     {
		 FragmentManager fm = MainActivity.this.getSupportFragmentManager();
	     FragmentTransaction ft = fm.beginTransaction();
	     Fragment fr = null;
		 if( tvTitle.getText().toString().equals("Acceuil"))		
		 { switch (touchevent.getAction())
                  {
                          case MotionEvent.ACTION_DOWN: 
                          {
                              x1 = touchevent.getX();
                              y1 = touchevent.getY();
                              break;
                         }
                          case MotionEvent.ACTION_UP: 
                          {
                              x2 = touchevent.getX();
                              y2 = touchevent.getY(); 

                              //if left to right sweep event on screen
                              if (x1 > x2)  
                                {                           	 
                            	  pos=(pos+1)%frags.size();
                            	  fr= new DiapoFragment(id_img[pos]); 
                                  ft.replace(R.id.activity_main_content_fragment,fr);
                    	          ft.commit();
                    	         }
                              if (x1 < x2)  
                              {
                          	  pos=(frags.size()+pos-1)%frags.size();
                          	  fr= new DiapoFragment(id_img[pos]); 
                                ft.replace(R.id.activity_main_content_fragment,fr);
                  	          ft.commit();
                  	         }
                              break;
                          }
                  }
		 }
                  return false;
     }
	 //fn qui permet de vider le cache de l'application
	 public void viderCache(View v)
		{
		 final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		 builder.setTitle("Attention");
	        builder.setMessage(" Cette action va entrainer " +
	        		"la suppression des cartes et listes des courses . Voulez vraiment vider le cache ?")
	               .setPositiveButton("oui", new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {                	  
	           			mydb.clearDB();
	                   }
	               })  ; 
	       builder .setNegativeButton("non", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int id) {}   })  ;   
	        builder.create();
	        builder.show();	
			
		}
	 //cocher /décocher l'option du l'affichage des images dans la page paramètres
	 public void reseauOption(View v)
	 {
		 CheckBox c=(CheckBox)v;
		 if(c.isChecked()) mydb.updateMaxConstants(Constants.CONSTANTS_RESAUX,1);
		 else mydb.updateMaxConstants(Constants.CONSTANTS_RESAUX,0);
	 }

}
