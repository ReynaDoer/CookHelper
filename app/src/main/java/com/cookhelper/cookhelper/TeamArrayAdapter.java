package com.cookhelper.cookhelper;

import android.content.Context;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;



public class TeamArrayAdapter extends ArrayAdapter {
    private final Context context;
    private final String[] values;
    private final Icon[] images;
    public TeamArrayAdapter(Context context, String[] values, Icon[] images) {
        super(context, R.layout.recipe_list_layout, values);
        this.context = context;
        this.values = values;
        this.images = images;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.recipe_list_layout, parent, false);
        Button recipe = (Button) rowView.findViewById(R.id.button);
        ImageView image = (ImageView) rowView.findViewById(R.id.imageView);
        recipe.setText(values[position]);
        if(images[position]!=null) {

                image.setImageIcon(images[position]);
            }

        ;
// Change the icon for Windows and iPhone
        String s = values[position];
        /*
        if (s == null || s.isEmpty() || s.equals("empty")) {
            imageView.setImageResource(R.drawable.ic_logo_empty);
        } else {
            imageView.setImageResource(R.drawable.ic_logo_mil);
        }
        */
        return rowView;
    }



}

