package com.slothink.find_disk;

/**
 * Created with IntelliJ IDEA.
 * User: slothink
 * Date: 13. 8. 26
 * Time: 오후 5:19
 * To change this template use File | Settings | File Templates.
 */
public class ParseRequest {
    public String file;
    public String diskName;

    public ParseRequest() {

    }

    public ParseRequest(String file, String diskName) {
        this.file = file;
        this.diskName = diskName;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getDiskName() {
        return diskName;
    }

    public void setDiskName(String diskName) {
        this.diskName = diskName;
    }
}
