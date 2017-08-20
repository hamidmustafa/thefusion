package thefusion.thefusion;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.content.ContentValues;

import android.content.Intent;
import android.content.IntentFilter;

import android.view.ViewConfiguration;
import android.widget.TextView;
import android.content.ContentResolver;

import android.provider.Settings;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.app.ActionBar;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.lang.reflect.Field;


public class MainActivity_TheFusion extends AppCompatActivity
{
   private AdView mAdView;


    public static SeekBar brightbar;
    public static int internlval = 1;
    public static int brightvalue;
    public static int size_brightness;
    public static int size_battery;

    private int brightness; //Variable to store brightness value
    public static ContentResolver cResolver; //Content resolver used as a handle to the system's settings
    public static Window window; //Window object, that will store a reference to the current window
    TextView txtPerc;
    TextView vl;

    public TextView currentbatteryleveltxtview;
    public static  IntentFilter batteryLevelFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity__the_fusion);
        startService(new Intent(getBaseContext(), MyService_Brightness.class));

       MobileAds.initialize(this, "ca-app-pub-0442674465012962~7931809659");
        mAdView = (AdView) findViewById(R.id.ad_view);


        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);


       // TextView textViewEnergy=(TextView)findViewById(R.id.energy);
        TextView textViewBattery=(TextView)findViewById(R.id.battery_level);

        textViewBattery.setGravity(Gravity.CENTER);

        //textViewEnergy.setGravity(Gravity.CENTER);

        Button save=(Button)findViewById(R.id.save);
        save.setGravity(Gravity.CENTER);
        Button dummylist= (Button)findViewById(R.id.btn_listview);
        dummylist.setGravity(Gravity.CENTER);

        dummylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocationsDB.getCursorDataForEnergyLevels();
                LocationsDB.LoopLocsEnergyLevels();
                int checksizeOFbatterylevels=LocationsDB.BatteryArrayList_EnergyLevels.size();
                int checksize1OFbrightnesslevels=LocationsDB.BrightnessArayList_EnergyLevels.size();
                if(checksizeOFbatterylevels==0 && checksize1OFbrightnesslevels==0)
                {
                    final AlertDialog.Builder alertDialog_ChooseImage=new AlertDialog.Builder(MainActivity_TheFusion.this);
                   // alertDialog_ChooseImage.setIcon(R.drawable.warning_blue_96px);
                    alertDialog_ChooseImage.setTitle("Alert");
                    alertDialog_ChooseImage.setMessage("No battery and brightness levels are currently stored. Add now");
                    alertDialog_ChooseImage.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    alertDialog_ChooseImage.show();
                }
                else
                {
                    Intent intent = new Intent(MainActivity_TheFusion.this, ListView_EnergyController.class);
                    startActivity(intent);}
            }
        });



        final NumberPicker numberPicker1 = (NumberPicker) findViewById(R.id.numberPicker);
        numberPicker1.setGravity(Gravity.CENTER);
        numberPicker1.setMaxValue(100);
        numberPicker1.setMinValue(1);
        numberPicker1.setWrapSelectorWheel(true);
        numberPicker1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                internlval = numberPicker1.getValue();

            }
        });
        brightbar = (SeekBar) findViewById(R.id.seekBar);

        txtPerc = (TextView) findViewById(R.id.txtPercentage);
        cResolver = getContentResolver();
        window = getWindow();
        brightbar.setMax(255); //Set the seekbar range between 0 and 255
        brightbar.setKeyProgressIncrement(1);//Set the seek bar progress to 1

        try
        {
            //Get the current system brightness
            brightness = Settings.System.getInt(cResolver, Settings.System.SCREEN_BRIGHTNESS);
        }
        catch (Settings.SettingNotFoundException e)
        {
            //Throw an error case it couldn't be retrieved
            Log.e("Error", "Cannot access system brightness");
            e.printStackTrace();
        }

        //Set the progress of the seek bar based on the system's brightness
        brightbar.setProgress(brightness);

        //Register OnSeekBarChangeListener, so it can actually change values
        brightbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                //Nothing handled here
            }

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //Set the minimal brightness level
                //if seek bar is 20 or any value below
                if (progress <= 20) {
                    //Set the brightness to 20
                    brightness = 20;
                } else //brightness is greater than 20
                {
                    //Set brightness variable based on the progress bar
                    brightness = progress;
                }
                //Calculate the brightness percentage
                float perc = (brightness / (float) 255) * 100;
                //Set the brightness percentage
                txtPerc.setText((int) perc + " %");

                brightvalue=(int)perc;
//                fetchbrightness.setText(String.valueOf(brightvalue));
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocationsDB.getCursorDataForEnergyLevels();
                LocationsDB.LoopLocsEnergyLevels();
                size_brightness = LocationsDB.BrightnessArayList_EnergyLevels.size();
                size_battery = LocationsDB.BatteryArrayList_EnergyLevels.size();
                int j;
                int extra=0;
                int counter=0;
                if (size_battery != 0)
                {
                    for ( j = 0; j < size_battery; j++)
                    {
                        extra = LocationsDB.BatteryArrayList_EnergyLevels.get(j);
                        if (internlval == extra)
                        {
                            final AlertDialog.Builder alertDialog_ChooseImage=new AlertDialog.Builder(MainActivity_TheFusion.this);
                            alertDialog_ChooseImage.setIcon(R.drawable.sadsmily_blue_96px);
                            alertDialog_ChooseImage.setTitle("Sorry");
                            alertDialog_ChooseImage.setMessage("This battery level is already set. Choose other");
                            alertDialog_ChooseImage.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                            alertDialog_ChooseImage.show();
                            break;
                        }
                        counter++;
                    }
                    if(counter==size_battery)
                    {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(LocationsDB.FIELD_BATTERY_LEVEL, internlval);
                        contentValues.put(LocationsDB.FIELD_BRIGHTNESS_LEVEL, brightvalue);
                        getContentResolver().insert(ContentProviderClassForEnergyLevels.CONTENT_URI_EnergyLevels, contentValues);
                        Toast.makeText(getBaseContext(), "Levels added", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {  ContentValues contentValues = new ContentValues();
                    contentValues.put(LocationsDB.FIELD_BATTERY_LEVEL, internlval);
                    contentValues.put(LocationsDB.FIELD_BRIGHTNESS_LEVEL, brightvalue);
                    getContentResolver().insert(ContentProviderClassForEnergyLevels.CONTENT_URI_EnergyLevels, contentValues);
                    Toast toast = Toast.makeText(getBaseContext(), "Levels added", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
        makeActionOverflowMenuShown();

    }
    private void makeActionOverflowMenuShown() {
        //devices with hardware menu button (e.g. Samsung Note) don't show action overflow menu
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception e) {
            Log.d("app", e.getLocalizedMessage());
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mainactivity_energycontroller_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_activator)
        {
            Intent intent=new Intent(MainActivity_TheFusion.this, Activators_Activity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPause()
    {
        IntentFilter intentfilterAtPause = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        // registerReceiver(broadcastReceiver, intentfilterAtPause);
        super.onPause();
    }

    @Override
    public void onResume()
    {

        super.onResume();
    }

    @Override
    public void onBackPressed()
    {

        // Intent intent=new Intent(EnergyController_Main.this, Profile_Handler_Menu.class);
        //startActivity(intent);


        super.onBackPressed();
    }
}
