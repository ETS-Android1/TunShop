package com.promotion;


import com.entite.Promotion;
import com.menu.MainActivity;
import com.menu.R;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class PromoMain extends FragmentActivity implements PromoListFragment.Callbacks {
 
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.promotionmain);    
        ((PromoListFragment) getSupportFragmentManager().findFragmentById(R.id.promo_list)).setActivateOnItemClick(true);
        TextView t=(TextView)findViewById(R.id.titre);
    	t.setText("Promotions");
    }
    public void openmenu(View v)
	   {
		   openOptionsMenu();
	   }
	@Override
    public void onItemSelected(String id) 
	{
		try
		{	
			Promotion selected = (Promotion) ((PromoListFragment) getSupportFragmentManager().
					findFragmentById(R.id.promo_list)).getListAdapter().getItem(Integer.parseInt(id));
	
			if(selected !=null)
			{	
				 Intent detailIntent = new Intent(this, PromoDetailsActivity.class);
				 Bundle b = new Bundle();
				 b.putString("titre",selected.getTitre() );
				 b.putString("description",selected.getDescription() );
				 b.putString("url",selected.getUrl_img() );
				 detailIntent.putExtras(b);
				 startActivity(detailIntent);
			}
			}
		catch (Exception e){Log.e("on item selected event ",e.getMessage());}
		}
	public void retourner(View v)
	{
		 NavUtils.navigateUpTo(this, new Intent(this, MainActivity.class));
	}
}
