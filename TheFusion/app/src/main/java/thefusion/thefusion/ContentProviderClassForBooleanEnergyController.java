package thefusion.thefusion;


import java.sql.SQLException;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
public class ContentProviderClassForBooleanEnergyController extends  ContentProvider

{
    public  static  final String PROVIDER_NAME_BOOLEAN_ENERGYCONTROLLER="thefusion.thefusion.booleanenergycontroller";
    /*  Every provider will be identified by its URI*/
    public static final Uri CONTENT_URI_BOOLEAN_ENERGYCONTROLLER = Uri.parse("content://" + PROVIDER_NAME_BOOLEAN_ENERGYCONTROLLER + "/booleanenergycontroller" );
    // Constant to indentify the requested operation
    private static  final int LOCATIONS_BOOLEAN_ENERGYCONTROLLER=1;
    private static final UriMatcher uriMatcherBooleanEnergyController;

    static {

        uriMatcherBooleanEnergyController= new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcherBooleanEnergyController.addURI(PROVIDER_NAME_BOOLEAN_ENERGYCONTROLLER,"booleanenergycontroller",LOCATIONS_BOOLEAN_ENERGYCONTROLLER);
    }
    public LocationsDB mDBdata;
    @Override
    public boolean onCreate()
    {

        mDBdata=new LocationsDB(getContext()) {
            @Override
            public void onCreate(SQLiteDatabase dbbdata) {
                super.onCreate(dbbdata);
            }
        };

        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        if(uriMatcherBooleanEnergyController.match(uri)==LOCATIONS_BOOLEAN_ENERGYCONTROLLER)
        {return mDBdata.getAllBooleanEnergyController();}
        return null;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long rowID5_data=mDBdata.insertBooleanEnergyController(values)    ;
        Uri _uridata=null;
        if (rowID5_data>0)
        {_uridata=ContentUris.withAppendedId(CONTENT_URI_BOOLEAN_ENERGYCONTROLLER, rowID5_data);}
        else {
            try
            {throw  new SQLException("Failed to insert:"+ uri)  ;}
            catch (SQLException e)
            {e.printStackTrace();}
        }


        return _uridata;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int cnt4=0;
        cnt4=mDBdata.delBooleanEnergyController(uri,selection,selectionArgs);
        return cnt4;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }


}
