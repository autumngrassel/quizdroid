package edu.washington.grassela.quizdroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class ViewQuestionFragment extends Fragment{

    public ViewQuestionFragment() { }



    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.activity_view_questions, container, false);

        final Intent launchedMe = getActivity().getIntent();

        final Topic topic = (Topic) launchedMe.getSerializableExtra("topic");

        int questionIndex = launchedMe.getIntExtra("questionNum", 0);

        final Question currentQuestion = topic.getQuestions().get(questionIndex);
        String questionText = currentQuestion.getQuestionText();

        String[] answerChoices = currentQuestion.getAnswers();

        TextView displayQuestion = (TextView) rootView.findViewById(R.id.question);
        displayQuestion.setText(questionText);

        final RadioButton[] options = {(RadioButton) rootView.findViewById(R.id.radio1),
                (RadioButton) rootView.findViewById(R.id.radio2),
                (RadioButton) rootView.findViewById(R.id.radio3),
                (RadioButton) rootView.findViewById(R.id.radio4),
        };
        // randomly choose index to put correct answer at
        final int correctAnswer = (int) (Math.floor(Math.random() * 4));
        int index = 1;
        for (int i = 0; i < options.length; i++) {
            if (i != correctAnswer) {
                options[i].setText(answerChoices[index]);
                index++;
            }
        }
        options[correctAnswer].setText(answerChoices[0]);
        Button submit = (Button) rootView.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioGroup answers = (RadioGroup) rootView.findViewById(R.id.radioGroup);
                int selected = answers.getCheckedRadioButtonId();
                RadioButton selectedButton = (RadioButton) rootView.findViewById(selected);
                String selectedAnswer = (String) selectedButton.getText();
                String correctAnswer = currentQuestion.getCorrectAnswer();
                boolean correct = correctAnswer.equals(selectedAnswer);
                int numCorrect = launchedMe.getIntExtra("numCorrect", 0);
                if (correct) {
                    numCorrect++;
                }
                int questionNum = launchedMe.getIntExtra("questionNum", 0);
                questionNum++;
                launchedMe.putExtra("topic", topic);
                launchedMe.putExtra("correctAnswer", correctAnswer);
                launchedMe.putExtra("questionNum", questionNum);
                launchedMe.putExtra("numCorrect", numCorrect);
                launchedMe.putExtra("quizFinished", questionNum == topic.getNumQuestions());
                launchedMe.putExtra("isCorrect", correct);
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, new ReviewAnswerFragment()).commit();
            }
        });

        return rootView;
    }
}
