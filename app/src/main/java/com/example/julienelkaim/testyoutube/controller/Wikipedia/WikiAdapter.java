package com.example.julienelkaim.testyoutube.controller.Wikipedia;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.julienelkaim.testyoutube.R;

import java.util.ArrayList;
import java.util.List;

public class WikiAdapter extends ArrayAdapter<WikiArticle> {

    private Context mContext;
    private List<WikiArticle> wikiArticlesList;


    WikiAdapter(@NonNull Context context, @SuppressLint("SupportAnnotationUsage") @LayoutRes ArrayList<WikiArticle> list) {
        super(context, 0, list);
        mContext = context;
        wikiArticlesList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_item,parent,false);

        WikiArticle currentArticle = wikiArticlesList.get(position);
        TextView name = listItem.findViewById(R.id.textView_name);
        name.setText(currentArticle.getmName());

        return listItem;
    }

}
