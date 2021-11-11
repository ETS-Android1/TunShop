package com.entite;

import java.util.ArrayList;

public class Magasin {
	private String lib;
	private String url_logo;
	private String libcarte;
	private ArrayList<PointVente> pointsvente=new ArrayList<PointVente> ();
	private GeoPoint gp;
	public Magasin()
	{}
	public Magasin (String lib,String l)
	{
		setLib(lib);setUrl_logo(l);
	}
	public String getLib() {
		return lib;
	}
	public void setLib(String lib) {
		this.lib = lib;
	}
	public String getUrl_logo() {
		return url_logo;
	}
	public void setUrl_logo(String url_logo) {
		this.url_logo = url_logo;
	}
	public String getLibcarte() {
		return libcarte;
	}
	public void setLibcarte(String libcarte) {
		this.libcarte = libcarte;
	}
	public GeoPoint getGp() {
		return gp;
	}
	public void setGp(GeoPoint gp) {
		this.gp = gp;
	}
	public ArrayList<PointVente> getPointsvente() {
		return pointsvente;
	}
	public void setPointsvente(ArrayList<PointVente> pointsvente) {
		this.pointsvente = pointsvente;
	}
	public void ajouter(PointVente p)
	{
		this.pointsvente .add(p);
	}
	public void supprimer(PointVente p)
	{
		if( this.pointsvente.size()>0)this.pointsvente .remove(p);
	}
	
}
