package com.util;

public class Constants {
	public static  String dans_panier = "DANS VOTRE PANIER";
	//public static  String TAG_SUCCESS = "success";
	public static  String TAG_COMP = "posts";
	public static  String TAG_VAL = "validite";
	public static  String TAG_POINTS = "nbrpoints";
	public static  String TAG_CODEP = "codep";
	public static  String TAG_DESC="desprod";
	public static  String TAG_LOGO="logo";
	public static  String TAG_IMG="img";
	public static  String TAG_LIBM = "libm";
	public static  String TAG_LIBP = "libp";
	public static  String TAG_LIBF = "libf";
	public static  String TAG_PRIX = "prixp";
	public static  String TAG_OUVERTURE = "ouverture";
	public static final String TAG_FERMETURE = "fermeture";
	public static  String TAG_LAT = "lat";
	public static  String TAG_LNG = "lng";
	public static  String adresse="http://192.168.1.117/tunshop/";
	public static  String en_cours = "EN COURS";
	public static String DATABASE_NAME="TunShop_interne.db";
	//Table course
	public static String TAB_COURSE = "ListeCourse";
	public static String COURSE_ID="id_Course";
	public static String COURSE_LIB="libC";
	public static String COURSE_BUDGET="budget";
	public static String COURSE_PROD="produits";
	//table mescartes
	public static String TAB_MESCARTES="mescartes";
	public static String CARTE_LIB="libc";
	public static String CARTE_LIBM="libm";
	public static String CARTE_PORTEUR="nomporteur";
	public static String CARTE_CODE="codec";
	public static String CARTE_ID="id_carte";
	public static String TAG_CODE="code_carte";
	
	public static final String adr_connexion=adresse+"scripts/Connexion.php";
	//Comparateur constants
	public static final String adresse_script_comp=adresse+"scripts/comparateur_prix.php";
	
	//carte 
	public static final String adresse_script_points =adresse+"scripts/getsolde.php";
	public static final String adresse_script_mag=adresse+"scripts/get_cartes.php";
	public static final String adresse_script_prix=adresse+"scripts/getprix.php";
	//promotion constants
	public static final String xml_URL = adresse+"promotions.xml";
	public static final String url_prod=adresse+"produits/";
	public static final String url_mag=adresse+"magasins/";
	public static final String url_promo=adresse+"promotions/";
	
	//Geolocalisation
	public static final String adresse_script_geo=adresse+"scripts/geolocaliser.php";
	public static final String TAG_ADRESSE = "adresse";
	public static final String TAG_VILLE = "ville";
	
	//table constants
	public static final String TAB_CONSTANTS = "constants";
	public static final String CONSTANTS_NOM = "constante_nom";
	public static final String CONSTANTS_VALEUR = "constante_valeur";
	public static final String CONSTANTS_RESAUX = "constante_reseau";
	

	
	public static int max_carte=20;
	public static int max_course=30;
	public static String Constantecarte="nbr_carte";
	public static String Constantecourse="nbr_course";
	
	//table aide
	public static String TAB_AIDE="aide";
	public static String AIDE_QUESTION="question"; 
	public static String AIDE_REPONSE="reponse";
	
	public static String message_erreur="Veuillez Vérifier votre connexion à Internet";
	
	//Zbar constants 
	   public static final String SCAN_MODES = "SCAN_MODES";
	    public static final String SCAN_RESULT = "SCAN_RESULT";
	    public static final String SCAN_RESULT_TYPE = "SCAN_RESULT_TYPE";
	    public static final String ERROR_INFO = "ERROR_INFO";
}
