package dev.roniel.models;

import lombok.Data;

import java.util.LinkedList;

@Data
public class Changelog {
    private String title;
    private String description;
    private LinkedList<Version> versions;

    public void addVersion(Version version) {
        versions.add(0, version);
    }
}
