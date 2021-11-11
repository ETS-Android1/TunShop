package com.course;

import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import com.entite.Produit;
import com.menu.R;
import com.util.Constants;

public 	 class ProduitAdapter extends ArrayAdapter<Produit> {

    public List<Produit> items;
    Context context;
	
    
    public ProduitAdapter(final Context context,int layout,final List<Produit> items) 
    {
        super(context, layout, items);
        this.items = items;
        this.context=context;
    }
    private class ViewHolder {
    	   TextView txt;
    	   CheckBox ch;
    	   TextView quantite;
    	   TextView prix; 	   
    	  }

    @Override
    public View getView(final int position,  View convertView,final ViewGroup parent) 
    {
    	ViewHolder holder;
        if (convertView == null) 
       {
		       final LayoutInflater vi = (LayoutInflater) context
		                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		        convertView = vi.inflate(R.layout.produit_item, null);               						
		        holder = new ViewHolder();
		        holder.txt = (TextView) convertView.findViewById(R.id.produit);
		        holder.ch = (CheckBox) convertView.findViewById(R.id.ch);
		        holder.quantite = (TextView) convertView.findViewById(R.id.quantite);
		        holder.prix = (TextView) convertView.findViewById(R.id.prix);
		        convertView.setTag(holder); 
		}
        else {
        	 holder = (ViewHolder) convertView.getTag();
             }
        final Produit currentItem = getItem(position);
        if (currentItem != null) {
        	
                holder.ch.setTag(currentItem);
                holder.txt.setTag(currentItem);
                holder.prix.setTag(currentItem);
                holder.quantite.setTag(currentItem);
                
                holder.quantite.setText(currentItem.getQuantite()+"");
                holder.prix.setText(currentItem.getPrix().toString());
                holder.txt.setText(currentItem.getTitle().toString());
                TextView total=(TextView)convertView.findViewById(R.id.total);
                total.setText(currentItem.getTotal().toString());
                
            if (currentItem.getSection().contains(Constants.dans_panier))
            {
            	holder.ch.setChecked(true);
            	convertView.setBackgroundResource(R.color.mauve_fonce);
            	convertView.invalidate();
            	holder.txt.setBackgroundResource(R.drawable.barre);
            }
            else 
            {
            	holder.ch.setChecked(false);
            	convertView.setBackgroundColor(0xD5EAEA);
            	holder.txt.setBackgroundColor(0xD5EAEA);
            } 
            }       
        return convertView;
    }
    
    public void remove(Produit p)
    {
    	items.remove(p);
		notifyDataSetChanged();
    }
}