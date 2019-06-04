package com.example.julienelkaim.testyoutube.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;

import com.example.julienelkaim.testyoutube.R;

import java.util.ArrayList;
import java.util.List;


public class PictogrammesAdapter extends BaseAdapter {

    private Context mContext;
    public ArrayList<Integer> imageId;

    public PictogrammesAdapter(Context context, ArrayList<Integer> imageId){
        this.mContext = context;
        this.imageId = imageId;

    }

    @Override
    public int getCount() {
        return imageId.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null){

            gridView = new View(mContext);

            gridView = inflater.inflate(R.layout.pictogramme,null);

            ImageButton imageButton = (ImageButton) gridView.findViewById(R.id.imagePictogramme);
            imageButton.setImageResource(imageId.get(position));

        }else{
            gridView = (View) convertView;
        }
        return gridView;
    }
}
