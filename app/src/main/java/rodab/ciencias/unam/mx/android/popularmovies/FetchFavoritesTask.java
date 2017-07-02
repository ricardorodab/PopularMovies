package rodab.ciencias.unam.mx.android.popularmovies;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import rodab.ciencias.unam.mx.android.popularmovies.data.MovieContract;

/**
 * Created by ricardo_rodab on 29/06/17.
 */

public class FetchFavoritesTask extends AsyncTask<Void, Void, Cursor> {

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
    protected Cursor doInBackground(Void... params) {
        try {
            return this.context.getContentResolver().query(MovieContract.RowEntry.CONTENT_URI,
                    null, null, null, null);
        } catch (Exception e) {
            Log.e("ERROR", "Failed to get favs");
            e.printStackTrace();
            return null;
        }


    }

    @Override
    protected void onPostExecute(Cursor cursor) {
        ((MainActivity) this.context).mLoading.setVisibility(View.INVISIBLE);
        ((MainActivity) this.context).errorMsg.setVisibility(View.INVISIBLE);
        ((MainActivity) this.context).mRecyclerView.setVisibility(View.VISIBLE);
        ((MainActivity) this.context).mMoviesAdapter.setCursor(cursor);
        ((MainActivity) this.context). mMoviesAdapter.setMovieData(null);
    }
}