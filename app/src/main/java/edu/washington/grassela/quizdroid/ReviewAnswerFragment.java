package edu.washington.grassela.quizdroid;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by autumngrassel on 2/11/15.
 */
public class ReviewAnswerFragment extends Fragment {
    public ReviewAnswerFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // get View of Fragment from fragment_main.xml
        View rootView = inflater.inflate(R.layout.activity_review_answers, container, false);

        // get button @id/button4 from fragment_main.xml
        Button btn4 = (Button) rootView.findViewById(R.id.button4);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // make Toast in main view that holds the fragment. in this case => container
                Toast.makeText(container.getContext(), "Howdy", Toast.LENGTH_SHORT).show();

            }
        });

        return rootView;
    }
}


