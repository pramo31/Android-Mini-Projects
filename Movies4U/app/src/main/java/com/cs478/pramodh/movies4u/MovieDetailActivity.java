package com.cs478.pramodh.movies4u;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MovieDetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details);

        Bundle data = getIntent().getExtras();
        Movie movieObj = data.getParcelable(String.valueOf(R.string.movie_parcel));

        ImageView bgImage = findViewById(R.id.movie_background_poster);
        TextView movieName = findViewById(R.id.movie_name_text);
        TextView releaseDate = findViewById(R.id.movie_release_date);
        TextView runtime = findViewById(R.id.movie_runtime);
        TextView imdbRating = findViewById(R.id.movie_imdb_rating);
        TextView rottenTomatoesRating = findViewById(R.id.movie_rotten_tomatoes_rating);
        TextView directorName = findViewById(R.id.movie_director);
        TextView castDetails = findViewById(R.id.movie_stars);

        bgImage.setBackgroundResource(movieObj.image);
        movieName.setText(movieObj.movieName);
        releaseDate.setText(movieObj.releaseDate);
        runtime.setText(movieObj.runtime);
        imdbRating.setText(movieObj.imdbRating);
        rottenTomatoesRating.setText(movieObj.rottenTomatoesRating);
        directorName.setText(movieObj.director);
        castDetails.setText(movieObj.cast);

    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}