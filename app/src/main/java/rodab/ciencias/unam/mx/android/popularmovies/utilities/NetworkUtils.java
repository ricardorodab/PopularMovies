package rodab.ciencias.unam.mx.android.popularmovies.utilities;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by ricardo_rodab on 27/06/17.
 */

public class NetworkUtils {

    public static final String URL_IMAGE = "http://image.tmdb.org/t/p/w185";
    private static final String URL_MOVIE = "http://api.themoviedb.org/3/movie";
    private static final String URL_VIDEOS = "/videos?api_key=";
    private static final String URL_REVIEWS = "/reviews?api_key=";
    private static final String FORMAT_PARAM = "mode";
    private static final String format = "json";
    private static final String KEY = "";

    public static final boolean VIDEOS = true;
    public static final boolean REVIEWS = false;

    public static URL buildUrl(boolean popular) {
        String extension = popular ? "/popular?api_key=" + KEY : "/top_rated?api_key=" + KEY;
        Uri buildUri = Uri.parse(URL_MOVIE + extension).buildUpon()
                .appendQueryParameter(FORMAT_PARAM, format).build();
        URL url = null;
        try {
            url = new URL(buildUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static URL buildUrlVideoReviews(boolean videos, Movie movie) {
        String urlFinal = URL_MOVIE + "/" + movie.getId();
        urlFinal = videos ? urlFinal.concat(URL_VIDEOS + KEY) :
                            urlFinal.concat(URL_REVIEWS + KEY);
        Uri buildUri = Uri.parse(urlFinal).buildUpon()
                .appendQueryParameter(FORMAT_PARAM, format).build();
        URL url = null;
        try {
            url = new URL(buildUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

}
