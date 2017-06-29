package rodab.ciencias.unam.mx.android.popularmovies.data;

import android.net.Uri;
import android.provider.BaseColumns;

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
public final class MovieContract {

    /**
     * It's the url for the domain in the database app.
     * */
    public static final String CONTENT_AUTHORITY = "rodab.ciencias.unam.mx.android.popularmovies";

    /**
     * It's @CONTENT_AUTHORITY but add the reserved prefix for the local database.
     */
    public static final String uri = "content://" + CONTENT_AUTHORITY;

    /**
     * To get the conexion to de database, we use a Uri object parsered from @uri.
     */
    public static final Uri CONTENT_URI = Uri.parse(uri);

    // We don't want to create an instance for this class.
    private MovieContract() {}

    /**
     * Inner class that define just String for the columns for each row.
     */
    public static final class RowEntry implements BaseColumns {

        /**
         * This is the constant for the name movie in the db.
         */
        public static final String TABLE_NAME = "movie";

        /**
         * This is the constant for the (unique) id movie in the db.
         */
        public static final String COL_ID = "id";

        /**
         * This is the constant for the title movie in the db.
         */
        public static final String COL_TITLE = "title";

        /**
         * This is the constant for the url image from the movie in the db.
         */
        public static final String COL_URL_IMAGE = "urlImage";

        /**
         * This is the constant for the synopsis movie in the db.
         */
        public static final String COL_SYNOPSIS = "synopsis";

        /**
         * This is the constant for the ranking movie in the db.
         */
        public static final String COL_RANKING = "ranking";

        /**
         * This is the constant for the date movie in the db.
         */
        public static final String COL_DATE = "date";
    }

} // End of MovieContract.java