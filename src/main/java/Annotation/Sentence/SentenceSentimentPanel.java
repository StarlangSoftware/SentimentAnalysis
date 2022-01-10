package Annotation.Sentence;

import AnnotatedSentence.AnnotatedSentence;
import AnnotatedSentence.AnnotatedWord;
import AnnotatedSentence.ViewLayerType;
import DataCollector.Sentence.SentenceAnnotatorPanel;

import java.awt.*;
import java.util.ArrayList;

public class SentenceSentimentPanel extends SentenceAnnotatorPanel {

    public SentenceSentimentPanel(String currentPath, String fileName) {
        super(currentPath, fileName, ViewLayerType.POLARITY);
        setLayout(new BorderLayout());
    }

    @Override
    protected void setWordLayer() {
        clickedWord.setPolarity(list.getSelectedValue().toString());
    }

    @Override
    protected void setBounds() {
        pane.setBounds(((AnnotatedWord)sentence.getWord(selectedWordIndex)).getArea().x, ((AnnotatedWord)sentence.getWord(selectedWordIndex)).getArea().y + 20, 120, 80);
    }

    @Override
    protected void drawLayer(AnnotatedWord word, Graphics g, int currentLeft, int lineIndex, int wordIndex, int maxSize, ArrayList<Integer> wordSize, ArrayList<Integer> wordTotal) {
        if (word.getPolarity() != null){
            String correct = word.getPolarity().toString();
            g.drawString(correct, currentLeft, (lineIndex + 1) * lineSpace + 30);
        }
    }

    @Override
    protected int getMaxLayerLength(AnnotatedWord word, Graphics g) {
        int maxSize = g.getFontMetrics().stringWidth(word.getName());
        if (word.getPolarity() != null){
            int size = g.getFontMetrics().stringWidth(word.getPolarity().toString());
            if (size > maxSize){
                maxSize = size;
            }
        }
        return maxSize;
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
