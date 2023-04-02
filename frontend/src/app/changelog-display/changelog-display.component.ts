import {Component, OnInit} from '@angular/core';
import {ChangelogService} from '../services/changelog.service';

@Component({
  selector: 'app-changelog-display',
  templateUrl: './changelog-display.component.html',
  styleUrls: ['./changelog-display.component.scss']
})
export class ChangelogDisplayComponent implements OnInit {
  changelogMarkdown: string;

  constructor(private changelogService: ChangelogService) {
  }

  ngOnInit(): void {
    this.loadChangelog();
  }

  loadChangelog(): void {
    this.changelogService.loadChangelog().subscribe(changelog => {
      this.changelogMarkdown = changelog;
    });
  }
}
