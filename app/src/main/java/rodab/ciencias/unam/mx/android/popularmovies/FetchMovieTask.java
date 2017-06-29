package rodab.ciencias.unam.mx.android.popularmovies;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import java.net.URL;

import rodab.ciencias.unam.mx.android.popularmovies.utilities.Movie;
import rodab.ciencias.unam.mx.android.popularmovies.utilities.NetworkUtils;
import rodab.ciencias.unam.mx.android.popularmovies.utilities.OpenMovieJsonUtils;
import rodab.ciencias.unam.mx.android.popularmovies.MainActivity;

/**
 * Created by ricardo_rodab on 29/06/17.
 */

public class FetchMovieTask extends AsyncTask<Boolean, Void, Movie[]> {

    private Context context;
    private AsyncTaskCompleteListener<Movie[]> listener;

    public FetchMovieTask(Context ctx, AsyncTaskCompleteListener<Movie[]> listener)
    {
        this.context = ctx;
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        ((MainActivity) this.context).mLoading.setVisibility(View.VISIBLE);
    }

    @Override
    protected Movie[] doInBackground(Boolean... params) {
        if (params.length == 0) {
            return null;
        }

        boolean reciente = params[0].booleanValue();
        URL movieRequestUrl = NetworkUtils.buildUrl(reciente, context.getString(R.string.THE_MOVIE_DB_API_TOKEN));
        try {
            String jsonMovieResponse = NetworkUtils
                    .getResponseFromHttpUrl(movieRequestUrl);

            Movie[] simpleJsonVideoData = OpenMovieJsonUtils
                    .getSimpleMoviesFromJson(jsonMovieResponse);

            return simpleJsonVideoData;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(Movie[] movieData) {
        ((MainActivity) this.context).mLoading.setVisibility(View.INVISIBLE);
        if (movieData != null) {
            ((MainActivity) this.context).errorMsg.setVisibility(View.INVISIBLE);
            ((MainActivity) this.context).mRecyclerView.setVisibility(View.VISIBLE);
            ((MainActivity) this.context).mMoviesAdapter.setCoursor(null);
            ((MainActivity) this.context).mMoviesAdapter.setMovieData(movieData);
        } else {
            ((MainActivity) this.context).showErrorMessage();
        }
    }
}