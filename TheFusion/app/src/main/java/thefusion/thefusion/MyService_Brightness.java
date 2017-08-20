package thefusion.thefusion;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.IBinder;
import android.provider.Settings;
import android.widget.Toast;



public class MyService_Brightness extends Service {
    ScreenBroadcastReceiver1 m_receiver1;

    public static  Activators_Activity activatorsactivity;
    public static ContentResolver cResolver2; //Content resolver used as a handle to the system's settings
    public static IntentFilter filterr;

    @Override
    public void onCreate() {

        m_receiver1 = new ScreenBroadcastReceiver1();
        filterr = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        filterr.addAction(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(m_receiver1, filterr);
        cResolver2 = getContentResolver();






    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        registerReceiver(m_receiver1,filterr);

        // start();
       // Toast.makeText(getBaseContext(), "Brightness Service activated", Toast.LENGTH_SHORT).show();




        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        //     Toast.makeText(this, "Brightness Service stopped", Toast.LENGTH_LONG).show();



    }




    private class ScreenBroadcastReceiver1 extends BroadcastReceiver {


        @Override
        public void onReceive(Context context, Intent intent ) {


            if (intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED)) {


                int size_brightness2;
                int size_battery2;


                LocationsDB.getCursorDataForBooleanEnergyController();
                LocationsDB.LoopLocsBooleanEnergyController();
                LocationsDB.getCursorDataForEnergyLevels();
                LocationsDB.LoopLocsEnergyLevels();
                if (LocationsDB.ArrayList_Boolean_EnergyController.size() != 0) {

                    if (LocationsDB.ArrayList_Boolean_EnergyController.get(0) == true) {
                        IntentFilter batterylevelfilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
                        registerReceiver(m_receiver1, batterylevelfilter);
                        context.unregisterReceiver(this);
                        int currentLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                        int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
                        int level = -1;
                        if (currentLevel >= 0 && scale > 0) {
                            level = (currentLevel * 100) / scale;
                        }
                        size_brightness2 = LocationsDB.BrightnessArayList_EnergyLevels.size();
                        size_battery2 = LocationsDB.BatteryArrayList_EnergyLevels.size();
                        if (size_battery2 != 0 || size_brightness2 != 0) {
                            for (int a = 0; a < size_battery2; a++)
                                if (level == LocationsDB.BatteryArrayList_EnergyLevels.get(a)) {
                                    int brightnessget = LocationsDB.BrightnessArayList_EnergyLevels.get(a);
                                    Settings.System.putInt(cResolver2, Settings.System.SCREEN_BRIGHTNESS, brightnessget);

                                }
                        }
                        IntentFilter batterylevelfilter_1 = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
                        registerReceiver(m_receiver1, batterylevelfilter_1);
                    }

                }


            }


        }
    }


    @Override
    public IBinder onBind(Intent intent)
    {
        // TODO Auto-generated method stub
        return null;
    }


}
