package com.example.kuljindersingh.sellit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;


public class front_grid_adapter extends BaseAdapter

{
    private int[] array;
    private String[] array1;
    private  Context mc;
    LayoutInflater lin;
    public front_grid_adapter(Context mc,int array[] , String array1[])
    {
        this.array=array;
        this.array1 = array1;
        this.mc=mc;
        this.lin=LayoutInflater.from(mc);

    }

    @Override
    public int getCount() {
        return array.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        convertView=lin.inflate(R.layout.custom_ads,null);
        ImageView image;
        TextView text;
        image =(ImageView)convertView.findViewById(R.id.custom_img_id);
        text=(TextView)convertView.findViewById(R.id.custom_text_id);
        image.setImageResource(array[position]);
        text.setText(array1[position]);
        return convertView;
    }

}