package edu.washington.grassela.quizdroid;

import java.io.Serializable;

public class Question implements Serializable {

    private String questionText;
    private String[] answers;
    private int correctAnswer;

    public Question() { }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String[] getAnswers() {
        return answers;
    }

    public void setAnswers(String[] answers) {
        this.answers = answers;
    }

    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getCorrectAnswer() {
        return answers[correctAnswer];
    }
}
