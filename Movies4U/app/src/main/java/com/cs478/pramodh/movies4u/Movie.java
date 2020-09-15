package com.cs478.pramodh.movies4u;

import android.os.Parcel;
import android.os.Parcelable;

class Movie implements Parcelable {

    int image;
    String movieName;
    String releaseYear;
    String imdbLink;
    String trailerLink;
    String directorWikiLink;
    String director;
    String cast;
    String runtime;
    String releaseDate;
    String imdbRating;
    String rottenTomatoesRating;

    Movie(int image, String movieName, String releaseYear, String imdbLink, String trailerLink, String directorWikiLink, String director, String cast, String runtime, String releaseDate, String imdbRating, String rottenTomatoesRating) {
        this.image = image;
        this.movieName = movieName;
        this.releaseYear = releaseYear;
        this.imdbLink = imdbLink;
        this.trailerLink = trailerLink;
        this.directorWikiLink = directorWikiLink;
        this.director = director;
        this.imdbRating = imdbRating;
        this.rottenTomatoesRating = rottenTomatoesRating;
        this.cast = cast;
        this.runtime = runtime;
        this.releaseDate = releaseDate;
    }

    private Movie(Parcel in) {
        image = in.readInt();
        movieName = in.readString();
        releaseYear = in.readString();
        imdbLink = in.readString();
        trailerLink = in.readString();
        directorWikiLink = in.readString();
        director = in.readString();
        cast = in.readString();
        runtime = in.readString();
        releaseDate = in.readString();
        imdbRating = in.readString();
        rottenTomatoesRating = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(image);
        dest.writeString(movieName);
        dest.writeString(releaseYear);
        dest.writeString(imdbLink);
        dest.writeString(trailerLink);
        dest.writeString(directorWikiLink);
        dest.writeString(director);
        dest.writeString(cast);
        dest.writeString(runtime);
        dest.writeString(releaseDate);
        dest.writeString(imdbRating);
        dest.writeString(rottenTomatoesRating);
    }
}
