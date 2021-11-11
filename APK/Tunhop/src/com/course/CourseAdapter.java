package com.course;
import java.util.List;

import com.entite.Course;
import com.menu.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
 
public class CourseAdapter extends ArrayAdapter<Course> 
{
	public List<Course> courseListe;
    Context context;
	LayoutInflater inflater;
 
    public CourseAdapter(Context context, int resourceId,List<Course> items) {
        super(context, resourceId, items);
        this.context = context;
        this.courseListe =items;
		inflater = LayoutInflater.from(context);
    }
     
    /*private view holder class*/
    private class ViewHolder {
        ImageView imageView;
        TextView txtTitle;
        TextView budg;
    }
     
    public View getView( int position, View convertView, ViewGroup parent) {
        	ViewHolder holder = null;
        	Course course = getItem(position);       
        	LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        
        	if (convertView == null) 
        {
        		convertView = mInflater.inflate(R.layout.course_item, null);
        		holder = new ViewHolder();
        		holder.txtTitle = (TextView) convertView.findViewById(R.id.title);
        		holder.imageView = (ImageView) convertView.findViewById(R.id.icon);
        		holder.budg=(TextView)convertView.findViewById(R.id.bud);        		
        		convertView.setTag(holder);
        		
        }
        else  {holder = (ViewHolder) convertView.getTag(); }     
        holder.txtTitle.setText(course.getTitle());
        holder.imageView.setImageResource(course.getImageId());
        holder.budg.setText(course.getBudget()+" DT");
        holder.txtTitle.setTag(course);
       
        return convertView;
    }
    @Override
	public void remove(Course object) {
    	courseListe.remove(object);
		notifyDataSetChanged();
	}
   
}