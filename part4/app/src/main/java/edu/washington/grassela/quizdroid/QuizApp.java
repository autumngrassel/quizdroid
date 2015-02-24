package edu.washington.grassela.quizdroid;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import java.util.List;

public class QuizApp extends Application {

    public static final String TAG = "QuizApp";
    private QuizTopics repo;
    private static Context context;

    public QuizApp() { }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "Application loaded");
        initSingletons();
        QuizApp.context = getApplicationContext();
        repo = new QuizTopics();
    }

    protected void initSingletons() {
        QuizSingleton.initInstance();
    }

    public List<Topic> getRepo() {
        return repo.getTopics();
    }

    public static Context getAppContext() {
        return QuizApp.context;
    }

}
