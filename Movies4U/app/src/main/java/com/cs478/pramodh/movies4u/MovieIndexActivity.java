package com.cs478.pramodh.movies4u;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MovieIndexActivity extends Activity {

    List<Movie> movieList;
    ListView lstView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_index);

        movieList = new ArrayList<>();
        ArrayList<Integer> movieImages = new ArrayList<>(
                Arrays.asList(R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4, R.drawable.image5, R.drawable.image6, R.drawable.image7, R.drawable.image8, R.drawable.image9, R.drawable.image10));

        String[] releaseYears = getResources().getStringArray(R.array.release_years);
        String[] movieNames = getResources().getStringArray(R.array.movie_names);
        String[] imdbLinks = getResources().getStringArray(R.array.imdb_links);
        String[] trailerLinks = getResources().getStringArray(R.array.trailer_links);
        String[] wikiDirectorLinks = getResources().getStringArray(R.array.director_wiki_links);
        String[] directors = getResources().getStringArray(R.array.directors);
        String[] imdbRatings = getResources().getStringArray(R.array.imdb_ratings);
        String[] rottenTomatoesRatings = getResources().getStringArray(R.array.rotten_tomatoes_ratings);
        String[] casts = getResources().getStringArray(R.array.casts);
        String[] runtimes = getResources().getStringArray(R.array.runtimes);
        String[] releaseDates = getResources().getStringArray(R.array.release_dates);

        for (int i = 0; i < movieImages.size(); i++) {
            movieList.add(new Movie(movieImages.get(i), movieNames[i], releaseYears[i], imdbLinks[i], trailerLinks[i], wikiDirectorLinks[i], directors[i], casts[i], runtimes[i], releaseDates[i], imdbRatings[i], rottenTomatoesRatings[i]));
        }


        lstView = findViewById(R.id.movie_index);
        MovieListAdapter adapter = new MovieListAdapter(this, R.layout.movie_index_individual, movieList);
        lstView.setAdapter(adapter);

        lstView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(MovieIndexActivity.this, ImageViewActivity.class);
                intent.putExtra(String.valueOf(R.string.movie_parcel), movieList.get(position));
                startActivity(intent);
            }
        });

        registerForContextMenu(lstView);
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, R.id.movie_details_menu, 0, R.string.movie_details_menu);
        menu.add(0, R.id.trailer_link_menu, 0, R.string.trailer_link_menu);
        menu.add(0, R.id.director_wiki_menu, 0, R.string.director_wiki_menu);
        menu.add(0, R.id.imdb_link_menu, 0, R.string.imdb_link_menu);
    }


    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Intent intent;
        switch (item.getItemId()) {
            case R.id.movie_details_menu:
                showMovieDetails(info.position);
                return true;
            case R.id.trailer_link_menu:
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(movieList.get(info.position).trailerLink));
                startActivity(intent);
                return true;
            case R.id.director_wiki_menu:
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(movieList.get(info.position).directorWikiLink));
                startActivity(intent);
                return true;
            case R.id.imdb_link_menu:
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(movieList.get(info.position).imdbLink));
                startActivity(intent);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }


    private void showMovieDetails(int position) {
        Intent intent = new Intent(MovieIndexActivity.this, MovieDetailActivity.class);
        intent.putExtra(String.valueOf(R.string.movie_parcel), movieList.get(position));
        startActivity(intent);
    }
}
