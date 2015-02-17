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
import java.util.List;


public class MainActivity extends ActionBarActivity {

    protected QuizApp app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        app = (QuizApp) getApplication();

        final List<Topic> topics = app.getRepo();
        String[] topicNames = new String[topics.size()];
        for (int i = 0; i < topics.size(); i++) {
            topicNames[i] = topics.get(i).getTopic();
        }
        final ListView chooseTopic = (ListView) findViewById(R.id.topicList);

        Integer[] images = {
            R.drawable.ic_launcher,
            R.drawable.ic_launcher,
            R.drawable.ic_launcher
        };

        CustomList adapter = new CustomList(MainActivity.this, topicNames, images);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, topicNames);

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

    public Context getContext() {
        return (Context) app.getApplicationContext();
    }
}
