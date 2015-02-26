package edu.washington.grassela.quizdroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class QuizActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_layout);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new TopicOverviewFragment())
                    .commit();
        }

    }

    // shows submit button once an option has been selected
    public void onRadioButtonClicked(View view) {
        Button submit = (Button) findViewById(R.id.submit);
        submit.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        //menu.setGroupVisible();
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

    public void openPrefs() {
        startActivity(new Intent(QuizActivity.this, PreferencesActivity.class));
    }
}
