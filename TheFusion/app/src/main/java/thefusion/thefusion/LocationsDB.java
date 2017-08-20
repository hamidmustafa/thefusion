package thefusion.thefusion;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import java.util.ArrayList;

public abstract class LocationsDB extends SQLiteOpenHelper
{
    private static String db_name="LocationsDB";
    private static int version=1;
    public static String DATABASE_TABLE_EnergyLevel="energylevels";
    public static String DATABASE_TABLE_BOOLEAN_ENERGYCONTROLLER="booleanenergycontroller";


    public static final String ROW_ID_EnergyLevels="_id_energylevels";
    public static final String FIELD_BATTERY_LEVEL="batterylevel";
    public static final String FIELD_BRIGHTNESS_LEVEL="brightnesslevel";



    public static final String FIELD_BOOLEAN_ENERGYCONTROLLER_ID="idenergycontrollerboolean";
    public static final String FIELD_BOOLEAN_ENERGYCONTROLLER="energycontrollerboolean";

    private SQLiteDatabase DB;
    public static SQLiteDatabase DBB;

    public static  Cursor MyCursor_EnergLevels;
    public static  Cursor MyCursor_BooleanEnergyController;

    public static int dummybatterylevel;
    public static int dummybrightnesslevel;
    public static  int dummyenergycontroller_id;

    public static String  dummy_boolean_energycontroller;

    public static boolean  dummy_boolean_energycontroller1;

    public static ArrayList<Integer> BatteryArrayList_EnergyLevels;
    public static ArrayList<Integer> BrightnessArayList_EnergyLevels;
    public static ArrayList<Integer> EnergyControllerArrayList_ID;

    public static ArrayList<Boolean> ArrayList_Boolean_EnergyController;

    public LocationsDB(Context context) {
        super(context, db_name, null, version);
        this.DB=getWritableDatabase();
        this.DBB=getReadableDatabase();
    }

    public void onCreate(SQLiteDatabase db)
    {

        String Create_SQL_EnergyLevels=" CREATE TABLE " + LocationsDB.DATABASE_TABLE_EnergyLevel + "(" + LocationsDB.ROW_ID_EnergyLevels + " INTEGER PRIMARY KEY AUTOINCREMENT, " + LocationsDB.FIELD_BATTERY_LEVEL
                + " INTEGER," + LocationsDB.FIELD_BRIGHTNESS_LEVEL + " INTEGER " + ")";
        String Create_SQL_Boolean_EnergyController=" CREATE TABLE " + LocationsDB.DATABASE_TABLE_BOOLEAN_ENERGYCONTROLLER+ "(" + LocationsDB.FIELD_BOOLEAN_ENERGYCONTROLLER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                LocationsDB.FIELD_BOOLEAN_ENERGYCONTROLLER + " TEXT  "  + ")";

        db.execSQL(Create_SQL_EnergyLevels);
        db.execSQL(Create_SQL_Boolean_EnergyController);


    }


    public long insertEnergyLevels(ContentValues contentValues)
    {
        long rowID=DB.insert(DATABASE_TABLE_EnergyLevel, null, contentValues);
        return rowID;
    }


    public long insertBooleanEnergyController(ContentValues contentValues)
    {
        long rowID=DB.insert(DATABASE_TABLE_BOOLEAN_ENERGYCONTROLLER, null, contentValues);
        return rowID;
    }

    public  int delEnergyLevel(Uri uri, String selection, String[] selectionArgs)
    {

        int cnt=DB.delete(DATABASE_TABLE_EnergyLevel, selection, selectionArgs);
        return cnt;
    }


    public  int delBooleanEnergyController(Uri uri, String selection, String[] selectionArgs)
    {

        int cnt=DB.delete(DATABASE_TABLE_BOOLEAN_ENERGYCONTROLLER, selection, selectionArgs);
        return cnt;
    }


    public Cursor getAllLocationsEnergyLevels()
    { return  DB.query(DATABASE_TABLE_EnergyLevel, new String[]{ROW_ID_EnergyLevels, FIELD_BATTERY_LEVEL, FIELD_BRIGHTNESS_LEVEL}, null,null,null,null, null);

    }


    public Cursor getAllBooleanEnergyController()
    { return  DB.query(DATABASE_TABLE_BOOLEAN_ENERGYCONTROLLER, new String[]{FIELD_BOOLEAN_ENERGYCONTROLLER_ID, FIELD_BOOLEAN_ENERGYCONTROLLER}, null,null,null,null, null);

    }

    public static Cursor getCursorDataForEnergyLevels() {

        String[] levelcols = {LocationsDB.ROW_ID_EnergyLevels,LocationsDB.FIELD_BATTERY_LEVEL, LocationsDB.FIELD_BRIGHTNESS_LEVEL};
        MyCursor_EnergLevels = DBB.query(LocationsDB.DATABASE_TABLE_EnergyLevel, levelcols,null, null, null, null, null);
        if (MyCursor_EnergLevels!=null)
            MyCursor_EnergLevels.moveToFirst();
        return MyCursor_EnergLevels;
    }

    public static Cursor getCursorDataForBooleanEnergyController() {

        String[] levelcols = {LocationsDB.FIELD_BOOLEAN_ENERGYCONTROLLER_ID,LocationsDB.FIELD_BOOLEAN_ENERGYCONTROLLER};
        MyCursor_BooleanEnergyController = DBB.query(LocationsDB.DATABASE_TABLE_BOOLEAN_ENERGYCONTROLLER, levelcols,null, null, null, null, null);
        if (MyCursor_BooleanEnergyController!=null)
            MyCursor_BooleanEnergyController.moveToFirst();
        return MyCursor_BooleanEnergyController;
    }

    public static void LoopLocsEnergyLevels()
    {
        BatteryArrayList_EnergyLevels= new ArrayList<Integer>();
        BrightnessArayList_EnergyLevels=new ArrayList<Integer>();
        EnergyControllerArrayList_ID=new ArrayList<Integer>();
        for(MyCursor_EnergLevels.moveToFirst(); !MyCursor_EnergLevels.isAfterLast(); MyCursor_EnergLevels.moveToNext()) {
            dummybatterylevel = MyCursor_EnergLevels.getInt(MyCursor_EnergLevels.getColumnIndex(LocationsDB.FIELD_BATTERY_LEVEL));
            dummybrightnesslevel = MyCursor_EnergLevels.getInt(MyCursor_EnergLevels.getColumnIndex(LocationsDB.FIELD_BRIGHTNESS_LEVEL));
            dummyenergycontroller_id=MyCursor_EnergLevels.getInt(MyCursor_EnergLevels.getColumnIndex(LocationsDB.ROW_ID_EnergyLevels));

            BatteryArrayList_EnergyLevels.add(dummybatterylevel);
            BrightnessArayList_EnergyLevels.add(dummybrightnesslevel);
            EnergyControllerArrayList_ID.add(dummyenergycontroller_id);
        }
        MyCursor_EnergLevels.close();
    }

    public static void LoopLocsBooleanEnergyController()
    {
        ArrayList_Boolean_EnergyController= new ArrayList<Boolean>();

        for(MyCursor_BooleanEnergyController.moveToFirst(); !MyCursor_BooleanEnergyController.isAfterLast(); MyCursor_BooleanEnergyController.moveToNext()) {
            dummy_boolean_energycontroller = MyCursor_BooleanEnergyController.getString(MyCursor_BooleanEnergyController.getColumnIndex(LocationsDB.FIELD_BOOLEAN_ENERGYCONTROLLER));
            dummy_boolean_energycontroller1=Boolean.parseBoolean(dummy_boolean_energycontroller);

            ArrayList_Boolean_EnergyController.add(dummy_boolean_energycontroller1);

        }
        MyCursor_BooleanEnergyController.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        //db.execSQL("DROP TABLE IF EXISTS locations");
        // onCreate(db);
    }
}
