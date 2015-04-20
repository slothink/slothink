package com.slothink.increase_visit;

import org.apache.http.message.BasicHeader;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by slothink on 15. 3. 17..
 */
public class ClientTest {

    @Test
    public void test() throws IOException {
        Client client = new Client("http://search.daum.net/search?");

        client.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        client.addHeader("Accept-Encoding", "gzip, deflate, sdch");
        client.addHeader("Accept-Language", "ko-KR,ko;q=0.8,en-US;q=0.6,en;q=0.4");
        client.addHeader("Connection", "keep-alive");
        client.addHeader("Cookie", "AGEN=XK-7ZmkY1H5dQDIz_J7_Ydb0Z_6XPW8F8jDiuOL1f_o; SLEVEL=1; simpleCaptcha=4ndgd; TMSGROUPCODE=MOV; uvkey=VQeyPG4t1EcAAB4sEKMAAAAr; ODT=IIMZ_VO2Z_NNSZ_BR1Z_CCBZ_FGKZ_WSAZ_; DDT=NKSZ_BRDZ_IVRZ_DICZ_GG1Z_MS2Z_1DVZ_SNPZ_; DTQUERY=; TIARA=4P_jPmyoTK_8.9ZlpNYHgknwXQHuFj4sfb7B9vsXgtZvfZyF5jQH9wHDN1gX64IiM-u2saKGt.yeIDb8vDGDuxwknYtZYAdl");
        client.addHeader("Host", "search.daum.net");
        client.addHeader("Referer", "http://www.daum.net/");
        client.addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2272.89 Safari/537.36");

        client.addParameter("w", "tot");
        client.addParameter("q", "류현진 선발");
        client.addParameter("DA", "NPI");

        String content = client.get();
        System.out.println(content);
    }

}
