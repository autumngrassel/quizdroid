package edu.washington.grassela.quizdroid;

import java.io.InputStream;
import java.util.List;

/**
 * Created by autumngrassel on 2/16/15.
 */
public interface TopicRepository {
    public  void createTopics(InputStream inputStream);
    public List<Question> getQuestions(Topic t);
    public void updateTopic(Topic t, Question q);
    public void deleteTopic(Topic t);
}
