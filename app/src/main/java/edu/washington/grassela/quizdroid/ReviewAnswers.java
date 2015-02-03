package edu.washington.grassela.quizdroid;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


public class ReviewAnswers extends ActionBarActivity {
    int questionNum;
    int numCorrect;
    ArrayList<String> questionList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_answers);


        final Intent launchedMe = getIntent();
        questionNum = launchedMe.getIntExtra("questionNum", 0);
        numCorrect = launchedMe.getIntExtra("numCorrect", 0);
        questionList = launchedMe.getStringArrayListExtra("questionList");
        final boolean quizOver = launchedMe.getBooleanExtra("quizFinished", false);

        String displayText = "You have answered " + numCorrect + " out of " +
                questionNum + " questions correctly";

        TextView message = (TextView) findViewByIdName("message");
        message.setText(displayText);


        Button next = (Button) findViewByIdName("next");
        if (quizOver) {
            next.setText("Finish");
        } else {
            next.setText("Next");
        }
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quizOver) {
                    Intent nextActivity = new Intent(ReviewAnswers.this, MainActivity.class);
                    startActivity(nextActivity);
                    finish();
                } else {
                    Intent nextActivity = new Intent(ReviewAnswers.this, ViewQuestions.class);
                    nextActivity.putStringArrayListExtra("questionList", questionList);
                    nextActivity.putExtra("questionNum", questionNum);
                    nextActivity.putExtra("numCorrect", numCorrect);
                    startActivity(nextActivity);
                    finish();
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_review_answers, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Finds a view by a resource name. i.e. "btn" will return View associated with R.id.btn
     * @param idName a string of resource name
     * @return a view associated with the resource name. will return null if can't find it.
     */
    private View findViewByIdName(String idName) {
        int id = getResources().getIdentifier(idName, "id", getPackageName());
        View view = null;

        if (id != 0) {
            view = findViewById(id);
        }

        return view;
    }
}
