package edu.washington.grassela.quizdroid;

import java.util.List;

/**
 * Created by autumngrassel on 2/16/15.
 */
public interface TopicRepository {
    public void createTopics();
    public List<Question> getQuestions(Topic t);
    public void updateTopic(Topic t, Question q);
    public void deleteTopic(Topic t);
}
