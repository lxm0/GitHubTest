package test0912;

import ca.usask.cs.text.readability.FleschKincaidReadingEase;
import ca.usask.cs.text.readability.ReadabilityGradeProvider;
import ca.usask.cs.text.readability.TextStatProvider;
import ca.usask.cs.text.readability.test.ContentLoader;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class test extends TextStatProvider {

    int complexWord = 0;

    public test(String text) {
        super(text);

    }

    public static void main(String[] args) {
        String fileName = "C:\\Users\\GT\\Desktop\\experiment\\sample2.txt";
        String text = ContentLoader.loadFileContent(fileName);
        //计算评论中的句子属性
        //getSentences(text);
        //计算评论的ReadingEase得分
        getReadingEaseScore(text);
        //计算去除代码元素的评论的ReadingEase得分
        //getReadingEaseNL(text);
        //计算StopWordRatio
        double count = getStopWord(text);
        System.out.println("StopWordRatio: "+count);
        //计算代码元素比率
       // getCodeElement(text);
        //计算句子数
//        int sentenceCount  = getTotalSentences(text).length;
//        System.out.println("sentenceCount: "+sentenceCount);
//        //计算单词数
//        int WordsCount =  getWordsCount(text);
//        System.out.println("WordsCount: "+WordsCount);
//        //计算字符数
//        int CharCount = getSentencesCharCount(text);
//        System.out.println("CharCount: "+CharCount);
//        //计算音节数
//        int SyllableCount = getSentencesSyllableCount(text);
//        System.out.println("SyllableCount: "+SyllableCount);
//
//        //计算ReadingEase
        double ReadingEase =  getReadingEaseTest(text);
        System.out.println("ReadingEaseTest: "+ReadingEase);

    }
    public static void getSentences(String text){
        test provider = new test(text);
        System.out.println("Sentences:" + provider.getSentenceCount());
        System.out.println("Words:" + provider.getWordCount());
        System.out.println("Characters:" + provider.getCharCount());
        System.out.println("Syllables:" + provider.getSyllableCount());
        System.out.println("Complex words:" + provider.complexWord);
    }
    public static void getReadingEaseScore(String text){
        ReadabilityGradeProvider readabilityGradeProvider = new ReadabilityGradeProvider(text);
        System.out.println("ReadingEase: "+readabilityGradeProvider.getFleschKincaidReadingEase());
    }
    public static void getReadingEaseNL(String text){
        Pattern pattern = Pattern.compile("`{3}[\\S+\\s+]+`{3}|`[\\S+]+`|`[\\S+\\s+]+`");
        Matcher matcher = pattern.matcher(text);
        text = matcher.replaceAll("");
        FleschKincaidReadingEase fleschKincaidReadingEase1 = new FleschKincaidReadingEase(text);
        System.out.println("ReadingEaseNL: "+fleschKincaidReadingEase1.getReadingEase());
    }

    public static double  getStopWord(String text){
        int count = 0;
        int i = 0;
        String fileName = "C:\\Users\\GT\\Desktop\\experiment\\Tools Items for Replication\\Stop words.txt";
        String stopWords = ContentLoader.loadFileContent(fileName);
        String[] stopWord =  stopWords.split("\n");
        //System.out.println(text);
        ArrayList<String> words = new ArrayList();
        BreakIterator boundary = BreakIterator.getWordInstance(Locale.UK);
        boundary.setText(text);
        int start = boundary.first();
        for(int end = boundary.next(); end != -1; end = boundary.next()) {
            String word = text.substring(start, end);
            if(!word.trim().isEmpty()){
                words.add(word);
                 //System.out.println(word);
            }
            start = end;
        }
        for (int j=0;j<stopWord.length;j++){
            for (int k =0;k<words.size();k++)
            if(stopWord[j].equals(words.get(k))){
                //System.out.println(words.get(k));
                count++;
                break;
            }
        }
        return (double) count/(double) words.size();
    }


    public static void getCodeElement(String text){
        Pattern pattern = Pattern.compile("`{3}[\\S+\\s+]+`{3}|`[\\S+]+`|`[\\S+\\s+]+`");
        Matcher matcher = pattern.matcher(text);

        String str="";
        if (matcher.find()) {
            for (int i = 0; i <= matcher.groupCount(); i++) {
                str = str+matcher.group(i);
            }
        }
        test provider = new test(text);
        test provider1 = new test(str);
        double textwordcount = getWordsCount(text);
        double strwordcount = getWordsCount(str);
        System.out.println(strwordcount/textwordcount);
        System.out.println((float) provider1.getCharCount()/(float) provider.getCharCount());
        System.out.println((float) provider1.getWordCount()/(float) provider.getWordCount());


    }

    public static String[] getTotalSentences(String text){
        ArrayList<String> sentences = new ArrayList();
        BreakIterator boundary = BreakIterator.getSentenceInstance(Locale.UK);
        boundary.setText(text);
        int start = boundary.first();
        for(int end = boundary.next(); end != -1; end = boundary.next()) {
            String sentence = text.substring(start, end);
            sentences.add(sentence);
            //System.out.println(sentence);
            start = end;
        }
        return sentences.toArray(new String[sentences.size()]);
    }
    public static int getWordsCount(String text){
        int WordsCount = 0;
        String[] Sentences  =getTotalSentences(text);
        for(int i=0;i<Sentences.length;i++) {
            String[] Words = Sentences[i].split("\\p{Punct}+|\\s+");
            for(int j = 0;j <Words.length;j++){
                if(!Words[j].trim().isEmpty()) {
                    WordsCount++;
                    //System.out.println(j+": "+Words[j]);
                }
            }
        }
        //System.out.println("WordsCount"+WordsCount);
        return WordsCount;
    }
    public static int getSentencesCharCount(String text){
        int CharCount = 0;
        String[] words = text.split("\\s+|\\p{Punct}+");
        for(int i=0;i<words.length;i++){
           CharCount += words[i].trim().length();
        }
        return CharCount;
    }
    public static int getSentencesSyllableCount(String text){
        int SyllableCount = 0;
         String[] sentences = getTotalSentences(text);
        int syllableCount = 0;
        String[] var6 = sentences;
        int var5 = sentences.length;

        for(int var4 = 0; var4 < var5; ++var4) {
            String sentence = var6[var4];
            String[] words = sentence.split("\\p{Punct}+|\\s+");
            String[] var11 = words;
            int var10 = words.length;

            for(int var9 = 0; var9 < var10; ++var9) {
                String word = var11[var9];
                int count = getSyllableInWord(word);
                syllableCount += count;
            }
        }

        return syllableCount;
    }
    public static int getSyllableInWord(String word) {
        char[] vowels = new char[]{'a', 'e', 'i', 'o', 'u', 'y'};
        word = word.toLowerCase();
        int numVowels = 0;
        boolean lastWasVowel = false;
        char[] var8;
        int var7 = (var8 = word.toCharArray()).length;//单词的长度

        for(int var6 = 0; var6 < var7; ++var6) {
            char wc = var8[var6];//单词的每个字符
            boolean foundVowel = false;
            char[] var13 = vowels;
            int var12 = vowels.length;

            for(int var11 = 0; var11 < var12; ++var11) {
                char v = var13[var11];
                if (v == wc && lastWasVowel) {
                    foundVowel = true;
                    lastWasVowel = true;
                    break;
                }

                if (v == wc && !lastWasVowel) {
                    ++numVowels;
                    foundVowel = true;
                    lastWasVowel = true;
                    break;
                }
            }

            if (!foundVowel) {
                lastWasVowel = false;
            }
        }

        if (word.length() > 2 && word.endsWith("es")) {
            --numVowels;
        } else if (word.length() > 1 && word.endsWith("e")) {
            --numVowels;
        }


        return numVowels;
    }

    public static double getReadingEaseTest(String text){
        //计算句子数
        double sentenceCount  = (double)getTotalSentences(text).length;
        //计算单词数
        double WordsCount =  (double)getWordsCount(text);
        //计算音节
        int totalSyllableCount = getSentencesSyllableCount(text);
        Double ReadingEase = 206.835D - 1.015D * (WordsCount / sentenceCount) - 84.6D * (totalSyllableCount / WordsCount);
        return ReadingEase;
    }
}

