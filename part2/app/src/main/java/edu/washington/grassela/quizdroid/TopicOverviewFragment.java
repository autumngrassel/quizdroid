package edu.washington.grassela.quizdroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class TopicOverviewFragment extends Fragment {
    public TopicOverviewFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_topic_overview, container, false);

        final Intent launchedMe = getActivity().getIntent();
        String topic = launchedMe.getStringExtra("topic");
        TextView heading = (TextView) rootView.findViewById(R.id.topicHeading);
        heading.setText(topic);

        String summary = launchedMe.getStringExtra("overview");
        TextView overview = (TextView) rootView.findViewById(R.id.overview);
        overview.setText(summary);

        int numQuestions = launchedMe.getIntExtra("numQs", 0);
        TextView displayNum = (TextView) rootView.findViewById(R.id.numQuestions);
        displayNum.setText("Questions for this topic: " + numQuestions);

        final Bundle questionMap = launchedMe.getBundleExtra("questionMap");

        final ArrayList<String> questionList = launchedMe.getStringArrayListExtra("questionList");

        Button b = (Button) rootView.findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchedMe.putExtra("questionMap", questionMap);
                launchedMe.putStringArrayListExtra("questionList", questionList);
                launchedMe.putExtra("questionNum", 0);
                launchedMe.putExtra("numCorrect", 0);
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, new ViewQuestionFragment())
                        .commit();
            }
        });

        return rootView;
    }
}
