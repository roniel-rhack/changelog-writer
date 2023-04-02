package dev.roniel.models;

import lombok.Data;

import java.util.List;

@Data
public class Changelog {
    private String title;
    private String description;
    private List<Version> versions;

    public void addVersion(Version version) {
        versions.add(version);
    }
}
