package AutoProcessor.Sentence;/* Created by oguzkeremyildiz on 19.03.2021 */

import AnnotatedSentence.AnnotatedSentence;
import SentiNet.PolarityType;
import SentiNet.SentiNet;

public abstract class SentenceAutoSentiment {

    protected final SentiNet sentiNet;
    
    public SentenceAutoSentiment(SentiNet sentiNet) {
        this.sentiNet = sentiNet;
    }

    public abstract PolarityType autoSentiment(AnnotatedSentence sentence);
}
