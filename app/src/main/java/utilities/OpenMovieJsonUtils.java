package utilities;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;

/**
 * Created by ricardo_rodab on 27/06/17.
 */

public class OpenMovieJsonUtils {

    private static final String URL_YOUTUBE = "https://www.youtube.com/watch?v=";

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
     * <p/>
     * Later on, we'll be parsing the JSON into structured data within the
     * getFullWeatherDataFromJson function, leveraging the data we have stored in the JSON. For
     * now, we just convert the JSON into human-readable strings.
     *
     * @param jsonStr JSON response from server
     *
     * @return Array of Strings describing weather data
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
}
