package com.gmail.task04_database.contentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.gmail.task04_database.database.AppDatabase;
import com.gmail.task04_database.database.Contact;
import com.gmail.task04_database.database.ContactDAO;

public class PhoneBookContentProvider extends ContentProvider {

    private static String CONTACT_MINE_TYPE = "object/contact";

    private static final String AUTHORITIES = "com.gmail.task04_database.contentprovider";

    private static UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITIES, "contact", 1);
    }

    private AppDatabase database;

    @Override
    public boolean onCreate() {
        database = new AppDatabase() {
            @Override
            public ContactDAO contactDao() {
                return null;
            }

            @NonNull
            @Override
            protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
                return null;
            }

            @NonNull
            @Override
            protected InvalidationTracker createInvalidationTracker() {
                return null;
            }

            @Override
            public void clearAllTables() {

            }
        };
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor = null;
        if (uriMatcher.match(uri) == 1) {
            cursor = database.contactDao().getCursorAll();
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        if (uriMatcher.match(uri) == 1) {
            return CONTACT_MINE_TYPE;
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        if (uriMatcher.match(uri) == 1) {
            long id = database.contactDao().insertProvider(Contact.fromContentValues(values));
            if (id != -1) {
                return Uri.withAppendedPath(uri, String.valueOf(id));
            }
        }
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        if (uriMatcher.match(uri) == 1) {
            final int count = database.contactDao().deleteById(String.valueOf(ContentUris.parseId(uri)));
            getContext().getContentResolver().notifyChange(uri, null);
            return count;
        }
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        if (uriMatcher.match(uri) == 1) {
            return database.contactDao().updateProvider(Contact.fromContentValues(values));
        }
        return 0;
    }
}
