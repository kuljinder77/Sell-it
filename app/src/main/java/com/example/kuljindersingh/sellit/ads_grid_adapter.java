package com.example.kuljindersingh.sellit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

class ads_grid_adapter extends BaseAdapter {
    private ArrayList<item> item_list;
    private  Context mc;
    LayoutInflater lin;


    public ads_grid_adapter(Context mc , ArrayList<item> item_list) {
        this.item_list = item_list;
        this.mc=mc;
        this.lin= LayoutInflater.from(mc);
    }

    @Override
    public int getCount() {
        return item_list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        convertView=lin.inflate(R.layout.custom_ads_without_name,null);
        ImageView image;
        image =(ImageView)convertView.findViewById(R.id.custom_without_image);

        Picasso.get().load(item_list.get(position).getUrl()).resize(150,150).centerCrop().into(image);

        return convertView;
    }
}
