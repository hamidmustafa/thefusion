package thefusion.thefusion;


import java.sql.SQLException;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;


public class ContentProviderClassForEnergyLevels  extends ContentProvider {
    public static final String PROVIDER_NAME_EnergyLevels = "thefusion.thefusion.energylevels";
    /*  Every provider will be identified by its URI*/
    public static final Uri CONTENT_URI_EnergyLevels = Uri.parse("content://" + PROVIDER_NAME_EnergyLevels + "/energylevels");
    // Constant to indentify the requested operation
    private static final int LOCATIONS_EnergyLevels= 1;
    private static final UriMatcher uriMatcherdata;

    static {

        uriMatcherdata = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcherdata.addURI(PROVIDER_NAME_EnergyLevels, "energylevels", LOCATIONS_EnergyLevels);
    }

    public LocationsDB mDBdata;

    @Override
    public boolean onCreate() {

        mDBdata = new LocationsDB(getContext()) {
            @Override
            public void onCreate(SQLiteDatabase dbbdata) {
                super.onCreate(dbbdata);
            }
        };

        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        if (uriMatcherdata.match(uri) == LOCATIONS_EnergyLevels) {
            return mDBdata.getAllLocationsEnergyLevels();
        }
        return null;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long rowID5_data = mDBdata.insertEnergyLevels(values);
        Uri _uridata = null;
        if (rowID5_data > 0) {
            _uridata = ContentUris.withAppendedId(CONTENT_URI_EnergyLevels, rowID5_data);
        } else {
            try {
                throw new SQLException("Failed to insert:" + uri);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        return _uridata;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int cnt4 = 0;
        int cnt5=0;
        cnt4 = mDBdata.delEnergyLevel(uri,selection,selectionArgs);
        return cnt4;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;


    }
}