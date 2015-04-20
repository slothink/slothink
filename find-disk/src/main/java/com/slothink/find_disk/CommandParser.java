package com.slothink.find_disk;


import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: slothink
 * Date: 13. 8. 26
 * Time: 오후 5:18
 * To change this template use File | Settings | File Templates.
 */
public class CommandParser {

    public enum Step {
        VOLUNE, PROPERTY, POLICY, DISK
    }

    public List<Volumn> findVolune(ParseRequest request) throws IOException {
        File file = new File(request.getFile());
        Step step = Step.VOLUNE;
        List<String> lines = null;

        List<Volumn> volumns = new ArrayList<Volumn>();


        try {
            lines = FileUtils.readLines(file);
        } catch (IOException e) {
            throw e;
        }

        Volumn volumn = null;
        for(String line : lines) {
            if(step == Step.DISK) {
                if(line.startsWith(" ")) {
                    String[] frags = StringUtils.split(line, " ");
                    String diskName = frags[2];
                    volumn.getDisks().add(diskName);
                }else {
                    step = Step.VOLUNE;
                }
            }
            if(step == Step.PROPERTY) {
                String[] pairs = StringUtils.split(line, " ");
                for(String pair :  pairs) {
                    String[] frags = StringUtils.split(pair, "=");
                    if(frags.length == 2) {
                        volumn.getProperty().put(frags[0], frags[1]);
                    }
                }
                step = Step.POLICY;
            }else if(step == Step.POLICY) {
                step = Step.DISK;
            }else if(step == Step.VOLUNE) {
                if(volumn != null) {
                    volumns.add(volumn);
                    volumn = null;
                }
                if(!StringUtils.isBlank(line)) {
                    volumn = new Volumn();
                    String[] frags = StringUtils.split(line, " ");
                    volumn.setName(frags[0]);
                    step = Step.PROPERTY;
                }
            }
        }
        if(volumn != null) {
            volumns.add(volumn);
        }

        List<Volumn> result = new ArrayList<Volumn>();
        for(Volumn inVolumn : volumns) {
            if(inVolumn.getDisks().contains(request.getDiskName())) {
                result.add(inVolumn);
            }
        }
        return result;
    }
}
