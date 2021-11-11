package com.promotion;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import com.menu.R;
import com.database.DBHelper;
import com.database.JSONParser;
import com.entite.Magasin;
import com.entite.Promotion;
import com.util.Constants;
import com.util.DownloadImageTask;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

/**
 * Adaptateur des sections (soit libéllé des magasins , soit la catégorie
 */
public class SectionPromoAdapter extends BaseAdapter implements ListAdapter, OnItemClickListener {
   
	private final DataSetObserver dataSetObserver = new DataSetObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            updateSessionCache(tri);
        }

        @Override
        public void onInvalidated() {
            super.onInvalidated();
            updateSessionCache(tri);
        };
    };
    private ListAdapter linkedAdapter;
    private final Map<Integer, String> sectionPositions = new LinkedHashMap<Integer, String>();
    private final Map<Integer, Integer> itemPositions = new LinkedHashMap<Integer, Integer>();
    private final Map<View, String> currentViewSections = new HashMap<View, String>();
    private int viewTypeCount;
    protected final LayoutInflater inflater;
   // private View transparentSectionView;
    private OnItemClickListener linkedListener;
    public String tri;

    public SectionPromoAdapter(final LayoutInflater inflater,final ListAdapter linkedAdapter,String tri) {
    	this.linkedAdapter = linkedAdapter;
        this.inflater = inflater;
        linkedAdapter.registerDataSetObserver(dataSetObserver);
        this.tri=tri;
        updateSessionCache(tri);
    }

    private boolean isTheSame(final String previousSection,final String newSection) {
        if (previousSection == null) {
            return newSection == null;
        } else {
        	//Log.e("egal",previousSection+";"+newSection);
            return previousSection.equals(newSection);
        }
    }

    private synchronized void updateSessionCache(String tri) {
    	Trier();
    	int currentPosition = 0;
        sectionPositions.clear();
        itemPositions.clear();
        viewTypeCount = linkedAdapter.getViewTypeCount() + 1;
        String currentSection = null;
        final int count = linkedAdapter.getCount();
        for (int i = 0; i < count; i++) 
        {
            final Promotion item = (Promotion) linkedAdapter.getItem(i);
         if(tri.equals("magasin"))
         {    if (!isTheSame(currentSection,item.getMagasin().getLib())) 
            {
                sectionPositions.put(currentPosition,item.getMagasin().getLib());
                
                currentSection = item.getMagasin().getLib();
                currentPosition++;
            }
         }
         else if (tri.equals("categorie"))
         {
        	 
        	  if (!isTheSame(currentSection,item.getCategorie())) 
              {
                  sectionPositions.put(currentPosition,item.getCategorie());            
                  currentSection =item.getCategorie();
                  currentPosition++;
              }
         }
         itemPositions.put(currentPosition, i);
         currentPosition++;
        }
    }

    @Override
    public synchronized int getCount() {
        return sectionPositions.size() + itemPositions.size();
    }

    @Override
    public synchronized Object getItem(final int position) {
        if (isSection(position)) {
            return sectionPositions.get(position);
        } else {
            final int linkedItemPosition = getLinkedPosition(position);
            return linkedAdapter.getItem(linkedItemPosition);
        }
    }

    public synchronized boolean isSection(final int position) {
        return sectionPositions.containsKey(position);
    }

    public synchronized String getSectionName(final int position) {
        if (isSection(position)) {
            return sectionPositions.get(position);
        } else {
            return null;
        }
    }

    @Override
    public long getItemId(final int position) {
        if (isSection(position)) {
            return sectionPositions.get(position).hashCode();
        } else {
            return linkedAdapter.getItemId(getLinkedPosition(position));
        }
    }

    protected Integer getLinkedPosition(final int position) {
        return itemPositions.get(position);
    }

    @Override
    public int getItemViewType(final int position) {
        if (isSection(position)) {
            return viewTypeCount - 1;
        }
        return linkedAdapter.getItemViewType(getLinkedPosition(position));
    }

    private View getSectionView(final View convertView, final String section) {
        View theView = convertView;
        if (theView == null) {
            theView = createNewSectionView();
        }
        setSectionText(section, theView);
        replaceSectionViewsInMaps(section, theView);
        return theView;
    }

    protected void setSectionText(final String section,final View sectionView) {     
    	
    	final TextView textView = (TextView) sectionView.findViewById(R.id.section);
    	textView.setText(section);
    	if(tri.equals("magasin"))
    	{
    	PromotionAdapter a=(PromotionAdapter)linkedAdapter;
    	Magasin mag=a.getMagasin(section);
    	int on= new DBHelper(inflater.getContext()).ValeurConstante(Constants.CONSTANTS_RESAUX);
     	if( ( on==1&& JSONParser.HeightConnection(inflater.getContext()))||on==0)
        {
    	new DownloadImageTask((ImageView)sectionView.findViewById(R.id.logo))
    	.execute(mag.getUrl_logo());
        }
    	}
    }

    protected synchronized void replaceSectionViewsInMaps(final String section,
            final View theView) {
        if (currentViewSections.containsKey(theView)) {
            currentViewSections.remove(theView);
        }
        currentViewSections.put(theView, section);
    }

    protected View createNewSectionView() {
        return inflater.inflate(R.layout.sectionpromo, null);
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent)
    {
        if (isSection(position)) {        	
            return getSectionView(convertView, sectionPositions.get(position));
        }
        return linkedAdapter.getView(getLinkedPosition(position), convertView,parent);
    }

    @Override
    public int getViewTypeCount() {
        return viewTypeCount;
    }

    @Override
    public boolean hasStableIds() {
        return linkedAdapter.hasStableIds();
    }

    @Override
    public boolean isEmpty() {
        return linkedAdapter.isEmpty();
    }

    @Override
    public void registerDataSetObserver(final DataSetObserver observer) {
        linkedAdapter.registerDataSetObserver(observer);
    }

    @Override
    public void unregisterDataSetObserver(final DataSetObserver observer) {
        linkedAdapter.unregisterDataSetObserver(observer);
    }

    @Override
    public boolean areAllItemsEnabled() {
        return linkedAdapter.areAllItemsEnabled();
    }

    @Override
    public boolean isEnabled(final int position) {
        if (isSection(position)) {
            return true;
        }
        return linkedAdapter.isEnabled(getLinkedPosition(position));
    }


    protected void sectionClicked(final String section) {
        // do nothing
    }

    @Override
    public void onItemClick(final AdapterView< ? > parent, final View view,
            final int position, final long id) {
        if (isSection(position)) {
            sectionClicked(getSectionName(position));
        } else if (linkedListener != null) {
            linkedListener.onItemClick(parent, view,
                    getLinkedPosition(position), id);
        }
    }

    public void setOnItemClickListener(final OnItemClickListener linkedListener) {
        this.linkedListener = linkedListener;
    }
    public void Trier()
    {
    	if (tri.equals("categorie")){((PromotionAdapter)linkedAdapter).sortCategorie();}
    	else {((PromotionAdapter)linkedAdapter).sortMagasin();}
    }

}