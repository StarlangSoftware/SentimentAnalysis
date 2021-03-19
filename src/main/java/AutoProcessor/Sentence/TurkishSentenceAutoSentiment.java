package AutoProcessor.Sentence;/* Created by oguzkeremyildiz on 19.03.2021 */

import AnnotatedSentence.AnnotatedSentence;
import AnnotatedSentence.AnnotatedWord;
import SentiNet.SentiNet;
import SentiNet.SentiSynSet;
import SentiNet.PolarityType;

public class TurkishSentenceAutoSentiment extends SentenceAutoSentiment {

    public TurkishSentenceAutoSentiment(SentiNet sentiNet) {
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

    @Override
    public PolarityType autoSentiment(AnnotatedSentence sentence) {
        double polarityValue = 0.0;
        for (int i = 0; i < sentence.wordCount(); i++) {
            AnnotatedWord word = (AnnotatedWord) sentence.getWord(i);
            SentiSynSet sentiSynSet = sentiNet.getSentiSynSet(word.getSemantic());
            if (sentiSynSet != null) {
                switch (sentiSynSet.getPolarity()) {
                    case POSITIVE:
                        polarityValue += sentiSynSet.getPositiveScore();
                        break;
                    case NEGATIVE:
                        polarityValue -= sentiSynSet.getNegativeScore();
                        break;
                    default:
                        break;
                }
            }
        }
        return findPolarityType(polarityValue);
    }
}
