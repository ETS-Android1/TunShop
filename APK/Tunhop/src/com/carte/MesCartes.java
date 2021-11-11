package com.carte;

import java.util.ArrayList;

import com.entite.Carte;
/*
 * Remarque : il ne s'agit pas d'une classe métier , elle utilisée pour la mise en forme des cartes
 */
public class MesCartes {
	private String magasin;
	private ArrayList<Carte> GroupItemCollection;
	
	public MesCartes()
	{
		GroupItemCollection = new ArrayList<Carte>();
	}
	public MesCartes(String magasin,ArrayList<Carte> l)
	{
		this.magasin=magasin;GroupItemCollection=l;
	}
	public ArrayList<Carte> getCartes(){return GroupItemCollection;}
	public void setCartes(ArrayList<Carte> l){GroupItemCollection=l;}
	public void setMagasin(String m){magasin=m;}
	public String getMagasin(){return magasin;}
	public void  Ajouter (Carte c){GroupItemCollection.add(c);}
	public void Supprimer (Carte c){if (GroupItemCollection!=null) GroupItemCollection.remove(c);}
	public Carte getCarte(int i){return GroupItemCollection.get(i);}
}
