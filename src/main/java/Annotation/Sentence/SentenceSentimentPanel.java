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
        listModel.addElement("Pos");
        listModel.addElement("Neg");
        listModel.addElement("Neutral");
        if (word.getPolarity() != null){
            switch (word.getPolarity().toString().toLowerCase()){
                case "pos":
                case "positive":
                    selectedIndex = 0;
                    break;
                case "neg":
                case "negative":
                    selectedIndex = 1;
                    break;
                default:
                    selectedIndex = 2;
                    break;
            }
        }
        return selectedIndex;
    }

}
