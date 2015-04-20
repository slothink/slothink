package net.slothink.encode_text;

import org.junit.Test;

import java.nio.charset.Charset;

/**
 * Created by slothink on 2014. 8. 5..
 */
public class EncoderTest {

    @Test
    public void test() {
        Encoder encoder = new Encoder();
        encoder.encode("이것은 한글입니다".getBytes(Charset.forName("euc-kr")))
    }
}
