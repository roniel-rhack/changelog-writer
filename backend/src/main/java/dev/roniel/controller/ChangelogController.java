package dev.roniel.controller;

import dev.roniel.models.Changelog;
import dev.roniel.models.Version;
import dev.roniel.services.ChangelogService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@CrossOrigin(origins = {"${app.dev.frontend.local}"})
@RequiredArgsConstructor
@RestController
public class ChangelogController {

    private final ChangelogService changelogService;

    @PostMapping(value = "/changelog/add")
    public ResponseEntity<Changelog> getChangelogPage(@RequestBody Entry entry) throws IOException {

        Changelog changelog = changelogService.parseChangelog();
        changelogService.writeChangelog(changelog);
        return ResponseEntity.ok(changelog);
    }

    @PostMapping(value = "/changelog")
    public ResponseEntity<HttpStatus> addChangelogVersion(@RequestBody Version version) throws IOException {
        changelogService.addVersion(version);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping(value = "/changelog")
    public ResponseEntity<Changelog> getChangelog() throws IOException {
        return ResponseEntity.ok(changelogService.parseChangelog());
    }

    @Data
    static class Entry {
        private String entry;
    }
}
