package test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by slothink on 14. 4. 12..
 */
public class CharArrayList {
    private List<char[]> data = new ArrayList<char[]>();
    private int lastInputStartIndex = 0;
    private int lastInputEndIndex = 0;

    public List<char[]> getData() {
        return data;
    }

    public void setData(List<char[]> data) {
        this.data = data;
    }

    public int getLastInputStartIndex() {
        return lastInputStartIndex;
    }

    public void setLastInputStartIndex(int lastInputStartIndex) {
        this.lastInputStartIndex = lastInputStartIndex;
    }

    public int getLastInputEndIndex() {
        return lastInputEndIndex;
    }

    public void setLastInputEndIndex(int lastInputEndIndex) {
        this.lastInputEndIndex = lastInputEndIndex;
    }
}
