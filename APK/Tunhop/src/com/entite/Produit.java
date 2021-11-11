package com.entite;

import com.util.DoubleFormat;


/*
 * Product definition including the section
 */
public class Produit {
	 	
	    private String title;
	    private String section;
	    private int quantite=1;
	    private Double prix=0.0;
	    private String img;	 
	    public Produit (String lib,String img)
	    {
	    	setTitle(lib);setImg(img);
	    }
	    public Produit( String title,String section,int quantite,Double prix) {
	        this.title = title;
	        this.section=section;
	        this.quantite=quantite;
	        this.prix=prix;  
	    }
	    public String getTitle() { return title; }
	    public void setTitle(String title) { this.title = title; }
	    @Override
	    public String toString() {
	    	return getTitle()+","+getSection()+","+getQuantite()+","+getPrix();  } 
	   
	    public void setSection (String section)   {this.section=section;  }
	    
	    public String getSection (){return section;}
	    
	    public void setQuantite(int q){quantite=q;}
	    
	    public void setPrix(Double p){prix=p;}
	    public Double getPrix()
	    {
	    	return  DoubleFormat.round(prix,3);	    	
	    }
	    
	    public int getQuantite(){return quantite;}
	    
	    public Double getTotal()
	    {
	    	return DoubleFormat.round(quantite*prix, 2);
	    }
		public String getImg() {
			return img;
		}
		public void setImg(String img) {
			this.img = img;
		}
	   
	   

}
