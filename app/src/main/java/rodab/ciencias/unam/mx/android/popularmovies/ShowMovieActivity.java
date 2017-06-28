package rodab.ciencias.unam.mx.android.popularmovies;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.net.URL;

import utilities.Movie;
import utilities.NetworkUtils;
import utilities.OpenMovieJsonUtils;

public class ShowMovieActivity extends AppCompatActivity
                implements ShowMovieAdapter.ShowMovieAdapterOnClickHandler {

    private Movie localMovie;
    private TextView title;
    private TextView synopsis;
    private TextView date;
    private ImageView url;
    private TextView rating;
    private ShowMovieAdapter mShowMovieAdapter;
    private RecyclerView mShowRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_movie);

        mShowRecyclerView = (RecyclerView) findViewById(R.id.rv_videos);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mShowRecyclerView.setLayoutManager(layoutManager);

        mShowMovieAdapter = new ShowMovieAdapter(this);
        mShowRecyclerView.setAdapter(mShowMovieAdapter);

        //mShowRecyclerView.setHasFixedSize(true);
        title = (TextView) findViewById(R.id.tv_title);
        synopsis = (TextView) findViewById(R.id.tv_synopsis);
        date = (TextView) findViewById(R.id.tv_date);
        rating = (TextView) findViewById(R.id.tv_rating);
        url = (ImageView) findViewById(R.id.imageView);

        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra(Intent.EXTRA_TEXT)) {
                localMovie = Movie.getMovieFromParserText(intent.getStringExtra(Intent.EXTRA_TEXT));

                if (localMovie != null) {
                    title.setText("Title: " + localMovie.getTitle());
                    synopsis.setText("Synopsis: " + localMovie.getSynopsis());
                    date.setText("Release date: " + localMovie.getDate());
                    rating.setText("Rating: " + localMovie.getRating() + "/10");
                    Picasso.with(this).load(NetworkUtils.URL_IMAGE + localMovie.getUrlImage()).into(url);
                }
            }
        }
        loadTrailerVideos(localMovie);
    }

    private void loadTrailerVideos(Movie localMovie) {
        new FetchVideosTask().execute(localMovie);
    }

    @Override
    public void onClick(String str) {
        Intent watchVideo = new Intent(Intent.ACTION_VIEW);
        Uri build = Uri.parse(str).buildUpon().build();
        watchVideo.setData(build);
        if(watchVideo.resolveActivity(getPackageManager()) != null)
            startActivity(watchVideo);
    }

    public class FetchVideosTask extends AsyncTask<Movie, Void, String[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected String[] doInBackground(Movie... params) {
            if (params.length == 0) {
                return null;
            }

            Movie localMovie = params[0];
            URL weatherRequestUrl = NetworkUtils
                    .buildUrlVideoReviews(NetworkUtils.VIDEOS, localMovie);
            try {
                String jsonMovieResponse = NetworkUtils
                        .getResponseFromHttpUrl(weatherRequestUrl);

                String[] simpleJsonWeatherData = OpenMovieJsonUtils
                        .getSimpleVideosLinkFromJson(jsonMovieResponse);

                return simpleJsonWeatherData;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String[] videoLinks) {
            if (videoLinks != null) {
                mShowMovieAdapter.setMovieData(videoLinks);
            }
        }
    }
}
