package com.cs478.pramodh.movies4u;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MovieListAdapter extends ArrayAdapter<Movie> {

    private int mResource;
    private List<Movie> mListItems;
    private Context mContext;

    MovieListAdapter(Context context, int resource, List<Movie> movieList) {
        super(context, resource, movieList);
        this.mListItems = movieList;
        this.mContext = context;
        this.mResource = resource;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            view = inflater.inflate(mResource, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.posterView.setImageDrawable(mContext.getResources().getDrawable(getItem(position).image));
        viewHolder.nameView.setText(getItem(position).movieName);
        viewHolder.releaseView.setText(getItem(position).releaseYear);
        return view;
    }

    @Override
    public int getCount() {
        return mListItems.size();
    }

    @Override
    public Movie getItem(int position) {
        return mListItems.get(position);
    }


    private static class ViewHolder {
        ImageView posterView;
        TextView nameView;
        TextView releaseView;

        ViewHolder(View view) {
            posterView = view.findViewById(R.id.poster);
            nameView = view.findViewById(R.id.name);
            releaseView = view.findViewById(R.id.release);
        }
    }
}