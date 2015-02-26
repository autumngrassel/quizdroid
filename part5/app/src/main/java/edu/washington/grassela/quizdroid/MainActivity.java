package edu.washington.grassela.quizdroid;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    protected QuizApp app;
    private PendingIntent pendingIntent;
    private AlarmManager manager;
    private Intent alarmIntent;
    private SharedPreferences.OnSharedPreferenceChangeListener prefListener;
    private int time;
    static boolean isAirplaneEnabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        app = (QuizApp) getApplication();

        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        isAirplaneEnabled = Settings.System.getInt(getContentResolver(), Settings.Global.AIRPLANE_MODE_ON, 0) == 1;
        if (!isConnected && isAirplaneEnabled) {
            Log.i("TAG", "No connection currently");
            // To-do: send to prefs to change
            AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);

            dlgAlert.setMessage("This application requires an internet connection :( \n" +
                    "Would you like to turn Airplane Mode off so that you can use the application?");
            dlgAlert.setTitle("Airplane Mode On");
            dlgAlert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // go to Prefs
                }
            });
            dlgAlert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            dlgAlert.setCancelable(false);
            dlgAlert.create().show();
        } else if (!isConnected && !isAirplaneEnabled) {
            AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);

            dlgAlert.setMessage("This application requires an internet connection :( \n" +
                    "You can try again later once you have a connection!");
            dlgAlert.setTitle("No Connection");
            dlgAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            dlgAlert.setCancelable(false);
            dlgAlert.create().show();
        }


        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        String timeLapse = prefs.getString("time", "");
        try {
            time = Integer.parseInt(timeLapse);
        } catch (NumberFormatException nfe) {
            time = 5;
        }

        alarmIntent = new Intent(this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, alarmIntent, 0);
        manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        startAlarm();



        final List<Topic> topics = app.getRepo();
        String[] topicNames = new String[topics.size()];
        Integer[] images = new Integer[topics.size()];
        for (int i = 0; i < topics.size(); i++) {
            topicNames[i] = topics.get(i).getTopic() + "\n" + topics.get(i).getShortDesc();
            images[i] = topics.get(i).getImageId();
        }
        final ListView chooseTopic = (ListView) findViewById(R.id.topicList);

        CustomList adapter = new CustomList(MainActivity.this, topicNames, images);

        chooseTopic.setAdapter(adapter);

        chooseTopic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    Intent nextActivity = new Intent(MainActivity.this, QuizActivity.class);
                    nextActivity.putExtra("topic", topics.get(position));
                    startActivity(nextActivity);
                    finish();
                }
            }
        );


        prefListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
                if (key.equals("timeLapse")) {
                    String timeLapse = prefs.getString(key, "");
                    try {
                        time = Integer.parseInt(timeLapse);
                    } catch (NumberFormatException nfe) {
                        time = 5;
                    }
                    restartAlarm();
                } else if (key.equals("url")) {
                    restartAlarm();
                } else { // key.equals("airplane")
                    // set airplane mode to opposite
                }




            }
        };
        prefs.registerOnSharedPreferenceChangeListener(prefListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_preferences:
                openPrefs();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void startAlarm() {
        long interval = time * 60 * 1000;
        manager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);
    }

    public void cancelAlarm() {
        if (manager != null) {
            manager.cancel(pendingIntent);
        }
    }

    public void restartAlarm() {
        while (AlarmReceiver.isInProgress()) {
            //do nothing
        }
        cancelAlarm();
        startAlarm();
    }

    public void openPrefs() {
        startActivity(new Intent(MainActivity.this, PreferencesActivity.class));
    }

    public Context getContext() {
        return app.getApplicationContext();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cancelAlarm();
    }
}
