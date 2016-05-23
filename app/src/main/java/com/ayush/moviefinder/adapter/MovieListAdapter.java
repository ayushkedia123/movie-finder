package com.ayush.moviefinder.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ayush.moviefinder.R;
import com.ayush.moviefinder.activity.MovieDetailActivity;
import com.ayush.moviefinder.activity.MoviesListActivity;
import com.ayush.moviefinder.model.Movie;
import com.ayush.moviefinder.model.MovieSearchList;
import com.ayush.moviefinder.utils.AppUtil;
import com.ayush.moviefinder.view.CircleLoaderView;
import com.ayush.moviefinder.view.OnLoadMoreListener;

import java.util.ArrayList;

/**
 * Created by ayushkedia on 22/05/16.
 */
public class MovieListAdapter extends BaseAdapter {

    private static final int FOOTER_MOVIE_CARD = 2;
    private static final int MOVIE_CARD = 1;
    private Context context;
    private ArrayList<Movie> moviesList;
    private OnLoadMoreListener onLoadMoreListener;
    int pageSize;

    public MovieListAdapter(Context context, ArrayList<Movie> moviesList) {
        this.context = context;
        this.moviesList = moviesList;
    }

    @Override
    public int getCount() {
        return moviesList.size();
    }

    public void setPage(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public Object getItem(int position) {
        return moviesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    /* checking the condition to return loader view  */

    @Override
    public int getItemViewType(int position) {
        if (position == moviesList.size() - 1 && moviesList.size() == pageSize)
            return FOOTER_MOVIE_CARD;
        else
            return MOVIE_CARD;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (getItemViewType(position) == FOOTER_MOVIE_CARD) {
            onLoadMoreListener.onLoadMore(true);
        } else {
            onLoadMoreListener.onLoadMore(false);
        }

        ChildViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_movies_list, null);
            holder = new ChildViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ChildViewHolder) convertView.getTag();
        }

        Movie movie = moviesList.get(position);

        if (movie.Poster != null)
            AppUtil.getInstance().loadImageGlide(context, movie.Poster, holder.ivPosterImage, R.drawable.ic_movie);

        if (movie.Type != null) {
            holder.tvMovieType.setVisibility(View.VISIBLE);
            holder.tvMovieType.setText(movie.Type);
        } else
            holder.tvMovieType.setVisibility(View.GONE);

        if (movie.Title != null) {
            holder.tvMovieTitle.setVisibility(View.VISIBLE);
            holder.tvMovieTitle.setText(movie.Title);
        } else
            holder.tvMovieTitle.setVisibility(View.GONE);

        if (movie.Year != null) {
            holder.tvReleaseYear.setVisibility(View.VISIBLE);
            holder.tvReleaseYear.setText(movie.Year);
        } else
            holder.tvReleaseYear.setVisibility(View.GONE);

        return convertView;

    }

    /**
     * child view holder.
     */
    private class ChildViewHolder {
        ImageView ivPosterImage;
        TextView tvMovieTitle;
        TextView tvReleaseYear;
        TextView tvMovieType;

        ChildViewHolder(View v) {
            ivPosterImage = (ImageView) v.findViewById(R.id.iv_movie_poster);
            tvMovieTitle = (TextView) v.findViewById(R.id.tv_movie_title);
            tvReleaseYear = (TextView) v.findViewById(R.id.tv_year);
            tvMovieType = (TextView) v.findViewById(R.id.tv_movie_type);
        }
    }
}
