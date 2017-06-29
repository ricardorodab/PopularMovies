package rodab.ciencias.unam.mx.android.popularmovies.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by ricardo_rodab on 29/06/17.
 */

public final class MovieContract {
    // REVUSAR;
    public static final String CONTENT_AUTHORITY = "rodab.ciencias.unam.mx.android.popularmovies";
    public static final String uri = "content://" + CONTENT_AUTHORITY;
    public static final Uri CONTENT_URI = Uri.parse(uri);
    public static final String PATH_MOVIE = "movie";
    private MovieContract() {}

    public static final class RowEntry implements BaseColumns {

        public static final Uri CONTENT_URI = MovieContract.CONTENT_URI.buildUpon()
                .appendPath(PATH_MOVIE).build();
        public static final String TABLE_NAME = "movie";
        public static final String COL_ID = "id";
        public static final String COL_TITLE = "title";
        public static final String COL_URL_IMAGE = "urlImage";
        public static final String COL_SYNOPSIS = "synopsis";
        public static final String COL_RANKING = "ranking";
        public static final String COL_DATE = "date";

    }


}
