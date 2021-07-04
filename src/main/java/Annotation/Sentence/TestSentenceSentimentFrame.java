package Annotation.Sentence;

import SentiNet.*;
import WordNet.*;

import java.util.ArrayList;
import java.util.HashMap;

public class TestSentenceSentimentFrame {

    public static void generateLiteralXml(){
        HashMap<String, ArrayList<Double>> polarity = new HashMap<>();
        WordNet turkish = new WordNet();
        SentiNet hisnet = new SentiNet();
        for (String id : hisnet.getNeutrals()){
            SynSet synSet = turkish.getSynSetWithId(id);
            if (synSet != null){
                for (int i = 0; i < synSet.getSynonym().literalSize(); i++){
                    Literal literal = synSet.getSynonym().getLiteral(i);
                    ArrayList<Double> polarityLevel;
                    if (polarity.containsKey(literal.getName())){
                        polarityLevel = polarity.get(literal.getName());
                    } else {
                        polarityLevel = new ArrayList<>();
                    }
                    polarityLevel.add(0.0);
                    polarity.put(literal.getName(), polarityLevel);
                }
            }
        }
        for (String id : hisnet.getPositives()){
            SynSet synSet = turkish.getSynSetWithId(id);
            if (synSet != null){
                for (int i = 0; i < synSet.getSynonym().literalSize(); i++){
                    Literal literal = synSet.getSynonym().getLiteral(i);
                    ArrayList<Double> polarityLevel;
                    if (polarity.containsKey(literal.getName())){
                        polarityLevel = polarity.get(literal.getName());
                    } else {
                        polarityLevel = new ArrayList<>();
                    }
                    polarityLevel.add(hisnet.getSentiSynSet(id).getPositiveScore());
                    polarity.put(literal.getName(), polarityLevel);
                }
            }
        }
        for (String id : hisnet.getNegatives()){
            SynSet synSet = turkish.getSynSetWithId(id);
            if (synSet != null){
                for (int i = 0; i < synSet.getSynonym().literalSize(); i++){
                    Literal literal = synSet.getSynonym().getLiteral(i);
                    ArrayList<Double> polarityLevel;
                    if (polarity.containsKey(literal.getName())){
                        polarityLevel = polarity.get(literal.getName());
                    } else {
                        polarityLevel = new ArrayList<>();
                    }
                    polarityLevel.add(-hisnet.getSentiSynSet(id).getNegativeScore());
                    polarity.put(literal.getName(), polarityLevel);
                }
            }
        }
        System.out.println("<WORDS>");
        for (String literal : polarity.keySet()){
            double sum = 0.0;
            for (Double d : polarity.get(literal)){
                sum += d;
            }
            sum /= polarity.get(literal).size();
            System.out.print("<WORD><NAME>" + literal + "</NAME><PSCORE>");
            if (sum > 0){
                System.out.print(sum + "</PSCORE><NSCORE>0.0</NSCORE>");
            } else {
                if (sum < 0){
                    System.out.print("0.0</PSCORE><NSCORE>" + (-sum) + "</NSCORE>");
                } else {
                    System.out.print("0.0</PSCORE><NSCORE>0.0</NSCORE>");
                }
            }
            System.out.println("</WORD>");
        }
        System.out.println("</WORDS>");
    }

    public static void main(String[] args){
        new SentenceSentimentFrame();
    }

}
