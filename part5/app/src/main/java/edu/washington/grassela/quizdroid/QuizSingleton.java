package edu.washington.grassela.quizdroid;

public class QuizSingleton {

    private static QuizSingleton instance;

    public static void initInstance() {
        if (instance == null) {
            instance = new QuizSingleton();
        }
    }

    public static QuizSingleton getInstance() {
        // Return the instance
        return instance;
    }

    private QuizSingleton() {
    }

}
