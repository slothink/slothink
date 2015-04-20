package test;


import java.util.*;

public class UnionWord {
    private Automata34 automata34;
    private Set<String>	wordpinyin;
    private Map<String, Set<String>> likeKeyMap = new HashMap<String, Set<String>>();

    public UnionWord(Automata34 automata34, Set<String> wordpinyins) {
        this.automata34 = automata34;
        this.wordpinyin = wordpinyins;

        this.initKeyMap("a", "b", "c");
        this.initKeyMap("d", "e", "f");
        this.initKeyMap("g", "h", "i");
        this.initKeyMap("j", "k", "l");
        this.initKeyMap("m", "n", "o");
        this.initKeyMap("p", "q", "r", "s");
        this.initKeyMap("t", "u", "v");
        this.initKeyMap("w", "x", "y", "z");
        this.initKeyMap("b", "c","a");
        this.initKeyMap("c", "a", "b");
        this.initKeyMap("e", "f", "d");
        this.initKeyMap("f", "d", "e");
        this.initKeyMap("h", "i", "g");
        this.initKeyMap("i", "g", "h");
        this.initKeyMap("k", "l", "j");
        this.initKeyMap("l", "j", "k");
        this.initKeyMap("n", "o", "m");
        this.initKeyMap("o", "m", "n");
        this.initKeyMap("q", "r", "s", "p");
        this.initKeyMap("r", "s", "p", "q");
        this.initKeyMap("s", "p", "q", "r");
        this.initKeyMap("u", "v", "t");
        this.initKeyMap("v", "t", "u");
        this.initKeyMap("x", "y","z", "w");
        this.initKeyMap("y", "z", "w", "x");
        this.initKeyMap("z", "w", "x", "y");
    }

    private void initKeyMap(String key, String... likeKeys){
        Set<String>	likeKeySet = new HashSet<String>();
        for(String likeKey : likeKeys){
            likeKeySet.add(likeKey);
        }
        this.likeKeyMap.put(key, likeKeySet);
    }

    public List<String> suggest(String comString){

        List<String> removeDouble = new ArrayList<String>();
        List<String> matchList = new ArrayList<String>();
        List<String> unmatchList = new ArrayList<String>();
        List<String> unmatchTemp = new ArrayList<String>();
        List<String> result = new ArrayList<String>();

        List<String> likeWords = this.makeLikeWords(comString);

        if (comString.length() == 1) {
            likeWords.add(0, "EMPTY");
            return likeWords;
        } else {

            for (String likeWord : likeWords) {
                if ((wordpinyin.contains(likeWord) == true)
                        && (likeWord.length() != 1)
                        && (likeWord.charAt(0) != 'v')
                        && (likeWord.charAt(0) != 'i')
                        && (likeWord.charAt(0) != 'u'))
                    matchList.add(likeWord);
                else {
                    unmatchTemp.add(likeWord);
                }
            }

            if (matchList.size() == 0) { // match 하는게 없으면
                for (String likeWord : unmatchTemp) {
                    String temp = automata34.cut(likeWord); // 자른 후
                    unmatchList.add(temp);
                }
                unmatchList.add(0, "UNMATCH");
            } else {

                for( String s : matchList){
                    removeDouble.add(s);
                    s = automata34.cut(s);
                    String[] size = s.split(" ");
                    for(int i=0; i<size.length-2; i++){
                        if((s.contains(" ")) && (s.indexOf(" ") != s.lastIndexOf(" "))){
                            s = s.substring(0, s.lastIndexOf(" "));
                            s = s.replace(" ", "");
                            removeDouble.add(s);
                        }
                    }

                }
                removeDouble.add(0, "MATCH");
            }

            if ((unmatchList.size() > 0) && (unmatchList.get(0) == "UNMATCH")) {
                result.add(unmatchList.get(0));
                result.add(unmatchList.get(1));

                // sorting
                for (int i = 2; i < unmatchList.size(); i++) {
                    for (int j = 1; j < i; j++) {
                        if (unmatchList.get(i).length() < result.get(j)
                                .length()) {
                            result.add(j, unmatchList.get(i));
                            j = i - 1;
                        }
                    }
                    if (result.size() == i) {
                        result.add(unmatchList.get(i));
                    }
                }
                int firstLenght = result.get(1).length();
                removeDouble.clear();
                removeDouble.add(result.get(0));
                removeDouble.add(result.get(1));
                for (int i = 2; i < result.size(); i++) {
                    if (firstLenght == result.get(i).length())
                        removeDouble.add(result.get(i));
                }

            }
            return removeDouble;
        }
    }

    List<String> makeLikeWords(String inputWords) {		//map에서 문자 가져옴.
        Date time1 = new Date();
        List<List<Character>> cases = new ArrayList<List<Character>>();
        for(int i=0; i<inputWords.length(); i++){
            String originalChar = String.valueOf(inputWords.charAt(i));
            List<Character> casesPerChar = new ArrayList<Character>();
            if(originalChar.matches(" ")==false){
                casesPerChar.add(originalChar.toCharArray()[0]);
                Set<String> mapped = this.likeKeyMap.get(originalChar);
                if(mapped != null){
                    for(String mappedChar : mapped) {
                        casesPerChar.add(mappedChar.toCharArray()[0]);
                    }

                }
                cases.add(casesPerChar);
            }
        }
        Date time2 = new Date();
        System.out.println("time1-time2:"+(time2.getTime()-time1.getTime()));

        // 모든 경우 문자열 만듬
        List<char[]> makeStrings = this.multiplyRightStrings(cases, 0, cases.size()-1);
        Date time3 = new Date();
        System.out.println("time2-time3:"+(time3.getTime()-time2.getTime()));
        List<String> result = new ArrayList<String>();
        for(char[] string : makeStrings) {
            result.add(new String(string));
        }
        Date time4 = new Date();
        System.out.println("time3-time4:"+(time4.getTime()-time3.getTime()));

        return result;
    }

    private List<char[]> multiplyRightStrings(List<List<Character>> source, int currentIndex, int endIndex) {
        List<char[]> result = new ArrayList<char[]>();
        CharArrayBoard charArrayBoard = new CharArrayBoard();
        if(currentIndex < endIndex){
            for(Character myChar : source.get(currentIndex)){
                List<char[]> rightStrings = this.multiplyRightStrings(source, currentIndex+1, endIndex);
                for(char[] rightString : rightStrings) {
                    //StringBuffer buffer = new StringBuffer();
                    //buffer.append(myChar);
                    //buffer.append(rightString);
                    char[] sum = new char[rightString.length+1];
                    sum[0] = myChar;
                    for(int i = 0; i < rightString.length; i++) {
                        sum[i+1] = rightString[i];
                    }
                    result.add(sum);
                    //result.add(buffer.toString());
                }
                rightStrings.clear();
            }
        }else if(currentIndex == endIndex){
            for(Character myChar : source.get(currentIndex)) {
                result.add(new char[]{myChar});
            }
        }

        return result;
    }


}
