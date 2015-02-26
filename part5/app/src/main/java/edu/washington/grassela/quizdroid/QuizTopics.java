package edu.washington.grassela.quizdroid;

import android.util.JsonReader;
import android.util.JsonToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class QuizTopics implements TopicRepository {

    private List<Topic> topics;

    public QuizTopics()  {
        InputStream inputStream = QuizApp.getAppContext().getResources().openRawResource(R.raw.quizdata);
        topics = createTopics(inputStream);
    }

    public List<Topic> readTopicsArray(JsonReader reader) throws IOException {
        List<Topic> topic = new ArrayList<Topic>();
        reader.beginArray();
        while(reader.hasNext()) {
            topic.add(readTopic(reader));
        }
        reader.endArray();
        return topic;
    }

    public List<Question> readQuestionsArray(JsonReader reader) throws IOException {
        List<Question> questions = new ArrayList<>();
        reader.beginArray();
        while(reader.hasNext()) {
            questions.add(readQuestion(reader));
        }
        reader.endArray();
        return questions;
    }

    public Question readQuestion(JsonReader reader) throws IOException {
        String text = "";
        int answer = 0;
        String[] answers = new String[4];
        int index = 0;
        reader.beginObject();
        while(reader.hasNext()) {
            String name = reader.nextName();
            if(name.equals("text")) {
                text = reader.nextString();
            } else if (name.equals("answer")) {
                answer = Integer.parseInt(reader.nextString()) - 1;
            } else if (name.equals("answers")) {
                reader.beginArray();
                while (reader.hasNext()) {
                    answers[index] = reader.nextString();
                    index++;
                }
                reader.endArray();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        Question question = new Question();
        question.setCorrectAnswer(answer);
        question.setAnswers(answers);
        question.setQuestionText(text);
        return question;
    }

    public Topic readTopic(JsonReader reader) throws  IOException {
        String title = "";
        String desc = "";
        List<Question> questions = null;
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("title")) {
                title = reader.nextString();
            } else if (name.equals("desc")) {
                desc = reader.nextString();
            } else if (name.equals("questions") && reader.peek() != JsonToken.NULL) {
                questions = readQuestionsArray(reader);
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        Topic topic = new Topic();
        topic.setQuestions(questions);
        topic.setTopic(title);
        topic.setShortDesc(desc);
        return topic;
    }

    public List<Topic> createTopics(InputStream inputStream) {
        try {
            JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
            return readTopicsArray(reader);
        } catch (IOException e) {
            return null;
        }
    }

    public Topic getTopic(int topicNum) {
        return topics.get(topicNum);
    }

    public List<Topic> getTopics() {
        return topics;
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
