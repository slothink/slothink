package test;

import org.junit.Test;

import java.util.Date;
import java.util.List;

/**
 * Created by slothink on 14. 4. 9..
 */
public class UnionWordTest {

    @Test
    public void test() {
        Automata34 automata34 = new Automata34();
        UnionWord unionWord = new UnionWord(automata34, null);
        String input = "nhmaisej";
        Date start = new Date();
        List<String> words = unionWord.makeLikeWords(input);
        Date end = new Date();

        p("makes:"+words.size());
        p("cost:"+(end.getTime()-start.getTime()));


    }

    public void p(Object o) {
        System.out.println(o);
    }
}
