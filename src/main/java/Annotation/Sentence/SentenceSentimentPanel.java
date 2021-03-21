package Annotation.Sentence;

import AnnotatedSentence.AnnotatedSentence;
import AnnotatedSentence.AnnotatedWord;
import AnnotatedSentence.ViewLayerType;
import DataCollector.Sentence.SentenceAnnotatorPanel;

import java.awt.*;

public class SentenceSentimentPanel extends SentenceAnnotatorPanel {

    public SentenceSentimentPanel(String currentPath, String fileName) {
        super(currentPath, fileName, ViewLayerType.POLARITY);
        setLayout(new BorderLayout());
    }

    public int populateLeaf(AnnotatedSentence sentence, int wordIndex){
        int selectedIndex = -1;
        AnnotatedWord word = (AnnotatedWord) sentence.getWord(wordIndex);
        listModel.clear();
        listModel.addElement("POSITIVE");
        listModel.addElement("NEGATIVE");
        listModel.addElement("NEUTRAL");
        if (word.getPolarity() != null && word.getPolarity().toString().equals("POSITIVE")){
            selectedIndex = 0;
        }
        if (word.getPolarity() != null && word.getPolarity().toString().equals("NEGATIVE")){
            selectedIndex = 1;
        }
        if (word.getPolarity() != null && word.getPolarity().toString().equals("NEUTRAL")){
            selectedIndex = 2;
        }
        return selectedIndex;
    }

}
