package rodab.ciencias.unam.mx.android.popularmovies.utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;

/**
 * @author Jose Ricardo Rodriguez-Abreu
 * @version 1.0
 * @since Jun 26 2017.
 * <p>
 *     A parser Json data.
 * </p>
 *
 * <p>
 *     All the data of the apy moviedb is in Json so it was fundamental to parser the info.
 * </p>
 */
public class OpenMovieJsonUtils {

    /**
     * A URL to youtube videos prefix.
     */
    private static final String URL_YOUTUBE = "https://www.youtube.com/watch?v=";

    /**
     * This method parses JSON from a web response and returns an array of Strings
     * describing the weather over various days from the forecast.
     *
     * @param jsonStr JSON response from server
     *
     * @return Array of Strings describing videos of youtube.
     *
     * @throws JSONException If JSON data cannot be properly parsed
     */
    public static String[] getSimpleVideosLinkFromJson(String jsonStr)
            throws JSONException {
        final String LIST = "results";

        final String OWM_MESSAGE_CODE = "cod";

        String[] parsedMovieData = null;

        JSONObject movieJson = new JSONObject(jsonStr);

        /* Is there an error? */
        if (movieJson.has(OWM_MESSAGE_CODE)) {
            int errorCode = movieJson.getInt(OWM_MESSAGE_CODE);

            switch (errorCode) {
                case HttpURLConnection.HTTP_OK:
                    break;
                case HttpURLConnection.HTTP_NOT_FOUND:
                    /* Location invalid */
                    return null;
                default:
                    /* Server probably down */
                    return null;
            }
        }

        JSONArray movieArray = movieJson.getJSONArray(LIST);

        parsedMovieData = new String[movieArray.length()];

        for (int i = 0; i < movieArray.length(); i++) {
            String keyVideo;

            /* Get the JSON object representing the movie */
            JSONObject movie = movieArray.getJSONObject(i);
            keyVideo = movie.getString("key");
            parsedMovieData[i] = URL_YOUTUBE + keyVideo;
        }

        return parsedMovieData;
    }

    /**
     * This method parses JSON from a web response and returns an array of Strings
     * describing the weather over various days from the forecast.
     *
     * @param jsonStr JSON response from server
     *
     * @return Array of Movie with several data information of everyone.
     *
     * @throws JSONException If JSON data cannot be properly parsed
     */
    public static Movie[] getSimpleMoviesFromJson(String jsonStr)
            throws JSONException {
        final String LIST = "results";

        final String OWM_MESSAGE_CODE = "cod";

        Movie[] parsedMovieData = null;

        JSONObject movieJson = new JSONObject(jsonStr);

        /* Is there an error? */
        if (movieJson.has(OWM_MESSAGE_CODE)) {
            int errorCode = movieJson.getInt(OWM_MESSAGE_CODE);

            switch (errorCode) {
                case HttpURLConnection.HTTP_OK:
                    break;
                case HttpURLConnection.HTTP_NOT_FOUND:
                    /* Location invalid */
                    return null;
                default:
                    /* Server probably down */
                    return null;
            }
        }

        JSONArray movieArray = movieJson.getJSONArray(LIST);

        parsedMovieData = new Movie[movieArray.length()];

        for (int i = 0; i < movieArray.length(); i++) {
            String title;
            String urlImage;
            String synopsis;
            double rating;
            String date;
            int id;

            /* Get the JSON object representing the movie */
            JSONObject movie = movieArray.getJSONObject(i);
            title = movie.getString("title");
            urlImage = movie.getString("poster_path");
            synopsis = movie.getString("overview");
            rating = movie.getDouble("vote_average");
            id = movie.getInt("id");
            date = movie.getString("release_date");
            parsedMovieData[i] = new Movie(id,title,urlImage,synopsis,rating,date);
        }

        return parsedMovieData;
    }

    /**
     * This method parses JSON from a web response and returns an array of Strings
     * describing the weather over various days from the forecast.
     *
     * @param jsonStr JSON response from server
     *
     * @return Array of Review of a movie.
     *
     * @throws JSONException If JSON data cannot be properly parsed
     */
    public static Review[] getSimpleReviewLinkFromJson(String jsonStr) throws JSONException {
        final String LIST = "results";

        final String OWM_MESSAGE_CODE = "cod";

        Review[] parsedMovieData = null;

        JSONObject movieJson = new JSONObject(jsonStr);

        /* Is there an error? */
        if (movieJson.has(OWM_MESSAGE_CODE)) {
            int errorCode = movieJson.getInt(OWM_MESSAGE_CODE);

            switch (errorCode) {
                case HttpURLConnection.HTTP_OK:
                    break;
                case HttpURLConnection.HTTP_NOT_FOUND:
                    /* Location invalid */
                    return null;
                default:
                    /* Server probably down */
                    return null;
            }
        }

        JSONArray movieArray = movieJson.getJSONArray(LIST);

        parsedMovieData = new Review[movieArray.length()];

        for (int i = 0; i < movieArray.length(); i++) {
            String author;
            String content;

            /* Get the JSON object representing the movie */
            JSONObject movie = movieArray.getJSONObject(i);
            author = movie.getString("author");
            content = movie.getString("content");
            parsedMovieData[i] = new Review(author,content);
        }
        return parsedMovieData;
    }
} //End of OpenMovieJsonUtils.java