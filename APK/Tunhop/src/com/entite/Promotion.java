package com.entite;

import java.util.Comparator;


public class Promotion{
	private String titre;
	private String description;
	private String url_img;
	private String date_fin;
	private String Categorie;
	private Magasin magasin;
	
	
	public String getTitre() {
		return titre;
	}
	public void setTitre(String titre) {
		this.titre = titre;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUrl_img() {
		return url_img;
	}
	public void setUrl_img(String url_img) {
		this.url_img = url_img;
	}
	public String getCategorie() {
		return Categorie;
	}
	public void setCategorie(String categorie) {
		Categorie = categorie;
	}
	public String getDate_fin() {
		return date_fin;
	}
	public void setDate_fin(String date_fin) {
		this.date_fin = date_fin;
	}
	public Magasin getMagasin() {
		return magasin;
	}
	public void setMagasin(Magasin magasin) {
		this.magasin = magasin;
	}
	
	public static Comparator<Promotion> CatComparator = new Comparator<Promotion>() {

		public int compare(Promotion s1, Promotion s2) {
		   String PromoName1 = s1.getCategorie().toUpperCase();
		   String PromoName2 = s2.getCategorie().toUpperCase();

		   return PromoName1.compareTo(PromoName2);

	    }};

	    /*Comparator for sorting the list by magasin*/
	    public static Comparator<Promotion> MagComparator = new Comparator<Promotion>() {

	    	public int compare(Promotion s1, Promotion s2) {
	 		   String PromoName1 = s1.magasin.getLib().toUpperCase();
	 		   String PromoName2 = s2.magasin.getLib().toUpperCase();

	 		   return PromoName1.compareTo(PromoName2);

	 	    }};
	
	

}
