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

    /**
     * Updates the sentiment layer of the annotated word.
     */
    @Override
    protected void setWordLayer() {
        clickedWord.setPolarity(list.getSelectedValue().toString());
    }

    /**
     * Sets the width and height of the JList that displays the polarity tags.
     */
    @Override
    protected void setBounds() {
        pane.setBounds(((AnnotatedWord)sentence.getWord(selectedWordIndex)).getArea().getX(), ((AnnotatedWord)sentence.getWord(selectedWordIndex)).getArea().getY() + 20, 120, 80);
    }

    /**
     * Sets the space between displayed lines in the sentence.
     */
    @Override
    protected void setLineSpace() {
        lineSpace = 80;
    }

    /**
     * Draws the polarity tag of the word.
     * @param word Annotated word itself.
     * @param g Graphics on which polariy tag is drawn.
     * @param currentLeft Current position on the x-axis, where the polarity tag will be aligned.
     * @param lineIndex Current line of the word, if the sentence resides in multiple lines on the screen.
     * @param wordIndex Index of the word in the annotated sentence.
     * @param maxSize Maximum size in pixels of anything drawn in the screen.
     * @param wordSize Array storing the sizes of all words in pixels in the annotated sentence.
     * @param wordTotal Array storing the total size until that word of all words in the annotated sentence.
     */
    @Override
    protected void drawLayer(AnnotatedWord word, Graphics g, int currentLeft, int lineIndex, int wordIndex, int maxSize, ArrayList<Integer> wordSize, ArrayList<Integer> wordTotal) {
        if (word.getPolarity() != null){
            String correct = word.getPolarity().toString();
            g.drawString(correct, currentLeft, (lineIndex + 1) * lineSpace + 30);
        }
    }

    /**
     * Compares the size of the word and the size of the polarity tag in pixels and returns the maximum of them.
     * @param word Word annotated.
     * @param g Graphics on which sentiment tag is drawn.
     * @return Maximum of the graphic sizes of word and its sentiment tag.
     */
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

    /**
     * Fills the JList that contains all polarity values Pos, Neg, and Neutral.
     * @param sentence Sentence used to populate for the current word.
     * @param wordIndex Index of the selected word.
     * @return The index of the selected polarity tag, -1 if nothing selected.
     */
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
