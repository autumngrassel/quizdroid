package edu.washington.grassela.quizdroid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {

    private static boolean inProgress = false;

    @Override
    public void onReceive(Context ctxt, Intent intent) {
        inProgress = true;
        String url = PreferenceManager.getDefaultSharedPreferences(ctxt).getString("prefURL",
                "http://tednewardsandbox.site44.com/questions.json");
        // For our recurring task, we'll just display a message
        Toast.makeText(ctxt, url, Toast.LENGTH_SHORT).show();
        inProgress = false;

    }

    public static boolean isInProgress() {
        return inProgress;
    }
}