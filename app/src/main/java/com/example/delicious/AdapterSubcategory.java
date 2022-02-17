package com.example.delicious;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterSubcategory extends ArrayAdapter<Subcategory> {

    Context c;
    ArrayList<Subcategory> ass;

    public AdapterSubcategory(Context context, ArrayList<Subcategory> cont) {
        super(context, R.layout.subcategory_layout,cont);
        c=context;
        ass=cont;
    }

    class Holder
    {
        ImageView subcate_img;
        TextView subcate_txt;

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position

        Subcategory data = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view

        Holder viewHolder; // view lookup cache stored in tag

        if (convertView == null) {

            viewHolder = new Holder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.subcategory_layout, parent, false);

            viewHolder.subcate_txt = (TextView) convertView.findViewById(R.id.subcate_txt);

           viewHolder.subcate_img = (ImageView) convertView.findViewById(R.id.subcate_img);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (Holder) convertView.getTag();
        }
        PicassoClient.downloadImage(c,data.getLogo(),viewHolder.subcate_img);
        viewHolder.subcate_txt.setText(data.getName());


        // Return the completed view to render on screen
        return convertView;
    }


}
