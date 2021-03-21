package AutoProcessor.Sentence;/* Created by oguzkeremyildiz on 19.03.2021 */

import AnnotatedSentence.AnnotatedSentence;
import AnnotatedSentence.AnnotatedWord;
import SentiNet.PolarityType;
import SentiNet.SentiNet;
import SentiNet.SentiSynSet;

public abstract class SentenceAutoSentiment {

    protected final SentiNet sentiNet;
    
    public SentenceAutoSentiment(SentiNet sentiNet) {
        this.sentiNet = sentiNet;
    }

    protected abstract PolarityType getPolarity(PolarityType polarityType, AnnotatedSentence sentence, int index);

    private PolarityType findPolarityType(Double sum) {
        if (sum > 0.0) {
            return PolarityType.POSITIVE;
        } else if (sum < 0.0) {
            return PolarityType.NEGATIVE;
        }
        return PolarityType.NEUTRAL;
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
