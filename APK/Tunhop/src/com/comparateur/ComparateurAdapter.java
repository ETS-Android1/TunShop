package com.comparateur;

import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.database.DBHelper;
import com.database.JSONParser;
import com.entite.ProduitMagasin;
import com.menu.R;
import com.util.Constants;
import com.util.DownloadImageTask;

public 	 class ComparateurAdapter extends ArrayAdapter<ProduitMagasin> {

    public List<ProduitMagasin> items;
    Context context;
	
    public ComparateurAdapter(final Context context,int layout,final List<ProduitMagasin> items) 
    {
        super(context, layout, items);
        this.items = items;
        this.context=context;
    }
    private class ViewHolder {
    	   TextView lib;
    	   ImageView img;
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
		        convertView = vi.inflate(R.layout.comp_item, null);               						
		        holder = new ViewHolder();
		        holder.lib = (TextView) convertView.findViewById(R.id.libp);
		        holder.img = (ImageView) convertView.findViewById(R.id.img);
		        holder.prix = (TextView) convertView.findViewById(R.id.prix);
		        convertView.setTag(holder); 
		}
        else {
        	 holder = (ViewHolder) convertView.getTag();
             }
        final ProduitMagasin currentItem = getItem(position);
        if (currentItem != null) {
                holder.lib.setTag(currentItem);
                holder.prix.setTag(currentItem);
                holder.img.setTag(currentItem);              
                
                holder.prix.setText(currentItem.getPrix().toString()+" DT");
                holder.lib.setText(currentItem.getMagasin().getLib().toString());                   
                int on= new DBHelper(context).ValeurConstante(Constants.CONSTANTS_RESAUX);
        	 	if( ( on==1&& JSONParser.HeightConnection(context))||on==0)
                {
                new DownloadImageTask(holder.img).execute(Constants.url_mag+currentItem.getMagasin().getUrl_logo());  
                }
            	
        if (items.get(0).getPrix()==(currentItem.getPrix()))
        {
        	holder.prix.setBackgroundResource(R.color.green);
        }
        else
        {
        	holder.prix.setBackgroundResource(R.color.red);
        }
        }       
        return convertView;
    }
    
}