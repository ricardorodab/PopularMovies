package rodab.ciencias.unam.mx.android.popularmovies;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import rodab.ciencias.unam.mx.android.popularmovies.data.MovieContract;
import rodab.ciencias.unam.mx.android.popularmovies.utilities.Movie;

/**
 * Created by ricardo_rodab on 29/06/17.
 */

public class FavoriteSetTask extends AsyncTask<Movie, Void, Uri> {

    private Context context;
    private AsyncTaskCompleteListener<Void> asyncTaskCompleteListener;

    public FavoriteSetTask(Context context, AsyncTaskCompleteListener<Void> aT) {
        this.context = context;
        this.asyncTaskCompleteListener = aT;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Uri doInBackground(Movie... params) {
        Movie localMovie = params[0];
        ContentValues contentValues = new ContentValues();
        contentValues.put(MovieContract.RowEntry.COL_ID, localMovie.getId());
        contentValues.put(MovieContract.RowEntry.COL_DATE, localMovie.getDate());
        contentValues.put(MovieContract.RowEntry.COL_RANKING, (float)localMovie.getRaking());
        contentValues.put(MovieContract.RowEntry.COL_SYNOPSIS, localMovie.getSynopsis());
        contentValues.put(MovieContract.RowEntry.COL_URL_IMAGE, localMovie.getUrlImage());
        contentValues.put(MovieContract.RowEntry.COL_TITLE, localMovie.getTitle());
        Uri uri = this.context.getContentResolver()
                .insert(MovieContract.RowEntry.CONTENT_URI, contentValues);
        return uri;
    }

    @Override
    protected void onPostExecute(Uri uri) {
        if(uri != null) {
            Toast.makeText(this.context,
                    "Added to Favorites", Toast.LENGTH_SHORT).show();
        }
    }
}