package com.entite;

public class PointVente{
	private String  ouverture;
	private String  fermeture;
	private String adresse;
	private String ville;
	private GeoPoint p=new GeoPoint();
	
	
	public PointVente(){}
	public PointVente(String o,String a,String v,GeoPoint p)
	{
		setOuverture(o);setAdresse(a);setVille(v);setPoint(p);
	}
	
	public String getOuverture() {
		return ouverture;
	}
	public void setOuverture(String ouverture) {
		this.ouverture = ouverture;
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public GeoPoint getPoint() {
		return p;
	}

	public void setPoint(GeoPoint p) {
		this.p = p;
	}
	public String getFermeture() {
		return fermeture;
	}
	public void setFermeture(String fermeture) {
		this.fermeture = fermeture;
	}

}
