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

    public class AdapterRecipes extends ArrayAdapter<Recipes> {

        Context c;
        ArrayList<Recipes> info;

        public AdapterRecipes(Context context, ArrayList<Recipes> cont) {
            super(context, R.layout.recipe_layout,cont);
            c=context;
            info=cont;
        }

        class Holder
        {
            ImageView imageRecipe;
            TextView txtRecipe;
            RatingBar ratingBar;

        }

        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position

            Recipes data = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view

            Holder viewHolder; // view lookup cache stored in tag

            if (convertView == null) {

                viewHolder = new Holder();
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.recipe_layout, parent, false);

                viewHolder.txtRecipe = (TextView) convertView.findViewById(R.id.txt_Recipe);

                viewHolder.imageRecipe = (ImageView) convertView.findViewById(R.id.img_Recipe);

                viewHolder.ratingBar = (RatingBar) convertView.findViewById(R.id.ratingBar);

                convertView.setTag(viewHolder);

            } else
                {
                       viewHolder = (Holder) convertView.getTag();
                }

            PicassoClient.downloadImage(c,data.getRecipeImg(),viewHolder.imageRecipe);
            viewHolder.txtRecipe.setText(data.getRecipeName());
            viewHolder.ratingBar.setRating(data.getRatingBar());


            // Return the completed view to render on screen
            return convertView;
        }


    }
