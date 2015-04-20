package com.slothink.find_disk;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: slothink
 * Date: 13. 8. 26
 * Time: 오후 5:10
 * To change this template use File | Settings | File Templates.
 */
public class Launcher {
    public static void main(String[] args) {

        print("+=========================================================+");
        print("+ Find Disk Alias Utility                                 +");
        print("+ @author Hyunjang, Pyun (kernel@comas.co.kr)             +");
        print("+ @owner Byeongi, Kim (bkkim@comas.co.kr)                 +");
        print("+ Usage : if you want to exit then input ^C               +");
        print("+   ./find-disk.sh            : prompt file and disk name +");
        print("+   ./find-disk.sh [log file]          : prompt disk name +");
        print("+       ex) ./find-disk.sh /tmp/disk.log                  +");
        print("+   ./find-disk.sh [log file] [disk name]     : no prompt +");
        print("+       ex) ./find-disk.sh /tmp/disk.log sda              +");
        print("+=========================================================+");

        ParseRequest request = new ParseRequest();
        if(args.length >= 1) {
            print("Input a read file full path: "+args[0]);
            request.setFile(args[0]);
            if(args.length == 2) {
                print("Input find a disk name: "+args[1]);
                request.setDiskName(args[1]);
            }else {
                String diskName = command("Input find a disk name: ");
                request.setDiskName(diskName);
            }
        }else {
            String file = command("Input a read file full path: ");
            request.setFile(file);
            String diskName = command("Input find a disk name: ");
            request.setDiskName(diskName);
        }

        print("");
        CommandParser parser = new CommandParser();
        List<Volumn> volumns = null;
        try {
            volumns = parser.findVolune(request);
            if(volumns != null) {
                print("Found "+volumns.size() + " results.");
                for(Volumn volumn : volumns) {
                    print(volumn.getName());
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static String command(String command) {
        System.out.print(command);
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String line = null;
        try {
            line = input.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return line;
    }

    public static void print(String o) {
        System.out.println(o);
    }
}
