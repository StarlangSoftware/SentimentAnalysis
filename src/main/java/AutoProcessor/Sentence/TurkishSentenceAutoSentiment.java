package AutoProcessor.Sentence;/* Created by oguzkeremyildiz on 19.03.2021 */

import AnnotatedSentence.AnnotatedSentence;
import AnnotatedSentence.AnnotatedWord;
import MorphologicalAnalysis.MorphologicalTag;
import SentiNet.SentiNet;
import SentiNet.PolarityType;

import java.util.Locale;

public class TurkishSentenceAutoSentiment extends SentenceAutoSentiment {

    public TurkishSentenceAutoSentiment(SentiNet sentiNet) {
        super(sentiNet);
    }

    protected PolarityType getPolarity(PolarityType polarityType, AnnotatedSentence sentence, int index) {
        if (((AnnotatedWord) sentence.getWord(index)).getParse().containsTag(MorphologicalTag.NEGATIVE)) {
            if (polarityType.equals(PolarityType.POSITIVE)) {
                polarityType = PolarityType.NEGATIVE;
            } else if (polarityType.equals(PolarityType.NEGATIVE)) {
                polarityType = PolarityType.POSITIVE;
            }
        }
        if (index + 1 < sentence.wordCount()) {
            AnnotatedWord nextWord = (AnnotatedWord) sentence.getWord(index + 1);
            if (nextWord.getParse().getWord().getName().toLowerCase(new Locale("tr")).equals("değil")) {
                if (polarityType.equals(PolarityType.POSITIVE)) {
                    return PolarityType.NEGATIVE;
                } else if (polarityType.equals(PolarityType.NEGATIVE)) {
                    return PolarityType.POSITIVE;
                }
            }
        }
        return polarityType;
    }
}
