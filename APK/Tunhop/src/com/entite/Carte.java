package com.entite;


public class Carte
{
	private String lib;
	private String id;
	private String code;
	private String NomPorteur;
	private int imageId;
	private int points;
	private String validite;
	
	public Carte (String id,String lib,String c,String nom ){this.lib=lib;code=c;NomPorteur=nom;}
	//public Carte(String lib){this.lib=lib;}
	public Carte(String p){this.NomPorteur=p;}
	public void setLib(String name){this.lib=name;}
	public String getLib(){return lib;}
	public void setCode(String c){code=c;}
	public String getCode(){return code;}
	public void setPorteur(String name){this.NomPorteur=name;}
	public String getPorteur(){return NomPorteur;}
	public void setImageId(int imageId) { this.imageId = imageId; }
	public int getImageId() { return imageId;  }
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
	public String getValidite() {
		return validite;
	}
	public void setValidite(String validite) {
		this.validite = validite;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}