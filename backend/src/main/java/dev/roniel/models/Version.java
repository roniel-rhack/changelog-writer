package dev.roniel.models;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class Version {
    private String version;
    private LocalDate releaseDate;
    private List<Change> changes;
}
