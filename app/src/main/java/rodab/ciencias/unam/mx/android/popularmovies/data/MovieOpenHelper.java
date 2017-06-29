package rodab.ciencias.unam.mx.android.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author Jose Ricardo Rodriguez-Abreu
 * @version 1.0
 * @since Jun 26 2017.
 * <p>
 *     Class for managment for the DB in SQLite.
 * </p>
 *
 * <p>
 *     Create, modify and define the sruct for the database.
 * </p>
 */
public class MovieOpenHelper extends SQLiteOpenHelper {

    /**
     * This is a constant for the name of the database with db extension.
     */
    public static final String NAME_DB = "movies.db";

    /**
     * Because there isn't been a modification in the schema. the version is 1.
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * This is a constant for the name of the (for now) unique table.
     */
    private static final String MOVIE_TABLE_NAME = "movie";

    /**
     * This String is the schema of the database and use the constant defined in RowEntry class.
     */
    private static final String MOVIE_TABLE_CREATE =
                    "CREATE TABLE " + MOVIE_TABLE_NAME + " (" +
                     MovieContract.RowEntry.COL_ID + " INTEGER PRIMARY KEY, " +
                     MovieContract.RowEntry.COL_TITLE + " TEXT, " +
                     MovieContract.RowEntry.COL_URL_IMAGE + " TEXT, " +
                     MovieContract.RowEntry.COL_SYNOPSIS + " TEXT," +
                     MovieContract.RowEntry.COL_RANKING  + " FLOAT, " +
                     MovieContract.RowEntry.COL_DATE + " TEXT );";

    /**
     * Constructor that just call the super constructor in SQLiteOpenHelper.
     *
     * @param context Is the context in the application.
     */
    public MovieOpenHelper(Context context) {
        super(context, NAME_DB, null, DATABASE_VERSION);
    }

    /**
     * Called when the database is created for the first time. This is where the creation of
     * tables and the initial population of the tables should happen.
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MOVIE_TABLE_CREATE);

    }

    /**
     * This database is only a cache for online data, so its upgrade policy is simply to discard
     * the data and call through to onCreate to recreate the table.
     *
     * @param db Database that is being upgraded
     * @param oldVersion     The old database version
     * @param newVersion     The new database version
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + NAME_DB);
        onCreate(db);
    }
} //End of MovieOpenHelper