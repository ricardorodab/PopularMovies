package rodab.ciencias.unam.mx.android.popularmovies;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.net.URL;

import rodab.ciencias.unam.mx.android.popularmovies.data.MovieContract;
import rodab.ciencias.unam.mx.android.popularmovies.data.MovieOpenHelper;
import rodab.ciencias.unam.mx.android.popularmovies.utilities.Movie;
import rodab.ciencias.unam.mx.android.popularmovies.utilities.NetworkUtils;
import rodab.ciencias.unam.mx.android.popularmovies.utilities.OpenMovieJsonUtils;

/**
 * @author Jose Ricardo Rodriguez-Abreu
 * @version 1.0
 * @since Jun 26 2017.
 * <p>
 *     Main class of the app.
 * </p>
 *
 * <p>
 *     The app start with this class.
 * </p>
 */
public class MainActivity extends AppCompatActivity
        implements MovieAdapter.MovieAdapterOnClickHandler{

    private RecyclerView mRecyclerView;
    private TextView errorMsg;
    private MovieAdapter mMoviesAdapter;
    private ProgressBar mLoading;
    private boolean popular;
    private SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_movies);
        errorMsg = (TextView) findViewById(R.id.tv_error_message_display);
        GridLayoutManager gridLayoutManager
                = new GridLayoutManager(this,2);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        gridLayoutManager.setSpanCount(2);
        mMoviesAdapter = new MovieAdapter(this);
        mRecyclerView.setAdapter(mMoviesAdapter);
        mLoading = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        popular = true;

        MovieOpenHelper dbHelper = new MovieOpenHelper(this);
        mDb = dbHelper.getWritableDatabase();
        loadMovieData();
    }

    //We get all the information in a Cursor (in the DB).
    private Cursor getAllFavMovies() {
        return mDb.query(MovieContract.RowEntry.TABLE_NAME,
                null, null, null, null, null, null);
    }

    //The data is loaded in a Task.
    private void loadMovieData() {
        errorMsg.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
        Boolean bool = new Boolean(popular);
        new FetchMovieTask().execute(bool);
    }

    //This private method start a task to get the db data.
    private void loadFavoritesData() {
        errorMsg.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
        new FetchFavoritesTask().execute();
    }

    /**
     * The action of the app when the user "clicks" a film picture.
     * @param data - the data of the film that was pointed.
     */
    @Override
    public void onClick(Movie data) {
        Intent intent = new Intent(this, ShowMovieActivity.class);
        intent.putExtra(Intent.EXTRA_TEXT, data.getParserText());
        startActivity(intent);
    }

    // We can reuse code.
    private void showErrorMessage() {
        /* First, hide the currently visible data */
        mRecyclerView.setVisibility(View.INVISIBLE);
        /* Then, show the error */
        errorMsg.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tools, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.popular:
                mMoviesAdapter = new MovieAdapter(this);
                mRecyclerView.setAdapter(mMoviesAdapter);
                popular = true;
                loadMovieData();
                return true;

            case R.id.rated:
                mMoviesAdapter = new MovieAdapter(this);
                mRecyclerView.setAdapter(mMoviesAdapter);
                popular = false;
                loadMovieData();
                return true;
            case R.id.by_favorites:
                mMoviesAdapter = new MovieAdapter(this);
                mRecyclerView.setAdapter(mMoviesAdapter);
                loadFavoritesData();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public class FetchMovieTask extends AsyncTask<Boolean, Void, Movie[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoading.setVisibility(View.VISIBLE);
        }

        @Override
        protected Movie[] doInBackground(Boolean... params) {
            if (params.length == 0) {
                return null;
            }

            boolean reciente = params[0].booleanValue();
            URL movieRequestUrl = NetworkUtils.buildUrl(reciente);
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
            mLoading.setVisibility(View.INVISIBLE);
            if (movieData != null) {
                errorMsg.setVisibility(View.INVISIBLE);
                mRecyclerView.setVisibility(View.VISIBLE);
                mMoviesAdapter.setCoursor(null);
                mMoviesAdapter.setMovieData(movieData);
            } else {
                showErrorMessage();
            }
        }
    }

   public class FetchFavoritesTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoading.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPostExecute(Void empty) {
            mLoading.setVisibility(View.INVISIBLE);
            errorMsg.setVisibility(View.INVISIBLE);
            mRecyclerView.setVisibility(View.VISIBLE);
            mMoviesAdapter.setCoursor(getAllFavMovies());
            mMoviesAdapter.setMovieData(null);
        }
    }
} //End of MainActivity.java