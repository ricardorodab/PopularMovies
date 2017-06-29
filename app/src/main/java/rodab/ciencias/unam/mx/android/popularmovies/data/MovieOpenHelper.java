package rodab.ciencias.unam.mx.android.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ricardo_rodab on 28/06/17.
 */

public class MovieOpenHelper extends SQLiteOpenHelper {

    public static final String NAME_DB = "movies.db";
    private static final int DATABASE_VERSION = 1;
    private static final String MOVIE_TABLE_NAME = "movie";

    private static final String MOVIE_TABLE_CREATE =
                    "CREATE TABLE " + MOVIE_TABLE_NAME + " (" +
                     MovieContract.RowEntry.COL_ID + " INTEGER PRIMARY KEY, " +
                     MovieContract.RowEntry.COL_TITLE + " TEXT, " +
                     MovieContract.RowEntry.COL_URL_IMAGE + " TEXT, " +
                     MovieContract.RowEntry.COL_SYNOPSIS + " TEXT," +
                     MovieContract.RowEntry.COL_RANKING  + " FLOAT, " +
                     MovieContract.RowEntry.COL_DATE + " TEXT );";


    public MovieOpenHelper(Context context) {
        super(context, NAME_DB, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MOVIE_TABLE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + NAME_DB);
        onCreate(db);
    }
}
