package dev.roniel.services;

import com.vladsch.flexmark.ast.BulletList;
import com.vladsch.flexmark.ast.BulletListItem;
import com.vladsch.flexmark.ast.Heading;
import com.vladsch.flexmark.ast.Paragraph;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import dev.roniel.config.ChangelogConfig;
import dev.roniel.models.Change;
import dev.roniel.models.Changelog;
import dev.roniel.models.Version;
import dev.roniel.utils.ChangeType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Service
public class ChangelogService {

    public static final String LINE_BREAK = "\n";
    private final ChangelogConfig changelogConfig;

    public String getChangelogFile() throws IOException {
        return Files.readString(Path.of(changelogConfig.getReadPath()), StandardCharsets.UTF_8);
    }

    public Changelog parseChangelog() throws IOException {
        String content = Files.readString(Path.of(changelogConfig.getReadPath()), StandardCharsets.UTF_8);
        Parser parser = Parser.builder().build();
        Node document = parser.parse(content);

        Changelog changelog = new Changelog();
        LinkedList<Version> versions = new LinkedList<>();
        Version currentVersion = null;
        ChangeType currentChangeType = null;

        for (Node node : document.getChildren()) {
            if (node instanceof Heading node1) {
                String headingText = node1.getText().toString();
                if (node1.getLevel() == 1) {
                    changelog.setTitle(headingText);
                } else {
                    if (isVersionHeading(headingText)) {
                        if (currentVersion != null) {
                            versions.add(currentVersion);
                        }
                        currentVersion = parseVersion(headingText);
                    } else {
                        currentChangeType = parseChangeType(headingText);
                    }
                }
            } else if (node instanceof Paragraph node1) {
                String changelogDescription = changelog.getDescription();
                String description1 = changelogDescription != null && !changelogDescription.equals(
                        "") ? changelogDescription + LINE_BREAK : "";
                String description = description1 + node1.getChars();
                changelog.setDescription(description);
            } else if (node instanceof BulletList && (currentVersion != null && currentChangeType != null)) {
                for (Node item : node.getChildren()) {
                    if (item instanceof BulletListItem) {
                        BasedSequence listItemText = item.getFirstChild().getChars();
                        Change change = parseChange(listItemText, currentChangeType);
                        currentVersion.getChanges().add(change);
                    }
                }
            }
        }

        if (currentVersion != null) {
            versions.add(currentVersion);
        }

        changelog.setVersions(versions);
        return changelog;
    }

    private ChangeType parseChangeType(String heading) {
        try {
            return ChangeType.valueOf(heading.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private Change parseChange(BasedSequence listItemText, ChangeType changeType) {
        String description = listItemText.toString().trim();
        String usNumber = parseUSNumber(description);

        Change change = new Change();
        change.setType(changeType);
        change.setDescription(description);
        change.setUsNumber(usNumber);
        return change;
    }

    private boolean isVersionHeading(String heading) {
        return heading.startsWith("[") && heading.contains("]");
    }

    private Version parseVersion(String heading) {
        Pattern versionPattern = Pattern.compile("\\[(.+?)]");
        Matcher versionMatcher = versionPattern.matcher(heading);
        if (versionMatcher.find()) {
            String version = versionMatcher.group(1);
            LocalDate releaseDate = parseReleaseDate(heading);

            Version changelogVersion = new Version();
            changelogVersion.setVersion(version);
            changelogVersion.setReleaseDate(releaseDate);
            changelogVersion.setChanges(new ArrayList<>());
            return changelogVersion;
        }
        return null;
    }

    private LocalDate parseReleaseDate(String heading) {
        Pattern datePattern = Pattern.compile("- (\\d{4}-\\d{2}-\\d{2})");
        Matcher dateMatcher = datePattern.matcher(heading);
        if (dateMatcher.find()) {
            String dateString = dateMatcher.group(1);
            return LocalDate.parse(dateString, DateTimeFormatter.ISO_DATE);
        }
        return null;
    }

    private String parseUSNumber(String description) {
        Pattern issuePattern = Pattern.compile("\\((.+)\\)\\.?\\s*$");
        Matcher issueMatcher = issuePattern.matcher(description);
        if (issueMatcher.find()) {
            return issueMatcher.group(1);
        }
        return null;
    }

    public void writeChangelog(Changelog changelog) throws IOException {

        StringBuilder sb = new StringBuilder();
        sb.append("# ")
                .append(changelog.getTitle())
                .append(LINE_BREAK)
                .append(changelog.getDescription())
                .append(LINE_BREAK);
        for (Version version : changelog.getVersions()) {
            sb.append("## [").append(version.getVersion()).append("] - ").append(version.getReleaseDate()).append(
                    LINE_BREAK);
            for (ChangeType changeType : ChangeType.values()) {
                List<Change> changesOfType = filterChangesByType(version.getChanges(), changeType);
                if (!changesOfType.isEmpty()) {
                    sb.append("### ").append(changeType).append(LINE_BREAK);
                    for (Change change : changesOfType) {
                        sb.append("- ").append(change.getDescription());
                        if (change.getUsNumber() != null && !"".equals(
                                change.getUsNumber()) && !change.getDescription().contains("US")) {
                            sb.append(" (").append(change.getUsNumber()).append(")");
                        }
                        sb.append(LINE_BREAK);
                    }
                }
            }
            sb.append(LINE_BREAK);
        }

        Files.writeString(Path.of(changelogConfig.getWritePath()), sb.toString(), StandardCharsets.UTF_8,
                StandardOpenOption.TRUNCATE_EXISTING);
    }

    private List<Change> filterChangesByType(List<Change> changes, ChangeType changeType) {
        List<Change> filteredChanges = new ArrayList<>();
        for (Change change : changes) {
            if (change.getType() == changeType) {
                filteredChanges.add(change);
            }
        }
        return filteredChanges;
    }

    public void addVersion(Version version) throws IOException {
        Changelog changelog = parseChangelog();

        changelog.addVersion(version);

        writeChangelog(changelog);
    }
}

