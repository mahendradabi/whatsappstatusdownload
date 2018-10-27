package com.urskart.utility;

import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.urskart.R;

public class Utility {
    public static void loadImage(String path, ImageView imageView) {
        Picasso.get()
                .load(path)
                .placeholder(R.drawable.logo)
                .error(R.drawable.logo)
                .into(imageView);
    }
}
