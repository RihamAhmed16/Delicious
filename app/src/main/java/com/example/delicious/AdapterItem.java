package com.example.delicious;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.delicious.Category;
import com.example.delicious.PicassoClient;
import com.example.delicious.R;

import java.util.ArrayList;

public class AdapterItem extends ArrayAdapter<String> {

    Context c;
    ArrayList<String> ass;

    public AdapterItem(Context context, ArrayList<String> cont) {
        super(context, R.layout.items_layout,cont);
        c=context;
        ass=cont;
    }

    class Holder
    {
        TextView item_txt;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position

        String data = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view

        Holder viewHolder; // view lookup cache stored in tag

        if (convertView == null) {

            viewHolder = new Holder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.items_layout, parent, false);

            viewHolder.item_txt = (TextView) convertView.findViewById(R.id.item);


            convertView.setTag(viewHolder);

        } else {
            viewHolder = (Holder) convertView.getTag();
        }
        viewHolder.item_txt.setText(data);


        // Return the completed view to render on screen
        return convertView;
    }

}
