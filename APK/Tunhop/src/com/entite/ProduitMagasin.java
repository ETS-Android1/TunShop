package com.entite;

public class ProduitMagasin 
{
       private Produit p;
       private Magasin m;
	   private Double prix;
	   private String description;
	
	   public ProduitMagasin(){}
	   public ProduitMagasin(Produit p,Magasin m,Double pr,String d)
	   {
		   setProduit(p);
		   setMagasin(m);
		   setPrix(pr);
		   setDescription(d);
	   }

	public Produit getProduit() {
		return p;
	}

	public void setProduit(Produit p) {
		this.p = p;
	}


	public Double getPrix() {
		return prix;
	}

	public void setPrix(Double p) {
		prix = p;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	public Magasin getMagasin() {
		return m;
	}
	public void setMagasin(Magasin m) {
		this.m = m;
	}
	   
	   
	   
}
