package com.example.delicious;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterRatings extends ArrayAdapter<RateClass> {

    Context c;
    ArrayList<RateClass> info;

    public AdapterRatings(Context context, ArrayList<RateClass> cont) {
        super(context, R.layout.view_ratings_layout,cont);
        c=context;
        info=cont;
    }

    class Holder
    {
        TextView date;
        TextView comment;
        RatingBar rateBar;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position

        RateClass data = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view

        Holder viewHolder; // view lookup cache stored in tag

        if (convertView == null) {

            viewHolder = new Holder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.view_ratings_layout, parent, false);

            viewHolder.date = (TextView) convertView.findViewById(R.id.view_date);

            viewHolder.comment = (TextView) convertView.findViewById(R.id.view_comment);

            viewHolder.rateBar = (RatingBar) convertView.findViewById(R.id.view_rate);

            convertView.setTag(viewHolder);

        } else
            {
                   viewHolder = (Holder) convertView.getTag();
            }

        viewHolder.date.setText(data.getDate());
        viewHolder.comment.setText(data.getComment());
        viewHolder.rateBar.setRating(data.getValue());


        // Return the completed view to render on screen
        return convertView;
    }


}
