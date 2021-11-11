package com.entite;

import com.util.DoubleFormat;

public class Course
{
    private int imageId;
    Double budget;
    String liste_prod;
    private String title;
    boolean selected = false;
     
    public Course(int imageId, String title,Double budget) {
        this.imageId = imageId;
        this.title = title;  
        this.budget=budget;
    }
    public Course(String string, double double1) {
		setTitle(string);setBudget(double1);
	}
	public int getImageId() {
        return imageId;
    }
    public String getListProd(){return liste_prod;}
    public void setListProd(String s){liste_prod=s;}
    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    @Override
    public String toString() {
        return getTitle();
    }   
    public boolean isSelected() {
    	  return selected;
    	 }
    	 public void setSelected(boolean selected) {
    	  this.selected = selected;
    	 }
  public void setBudget(Double bud){budget=bud;}
  public Double getBudget(){return DoubleFormat.round(budget, 2);}
}