package rodab.ciencias.unam.mx.android.popularmovies;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import utilities.Movie;
import utilities.NetworkUtils;

/**
 * Created by ricardo_rodab on 27/06/17.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {

    public interface MovieAdapterOnClickHandler {
        void onClick(Movie data);
    }

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        public final ImageView mMovieImage;

        public MovieAdapterViewHolder(View itemView) {
            super(itemView);
            mMovieImage = (ImageView) itemView.findViewById(R.id.iv_movie_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Movie dataMovie = mMovieData[adapterPosition];
            mClickHandler.onClick(dataMovie);
        }
    }
    private Movie[] mMovieData;
    private final MovieAdapterOnClickHandler mClickHandler;

    public void setMovieData(Movie[] movies) {
        this.mMovieData = movies;
        notifyDataSetChanged();
    }

    public MovieAdapter(MovieAdapterOnClickHandler mClickHandler) {
        this.mClickHandler = mClickHandler;
    }


    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context conext = parent.getContext();
        int layoutid = R.layout.movie_grid_item;
        View view = LayoutInflater.from(conext).inflate(layoutid, parent, false);
        return new MovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieAdapterViewHolder holder, int position) {
        Movie movie = mMovieData[position];
        Context context = holder.mMovieImage.getContext();
        Picasso.with(context)
                .load(NetworkUtils.URL_IMAGE+movie.getUrlImage())
                .into(holder.mMovieImage);
    }

    @Override
    public int getItemCount() {
        if (mMovieData == null)
            return 0;
        return mMovieData.length;
    }



}

