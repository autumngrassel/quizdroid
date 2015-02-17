package edu.washington.grassela.quizdroid;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;

public class QuizActivity extends FragmentActivity {

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
}
