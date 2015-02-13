package edu.washington.grassela.quizdroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ViewQuestionFragment extends Fragment{

    public ViewQuestionFragment() { }



    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.activity_view_questions, container, false);

        final Intent launchedMe = getActivity().getIntent();


        Bundle questionMap = launchedMe.getBundleExtra("questionMap");

        int questionIndex = launchedMe.getIntExtra("questionNum", 0);

        ArrayList<String> questionList = launchedMe.getStringArrayListExtra("questionList");
        String currentQuestion = questionList.get(questionIndex);
        final String[] answerChoices = questionMap.getStringArray(currentQuestion);

        TextView displayNum = (TextView) rootView.findViewById(R.id.question);
        displayNum.setText(currentQuestion);

        final RadioButton[] options = {(RadioButton) rootView.findViewById(R.id.radio1),
                (RadioButton) rootView.findViewById(R.id.radio2),
                (RadioButton) rootView.findViewById(R.id.radio3),
                (RadioButton) rootView.findViewById(R.id.radio4),
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
        Button submit = (Button) rootView.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioGroup answers = (RadioGroup) rootView.findViewById(R.id.radioGroup);
                int selected = answers.getCheckedRadioButtonId();
                RadioButton selectedButton = (RadioButton) rootView.findViewById(selected);
                String selectedAnswer = (String) selectedButton.getText();
                String correctAnswer = answerChoices[0];
                boolean correct = correctAnswer.equals(selectedAnswer);
                int numCorrect = launchedMe.getIntExtra("numCorrect", 0);
                if (correct) {
                    numCorrect++;
                }
                ArrayList<String> questions = launchedMe.getStringArrayListExtra("questionList");
                int questionNum = launchedMe.getIntExtra("questionNum", 0);
                questionNum++;
                launchedMe.putStringArrayListExtra("questionList", questions);
                launchedMe.putExtra("correctAnswer", correctAnswer);
                launchedMe.putExtra("questionNum", questionNum);
                launchedMe.putExtra("numCorrect", numCorrect);
                launchedMe.putExtra("quizFinished", questionNum == questions.size());
                launchedMe.putExtra("isCorrect", correct);
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, new ReviewAnswerFragment()).commit();
            }
        });

        return rootView;
    }
}
