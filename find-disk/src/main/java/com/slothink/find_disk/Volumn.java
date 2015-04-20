package com.slothink.find_disk;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: slothink
 * Date: 13. 8. 26
 * Time: 오후 5:23
 * To change this template use File | Settings | File Templates.
 */
public class Volumn {
    private String name;
    private Map<String, String> property = new LinkedHashMap<String, String>();
    private Set<String> disks = new LinkedHashSet<String>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getProperty() {
        return property;
    }

    public void setProperty(Map<String, String> property) {
        this.property = property;
    }

    public Set<String> getDisks() {
        return disks;
    }

    public void setDisks(Set<String> disks) {
        this.disks = disks;
    }

    @Override
    public String toString() {
        return "Volumn{" +
                "name='" + name + '\'' +
                ", property=" + property +
                ", disks=" + disks +
                '}';
    }
}
