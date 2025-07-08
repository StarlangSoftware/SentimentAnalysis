package Annotation.Sentence;

import DataCollector.Sentence.SentenceAnnotatorFrame;
import DataCollector.Sentence.SentenceAnnotatorPanel;

public class SentenceSentimentFrame extends SentenceAnnotatorFrame {

    public SentenceSentimentFrame(){
        super();
    }

    @Override
    public SentenceAnnotatorPanel generatePanel(String currentPath, String rawFileName) {
        return new SentenceSentimentPanel(currentPath, rawFileName);
    }
}
