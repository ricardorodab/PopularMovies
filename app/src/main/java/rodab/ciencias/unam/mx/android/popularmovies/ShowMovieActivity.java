package rodab.ciencias.unam.mx.android.popularmovies;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.net.URL;

import rodab.ciencias.unam.mx.android.popularmovies.data.MovieContract;
import rodab.ciencias.unam.mx.android.popularmovies.data.MovieOpenHelper;
import rodab.ciencias.unam.mx.android.popularmovies.utilities.Movie;
import rodab.ciencias.unam.mx.android.popularmovies.utilities.NetworkUtils;
import rodab.ciencias.unam.mx.android.popularmovies.utilities.OpenMovieJsonUtils;
import rodab.ciencias.unam.mx.android.popularmovies.utilities.Review;

/**
 * @author Jose Ricardo Rodriguez-Abreu
 * @version 1.0
 * @since Jun 26 2017.
 * <p>
 *     A contract class.
 * </p>
 *
 * <p>
 *     For constant variables like url's and names for the DB.
 * </p>
 */
public class ShowMovieActivity extends AppCompatActivity
                implements ShowMovieAdapter.ShowMovieAdapterOnClickHandler,
                            ShowReviewsAdapter.ShowReviewsAdapterOnClickHandler {

    private Movie localMovie;
    private TextView title;
    private TextView synopsis;
    private TextView date;
    private ImageView url;
    private TextView rating;
    private ShowMovieAdapter mShowMovieAdapter;
    private ShowReviewsAdapter mShowReviewAdapter;
    private RecyclerView mShowRecyclerView;
    private RecyclerView getmShowRecyclerViewReviews;
    private SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_movie);

        mShowRecyclerView = (RecyclerView) findViewById(R.id.rv_videos);
        getmShowRecyclerViewReviews = (RecyclerView) findViewById(R.id.rv_reviews);

        LinearLayoutManager layoutManagerReview
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        getmShowRecyclerViewReviews.setLayoutManager(layoutManagerReview);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mShowRecyclerView.setLayoutManager(layoutManager);

        mShowMovieAdapter = new ShowMovieAdapter(this);
        mShowRecyclerView.setAdapter(mShowMovieAdapter);

        mShowReviewAdapter = new ShowReviewsAdapter(this);
        getmShowRecyclerViewReviews.setAdapter(mShowReviewAdapter);

        title = (TextView) findViewById(R.id.tv_title);
        synopsis = (TextView) findViewById(R.id.tv_synopsis);
        date = (TextView) findViewById(R.id.tv_date);
        rating = (TextView) findViewById(R.id.tv_rating);
        url = (ImageView) findViewById(R.id.imageView);

        MovieOpenHelper dbHelper = new MovieOpenHelper(this);
        mDb = dbHelper.getWritableDatabase();

        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra(Intent.EXTRA_TEXT)) {
                localMovie = Movie.getMovieFromParserText(intent.getStringExtra(Intent.EXTRA_TEXT));
                localMovie.setFavorite(checkIfDataExist(localMovie));
                if (localMovie != null) {
                    title.setText("Title: " + localMovie.getTitle());
                    synopsis.setText("Synopsis: " + localMovie.getSynopsis());
                    date.setText("Release date: " + localMovie.getDate());
                    rating.setText("Rating: " + localMovie.getRaking() + "/10");
                    Picasso.with(this).load(NetworkUtils.URL_IMAGE + localMovie.getUrlImage()).into(url);
                }
            }
        }
        loadTrailerExtraData(localMovie);
    }

    private void loadTrailerExtraData(Movie localMovie) {
        new FetchDataTask().execute(localMovie);
    }

    @Override
    public void onClick(String str) {
        Intent watchVideo = new Intent(Intent.ACTION_VIEW);
        Uri build = Uri.parse(str).buildUpon().build();
        watchVideo.setData(build);
        if(watchVideo.resolveActivity(getPackageManager()) != null)
            startActivity(watchVideo);
    }

    @Override
    public void onClick(Review review) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.favorite, menu);
        MenuItem favorite = menu.getItem(0);
        MenuItemCompat.getActionView(favorite);
        if(localMovie.isFavorite()) {
            favorite.setIcon(R.drawable.heart_full_red);
        } else {
            favorite.setIcon(R.drawable.heart_empty_white);
        }
        return true;
    }

    private void onClickFavorite() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MovieContract.RowEntry.COL_ID, localMovie.getId());
        contentValues.put(MovieContract.RowEntry.COL_DATE, localMovie.getDate());
        contentValues.put(MovieContract.RowEntry.COL_RANKING, localMovie.getRaking());
        contentValues.put(MovieContract.RowEntry.COL_SYNOPSIS, localMovie.getSynopsis());
        contentValues.put(MovieContract.RowEntry.COL_URL_IMAGE, localMovie.getUrlImage());
        contentValues.put(MovieContract.RowEntry.COL_TITLE, localMovie.getTitle());
        localMovie.setFavorite(true);

        mDb.insert(MovieContract.RowEntry.TABLE_NAME, null, contentValues);
    }

    private void onClickUnfavorite() {
        mDb.delete(MovieContract.RowEntry.TABLE_NAME,
                    MovieContract.RowEntry.COL_ID + "=" + localMovie.getId(), null);
        localMovie.setFavorite(false);
    }

    private boolean checkIfDataExist(Movie movie) {
        String query = "SELECT * FROM " + MovieContract.RowEntry.TABLE_NAME + " WHERE " +
                MovieContract.RowEntry.COL_ID  + "=" + movie.getId();
        Cursor cursor = mDb.rawQuery(query,null);
        if(cursor.getCount() <= 0)
            return false;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        MenuItem other;
        switch (item.getItemId()) {
            case R.id.favorites:
                Drawable.ConstantState drawable = ContextCompat
                                .getDrawable(getApplicationContext(), R.drawable.heart_empty_white)
                                .getConstantState();
                if(item.getIcon().getConstantState().equals(drawable)) {
                    item.setIcon(R.drawable.heart_full_red);
                    onClickFavorite();
                    Toast.makeText(getApplicationContext(),
                            "Added to Favorites", Toast.LENGTH_SHORT).show();
                } else {
                    item.setIcon(R.drawable.heart_empty_white);
                    onClickUnfavorite();
                    Toast.makeText(getApplicationContext(),
                            "Deleted from Favorites", Toast.LENGTH_SHORT).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }


    public class FetchDataTask extends AsyncTask<Movie, Void, Movie> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected Movie doInBackground(Movie... params) {
            if (params.length == 0) {
                return null;
            }

            Movie localMovie = params[0];
            URL movieRequestUrl = NetworkUtils
                    .buildUrlVideoReviews(NetworkUtils.VIDEOS, localMovie);

            URL reviewsRequestUrl = NetworkUtils
                    .buildUrlVideoReviews(NetworkUtils.REVIEWS, localMovie);
            try {
                String jsonMovieResponse = NetworkUtils
                        .getResponseFromHttpUrl(movieRequestUrl);
                String jsonReviewResponse = NetworkUtils
                        .getResponseFromHttpUrl(reviewsRequestUrl);

                String[] simpleJsonVideoData = OpenMovieJsonUtils
                        .getSimpleVideosLinkFromJson(jsonMovieResponse);

                Review[] simpleJsonReviewData = OpenMovieJsonUtils
                        .getSimpleReviewLinkFromJson(jsonReviewResponse);

                localMovie.setVideos(simpleJsonVideoData);
                localMovie.setReviews(simpleJsonReviewData);

                return localMovie;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Movie data) {
            if (data != null && data.getVideos() != null) {
                mShowMovieAdapter.setMovieData(data.getVideos());
            }
            if(data != null && data.getReviews() != null) {
                mShowReviewAdapter.setReviewsData(data.getReviews());
            }
        }
    }
}
