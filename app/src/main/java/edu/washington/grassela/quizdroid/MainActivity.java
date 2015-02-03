package edu.washington.grassela.quizdroid;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends ActionBarActivity {

    static Map<String, String[]> questionMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] topicNames = new String[] { "Math",  "Physics", "Marvel Super Heroes"};
        int[] resourceFiles = {R.raw.math, R.raw.physics, R.raw.marvel};
        final String[] overviews = {"Contains various math story problems and basic geometry terms and questions.",
                "Test your physics trivia with questions about common theories.",
                "Find out just how well you know your superheroes (and villains)."};
        final Topic[] topics = new Topic[3];
        for (int i = 0; i < topics.length; i++) {
            topics[i] = getQuestions(this, resourceFiles[i], topicNames[i]);
        }

        final ListView chooseTopic = (ListView) findViewByIdName("topicList");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, topicNames);

        chooseTopic.setAdapter(adapter);

        chooseTopic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    String topic = (String) chooseTopic.getItemAtPosition(position);
                    // Do something with itemValue
                    Intent nextActivity = new Intent(MainActivity.this, TopicOverview.class);
                    nextActivity.putExtra("topic", topic);
                    for (int i = 0; i < topics.length; i++) {
                        if (topic.equals(topics[i].getTopic())) {
                            questionMap = topics[i].getMap();
                            nextActivity.putExtra("overview", overviews[i]);
                            nextActivity.putExtra("numQs", topics[i].getNumQuestions());
                            nextActivity.putStringArrayListExtra("questionList",
                                    topics[i].getQuestions());
                        }
                    }
                    startActivity(nextActivity);
                    finish();
                }
            }
        );


    }
    private Topic getQuestions(Context ctx, int resId, String topicName) {
        InputStream inputStream = ctx.getResources().openRawResource(resId);

        InputStreamReader inputReader = new InputStreamReader(inputStream);
        BufferedReader bufferedreader = new BufferedReader(inputReader);
        String line;
        Topic topic = new Topic(topicName);
        try {
            while ((line = bufferedreader.readLine()) != null) {
                String question = line;
                String[] answers = {bufferedreader.readLine(), bufferedreader.readLine(),
                        bufferedreader.readLine(), bufferedreader.readLine()};
                topic.addQuestion(question, answers);
            }
        } catch (IOException e) {
            return null;
        }
        return topic;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public class Topic {
        private HashMap<String, String[]> questions;
        private int numQuestions;
        private String topic;

        public Topic(String topic) {
            questions = new HashMap<String, String[]>();
            numQuestions = 0;
            this.topic = topic;
        }

        public HashMap<String, String[]> getMap() {
            return questions;
        }

        public void addQuestion(String question, String[] answers) {
            questions.put(question, answers);
            numQuestions++;
        }

        public boolean isAnswer(String question, String answer) {
            return questions.get(question)[0].equals(answer);
        }

        public int getNumQuestions() {
            return numQuestions;
        }

        public int getNumAnswers(String question) {
            return questions.get(question).length;
        }

        public ArrayList<String> getQuestions() {
            return new ArrayList<String>(questions.keySet());
        }

        public String[] getAnswers(String question) {
            return questions.get(question);
        }
        public String getTopic() {
            return topic;
        }
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
            view = (View) findViewById(id);
        }

        return view;
    }
}
