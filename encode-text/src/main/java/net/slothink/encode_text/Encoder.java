package net.slothink.encode_text;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

/**
 * Created by slothink on 2014. 8. 4..
 */
public class Encoder {

    public String encode(String source, String encoding) {

        return null;
    }

    public String encode(File sourceFile, String encoding) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(32);
        Charset charset = Charset.forName(encoding);
        FileInputStream fis = new FileInputStream(sourceFile);
        FileChannel fileChannel = fis.getChannel();
        fileChannel.read(byteBuffer);
    }
}
