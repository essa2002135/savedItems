package org.redsoft.saveditems;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {



    ArrayList<String> resultList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SQLiteDatabase mydatabase = openOrCreateDatabase("Doctors",MODE_PRIVATE,null);
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS appointment(drname VARCHAR,draddress VARCHAR);");
        mydatabase.execSQL("INSERT INTO appointment VALUES('admin4','admin4');");

        Cursor resultSet = mydatabase.rawQuery("Select * from appointment",null);
       // resultSet.moveToFirst();



        while (resultSet.moveToNext())
        {
            String date = resultSet.getString(resultSet.getColumnIndex("drname"));

            try
            {

                resultList.add(date);
            }
            catch (Exception e) {
            }

        }


        for(int i = 0;i < resultList.size();i++)
        {
            Log.i("theis TheArray1",resultList.get(i));

        }


       setContentView(R.layout.activity_main);



        AlarmManager alarms = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);

        Receiver receiver = new Receiver();
        IntentFilter filter = new IntentFilter("ALARM_ACTION");
        registerReceiver(receiver, filter);

        Intent intent = new Intent("ALARM_ACTION");
        intent.putExtra("param", "My scheduled action");
        PendingIntent operation = PendingIntent.getBroadcast(this, 0, intent, 0);
        // I choose 3s after the launch of my application
        alarms.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+3000, operation) ;
    }
}
