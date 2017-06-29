package rodab.ciencias.unam.mx.android.popularmovies.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by ricardo_rodab on 28/06/17.
 */

public class MovieProvider extends ContentProvider {

    public static final class MovieDb implements BaseColumns {
    }

    private MovieOpenHelper movdbh;
    private static final String TABLE = "movie";

    public MovieProvider() {}

    @Override
    public boolean onCreate() {
        movdbh = new MovieOpenHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase db = movdbh.getReadableDatabase();
        Cursor cursor = db.query(TABLE, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        SQLiteDatabase db = movdbh.getWritableDatabase();
        long regId = db.insert(TABLE, null, values);
        return ContentUris.withAppendedId(MovieContract.CONTENT_URI, regId);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int numRowsDeleted;
        if (null == selection) selection = "1";
        SQLiteDatabase db = movdbh.getWritableDatabase();
        numRowsDeleted = db.delete(TABLE, selection, selectionArgs);
        if (numRowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return numRowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
