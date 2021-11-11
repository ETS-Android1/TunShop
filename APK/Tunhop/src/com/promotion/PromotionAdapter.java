package com.promotion;

import java.util.Collections;
import java.util.List;
import com.menu.R;
import com.database.DBHelper;
import com.database.JSONParser;
import com.entite.Magasin;
import com.entite.Promotion;
import com.util.Constants;
import com.util.DownloadImageTask;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PromotionAdapter extends ArrayAdapter<Promotion>{
	
	public List<Promotion> items;
	Activity ac;
	String tri;
	
    public PromotionAdapter(Activity activity, int layout,List<Promotion> promotions ,String tri) 
    {
        super(activity, layout, promotions);
        this.items = promotions;
        ac=activity;
        this.tri=tri;
    }
    private class ViewHolder {
    	   TextView titre;
    	   ImageView img;
    	   TextView a; 	   
    	  }
    @Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Activity activity = (Activity) getContext();
		LayoutInflater inflater = activity.getLayoutInflater();
		ViewHolder holder;
        if (convertView == null) 
       {
		        convertView = inflater.inflate(R.layout.promotion_item, null);               						
		        holder = new ViewHolder();
		        holder.titre = (TextView) convertView.findViewById(R.id.titre);
		        holder.img = (ImageView) convertView.findViewById(R.id.img);
		        holder.a = (TextView) convertView.findViewById(R.id.date_f);
		        convertView.setTag(holder); 
		}
        else {
        	 holder = (ViewHolder) convertView.getTag();
             }
        final Promotion currentItem = getItem(position);
        if (currentItem != null) {
        	
                holder.titre.setTag(currentItem);
                holder.img.setTag(currentItem);
                holder.a.setTag(currentItem);               
                holder.titre.setText(currentItem.getTitre()+"");
                int on= new DBHelper(ac).ValeurConstante(Constants.CONSTANTS_RESAUX);
        	 	if( ( on==1&& JSONParser.HeightConnection(ac))||on==0)
                {
        	 		if( tri.equals("magasin"))
                new DownloadImageTask(holder.img).
                		execute(currentItem.getUrl_img());
        	 		else 
        	 new DownloadImageTask(holder.img).
                		execute(currentItem.getMagasin().getUrl_logo());
                }
                holder.a.setText(currentItem.getDate_fin()+""); 
                
            }
        return convertView;
	} 
    public  Magasin getMagasin(String m)
    {
    	Magasin mag=null;
     for (int i=0;i<items.size();i++)
     {
    	 if (items.get(i).getMagasin().getLib().equals(m))
    	 { mag=items.get(i).getMagasin();break;}
     }
     return mag;
    }
    public void sortCategorie() 
    {
    	Collections.sort(items, Promotion.CatComparator);
    }
    public void sortMagasin() 
    {
    	Collections.sort(items, Promotion.MagComparator);
    }
}