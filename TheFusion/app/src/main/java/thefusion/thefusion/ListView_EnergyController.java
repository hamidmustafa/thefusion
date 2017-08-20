package thefusion.thefusion;


import android.app.ActionBar;
import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListView_EnergyController extends AppCompatActivity {
    private ListView listView;
    private  ArrayAdapter<String> namesAdpater;

    public static ContentProviderClassForEnergyLevels cpcforeng;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view__energy_controller);
        listView=(ListView)findViewById(R.id.id_listview_EnergyController);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        LocationsDB.getCursorDataForEnergyLevels();
        LocationsDB.LoopLocsEnergyLevels();
        final int size_battery1 = LocationsDB.BatteryArrayList_EnergyLevels.size();
        final ArrayList<String> arrayListstring=new ArrayList<String>();
        final ArrayList<String> arraListStringdummy=new ArrayList<String>();
        for(int i=0; i<size_battery1; i++)
        {
            String dummy=String.valueOf(LocationsDB.BatteryArrayList_EnergyLevels.get(i));
            String dummy2=String.valueOf(LocationsDB.BrightnessArayList_EnergyLevels.get(i));
            arrayListstring.add("Battery:"+" "+dummy+"%"+" "+"--"+" "+"Brightness:"+" "+dummy2+"%");
            arraListStringdummy.add(dummy);
        }
        namesAdpater=new ArrayAdapter<String>(this,R.layout.simplerow,R.id.rowTextView,arrayListstring);
        listView.setAdapter(namesAdpater);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                LocationsDB.getCursorDataForEnergyLevels();
                LocationsDB.LoopLocsEnergyLevels();
                namesAdpater.remove(arrayListstring.get(position));
                // String[] selecteditem={listView.getItemAtPosition(position).toString()};
                String dummystring=arraListStringdummy.get(position);
                getContentResolver().delete(cpcforeng.CONTENT_URI_EnergyLevels, LocationsDB.FIELD_BATTERY_LEVEL + "=?", new String[]{dummystring});
                listView.setAdapter(namesAdpater);
                if(listView.getCount()==0)
                {
                    Intent intent = new Intent(ListView_EnergyController.this, MainActivity_TheFusion.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "No Battery and Brightness are set now!", Toast.LENGTH_SHORT).show();
                }
                return false;
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
        getMenuInflater().inflate(R.menu.listview_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatic//ally handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if(id==R.id.action_delete_levels)
        {
            getContentResolver().delete(cpcforeng.CONTENT_URI_EnergyLevels, null, null);
            Toast.makeText(getApplicationContext(), "All levels data deleted", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(ListView_EnergyController.this, MainActivity_TheFusion.class);
            startActivity(intent);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}

