package rodab.ciencias.unam.mx.android.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import utilities.Movie;
import utilities.NetworkUtils;

public class ShowMovieActivity extends AppCompatActivity {

    private Movie localMovie;
    private TextView title;
    private TextView synopsis;
    private TextView date;
    private ImageView url;
    private TextView rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_movie);
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
    }
}
