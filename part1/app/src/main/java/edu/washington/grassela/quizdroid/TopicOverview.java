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

public class TopicOverview extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_overview);

        final Intent launchedMe = getIntent();
        String topic = launchedMe.getStringExtra("topic");
        TextView heading = (TextView) findViewByIdName("topicHeading");
        heading.setText(topic);

        String summary = launchedMe.getStringExtra("overview");
        TextView overview = (TextView) findViewByIdName("overview");
        overview.setText(summary);

        int numQuestions = launchedMe.getIntExtra("numQs", 0);
        TextView displayNum = (TextView) findViewByIdName("numQuestions");
        displayNum.setText("Questions for this topic: " + numQuestions);

        final ArrayList<String> questionList = launchedMe.getStringArrayListExtra("questionList");

        Button b = (Button) findViewByIdName("button");
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextActivity = new Intent(TopicOverview.this, ViewQuestions.class);
                nextActivity.putStringArrayListExtra("questionList", questionList);
                nextActivity.putExtra("questionNum", 0);
                nextActivity.putExtra("numCorrect", 0);
                startActivity(nextActivity);
                finish();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_topic_overview, menu);
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
