package rodab.ciencias.unam.mx.android.popularmovies;

import android.content.Intent;
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
import android.widget.Toast;

import java.net.URL;

import utilities.Movie;
import utilities.NetworkUtils;
import utilities.OpenMovieJsonUtils;

public class MainActivity extends AppCompatActivity
        implements MovieAdapter.MovieAdapterOnClickHandler {

    private RecyclerView mRecyclerView;
    private TextView errorMsg;
    private MovieAdapter mMoviesAdapter;
    private ProgressBar mLoading;
    private boolean popular;

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
        loadMovieData();
    }

    // @TODO - Modificar el valor de bool
    private void loadMovieData() {
        errorMsg.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
        Boolean bool = new Boolean(popular);
        new FetchMovieTask().execute(bool);

    }

    // @TODO - Modificar para que haga alguna accion.
    @Override
    public void onClick(Movie data) {
        Intent intent = new Intent(this, ShowMovieActivity.class);
        intent.putExtra(Intent.EXTRA_TEXT, data.getParserText());
        startActivity(intent);
    }

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
            URL weatherRequestUrl = NetworkUtils.buildUrl(reciente);
            try {
                String jsonMovieResponse = NetworkUtils
                        .getResponseFromHttpUrl(weatherRequestUrl);

               Movie[] simpleJsonWeatherData = OpenMovieJsonUtils
                        .getSimpleMoviesFromJson(jsonMovieResponse);

                return simpleJsonWeatherData;

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
                mMoviesAdapter.setMovieData(movieData);
            } else {
                showErrorMessage();
            }
        }
    }

}
