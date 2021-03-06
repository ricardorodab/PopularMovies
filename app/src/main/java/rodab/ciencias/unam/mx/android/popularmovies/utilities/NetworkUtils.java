package rodab.ciencias.unam.mx.android.popularmovies.utilities;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

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
public class NetworkUtils {

    public static final String URL_IMAGE = "http://image.tmdb.org/t/p/w185";
    private static final String URL_MOVIE = "http://api.themoviedb.org/3/movie";
    private static final String URL_VIDEOS = "/videos?api_key=";
    private static final String URL_REVIEWS = "/reviews?api_key=";
    private static final String FORMAT_PARAM = "mode";
    private static final String format = "json";
    //private static final String KEY = context.getString(R.string.THE_MOVIE_DB_API_TOKEN);

    public static final boolean VIDEOS = true;
    public static final boolean REVIEWS = false;

    public static URL buildUrl(boolean popular, String KEY) {
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

    public static URL buildUrlVideoReviews(boolean videos, Movie movie, String KEY) {
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
