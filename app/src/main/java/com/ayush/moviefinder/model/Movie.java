package com.ayush.moviefinder.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ayushkedia on 22/05/16.
 */
public class Movie implements Parcelable {
    public String Title;
    public String Year;
    public String Rated;
    public String Released;
    public String Runtime;
    public String Genre;
    public String Director;
    public String Writer;
    public String Actors;
    public String Plot;
    public String Language;
    public String Country;
    public String Awards;
    public String Poster;
    public String Metascore;
    public String imdbRating;
    public String imdbVotes;
    public String imdbID;
    public String Type;
    public String Response;
    public String Error;

    protected Movie(Parcel in) {
        Title = in.readString();
        Year = in.readString();
        Rated = in.readString();
        Released = in.readString();
        Runtime = in.readString();
        Genre = in.readString();
        Director = in.readString();
        Writer = in.readString();
        Actors = in.readString();
        Plot = in.readString();
        Language = in.readString();
        Country = in.readString();
        Awards = in.readString();
        Poster = in.readString();
        Metascore = in.readString();
        imdbRating = in.readString();
        imdbVotes = in.readString();
        imdbID = in.readString();
        Type = in.readString();
        Response = in.readString();
        Error = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Title);
        dest.writeString(Year);
        dest.writeString(Rated);
        dest.writeString(Released);
        dest.writeString(Runtime);
        dest.writeString(Genre);
        dest.writeString(Director);
        dest.writeString(Writer);
        dest.writeString(Actors);
        dest.writeString(Plot);
        dest.writeString(Language);
        dest.writeString(Country);
        dest.writeString(Awards);
        dest.writeString(Poster);
        dest.writeString(Metascore);
        dest.writeString(imdbRating);
        dest.writeString(imdbVotes);
        dest.writeString(imdbID);
        dest.writeString(Type);
        dest.writeString(Response);
        dest.writeString(Error);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}