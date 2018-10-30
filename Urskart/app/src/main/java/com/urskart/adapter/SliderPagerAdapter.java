package com.urskart.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.urskart.R;
import com.urskart.modal.Category;
import com.urskart.utility.Utility;

import java.util.List;


/**
 * Created by xyz on 06-02-2018.
 */

public class SliderPagerAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<Category> images;

    public SliderPagerAdapter(Context context, List<Category> images) {
        this.context = context;
        this.images=images;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.itme_slider_img, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
    //    imageView.setImageResource(images[position]);
        Utility.loadImage(images.get(position).getBanner_image(),imageView);
        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);

    }
}
