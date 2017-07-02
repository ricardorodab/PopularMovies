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
import rodab.ciencias.unam.mx.android.popularmovies.utilities.MovieTypeEnum;
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

    protected RecyclerView mRecyclerView;
    protected TextView errorMsg;
    protected MovieAdapter mMoviesAdapter;
    protected ProgressBar mLoading;
    protected MovieTypeEnum popular;

    private static final String QUERY_KEY = "movies_query_show";

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
        popular = MovieTypeEnum.POPULAR;
        if(savedInstanceState != null) {
            if(savedInstanceState.containsKey(QUERY_KEY)) {
                String viewQuery = savedInstanceState.getString(QUERY_KEY);
                if(viewQuery.equals(MovieTypeEnum.POPULAR.toString())) {
                    popular = MovieTypeEnum.POPULAR;
                } else if(viewQuery.equals(MovieTypeEnum.TOP.toString())) {
                    popular = MovieTypeEnum.TOP;
                } else {
                    popular = MovieTypeEnum.FAVORITE;
                }
            }
        }
        if(popular != MovieTypeEnum.FAVORITE) {
            loadMovieData();
        } else {
            loadFavoritesData();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(QUERY_KEY, popular.toString());
    }

    @Override
    public void onResume() {
        super.onResume();
        if(popular == MovieTypeEnum.FAVORITE) {
            loadFavoritesData();
        }
    }

    //The data is loaded in a Task.
    private void loadMovieData() {
        errorMsg.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
        new FetchMovieTask(this, new FetchMovieTaskCompleteListener()).execute(popular);
    }

    //This private method start a task to get the db data.
    private void loadFavoritesData() {
        errorMsg.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
        new FetchFavoritesTask(this, new FetchFavoriteTaskCompleteListener()).execute();
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
    protected void showErrorMessage() {
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
                popular = MovieTypeEnum.POPULAR;
                loadMovieData();
                return true;

            case R.id.rated:
                mMoviesAdapter = new MovieAdapter(this);
                mRecyclerView.setAdapter(mMoviesAdapter);
                popular = MovieTypeEnum.TOP;
                loadMovieData();
                return true;
            case R.id.by_favorites:
                mMoviesAdapter = new MovieAdapter(this);
                mRecyclerView.setAdapter(mMoviesAdapter);
                popular = MovieTypeEnum.FAVORITE;
                loadFavoritesData();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public class FetchMovieTaskCompleteListener implements AsyncTaskCompleteListener<Movie[]>
    {@Override public void onTaskComplete(Movie[] result) {}}

    public class FetchFavoriteTaskCompleteListener implements AsyncTaskCompleteListener<Void>
    {@Override public void onTaskComplete(Void result) {}}


} //End of MainActivity.java