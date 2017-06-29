package rodab.ciencias.unam.mx.android.popularmovies;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;

/**
 * Created by ricardo_rodab on 29/06/17.
 */

public class FetchFavoritesTask extends AsyncTask<Void, Void, Void> {

    private Context context;
    private AsyncTaskCompleteListener<Void> asyncTaskCompleteListener;

    public FetchFavoritesTask(Context context, AsyncTaskCompleteListener<Void> aT) {
        this.context = context;
        this.asyncTaskCompleteListener = aT;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        ((MainActivity) this.context).mLoading.setVisibility(View.VISIBLE);
    }

    @Override
    protected Void doInBackground(Void... params) {
        return null;
    }

    @Override
    protected void onPostExecute(Void empty) {
        ((MainActivity) this.context).mLoading.setVisibility(View.INVISIBLE);
        ((MainActivity) this.context).errorMsg.setVisibility(View.INVISIBLE);
        ((MainActivity) this.context).mRecyclerView.setVisibility(View.VISIBLE);
        ((MainActivity) this.context).mMoviesAdapter.setCoursor(((MainActivity) this.context)
                .getAllFavMovies());
        ((MainActivity) this.context). mMoviesAdapter.setMovieData(null);
    }
}