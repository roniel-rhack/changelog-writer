import {Component} from '@angular/core';
import {ChangelogService} from "../services/changelog.service";
import {FormArray, FormBuilder, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'app-changelog-create',
  templateUrl: './changelog-create.component.html',
  styleUrls: ['./changelog-create.component.css']
})
export class ChangelogCreateComponent {

  form: FormGroup;

  constructor(private fb: FormBuilder, private changelogService: ChangelogService) {
    this.form = this.fb.group({
      version: ['', Validators.required],
      releaseDate: ['', Validators.required],
      changes: this.fb.array([])
    });
  }

  get changes(): FormArray {
    return this.form.get('changes') as FormArray;
  }

  addChange() {
    this.changes.push(this.fb.group({
      type: ['', Validators.required],
      description: ['', Validators.required],
      usNumber: ['']
    }));
  }

  removeChange(index: number) {
    this.changes.removeAt(index);
  }

  onSubmit() {
    if (this.form.valid) {
      this.changelogService.addVersion(this.form.value).subscribe(response => {
        // Handle success
        console.log('Version added:', response);
      }, error => {
        // Handle error
        console.error('Error adding version:', error);
      });
    }
  }
}
