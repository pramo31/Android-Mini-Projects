package com.cs478.pramodh.movies4u;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

public class ImageViewActivity extends Activity {

    ImageView mImageView;
    String imdbLink;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_poster);

        mImageView = findViewById(R.id.movie_poster);
        Bundle data = getIntent().getExtras();
        Movie movieObj = data.getParcelable(String.valueOf(R.string.movie_parcel));

        mImageView.setImageResource(movieObj.image);
        imdbLink = movieObj.imdbLink;

        mImageView.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                Intent linkIntent = new Intent(Intent.ACTION_VIEW);
                linkIntent.setData(Uri.parse(imdbLink));
                startActivity(linkIntent);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mImageView.setImageDrawable(null);
        super.onDestroy();
    }
}
