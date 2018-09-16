/****************************************
 * Assignment HW3                       *
 * Movie.java                           *
 * Jarrod Norris, Andrew Schelesinger   *
 ****************************************/

package com.example.gameon.movierating;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Movie implements Parcelable {

    public String name;
    public String description;
    public String genre;
    public String rating;
    public String movieYear;
    public String imdbRating;

    protected Movie(Parcel in) {
        name = in.readString();
        description = in.readString();
        genre = in.readString();
        rating = in.readString();
        movieYear = in.readString();
        imdbRating = in.readString();
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
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(genre);
        dest.writeString(rating);
        dest.writeString(movieYear);
        dest.writeString(imdbRating);
    }

    public Movie() {
        this.name = "";
        this.description = "";
        this.genre = "";
        this.rating = "";
        this.movieYear = "";
        this.imdbRating = "";
    }

    public Movie(String name, String description, String genre, String rating, String movieYear, String imdbRating) {
        this.name = name;
        this.description = description;
        this.genre = genre;
        this.rating = rating;
        this.movieYear = movieYear;
        this.imdbRating = imdbRating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getMovieYear() {
        return movieYear;
    }

    public void setMovieYear(String movieYear) {
        this.movieYear = movieYear;
    }

    public String getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(String imdbRating) {
        this.imdbRating = imdbRating;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", genre='" + genre + '\'' +
                ", rating='" + rating + '\'' +
                ", movieYear='" + movieYear + '\'' +
                ", imdbRating='" + imdbRating + '\'' +
                '}';
    }
}
