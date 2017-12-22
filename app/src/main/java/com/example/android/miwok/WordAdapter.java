package com.example.android.miwok;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import static android.R.interpolator.linear;
import static android.view.View.GONE;

/**
 * Created by User.pc on 10/22/2017.
 */
public class WordAdapter extends ArrayAdapter<Word> {
    private int colorId;

    public WordAdapter(Activity context,ArrayList<Word> words,int colorId)
    {

        super(context,0,words);
       this.colorId=colorId;
    }
    @NonNull
    @Override

    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // Get the {@link AndroidFlavor} object located at this position in the list
        Word currentWord = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID version_name
        TextView nameTextView = (TextView) listItemView.findViewById(R.id.hindi_translation);
        // Get the version name from the current AndroidFlavor object and
        // set this text on the name TextView
        nameTextView.setText(currentWord.getHindiTranslation());

        // Find the TextView in the list_item.xml layout with the ID version_number
        TextView numberTextView = (TextView) listItemView.findViewById(R.id.default_translation);
        // Get the version number from the current AndroidFlavor object and
        // set this text on the number TextView
        numberTextView.setText(currentWord.getDefaultTranslation());
        ImageView imageView = (ImageView) listItemView.findViewById((R.id.image));
        if(currentWord.checkImage()) {

            imageView.setImageResource(currentWord.getImageId());
        }
        else {

            imageView.setVisibility(View.GONE);
        }
       ImageView imageView1=(ImageView) listItemView.findViewById((R.id.image_two));
       imageView1.setImageResource(R.drawable.ic_play_arrow);
        LinearLayout linearLayout1=(LinearLayout) listItemView.findViewById(R.id.sound_icon);

        LinearLayout linearLayout=(LinearLayout) listItemView.findViewById(R.id.text_view);
        int color= ContextCompat.getColor(getContext(),colorId);
        linearLayout.setBackgroundColor(color);
        linearLayout1.setBackgroundColor(color);

        // Return the whole list item layout (containing 2 TextViews and an ImageView)
        // so that it can be shown in the ListView
        return listItemView;
    }
}