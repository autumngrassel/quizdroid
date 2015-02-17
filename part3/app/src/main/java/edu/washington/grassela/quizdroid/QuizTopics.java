package edu.washington.grassela.quizdroid;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuizTopics implements TopicRepository {

    private List<Topic> topics;

    public QuizTopics() {
        createTopics();
    }

    public void createTopics() {
        String[] topicNames = new String[] { "Math",  "Physics", "Marvel Super Heroes"};
        int[] resourceFiles = {R.raw.math, R.raw.physics, R.raw.marvel};
        Log.d("yup", Arrays.toString(resourceFiles));
        String[] shortDesc = {"Test your math problem solving skills",
                "Questions about Physics knowledge and theories",
                "Questions for the comic book fans"};
        String[] longDesc = {"Contains various math story problems and basic geometry terms and questions.",
                "Test your physics trivia with questions about common theories.",
                "Find out just how well you know your superheroes (and villains)."};
        topics = new ArrayList<>();
        for (int i = 0; i < topicNames.length; i++) {
            Topic currentTopic = new Topic();
            currentTopic.setTopic(topicNames[i]);
            currentTopic.setShortDesc(shortDesc[i]);
            currentTopic.setLongDesc(longDesc[i]);
            setQuestions(resourceFiles[i], currentTopic);
            currentTopic.setImageId(R.drawable.ic_launcher);
            topics.add(currentTopic);
        }
    }

    public Topic getTopic(int topicNum) {
        return topics.get(topicNum);
    }

    public List<Topic> getTopics() {
        return topics;
    }

    private void setQuestions(int resId, Topic topic) {
        InputStream inputStream = QuizApp.getAppContext().getResources().openRawResource(resId);

        InputStreamReader inputReader = new InputStreamReader(inputStream);
        BufferedReader bufferedreader = new BufferedReader(inputReader);
        String question;
        topic.setQuestions(new ArrayList<Question>());

        try {
            while ((question = bufferedreader.readLine()) != null) {
                String[] answers = {bufferedreader.readLine(), bufferedreader.readLine(),
                        bufferedreader.readLine(), bufferedreader.readLine()};
                Question q = new Question();
                q.setQuestionText(question);
                q.setAnswers(answers);
                q.setCorrectAnswer(0);
                topic.addQuestion(q);
            }
        } catch (IOException e) {
            Log.e("Reading Files", "Error while processing files!");
        }
    }

    public List<Question> getQuestions(Topic t) {
        return t.getQuestions();
    }

    public void updateTopic(Topic t, Question q) {
        t.addQuestion(q);
    }

    public void deleteTopic(Topic t) {

    }
}
