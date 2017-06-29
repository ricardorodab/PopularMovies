package rodab.ciencias.unam.mx.android.popularmovies;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import rodab.ciencias.unam.mx.android.popularmovies.data.MovieContract;
import rodab.ciencias.unam.mx.android.popularmovies.utilities.Movie;
import rodab.ciencias.unam.mx.android.popularmovies.utilities.NetworkUtils;

/**
 * @author Jose Ricardo Rodriguez-Abreu
 * @version 1.0
 * @since Jun 26 2017.
 * <p>
 *   The adapter class for MainActivity.
 * </p>
 *
 * <p>
 *     To use the recyclerview we create this class.
 * </p>
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
            Movie dataMovie = null;
            if(mMovieData == null) {
                dataMovie = getMovieCursor(adapterPosition);
            } else {
                dataMovie = mMovieData[adapterPosition];
            }
            mClickHandler.onClick(dataMovie);
        }
    }
    private Movie[] mMovieData;
    private final MovieAdapterOnClickHandler mClickHandler;
    private Cursor mCursor;

    public void setMovieData(Movie[] movies) {
        this.mMovieData = movies;
        notifyDataSetChanged();
    }

    public MovieAdapter(MovieAdapterOnClickHandler mClickHandler) {
        this.mClickHandler = mClickHandler;
        this.mCursor = null;
    }

    public MovieAdapter(MovieAdapterOnClickHandler mClickHandler, Cursor count) {
        this.mClickHandler = mClickHandler;
        this.mCursor = count;
    }

    public void setCoursor(Cursor cursor) {
        this.mCursor = cursor;
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
        if(this.mCursor != null || mMovieData == null) {
            onBindViewHolderHelper(holder, position);
            return;
        }
        Movie movie = mMovieData[position];
        Context context = holder.mMovieImage.getContext();
        Picasso.with(context)
                .load(NetworkUtils.URL_IMAGE+movie.getUrlImage())
                .into(holder.mMovieImage);
    }

    private void onBindViewHolderHelper(MovieAdapterViewHolder holder, int position) {
        if(!mCursor.moveToPosition(position))
            return;
        Movie movie = getMovieCursor(position);
        Context context = holder.mMovieImage.getContext();
        Picasso.with(context)
                .load(NetworkUtils.URL_IMAGE+movie.getUrlImage())
                .into(holder.mMovieImage);
    }

    @Override
    public int getItemCount() {
        if(this.mCursor != null)
            return this.mCursor.getCount();
        if (mMovieData == null)
            return 0;
        return mMovieData.length;
    }

    protected Movie getMovieCursor(int position) {
        int id = mCursor.getInt(mCursor.getColumnIndex(MovieContract.RowEntry.COL_ID));
        String title = mCursor.getString(mCursor.getColumnIndex(MovieContract.RowEntry.COL_TITLE));
        String urlImage = mCursor.getString(mCursor.getColumnIndex(MovieContract.RowEntry.COL_URL_IMAGE));
        String synopsis = mCursor.getString(mCursor.getColumnIndex(MovieContract.RowEntry.COL_SYNOPSIS));
        double ranking = mCursor.getFloat(mCursor.getColumnIndex(MovieContract.RowEntry.COL_RANKING));
        String date = mCursor.getString(mCursor.getColumnIndex(MovieContract.RowEntry.COL_DATE));
        Movie movie = new Movie(id,title,urlImage,synopsis,ranking,date);
        movie.setFavorite(true);
        return movie;
    }


}

