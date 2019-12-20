package com.example.mediaplayer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class SongAdapter extends ArrayAdapter<Song> {
    private int resourceId;

    public SongAdapter(Context context, int resource, List<Song> objects) {
        super(context, resource,objects);
        resourceId=resource;
    }
    public View getView(int position, View convertView, ViewGroup parent){
        Song song=getItem(position);//获取当前项的song实例
        View view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        TextView _song=(TextView)view.findViewById(R.id.item_song);
        _song.setText(song.getSong());
        return view;
    }
}
