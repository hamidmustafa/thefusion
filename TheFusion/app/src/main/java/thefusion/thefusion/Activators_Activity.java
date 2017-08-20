package thefusion.thefusion;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import android.content.Intent;

public class Activators_Activity extends AppCompatActivity
{


    public static ContentResolver cResolver1; //Content resolver used as a handle to the system's settings
    public static Switch EnergyControllerSwtich;
    public  static  boolean CurrentValueEnergyController;
    public static boolean RetrievedValueEnergyController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activators_);



        EnergyControllerSwtich=(Switch)findViewById(R.id.switchEnergyController);




        LocationsDB.getCursorDataForBooleanEnergyController();
        LocationsDB.LoopLocsBooleanEnergyController();







        if(LocationsDB.ArrayList_Boolean_EnergyController.size()!=0) {

            RetrievedValueEnergyController = LocationsDB.ArrayList_Boolean_EnergyController.get(0);
            EnergyControllerSwtich.setChecked(RetrievedValueEnergyController);
        }
        else
        {
            ContentValues contentvalues7= new ContentValues();
            contentvalues7.put(LocationsDB.FIELD_BOOLEAN_ENERGYCONTROLLER, "false");
            getContentResolver().insert(ContentProviderClassForBooleanEnergyController.CONTENT_URI_BOOLEAN_ENERGYCONTROLLER, contentvalues7);
            EnergyControllerSwtich.setChecked(false);
        }







        EnergyControllerSwtich.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                CurrentValueEnergyController=EnergyControllerSwtich.isChecked();
                String ConvertedEnergyController=String.valueOf(CurrentValueEnergyController);
                getContentResolver().delete(ContentProviderClassForBooleanEnergyController.CONTENT_URI_BOOLEAN_ENERGYCONTROLLER, null, null);
                ContentValues contentValues=new ContentValues();
                contentValues.put(LocationsDB.FIELD_BOOLEAN_ENERGYCONTROLLER, ConvertedEnergyController);
                getContentResolver().insert(ContentProviderClassForBooleanEnergyController.CONTENT_URI_BOOLEAN_ENERGYCONTROLLER, contentValues);
            }
        });




    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.menu_activators_, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }













    @Override
    public void onStart()
    {


        super.onStart();
    }

    @Override
    public void onPause()
    {super.onPause();




    }

    @Override
    public void onResume()
    {
        super.onResume();



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();


    }
}


