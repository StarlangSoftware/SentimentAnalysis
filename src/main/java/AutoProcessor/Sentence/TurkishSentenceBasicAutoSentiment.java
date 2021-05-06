package AutoProcessor.Sentence;/* Created by oguzkeremyildiz on 19.03.2021 */

import AnnotatedSentence.AnnotatedSentence;
import AnnotatedSentence.AnnotatedWord;
import MorphologicalAnalysis.MorphologicalTag;
import SentiNet.SentiNet;
import SentiNet.PolarityType;
import SentiNet.SentiSynSet;

import java.util.Locale;

public class TurkishSentenceBasicAutoSentiment extends SentenceAutoSentiment {

    public TurkishSentenceBasicAutoSentiment(SentiNet sentiNet) {
        super(sentiNet);
    }

    private PolarityType findPolarityType(Double sum) {
        if (sum > 0.0) {
            return PolarityType.POSITIVE;
        } else if (sum < 0.0) {
            return PolarityType.NEGATIVE;
        }
        return PolarityType.NEUTRAL;
    }

    private PolarityType getPolarity(PolarityType polarityType, AnnotatedSentence sentence, int index) {
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

    public PolarityType autoSentiment(AnnotatedSentence sentence) {
        double polarityValue = 0.0;
        for (int i = 0; i < sentence.wordCount(); i++) {
            AnnotatedWord word = (AnnotatedWord) sentence.getWord(i);
            SentiSynSet sentiSynSet = sentiNet.getSentiSynSet(word.getSemantic());
            if (sentiSynSet != null) {
                double value = Math.max(sentiSynSet.getNegativeScore(), sentiSynSet.getPositiveScore());
                switch (getPolarity(sentiSynSet.getPolarity(), sentence, i)) {
                    case POSITIVE:
                        polarityValue += value;
                        break;
                    case NEGATIVE:
                        polarityValue -= value;
                        break;
                    default:
                        break;
                }
            }
        }
        return findPolarityType(polarityValue);
    }
}
