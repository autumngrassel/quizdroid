package edu.washington.grassela.quizdroid;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;


public class ViewQuestions extends ActionBarActivity {
    Map<String, String[]> map = MainActivity.questionMap;
    int questionNum;
    int numCorrect;
    int chosenIndex;
    ArrayList<String> questionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_questions);

        final Intent launchedMe = getIntent();

        questionNum = launchedMe.getIntExtra("questionNum", 0);
        numCorrect = launchedMe.getIntExtra("numCorrect", 0);
        questionList = launchedMe.getStringArrayListExtra("questionList");

        String currentQuestion = questionList.get(questionNum);
        final String[] answerChoices = map.get(currentQuestion);

        TextView displayNum = (TextView) findViewByIdName("question");
        displayNum.setText(currentQuestion);

        final RadioButton[] options = {(RadioButton) findViewByIdName("radio1"),
                (RadioButton) findViewByIdName("radio2"),
                (RadioButton) findViewByIdName("radio3"),
                (RadioButton) findViewByIdName("radio4"),
        };
        final int correctAnswer = (int) (Math.floor(Math.random() * 4));
        final String[] answerOrder = new String[4];
        int index = 1;
        for (int i = 0; i < options.length; i++) {
            if (i != correctAnswer) {
                options[i].setText(answerChoices[index]);
                answerOrder[i] = answerChoices[index];
                index++;
            }
        }
        options[correctAnswer].setText(answerChoices[0]);
        answerOrder[correctAnswer] = answerChoices[0];
        Button submit = (Button) findViewByIdName("submit");
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String correctAnswer = answerChoices[0];
                questionNum++;
                boolean correct = correctAnswer.equals(answerOrder[chosenIndex]);
                if (correct) {
                    numCorrect++;
                }
                Intent nextActivity = new Intent(ViewQuestions.this, ReviewAnswers.class);
                nextActivity.putStringArrayListExtra("questionList", questionList);
                nextActivity.putExtra("correctAnswer", correctAnswer);
                nextActivity.putExtra("questionNum", questionNum);
                nextActivity.putExtra("numCorrect", numCorrect);
                nextActivity.putExtra("quizFinished", questionNum == questionList.size());
                nextActivity.putExtra("isCorrect", correct);
                startActivity(nextActivity);
                finish();
            }
        });
    }

    public void onRadioButtonClicked(View view) {
        try {
            // Is the button now checked?
            boolean checked = ((RadioButton) view).isChecked();
            // Check which radio button was clicked
            Button submit = (Button) findViewByIdName("submit");
            submit.setVisibility(View.VISIBLE);
            switch (view.getId()) {
                case R.id.radio1:
                    if (checked)
                        chosenIndex = 0;
                    break;
                case R.id.radio2:
                    if (checked)
                        chosenIndex = 1;
                    break;
                case R.id.radio3:
                    if (checked)
                        chosenIndex = 2;
                    break;
                case R.id.radio4:
                    if (checked)
                        chosenIndex = 3;
                    break;
            }
        } catch (ClassCastException CCE) {
            Log.i("ViewQuestions", "screen touched outside of radio button area.");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_questions, menu);
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
