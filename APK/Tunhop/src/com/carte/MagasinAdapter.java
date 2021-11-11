package com.carte;

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
import com.entite.Magasin;
import com.menu.R;
import com.util.Constants;
import com.util.DownloadImageTask;

public 	 class MagasinAdapter extends ArrayAdapter<Magasin> {

    public List<Magasin> items;
    Context context;
	
    public MagasinAdapter(final Context context,int layout,final List<Magasin> items) 
    {
        super(context, layout, items);
        this.items = items;
        this.context=context;
    }
    private class ViewHolder {
    	   TextView mag;
    	   ImageView img;
    	   TextView carte; 	   
    	  }

    @Override
    public View getView(final int position,  View convertView,final ViewGroup parent) 
    {
    	ViewHolder holder;
        if (convertView == null) 
       {
		       final LayoutInflater vi = (LayoutInflater) context
		                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		        convertView = vi.inflate(R.layout.listecarte_item, null);               						
		        holder = new ViewHolder();
		        holder.mag = (TextView) convertView.findViewById(R.id.mag);
		        holder.img = (ImageView) convertView.findViewById(R.id.logo);
		        holder.carte = (TextView) convertView.findViewById(R.id.carte);
		        convertView.setTag(holder); 
		}
        else {
        	 holder = (ViewHolder) convertView.getTag();
             }
        final Magasin currentItem = getItem(position);
        if (currentItem != null) {
                holder.mag.setTag(currentItem);
                holder.carte.setTag(currentItem);
                holder.img.setTag(currentItem);              
                
                holder.mag.setText(currentItem.getLib());
                holder.carte.setText(currentItem.getLibcarte()); 
              int on= new DBHelper(context).ValeurConstante(Constants.CONSTANTS_RESAUX);
        	 	if( ( on==1&& JSONParser.HeightConnection(context))||on==0)
                {
                new DownloadImageTask(holder.img).execute(Constants.url_mag+currentItem.getUrl_logo());  
           
                }
                }       
        return convertView;
    }
    
}