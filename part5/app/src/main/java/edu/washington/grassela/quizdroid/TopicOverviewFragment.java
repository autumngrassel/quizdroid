package edu.washington.grassela.quizdroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class TopicOverviewFragment extends Fragment {
    public TopicOverviewFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_topic_overview, container, false);

        final Intent launchedMe = getActivity().getIntent();
        final Topic topic = (Topic) launchedMe.getSerializableExtra("topic");
        TextView heading = (TextView) rootView.findViewById(R.id.topicHeading);
        heading.setText(topic.getTopic());
        TextView overview = (TextView) rootView.findViewById(R.id.overview);
        overview.setText(topic.getLongDesc());
        TextView displayNum = (TextView) rootView.findViewById(R.id.numQuestions);
        displayNum.setText("Questions for this topic: " + topic.getNumQuestions());

        Button b = (Button) rootView.findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchedMe.putExtra("topic", topic);
                launchedMe.putExtra("questionNum", 0);
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, new ViewQuestionFragment())
                        .commit();
            }
        });

        return rootView;
    }
}
