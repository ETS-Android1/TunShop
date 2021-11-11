package com.database;

import java.util.ArrayList;

import com.entite.Aide;
import com.entite.Carte;
import com.entite.Course;
import com.menu.R;
import com.util.Constants;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
/**
 * Permet de g�rer la base de donn�e SQLite
 */
public class DBHelper extends SQLiteOpenHelper 
  {
   public DBHelper(Context context){super(context, Constants.DATABASE_NAME , null, 1); }
   public void init()
   {
	   SQLiteDatabase db = this.getWritableDatabase();
	   db.execSQL("DROP TABLE IF EXISTS "+Constants.TAB_COURSE+";");
	   db.execSQL("DROP TABLE IF EXISTS "+Constants.TAB_MESCARTES+";");
	   db.execSQL("DROP TABLE IF EXISTS "+Constants.TAB_CONSTANTS+";");
	   db.execSQL("DROP TABLE IF EXISTS "+Constants.TAB_AIDE+";");
	   onCreate(db);
   }
//creer la base de donn�es
   @Override
   public void onCreate(SQLiteDatabase db) {
	   db.execSQL("CREATE TABLE " +Constants.TAB_COURSE + " (" 
				+Constants.COURSE_ID+ " INTEGER NOT NULL PRIMARY KEY ," 
			    +Constants.COURSE_LIB+ " VARCHAR(50) NOT NULL ,"
			    +Constants.COURSE_BUDGET+" DECIMAL CHECK ("+Constants.COURSE_BUDGET+" >=0),"
			    +Constants.COURSE_PROD+" VARCHAR(4000));"); 
	  
	   db.execSQL("CREATE TABLE "+Constants.TAB_MESCARTES+"("+
			   Constants.CARTE_ID+" INTEGER NOT NULL PRIMARY KEY ,"+
			   Constants.CARTE_CODE+" VARCHAR(30) NOT NULL, "
			   +Constants.CARTE_LIB+" VARCHAR(50) ,"
			   +Constants.CARTE_LIBM+" VARCHAR(50),"
			   +Constants.CARTE_PORTEUR+" VARCHAR(50) );"		   
			   );
	   db.execSQL("CREATE TABLE "+Constants.TAB_CONSTANTS+" ("
			   +Constants.CONSTANTS_NOM+" VARCHAR(20) NOT NULL PRIMARY KEY ,"+
			   Constants.CONSTANTS_VALEUR+" INTEGER); "
			   );	  
	   db.execSQL("INSERT INTO "+Constants.TAB_CONSTANTS+" VALUES " +
	   		"('"+Constants.Constantecarte+"',"+Constants.max_carte+");");
	   db.execSQL("INSERT INTO "+Constants.TAB_CONSTANTS+" VALUES " +
	   		"('"+Constants.Constantecourse+"',"+Constants.max_course+");");
	   db.execSQL("INSERT INTO "+Constants.TAB_CONSTANTS+" VALUES " +
	   		"('"+Constants.CONSTANTS_RESAUX+"',0);");
   
	   db.execSQL(" CREATE TABLE  "+Constants.TAB_AIDE+" ( " +	   
			  Constants.AIDE_QUESTION+ " varchar(50) PRIMARY KEY , "+
			   Constants.AIDE_REPONSE+ " varchar(1000) );");
	   insererFAQ(db);
   }
   //ins�rer le FAQ
   public void insererFAQ(SQLiteDatabase db)
   {  
	   db.execSQL("INSERT INTO "+Constants.TAB_AIDE+" VALUES ('Que faire si un message s�affiche � "+Constants.message_erreur+"  � ?'," +
	   		"' Allez dans � param�tres � puis  s�lectionnez  une connexion ou activez votre Wi-Fi ');"); 	   
	   db.execSQL("INSERT INTO "+Constants.TAB_AIDE+" VALUES ('Comment je peux scanner un produit ou une carte ?'," +
		   		"'Alignez le code barre dans le viseur et attendez jusqu�� ce que le code soit reconnu');");	   
	   db.execSQL("INSERT INTO "+Constants.TAB_AIDE+" VALUES ('Impossible de scanner ma carte depuis mon smartphone !'," +
		   		"'Seules les caisses �quip�es de douchettes  peuvent lire les codes barres sur les t�l�phones portables. Dans ce cas ," +
		   		" demandez au caissier  de saisir le code manuellement');");
	   db.execSQL("INSERT INTO "+Constants.TAB_AIDE+" VALUES ('Comment faire pour cr�er mes listes de courses ?'," +
		   		"'Pour cr�er une liste de courses, appuyiez sur le bouton en haut puis ouvrez la " +
		   		".Maintenant vous pouvez ajouter en tant que vous voulez les produits. " +
		   		"Il faut noter que la capacit� de stockage de listes des courses est limit�e.');");	   
	   db.execSQL("INSERT INTO "+Constants.TAB_AIDE+" VALUES ('Comment je peux modifier ou supprimer une liste ?'" +
	   		",'Faites un appui long sur la liste en question puis choisissez l�action � appliquer');");	   
	   db.execSQL("INSERT INTO "+Constants.TAB_AIDE+" VALUES ('Comment je peux modifier ou supprimer un produit ?'" +
		   		",'Faites un appui sur le produit puis choisissez l�action � appliquer.');");
	   db.execSQL("INSERT INTO "+Constants.TAB_AIDE+" VALUES ('Pourquoi je suis invit� parfois � scanner le produit  lorsque je le coche ?'" +
		   		",'Lorsque vous marquez un produit comme achet�, l�application vous sugg�re, si vous �tes connect� � Internet, de  chercher son prix " +
		   		".Il suffit de choisir le magasin l� o� vous faites votre achat et scannez le code � barre.');");	   
	   db.execSQL("INSERT INTO "+Constants.TAB_AIDE+" VALUES ('Pourquoi dois �je ajouter les d�tails sur un produit ?'," +
		   		"'Pour que l�application puisse calculer le total de la liste des courses ce qui vous aidera � contr�ler votre budget.');");
	   db.execSQL("INSERT INTO "+Constants.TAB_AIDE+" VALUES ('Comment faire pour sauvegarder mes cartes ?'," +
		   		"'TunShop vous permet la possibilit� de sauvegarder vos cartes pour les avoir sur vous, il suffit de remplir les informations n�cessaires et " +
		   		"vous serez capable de les consulter � tout moment et sans avoir besoin d��tre connect� � Internet.');");
	   db.execSQL("INSERT INTO "+Constants.TAB_AIDE+" VALUES ('Puis �je sauvegarder les cartes de ma famille ?'," +
		   		"'Oui avec TunShop, vous avez la possibilit� de sauvegarder toutes les cartes de votre" +
		   		" famille en respectant la capacit� maximale de stockage');");	  	   
	   db.execSQL("INSERT INTO "+Constants.TAB_AIDE+" VALUES ('Le magasin de ma carte n�est pas propos� Que faire ?'," +
	   		"'Cliquez sur � cr�er une nouvelle carte � puis Choisissez � cr�ez-l� � en bas de page');");
	   db.execSQL("INSERT INTO "+Constants.TAB_AIDE+" VALUES ('Comment je peux modifier ou supprimer une carte ?'," +
		   		"'Affichez la carte puis appuyiez sur ta touche du menu');");
	   db.execSQL("INSERT INTO "+Constants.TAB_AIDE+" VALUES ('Impossible d�afficher mon code de carte sur l��cran !!'," +
		   		"'TunShop ne g�n�re que certains types de code � barre. v�rifiez si vous avez saisi correctement le code.');");
	   db.execSQL("INSERT INTO "+Constants.TAB_AIDE+" VALUES ('Comment faire pour afficher les magasins sur carte g�ographique ?'," +
		   		"'TunShop vous offre la possibilit� de visualiser sur carte toutes les magasins en Tunisie. " +
		   		"Vous pouvez s�lectionner les villes et magasins � afficher pour plus de pr�cision');");
	   db.execSQL("INSERT INTO "+Constants.TAB_AIDE+" VALUES ('Comment comparer les prix entre les magasins ?'," +
		   		"'Il suffit de scanner le produit, pour lui afficher les meilleurs prix .Pour afficher plus de d�tails " +
		   		"vous pouvez s�lectionner le produit dans la liste. ');");
	   db.execSQL("INSERT INTO "+Constants.TAB_AIDE+" VALUES ('Comment profiter des promotions et r�ductions ?'," +
		   		"'Vous pouvez consulter les derni�res promotions de tous les magasins en Tunisie avec leurs validit�s. " +
		   		"Les d�tails s�afficheront lorsque vous s�lectionnez la promotion en question.  ');");
	   db.execSQL("INSERT INTO "+Constants.TAB_AIDE+" VALUES ('Puis-je trier les promotions ?'," +
		   		"'Oui, vous pouvez trier les promotions selon les cat�gories des produits ou selon les magasins.');");
	   db.execSQL("INSERT INTO "+Constants.TAB_AIDE+" VALUES ('Comment augmenter le nombre maximum des cartes ou courses ?'," +
		   		"'Allez dans les param�tres de l�application puis fixez le nombre souhait� en tant que possible.');");
	   db.execSQL("INSERT INTO "+Constants.TAB_AIDE+" VALUES ('A quoi sert l�option � vider le cache � dans les param�tres ?'," +
		   		"'Cela entrainera la suppression de vos cartes et listes de courses, donc faites attention !');");
	   db.execSQL("INSERT INTO "+Constants.TAB_AIDE+" VALUES ('Les images ne sont plus affich�es dans l�application, que faire ?'," +
		   		"'Vous avez surement coch� l�option � ne pas charger les images si la connexion est lente � dans les param�tres." +
		   		"Pour l�annuler, d�cochez cette option');");  
   }

   @Override
   public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {init(); }

   //inserer une carte
   public boolean insertCarte (Carte c, String m)
   {
	   int a = (int) System.currentTimeMillis();
	   SQLiteDatabase db = this.getWritableDatabase();
	   ContentValues contentValues = new ContentValues();
	   contentValues.put(Constants.CARTE_CODE,c.getCode());
	   contentValues.put(Constants.CARTE_LIB,c.getLib());
	   contentValues.put(Constants.CARTE_LIBM,m);
	   contentValues.put(Constants.CARTE_PORTEUR,c.getPorteur());
	   db.insert(Constants.TAB_MESCARTES, null, contentValues);      
	   db.close(); // Closing database connection 
	   int  b = (int) System.currentTimeMillis();
       Log.e("Insertion Carte : temps de r�ponse",(b-a) +" ms" );
	   return true;   
   }
   // Obtenir la taille d'une table donn�e
public int getCount(String tab)
{
	 SQLiteDatabase db = this.getReadableDatabase();
     Cursor res =  db.rawQuery( "select count(*) as 'nbr' from "+tab, null );
     int n=0;
     if (res.moveToFirst())
     {
         n =res.getInt(res.getColumnIndex("nbr"));
         Log.e("taille "+tab+": ",n+"");
     }
     res.close();
     return n;
}
//supprimer une carte
public boolean deleteCarte(String port,String libm)
{
	   SQLiteDatabase db = this.getReadableDatabase();
	   db.delete(Constants.TAB_MESCARTES , Constants.CARTE_PORTEUR+"='"+port+"' and "+
			   Constants.CARTE_LIBM+"='"+libm+"'", null);
	   db.close(); // Closing database connection
	   return true;
}
   
//selectionner les cartes
   public ArrayList<Carte> getCartes(String libm)
   {
      ArrayList<Carte> list = new ArrayList<Carte>();
      SQLiteDatabase db = this.getReadableDatabase();
      Cursor res =  db.rawQuery( "select * from "+Constants.TAB_MESCARTES+" where "+
    		  Constants.CARTE_LIBM+" like '"+libm+"'", null );
      while(res.moveToNext())
      {
      Carte c=new Carte(res.getString(res.getColumnIndex(Constants.CARTE_ID)),res.getString(res.getColumnIndex(Constants.CARTE_LIB)),
    		  res.getString(res.getColumnIndex(Constants.CARTE_CODE)),
    		  res.getString(res.getColumnIndex(Constants.CARTE_PORTEUR)));
      list.add(c);
      }
   res.close();
   return list;
   }
   //selectionner les lib�ll�s des magasins
   public ArrayList<String> getMagasins ()
   {
	   		ArrayList<String> mag=new ArrayList<String>(); 
			SQLiteDatabase db = this.getReadableDatabase();
		    Cursor res =  db.rawQuery( "select DISTINCT "+Constants.CARTE_LIBM+" from "+Constants.TAB_MESCARTES, null );
		    while(res.moveToNext())
		    {
		    mag.add( res.getString(res.getColumnIndex(Constants.CARTE_LIBM)));
		    }
		 res.close();
 return mag;	
   }
   //selectionner un magasin � partir de l'id de la carte
   public String getMagasin(String id)
   {
	   SQLiteDatabase db = this.getReadableDatabase();
	      Cursor res =  db.rawQuery( "select "+Constants.CARTE_LIBM+" from "+
	    		  Constants.TAB_MESCARTES+" where "+Constants.CARTE_ID+" = "+id, null );	      
	      res.moveToFirst();
	      return res.getString(res.getColumnIndex(Constants.CARTE_LIBM)); 
   }
  //selectionner une carte 
   public Carte getCarte(String port,String mag)
   {
	   SQLiteDatabase db = this.getReadableDatabase();
	      Cursor res =  db.rawQuery( "select * from "+Constants.TAB_MESCARTES+" where "+Constants.CARTE_PORTEUR+
	    		  " like '"+port+"' and "+Constants.CARTE_LIBM+" like '"+mag+"'", null );
	Carte ca=null;
	      if(  res.moveToFirst())
	       ca=new Carte(res.getString(res.getColumnIndex(Constants.CARTE_ID)),
	    		  res.getString(res.getColumnIndex(Constants.CARTE_LIB)),
	    		  res.getString(res.getColumnIndex(Constants.CARTE_CODE)),
	    		  res.getString(res.getColumnIndex(Constants.CARTE_PORTEUR)));
	
	   res.close();
	   return ca;   
   }
   public String id_carte(String port,String mag)
   {
	   SQLiteDatabase db = this.getReadableDatabase();
	      Cursor res =  db.rawQuery( "select "+Constants.CARTE_ID+" from "+Constants.TAB_MESCARTES+" where "+
	    		  Constants.CARTE_PORTEUR+"='"+port+"' and "+Constants.CARTE_LIBM+"='"+mag+"'", null );	      
	      res.moveToFirst();
	      return res.getString(res.getColumnIndex(Constants.CARTE_ID));   
   }
   //modifier une carte
   public void updateCarte(String anc,String nouv,String mag,String code)
   {	   
	   SQLiteDatabase db = this.getWritableDatabase();
	   ContentValues data=new ContentValues();
	   data.put(Constants.CARTE_PORTEUR,nouv);
	   data.put(Constants.CARTE_CODE,code);
	   db.update(Constants.TAB_MESCARTES, data,Constants.CARTE_PORTEUR+" like '"
	   +anc+"' and "+Constants.CARTE_LIBM+" like '"+mag+"'",null);
       db.close();
   }
   // v�rifier l'existance d'une carte donn�e
public boolean existCarte(String p,String m)
{
	SQLiteDatabase db = this.getReadableDatabase();
    Cursor res =  db.rawQuery( "select * from "+Constants.TAB_MESCARTES+
    		" WHERE "+Constants.CARTE_PORTEUR+"='"+p+"' and "+ Constants.CARTE_LIBM+"='"+m+"';", null );   
    return res.moveToFirst();
}
   
   //insert new shopping list
   public boolean insertCourse  (String lib, Double budget)
   {
	      int  a = (int) System.currentTimeMillis();
	      SQLiteDatabase db = this.getWritableDatabase();
	      ContentValues contentValues = new ContentValues();
	      contentValues.put(Constants.COURSE_LIB,lib);
		  contentValues.put(Constants.COURSE_BUDGET, budget);
		  db.insert(Constants.TAB_COURSE, null, contentValues);      
		  db.close();
		  int  b = (int) System.currentTimeMillis();
	      Log.e("Carte : temps de r�ponse",(b-a) +" ms" );
	return true;
   }
   public int getCourseId(String lib)
   {
	   SQLiteDatabase db = this.getReadableDatabase();
	      Cursor res =  db.rawQuery( "select "+Constants.COURSE_ID+" from "+Constants.TAB_COURSE+" where "
	   +Constants.COURSE_LIB+"='"+lib+"';", null );
	      int id=0;
	      if (res.moveToFirst())
	      {
	          id = res.getInt(0);  
	      }
	      res.close();
	      return id;
   }
   public String getProduits (Course c)
   {
	   SQLiteDatabase db = this.getReadableDatabase();
	      Cursor res =  db.rawQuery( "select "+Constants.COURSE_PROD+" from "+
	    		  Constants.TAB_COURSE+" where "+Constants.COURSE_LIB+" like '"+c.getTitle()+"';", null );
	      String s="";
	      if ( res.moveToFirst())
	      { s= res.getString(res.getColumnIndex(Constants.COURSE_PROD));  }
	      res.close();
	      return s;
   }
   
   
   public Course getCourse(String lib)
   {
	   SQLiteDatabase db = this.getReadableDatabase();
	      Cursor res =  db.rawQuery( "select * from "+Constants.TAB_COURSE+" where "+Constants.COURSE_LIB+"='"+lib+"';", null );	      
	      res.moveToFirst();
	      Course c=new Course(R.drawable.basket,lib,
	    		  res.getDouble(res.getColumnIndex(Constants.COURSE_BUDGET)));
	      res.close();
	      return c;
   }
   
   public void updateCourse (Course c)
   {
	   SQLiteDatabase db = this.getWritableDatabase();
	   ContentValues data=new ContentValues();
	   data.put(Constants.COURSE_PROD,c.getListProd());
	   db.update(Constants.TAB_COURSE, data,Constants.COURSE_LIB+"='"+c.getTitle()+"'",null);
       db.close(); // Closing database connection
   }
   
  
   public void updateCourseDetails (String ancien,String nouv,Double budget)
   {
	   SQLiteDatabase db = this.getWritableDatabase();
	   ContentValues data=new ContentValues();
	   data.put(Constants.COURSE_BUDGET,budget);
	   data.put(Constants.COURSE_LIB,nouv);
	   db.update(Constants.TAB_COURSE, data,Constants.COURSE_LIB+"='"+ancien+"'",null);
       db.close(); 
   }
   

   public void deleteCourse(String lib)
   {
	   SQLiteDatabase db = this.getReadableDatabase();
	   Cursor res =  db.rawQuery( "select "+Constants.COURSE_ID+" from "+Constants.TAB_COURSE+
			   " where "+Constants.COURSE_LIB+" like '"+lib+"';", null );
	      res.moveToFirst();
	      int myid=res.getInt(res.getColumnIndex(Constants.COURSE_ID));
	   db= this.getWritableDatabase();
	   db.delete(Constants.TAB_COURSE , Constants.COURSE_ID+" = " +myid , null);
	   db.close();
  }
   public ArrayList<Course> getAllCourses()
   {
	   int  a = (int) System.currentTimeMillis();
	
      ArrayList<Course> list = new ArrayList<Course>();
      SQLiteDatabase db = this.getReadableDatabase();
      Cursor res =  db.rawQuery( "select * from "+Constants.TAB_COURSE, null );
      while(res.moveToNext())
      {
      Course c=new Course(R.drawable.basket,
    		  res.getString(res.getColumnIndex(Constants.COURSE_LIB)),
    		  res.getDouble(res.getColumnIndex(Constants.COURSE_BUDGET)));
      list.add(c);
      }
   res.close();
   int  b = (int) System.currentTimeMillis();
   Log.e("Affiche Courses : temps de r�ponse",(b-a) +" ms" );
   return list;
   }
 
   // v�rifier l'existance d'une course donn�e
public boolean exist(String l)
{
	SQLiteDatabase db = this.getReadableDatabase();
    Cursor res =  db.rawQuery( "select "+Constants.COURSE_LIB+" from "+Constants.TAB_COURSE+
    		" WHERE "+Constants.COURSE_LIB+" LIKE '"+l+"' ;", null );   
    return res.moveToFirst();
}
// retourner le nombre maximum de carte ou courses oubien si le checkbox est coch� ou non
public int ValeurConstante(String lib)
{
	SQLiteDatabase db = this.getReadableDatabase();
    Cursor res =  db.rawQuery( "select "+Constants.CONSTANTS_VALEUR+" from "+Constants.TAB_CONSTANTS+
    		" WHERE "+Constants.CONSTANTS_NOM+" LIKE '"+lib+"' ;", null );
    int n=0;
    if (res.moveToFirst())
    {
        n = res.getInt(res.getColumnIndex(Constants.CONSTANTS_VALEUR));
    }
    res.close();
    return n;
}
//modifier le nombre maximum ou l'�tat de l'option
public void updateMaxConstants(String nom,int valeur)
{
	SQLiteDatabase db = this.getWritableDatabase();
	   ContentValues data=new ContentValues();
	   data.put(Constants.CONSTANTS_VALEUR,valeur);
	   db.update(Constants.TAB_CONSTANTS, data,Constants.CONSTANTS_NOM+"='"+nom+"'",null);
       db.close(); 
}
//selectionner les FAQ
public ArrayList<Aide> getFAQ()
{
   ArrayList<Aide> list = new ArrayList<Aide>();
   SQLiteDatabase db = this.getReadableDatabase();
   Cursor res =  db.rawQuery( "select * from "+Constants.TAB_AIDE, null );
   while(res.moveToNext())
   {
   Aide a=new Aide(
 		  res.getString(res.getColumnIndex(Constants.AIDE_QUESTION)),
 		  res.getString(res.getColumnIndex(Constants.AIDE_REPONSE))
 		  );
   list.add(a);
   }
res.close();
return list;
}
public void clearDB() {	
	   SQLiteDatabase db = this.getWritableDatabase();
	   db.execSQL("DELETE FROM "+Constants.TAB_COURSE+";");
	   db.execSQL("DELETE FROM "+Constants.TAB_MESCARTES+";");

}

  }
