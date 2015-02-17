package edu.washington.grassela.quizdroid;

import java.io.Serializable;
import java.util.List;


public class Topic implements Serializable {
    private String topic;
    private String shortDesc;
    private String longDesc;
    private List<Question> questions;
    private int numQuestions;

    public Topic() { }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setLongDesc(String longDesc) {
        this.longDesc = longDesc;
    }

    public String getLongDesc() {
        return longDesc;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
        numQuestions = questions.size();
    }

    public void addQuestion(Question question) {
        questions.add(question);
        numQuestions++;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public int getNumQuestions() {
        return numQuestions;
    }


}
