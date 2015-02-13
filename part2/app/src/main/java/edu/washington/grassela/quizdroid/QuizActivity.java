package edu.washington.grassela.quizdroid;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

/**
 * Created by autumngrassel on 2/12/15.
 */
public class QuizActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_layout);

        if (savedInstanceState == null) { // if Application ever fired up before, is this the first time we're firing it up
            // r.id.container is where you want the fragment to be inserted
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new TopicOverviewFragment())
                    .commit();
        }
    }

    public void onRadioButtonClicked(View view) {
        try {
            // Is the button now checked?
            boolean checked = ((RadioButton) view).isChecked();
            // Check which radio button was clicked
            Button submit = (Button) findViewById(R.id.submit);
            submit.setVisibility(View.VISIBLE);
        } catch (ClassCastException CCE) {
            Log.i("ViewQuestion", "screen touched outside of radio button area.");
        }
    }


}
