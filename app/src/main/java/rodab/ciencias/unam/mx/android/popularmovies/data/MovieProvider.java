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
 * @author Jose Ricardo Rodriguez-Abreu
 * @version 1.0
 * @since Jun 26 2017.
 * <p>
 *     Privider databases services comunication for the app.
 * </p>
 *
 * <p>
 *     Querys, inserts, deletes and other CRUD's operations.
 * </p>
 */
public class MovieProvider extends ContentProvider {

    // We need this in our object to help us (da'h) on the create operation.
    private MovieOpenHelper movdbh;

    /**
     * Default constructor.
     */
    public MovieProvider() {}

    /**
     * In onCreate, we initialize our content provider on startup. This method is called for all
     * registered content providers on the application main thread at application launch time.
     * It must not perform lengthy operations, or application startup will be delayed.
     *
     * Nontrivial initialization (such as opening, upgrading, and scanning
     * databases) should be deferred until the content provider is used (via {@link #query},
     * {@link #bulkInsert(Uri, ContentValues[])}, etc).
     *
     * Deferred initialization keeps application startup fast, avoids unnecessary work if the
     * provider turns out not to be needed, and stops database errors (such as a full disk) from
     * halting application launch.
     *
     * @return true if the provider was successfully loaded, false otherwise
     */
    @Override
    public boolean onCreate() {
        movdbh = new MovieOpenHelper(getContext());
        return true;
    }

    /**
     * Handles query requests from clients.
     *
     * @param uri           The URI to query
     * @param projection    The list of columns to put into the cursor. If null, all columns are
     *                      included.
     * @param selection     A selection criteria to apply when filtering rows. If null, then all
     *                      rows are included.
     * @param selectionArgs You may include ?s in selection, which will be replaced by
     *                      the values from selectionArgs, in order that they appear in the
     *                      selection.
     * @param sortOrder     How the rows in the cursor should be sorted.
     * @return A Cursor containing the results of the query. In our implementation,
     */
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase db = movdbh.getReadableDatabase();
        Cursor cursor = db.query(MovieContract.RowEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    /**
     * Just null.
     *
     * @param uri the URI to query.
     * @return nothing in Sunshine, but normally a MIME type string, or null if there is no type.
     */
    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    /**
     * We neew to add a movie in the db to get its information later
     * when the user checks their favorite movies.
     *
     * @param uri    The URI of the insertion request. This must not be null.
     * @param values A set of column_name/value pairs to add to the database.
     *               This must not be null
     * @return nothing in Sunshine, but normally the URI for the newly inserted item.
     */
    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        SQLiteDatabase db = movdbh.getWritableDatabase();
        long regId = db.insert(MovieContract.RowEntry.TABLE_NAME, null, values);
        return ContentUris.withAppendedId(MovieContract.CONTENT_URI, regId);
    }

    /**
     * Deletes data at a given URI with optional arguments for more fine tuned deletions.
     *
     * @param uri           The full URI to query
     * @param selection     An optional restriction to apply to rows when deleting.
     * @param selectionArgs Used in conjunction with the selection statement
     * @return The number of rows deleted
     */
    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int numRowsDeleted;
        if (null == selection) selection = "1";
        SQLiteDatabase db = movdbh.getWritableDatabase();
        numRowsDeleted = db.delete(MovieContract.RowEntry.TABLE_NAME, selection, selectionArgs);
        if (numRowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return numRowsDeleted;
    }

    /**
     * Just cero.
     * @param uri The full URI to query
     * @param values A set of column_name/value pairs to add to the database.
     *               This must not be null
     * @param selection An optional restriction to apply to rows when deleting.
     * @param selectionArgs Used in conjunction with the selection statement
     * @return The number of rows updated
     */
    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
} //End of MovieProvider.java