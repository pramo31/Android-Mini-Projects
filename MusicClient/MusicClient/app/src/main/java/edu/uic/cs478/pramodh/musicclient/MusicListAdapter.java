package edu.uic.cs478.pramodh.musicclient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;
import java.util.Objects;

import edu.uic.cs478.pramodh.musicapi.Song;

public class MusicListAdapter extends ArrayAdapter<Song> {

    private int mResource;
    private List<Song> mListItems;
    private Context mContext;

    MusicListAdapter(Context context, int resource, List<Song> songList) {
        super(context, resource, songList);
        this.mListItems = songList;
        this.mContext = context;
        this.mResource = resource;
    }

    @Override
    public View getView(final int position, View view, final ViewGroup parent) {

        ViewHolder viewHolder;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            view = inflater.inflate(mResource, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.bitMapView.setImageBitmap(Objects.requireNonNull(getItem(position)).getImage());
        viewHolder.titleView.setText(Objects.requireNonNull(getItem(position)).getTitle());
        viewHolder.artistView.setText(Objects.requireNonNull(getItem(position)).getArtist());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ListView) parent).performItemClick(v, position, 0);
            }
        });

        return view;
    }

    @Override
    public int getCount() {
        return mListItems.size();
    }

    @Override
    public Song getItem(int position) {
        return mListItems.get(position);
    }


    static class ViewHolder {
        ImageView bitMapView;
        TextView titleView;
        TextView artistView;

        ViewHolder(View view) {
            bitMapView = view.findViewById(R.id.bitmap);
            titleView = view.findViewById(R.id.song_title);
            artistView = view.findViewById(R.id.artist_name);
        }
    }
}
