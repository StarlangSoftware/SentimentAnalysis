package AutoProcessor.Sentence;/* Created by oguzkeremyildiz on 6.05.2021 */

import AnnotatedSentence.*;
import Classification.Attribute.Attribute;
import Classification.Attribute.DiscreteAttribute;
import Classification.Instance.Instance;
import Classification.Model.TreeEnsembleModel;
import MorphologicalAnalysis.MorphologicalTag;
import SentiNet.*;

import java.util.ArrayList;

public class TurkishSentenceClassifierAutoSentiment extends SentenceAutoSentiment {

    private final ArrayList<TreeEnsembleModel> models;

    public TurkishSentenceClassifierAutoSentiment(SentiNet sentiNet, ArrayList<TreeEnsembleModel> models) {
        super(sentiNet);
        this.models = models;
    }

    private PolarityType findPolarityType(String type) {
        if (type.equals("POSITIVE")) {
            return PolarityType.POSITIVE;
        } else if (type.equals("NEGATIVE")) {
            return PolarityType.NEGATIVE;
        }
        return PolarityType.NEUTRAL;
    }

    @Override
    public PolarityType autoSentiment(AnnotatedSentence sentence) {
        ArrayList<Attribute> testData = new ArrayList<>();
        String classInfo;
        for (int i = 0; i < sentence.wordCount(); i++) {
            AnnotatedWord word = ((AnnotatedWord) sentence.getWord(i));
            SentiSynSet sentiSynSet = sentiNet.getSentiSynSet(word.getSemantic());
            if (sentiSynSet != null) {
                testData.add(new DiscreteAttribute(sentiSynSet.getPolarity().toString()));
                testData.add(new DiscreteAttribute(Double.toString(Math.max(sentiSynSet.getNegativeScore(), sentiSynSet.getPositiveScore()))));
            } else {
                testData.add(new DiscreteAttribute("null"));
                testData.add(new DiscreteAttribute("null"));
            }
            testData.add(new DiscreteAttribute(Boolean.toString(word.getParse().containsTag(MorphologicalTag.NEGATIVE))));
            testData.add(new DiscreteAttribute(Boolean.toString(word.getParse().containsTag(MorphologicalTag.POSITIVE))));
            testData.add(new DiscreteAttribute(Boolean.toString(word.getParse().containsTag(MorphologicalTag.ABLATIVE))));
            testData.add(new DiscreteAttribute(Boolean.toString(word.getParse().containsTag(MorphologicalTag.DATIVE))));
            testData.add(new DiscreteAttribute(Boolean.toString(word.getParse().containsTag(MorphologicalTag.GENITIVE))));
            testData.add(new DiscreteAttribute(Boolean.toString(word.getParse().containsTag(MorphologicalTag.NOMINATIVE))));
            testData.add(new DiscreteAttribute(Boolean.toString(word.getParse().containsTag(MorphologicalTag.ACCUSATIVE))));
            testData.add(new DiscreteAttribute(Boolean.toString(word.getParse().containsTag(MorphologicalTag.PROPERNOUN))));
            testData.add(new DiscreteAttribute(word.getParse().getPos()));
            testData.add(new DiscreteAttribute(word.getParse().getRootPos()));
        }
        classInfo = models.get(sentence.wordCount() - 2).predict(new Instance("", testData));
        return findPolarityType(classInfo);
    }
}
